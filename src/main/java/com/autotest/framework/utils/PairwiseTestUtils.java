package com.autotest.framework.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.setting.yaml.YamlUtil;
import com.alibaba.fastjson2.JSON;
import com.autotest.framework.antlr.constraint.core.ConstraintsLexer;
import com.autotest.framework.antlr.constraint.core.ConstraintsParser;
import com.autotest.framework.antlr.constraint.visitor.ConstraintsVisitor;
import com.autotest.framework.builder.BaseAutoCaseBuilder;
import com.autotest.framework.common.entities.autotest.FieldDefine;
import com.autotest.framework.common.entities.autotest.ModelDataDefine;
import com.autotest.framework.context.UserTestContext;
import com.autotest.framework.nodes.StepNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
public class PairwiseTestUtils {
    public static final String SEEDS = "QWERTYUIOPASDFGHJKLZXCVBNM_qwertyuiopasdfghjklzxcvbnm_1234567890_中文";
    public static final String PICT_FOLDER_DIR = System.getProperty("user.home") + "/.pict/";
    protected static final Map<String, FieldDefine> FIELD_DEFINE_MAP = new HashMap<>();
    public static final String EXPECTED_RESULT = "__EXPECTED_RESULT";
    public static final String REPEAT_RESULT = "__REPEAT_RESULT";
    public static final String CASE_DESCRIPTION = "__CASE_DESCRIPTION";
    public static final String PARAMETER_TYPE_ARRAY = "array";
    public static final String PARAMETER_TYPE_OBJECT = "object";
    public static final String PARAMETER_TYPE_STRING = "string";
    public static final String PARAMETER_TYPE_INTEGER = "integer";
    public static final String PARAMETER_VALUE_NON_NULL = "~NULL";
    public static final String PARAMETER_VALUE_NON_EMPTY = "~EMPTY";
    public static final String PARAMETER_VALUE_NON_MAXLENGTH_PLUS = "~MAXLENGTH_PLUS";
    public static final String PARAMETER_VALUE_NON_MINLENGTH_SUB = "~MINLENGTH_SUB";
    public static final String PARAMETER_VALUE_NON_MAX_PLUS = "~MAX_PLUS";
    public static final String PARAMETER_VALUE_NON_MIN_SUB = "~MIN_SUB";
    public static final String PARAMETER_VALUE_NON_ENUM_INVALID = "~ENUM_INVALID";

    public static final String RANDOM_STRING_PY_PATH = getFilePathWithName("random_string.py");

    private PairwiseTestUtils() {

    }

    public static List<LinkedHashMap<String, String>> generateTestGroups(String apiDefineYmlName) throws IOException, InterruptedException, TimeoutException {
        Dict dict = YamlUtil.loadByPath(getFilePathWithName(apiDefineYmlName));
        Map<String, List<String>> fieldValuesMap = new LinkedHashMap<>();

        generateFieldValues(dict, fieldValuesMap, "", "");

        List<String> requiredFields = new ArrayList<>();
        fieldValuesMap.forEach((k, v) -> {
            if(v.contains(PARAMETER_VALUE_NON_NULL) || v.contains(PARAMETER_VALUE_NON_EMPTY)) {
                requiredFields.add(k);
            }
        });

        log.info("参数的取值: {}", JSON.toJSONString(fieldValuesMap));
        List<String> fieldNames = new ArrayList<>();
        fieldValuesMap.forEach((k, v) -> fieldNames.add(k));

        StringBuilder builder1 = new StringBuilder();
        fieldValuesMap.forEach((k, v) -> {
            if(requiredFields.contains(k)) {
                List<Object> values = new ArrayList<>();
                for (String o : v) {
                    if(!StringUtils.equals(PARAMETER_VALUE_NON_NULL, o) && !StringUtils.equals(PARAMETER_VALUE_NON_EMPTY, o)
                            && !StringUtils.equals(PARAMETER_VALUE_NON_MAXLENGTH_PLUS, o)
                            && !StringUtils.equals(PARAMETER_VALUE_NON_MINLENGTH_SUB, o)
                            && !StringUtils.equals(PARAMETER_VALUE_NON_MAX_PLUS, o)
                            && !StringUtils.equals(PARAMETER_VALUE_NON_MIN_SUB, o)
                    ) {
                        values.add(o);
                    }
                }
                builder1.append(k).append(": ").append(StringUtils.join(values, ",")).append("\r\n");
            } else {
                builder1.append(k).append(": ").append(StringUtils.join(v, ",")).append("\r\n");
            }
        });

        String fileName = "complete_" + apiDefineYmlName.replaceAll(".yml", ".txt");
        // 生成全量组合
        final List<LinkedHashMap<String, String>> completeGroups = getPictGroups(fileName, builder1, fieldNames, "2");
        log.info("全量组合大小: {}", completeGroups.size());

        StringBuilder builder2 = new StringBuilder();
        fieldValuesMap.forEach((k, v) -> builder2.append(k).append(": ").append(StringUtils.join(v, ",")).append("\r\n"));
        // 处理约束
        List<String> constraints = dict.getByPath("constraints");
        if(constraints != null && !constraints.isEmpty()) {
            builder2.append("\r\n");
            for (String constraint : constraints) {
                builder2.append(constraint).append(";\r\n");
            }
        }

        fileName = apiDefineYmlName.replaceAll(".yml", ".txt");
        List<LinkedHashMap<String, String>> distinctList = getPictGroups(fileName, builder2, fieldNames, "1");

        for (final String key : fieldNames) {
            distinctList = distinctList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> {
                                String value = ((Map<String, String>) m).get(key);
                                if (value.startsWith("~")) {
                                    return "excluded";
                                }
                                return RandomUtil.randomString(32);
                            }))),
                            ArrayList::new
                    ));
        }

        List<LinkedHashMap<String, String>> constraintCounterexampleList = new ArrayList<>();

        if(constraints != null && !constraints.isEmpty()) {
            for (LinkedHashMap<String, String> group : completeGroups) {
                for (int i = 0; i < constraints.size(); i++) {
                    String constraint = constraints.get(i);
                    int trueCount = 0;
                    int currFalseCount = 0;
                    int otherFalseCount = 0;
                    int notSatisfiedIf = 0;
                    CharStream input = CharStreams.fromString(constraint);
                    ConstraintsLexer lexer = new ConstraintsLexer(input);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    ConstraintsParser parser = new ConstraintsParser(tokens);
                    ConstraintsParser.ExpressionContext context = parser.expression();
                    ConstraintsVisitor visitor = new ConstraintsVisitor(group);
                    try {
                        final Boolean visitResult = (Boolean) visitor.visit(context);
                        if(Boolean.TRUE.equals(visitResult)) {
                            trueCount++;
                        } else {
                            currFalseCount++;
                        }
                    } catch (Exception e) {
                        log.trace(e.getMessage());
                        notSatisfiedIf++;
                    }
                    if(currFalseCount < 1) {
                        continue;
                    }

                    for (int j = 0; j < constraints.size(); j++) {
                        try {
                            if(j != i) {
                                input = CharStreams.fromString(constraint);
                                lexer = new ConstraintsLexer(input);
                                tokens = new CommonTokenStream(lexer);
                                parser = new ConstraintsParser(tokens);
                                context = parser.expression();
                                visitor = new ConstraintsVisitor(group);
                                final Boolean visitResult = (Boolean) visitor.visit(context);
                                if(Boolean.TRUE.equals(visitResult)) {
                                    trueCount++;
                                } else {
                                    otherFalseCount++;
                                }
                            }
                        } catch (Exception e) {
                            log.trace(e.getMessage());
                            notSatisfiedIf++;
                        }
                    }
                    if(trueCount + notSatisfiedIf + otherFalseCount == constraints.size() - 1) {
                        group.put(CASE_DESCRIPTION, "违反约束: " + constraint);
                        constraintCounterexampleList.add(group);
                    }
                }
            }
        }
        log.info("约束反例大小1: {}", constraintCounterexampleList.size());
        constraintCounterexampleList = constraintCounterexampleList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Map::hashCode))),
                        ArrayList::new
                ));
        log.info("约束反例大小2: {}", constraintCounterexampleList.size());
        constraintCounterexampleList = constraintCounterexampleList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> {
                            for (String requiredField : requiredFields) {
                                String value = m.get(requiredField);
                                if(StringUtils.equals(value, PARAMETER_VALUE_NON_NULL) || StringUtils.equals(value, PARAMETER_VALUE_NON_EMPTY) || StringUtils.equals(value, PARAMETER_VALUE_NON_ENUM_INVALID)) {
                                    return "excluded";
                                }
                            }
                            return RandomUtil.randomString(32);
                        }))),
                        ArrayList::new
                ));
        log.info("约束反例大小3: {}", constraintCounterexampleList.size());
        log.info("约束反例详情: {}", JSON.toJSONString(constraintCounterexampleList));

        for (final String key : fieldNames) {
            distinctList = distinctList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> {
                                String value = m.get(key);
                                if (value.startsWith("~")) {
                                    return value;
                                }
                                return RandomUtil.randomString(64);
                            }))),
                            ArrayList::new
                    ));
        }

        log.info("组合数: {}", distinctList.size());
        for (LinkedHashMap<String, String> group : distinctList) {
            String expectedResult = "success";
            for (String value : group.values()) {
                if(value.startsWith("~")) {
                    expectedResult = "fail";
                    break;
                }
            }
            group.put(EXPECTED_RESULT, expectedResult);
        }
        for (LinkedHashMap<String, String> group : constraintCounterexampleList) {
            group.put(EXPECTED_RESULT, "fail");
        }
        distinctList.addAll(constraintCounterexampleList);
        distinctList.addAll(constraintCounterexampleList);
        if(dict.getByPath("repeat") != null && StringUtils.isNoneBlank(dict.getByPath("repeat").toString())) {
            for (LinkedHashMap group : distinctList) {
                if (StringUtils.equals(group.get(EXPECTED_RESULT).toString(), "success")) {
                    String jsonString = JSON.toJSONString(group);
                    LinkedHashMap copiedMap = JSON.parseObject(jsonString, LinkedHashMap.class);
                    copiedMap.put(REPEAT_RESULT, dict.getByPath("repeat"));
                    distinctList.add(copiedMap);
                    break;
                }
            }
        }

        return distinctList;
    }

    public static List<LinkedHashMap<String, String>> getPictGroups(String fileName, StringBuilder builder, List<String> fieldNames, String o) throws IOException, InterruptedException, TimeoutException {
        FileUtil.writeBytes(builder.toString().getBytes(StandardCharsets.UTF_8), PICT_FOLDER_DIR + fileName);
        String output = new ProcessExecutor().command(getFilePathWithName("pict.exe"), PICT_FOLDER_DIR + fileName, "-o:"+o)
                .readOutput(true).execute()
                .outputUTF8();
        String[] lines = StringUtils.split(output, "\r\n");
        int lineNo = 0;
        for (int i = 0; i < lines.length; i++) {
            if(lines[i].startsWith(fieldNames.get(0))) {
                lineNo = i;
                break;
            }else{
                log.warn(lines[i]);
            }
        }

        // 用例组合列表
        List<LinkedHashMap<String, String>> groups = new ArrayList<>();

        for (int i = lineNo+1; i < lines.length; i++) {
            String[] values = lines[i].split("\t");
            LinkedHashMap<String, String> row = new LinkedHashMap<>();
            for (int j = 0; j < values.length; j++) {
                row.put(fieldNames.get(j), values[j]);
            }
            groups.add(row);
        }
        return groups;
    }

    public static String getDictPath(String dictPrefixPath, String name) {
        if(StringUtils.isBlank(dictPrefixPath)) {
            return name;
        }
        return dictPrefixPath + "." + name;
    }

    public static void generateFieldValues(Dict dict, Map<String, List<String>> fieldValuesMap, String dictPrefixPath, String fieldPrefixPath) {
        String type = dict.getByPath(getDictPath(dictPrefixPath, "type"));
        if(StringUtils.equals(type, PARAMETER_TYPE_OBJECT)) {
            List<String> requiredFields = dict.getByPath(getDictPath(dictPrefixPath, "required"));
            ((LinkedHashMap) dict.getByPath(getDictPath(dictPrefixPath, "properties"))).forEach((fieldName, fieldProperties) -> {
                LinkedHashMap properties = (LinkedHashMap) fieldProperties;
                if(StringUtils.equals(properties.get("type").toString(), PARAMETER_TYPE_ARRAY)) {
                    String itemType = dict.getByPath(getDictPath(dictPrefixPath, "properties." + fieldName.toString()) + ".items.type");
                    if(!StringUtils.equals(itemType, PARAMETER_TYPE_OBJECT) && !StringUtils.equals(itemType, PARAMETER_TYPE_ARRAY)) {
                        generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties." + fieldName + ".items"),fieldPrefixPath + fieldName + "[0]");
                    }else{
                        generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties." + fieldName + ".items"), fieldPrefixPath + fieldName + "[0].");
                    }
                }else if(StringUtils.equals(properties.get("type").toString(), PARAMETER_TYPE_OBJECT)) {
                    generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties." + fieldName), fieldPrefixPath + fieldName + ".");
                }else{
                    generateField(fieldValuesMap, fieldPrefixPath, requiredFields, fieldName, properties);
                }
            });
        } else if(StringUtils.equals(type, PARAMETER_TYPE_ARRAY)) {
            String itemType = getDictPath(dictPrefixPath, "") + ".items.type";
            if(!StringUtils.equals(itemType, PARAMETER_TYPE_OBJECT) && !StringUtils.equals(itemType, PARAMETER_TYPE_ARRAY)) {
                generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties.items"),"[0]");
            }else{
                generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties.items"), "[0].");
            }
        } else {
            Boolean must = dict.getByPath(getDictPath(dictPrefixPath, "must"));
            if(must != null && must) {
                generateField(fieldValuesMap, "", Collections.singletonList(fieldPrefixPath), fieldPrefixPath, dict.getByPath(dictPrefixPath));
            }else{
                generateField(fieldValuesMap, "", Collections.emptyList(), fieldPrefixPath, dict.getByPath(dictPrefixPath));
            }
        }
    }

    public static void generateField(Map<String, List<String>> fieldValuesMap, String fieldPrefixPath, List<String> requiredFields, Object fieldName, LinkedHashMap properties) {
        List<String> values = new ArrayList<>();
        if(requiredFields == null) {
            requiredFields = new ArrayList<>();
        }
        String fieldType = properties.get("type").toString();
        if (requiredFields.contains(fieldName.toString())) {
            values.add(PARAMETER_VALUE_NON_NULL);
            if (StringUtils.equals(fieldType, PARAMETER_TYPE_STRING)) {
                values.add(PARAMETER_VALUE_NON_EMPTY);
            }
        } else {
            if (StringUtils.equals(fieldType, PARAMETER_TYPE_STRING)) {
                values.add("NULL");
                values.add("EMPTY");
            }else{
                values.add("NULL");
            }
        }
        if (properties.get("enums") != null) {
            List enums = (List) properties.get("enums");
            for (Object anEnum : enums) {
                String value = anEnum.toString();
                if (ReUtil.isMatch("([0-9][0-9]*)+(.[0-9]+)?", value)) {
                    values.add("NUMBER_" + value);
                } else {
                    values.add(value);
                }
            }
            values.add(PARAMETER_VALUE_NON_ENUM_INVALID);
        } else {
            if(StringUtils.equals(fieldType, PARAMETER_TYPE_STRING)) {
                Integer minLength = (Integer) properties.get("minLength");
                if(minLength != null && minLength > 1) {
                    values.add(PARAMETER_VALUE_NON_MINLENGTH_SUB);
                }
                Integer maxLength = (Integer) properties.get("maxLength");
                if(maxLength != null && maxLength > 1 && minLength != maxLength) {
                    values.add(PARAMETER_VALUE_NON_MAXLENGTH_PLUS);
                }
            } else if(StringUtils.equals(fieldType, PARAMETER_TYPE_INTEGER)) {
                Integer min = (Integer) properties.get("min");
                if(min != null) {
                    values.add(PARAMETER_VALUE_NON_MIN_SUB);
                }
                Integer max = (Integer) properties.get("max");
                if(max != null && !Objects.equals(min, max)) {
                    values.add(PARAMETER_VALUE_NON_MAX_PLUS);
                }
            } else {
                values.add("~INVALID");
            }
            values.add("VALID");
        }
        FIELD_DEFINE_MAP.put(fieldPrefixPath + fieldName, JSON.parseObject(JSON.toJSONString(properties), FieldDefine.class));
        fieldValuesMap.put(fieldPrefixPath + fieldName, values);
    }

    public static Object[][] getApiProviderObjects(String apiDefineYmlPath) throws Exception {
        List<LinkedHashMap<String, String>> testGroups = generateTestGroups(apiDefineYmlPath);

        Object[][] objects = new Object[testGroups.size()][1];
        for (int i = 0; i < testGroups.size(); i++) {
            objects[i][0] = testGroups.get(i);
        }
        return objects;
    }

    // 根据接口参数定义生成具体的值
    public static Map<String, Object> getFieldValues(Dict dictApiDefine, Map<String, String> group) {
        log.info("组合详情: {}", JSON.toJSONString(group));

        Map<String, Object> fieldValues = new HashMap<>();
        group.forEach((fieldName, fieldAbstractValue) -> {
            FieldDefine fieldDefine = FIELD_DEFINE_MAP.get(fieldName);
            Object fieldTrueValue = String.copyValueOf(fieldAbstractValue.toCharArray());
            if(StringUtils.equals(fieldAbstractValue, "NULL") || StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_NULL)) {
                fieldTrueValue = "__NULL__";
                if(StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_NULL)) {
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " 是NULL");
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "EMPTY") || StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_EMPTY)) {
                fieldTrueValue = "";
                if(StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_EMPTY)) {
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " 是EMPTY");
                }
            }
            if(StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_ENUM_INVALID)) {
                fieldValues.put(CASE_DESCRIPTION, fieldName + " 是非法的枚举值");
                if(StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_STRING)) {
                    fieldTrueValue = RandomStringUtils.random(3, SEEDS);
                }else if(StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_INTEGER)) {
                    fieldTrueValue = RandomUtil.randomInt(10, 20);
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "VALID")) {
                if(StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_STRING)) {
                    if(StringUtils.isNoneBlank(fieldDefine.getRegex())) {
                        fieldTrueValue = randomStringByRegex(fieldDefine.getRegex());
                    }else if(fieldDefine.getMaxLength() != null) {
                        fieldTrueValue = RandomStringUtils.random(fieldDefine.getMaxLength(), SEEDS);
                    }
                }
                if(StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_INTEGER)) {
                    Integer min = fieldDefine.getMin();
                    if(min == null) {
                        min = 0;
                    }
                    Integer max = fieldDefine.getMax();
                    if(max == null) {
                        max = 100;
                    }
                    if(min > max) {
                        max = min+1;
                    }
                    fieldTrueValue = RandomUtil.randomInt(min, max);
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "~INVALID") && StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_STRING)) {
                    if(StringUtils.isNoneBlank(fieldDefine.getRegex())) {
                        fieldTrueValue = StringUtils.reverse(randomStringByRegex(fieldDefine.getRegex()));
                        fieldValues.put(CASE_DESCRIPTION, fieldName + " 不符合配置的正则表达式");
                    }else if(fieldDefine.getMaxLength() != null) {
                        fieldTrueValue = RandomStringUtils.random(fieldDefine.getMaxLength() + 1, SEEDS);
                        fieldValues.put(CASE_DESCRIPTION, fieldName + " string长度向上越界");
                    }
                }

            if(StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_MAXLENGTH_PLUS) && StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_STRING)) {
                    fieldTrueValue = RandomStringUtils.random(fieldDefine.getMaxLength() + 1, SEEDS);
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " string长度向上越界");
                }

            if(StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_MINLENGTH_SUB) && StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_STRING)) {
                    fieldTrueValue = RandomStringUtils.random(fieldDefine.getMinLength() - 1, SEEDS);
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " string长度向下越界");
                }

            if(StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_MAX_PLUS) && StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_INTEGER)) {
                    fieldTrueValue = fieldDefine.getMax() + 1;
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " integer值向上越界");
                }

            if(StringUtils.equals(fieldAbstractValue, PARAMETER_VALUE_NON_MIN_SUB) && StringUtils.equals(fieldDefine.getType(), PARAMETER_TYPE_INTEGER)) {
                    fieldTrueValue = fieldDefine.getMin() - 1;
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " integer值向下越界");
                }

            if(ReUtil.isMatch(StepNode.TEMPLATE_CASE_VARIABLE_PATTERN, fieldAbstractValue)) {
                String modelDataPath = fieldAbstractValue.substring(2, fieldAbstractValue.length() - 1);
                log.trace("库表列路径: {}", modelDataPath);
                ModelDataDefine modelDataDefine = dictApiDefine.getByPath(modelDataPath, ModelDataDefine.class);
                if(modelDataDefine == null) {
                    throw new RuntimeException("缺少[" + modelDataPath + "] model data定义");
                }
                Connection connection = null;
                try {
                    connection = UserTestContext.getInstance().getConnection(modelDataDefine.getDatasource());
                    log.info("SQL: {}", modelDataDefine.getSql());
                    List<Map<String, Object>> maps = JDBCUtils.doQuery(connection, modelDataDefine.getSql());
                    log.info("SQL执行结果: {}", JSON.toJSONString(maps));
                    fieldTrueValue = JsonPath.read(maps, modelDataDefine.getExtractPath());
                } catch (Exception e) {
                    log.error("", e);
                    throw new RuntimeException(e);
                } finally {
                    if(connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if(fieldTrueValue instanceof String value) {
                if(value.startsWith("NUMBER_")) {
                    fieldValues.put("$." + fieldName, value.substring(7));
                } else {
                    fieldValues.put("$." + fieldName, fieldTrueValue);
                }
            } else {
                fieldValues.put("$." + fieldName, fieldTrueValue);
            }
        });
        fieldValues.put(EXPECTED_RESULT, group.get(EXPECTED_RESULT));
        if(group.get(CASE_DESCRIPTION) != null) {
            fieldValues.put(CASE_DESCRIPTION, group.get(CASE_DESCRIPTION));
        }
        log.info("该组合测试点: {}", fieldValues.get(CASE_DESCRIPTION));
        log.info("该组合预期结果: {}", fieldValues.get(EXPECTED_RESULT));
        log.info("参数取值: {}", JSON.toJSONString(fieldValues));
        return fieldValues;
    }

    public static String getFilePathWithName(String fileName) {
        List<File> files = FileUtil.loopFiles(System.getProperty("user.dir"), pathname -> pathname.getName().equals(fileName));
        if(files != null && !files.isEmpty()) {
            return files.get(0).getAbsolutePath();
        }
        return null;
    }

    public static String randomStringByRegex(String regex) {
        try {
            return new ProcessExecutor().command("python", RANDOM_STRING_PY_PATH, regex)
                    .readOutput(true).execute()
                    .outputUTF8();
        } catch (IOException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runGroup(Map<String, String> row, Dict dictApiDefine, String apiName, String uri, String bodyTemplate, UserTestContext autoTestContext) throws Exception {
        Map<String, Object> fieldValues = getFieldValues(dictApiDefine, row);
        String expectedResult = (String) fieldValues.get(EXPECTED_RESULT);
        String repeatResult = (String) fieldValues.get(REPEAT_RESULT);
        String isRepeat = "no";
        if (row.containsKey(REPEAT_RESULT)) {
            isRepeat = "yes";
        }
        log.info("预期结果: {}", expectedResult);
        log.info("用例描述: {}", row.get(CASE_DESCRIPTION));

        new BaseAutoCaseBuilder(autoTestContext)
                .updateVariable(fieldValues)
                .updateVariable("expectedResult", expectedResult)
                .updateVariable("isRepeat", isRepeat)
                .updateVariable("repeatResult", repeatResult)
                .post(apiName, uri, bodyTemplate)
                .beginIf("如果预期成功", "${expectedResult} == 'success'")
                .assertSuccess()
                .endIf("预期成功结束")
                .beginIf("如果预期失败", "${expectedResult} == 'fail'")
                .assertFail()
                .endIf("预期失败结束")
                .beginIf("重放开始", "${isRepeat} == 'yes'")
                .post("重放请求", uri, "${__request}")
                .beginIf("如果预期成功", "${repeatResult} == 'success'")
                .assertSuccess()
                .endIf("预期成功结束")
                .beginIf("如果预期失败", "${repeatResult} == 'fail'")
                .assertFail()
                .endIf("预期失败结束")
                .endIf("重放结束")
                .run();
    }
}

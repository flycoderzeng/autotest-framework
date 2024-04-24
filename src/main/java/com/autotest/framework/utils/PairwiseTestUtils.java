package com.autotest.framework.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.setting.yaml.YamlUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.autotest.framework.antlr.constraint.core.ConstraintsLexer;
import com.autotest.framework.antlr.constraint.core.ConstraintsParser;
import com.autotest.framework.antlr.constraint.visitor.ConstraintsVisitor;
import com.autotest.framework.common.entities.autotest.FieldDefine;
import com.autotest.framework.common.entities.autotest.ModelDataDefine;
import com.autotest.framework.context.UserTestContext;
import com.autotest.framework.nodes.StepNode;
import com.jayway.jsonpath.JsonPath;
import com.mifmif.common.regex.Generex;
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
    public static final String SEEDS = "QWERTYUIOPASDFGHJKLZXCVBNM_qwertyuiopasdfghjklzxcvbnm_1234567890_中文哈哈哈";
    public static final String PICT_FOLDER_DIR = "C:\\Users\\zengb\\Documents\\pict\\";
    public static final Map<String, FieldDefine> FIELD_DEFINE_MAP = new HashMap<>();
    public static final String EXPECTED_RESULT = "__EXPECTED_RESULT";
    public static final String CASE_DESCRIPTION = "__CASE_DESCRIPTION";

    public static List<LinkedHashMap<String, String>> generateTestGroups(String apiDefineYmlName) throws IOException, InterruptedException, TimeoutException {
        Dict dict = YamlUtil.loadByPath(getApiDefineYmlPath(apiDefineYmlName));
        Map<String, List<String>> fieldValuesMap = new LinkedHashMap<>();

        generateFieldValues(dict, fieldValuesMap, "", "");

        List<String> requiredFields = new ArrayList<>();
        fieldValuesMap.forEach((k, v) -> {
            if(v.contains("~NULL") || v.contains("~EMPTY")) {
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
                for (Object o : v) {
                    if(!StringUtils.equals("~NULL", (String) o) && !StringUtils.equals("~EMPTY", (String) o)
                            && !StringUtils.equals("~MAXLENGTH_PLUS", (String) o)
                            && !StringUtils.equals("~MINLENGTH_SUB", (String) o)
                            && !StringUtils.equals("~MAX_PLUS", (String) o)
                            && !StringUtils.equals("~MIN_SUB", (String) o)
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
        fieldValuesMap.forEach((k, v) -> {
            builder2.append(k).append(": ").append(StringUtils.join(v, ",")).append("\r\n");
        });
        // 处理约束
        List<String> constraints = dict.getByPath("constraints");
        if(constraints != null && !constraints.isEmpty()) {
            builder2.append("\r\n");
            for (String constraint : constraints) {
                builder2.append(constraint).append(";\r\n");
            }
        }

        fileName = apiDefineYmlName.replaceAll(".yml", ".txt");
        final List<LinkedHashMap<String, String>> groups = getPictGroups(fileName, builder2, fieldNames, "1");
        List<LinkedHashMap<String, String>> distinctList = groups;

        for (int i = 0; i < fieldNames.size(); i++) {
            final String key = fieldNames.get(i);
            distinctList = distinctList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> {
                                String value = ((Map<String, String>) m).get(key);
                                if(value.startsWith("~")) {
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
                    ConstraintsVisitor visitor = new ConstraintsVisitor(dict, fieldValuesMap, constraint, group);
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
                                visitor = new ConstraintsVisitor(dict, fieldValuesMap, constraints.get(j), group);
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
        log.info("约束反例大小: {}", constraintCounterexampleList.size());
        constraintCounterexampleList = constraintCounterexampleList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Map::hashCode))),
                        ArrayList::new
                ));
        log.info("约束反例大小: {}", constraintCounterexampleList.size());
        constraintCounterexampleList = constraintCounterexampleList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> {
                            for (String requiredField : requiredFields) {
                                String value = ((Map<String, String>) m).get(requiredField);
                                if(StringUtils.equals(value, "~NULL") || StringUtils.equals(value, "~EMPTY") || StringUtils.equals(value, "~ENUM_INVALID")) {
                                    return "excluded";
                                }
                            }
                            return RandomUtil.randomString(32);
                        }))),
                        ArrayList::new
                ));
        log.info("约束反例大小: {}", constraintCounterexampleList.size());
        log.info("约束反例详情: {}", JSON.toJSONString(constraintCounterexampleList));

        for (int i = 0; i < fieldNames.size(); i++) {
            final String key = fieldNames.get(i);
            distinctList = distinctList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> {
                                String value = ((Map<String, String>) m).get(key);
                                if(value.startsWith("~")) {
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
            for (Object value : group.values()) {
                if(((String)value).startsWith("~")) {
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

        log.info("组合数: {}", distinctList.size());
        log.info("组合详情: {}", JSON.toJSONString(distinctList));

        return distinctList;
    }

    public static List<LinkedHashMap<String, String>> getPictGroups(String fileName, StringBuilder builder, List<String> fieldNames, String o) throws IOException, InterruptedException, TimeoutException {
        FileUtil.writeBytes(builder.toString().getBytes(StandardCharsets.UTF_8), PICT_FOLDER_DIR + fileName);
        String output = new ProcessExecutor().command(PICT_FOLDER_DIR + "\\pict.exe", PICT_FOLDER_DIR + fileName, "-o:"+o)
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
        if(StringUtils.equals(type, "object")) {
            List<String> requiredFields = dict.getByPath(getDictPath(dictPrefixPath, "required"));
            ((LinkedHashMap) dict.getByPath(getDictPath(dictPrefixPath, "properties"))).forEach((fieldName, fieldProperties) -> {
                LinkedHashMap properties = (LinkedHashMap) fieldProperties;
                if(StringUtils.equals(properties.get("type").toString(), "array")) {
                    String itemType = getDictPath(dictPrefixPath, fieldName.toString()) + ".items.type";
                    if(!StringUtils.equals(itemType, "object") && !StringUtils.equals(itemType, "array")) {
                        generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties." + fieldName + ".items"),fieldName + "[0]");
                    }else{
                        generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties." + fieldName + ".items"), fieldName + "[0].");
                    }
                }else if(StringUtils.equals(properties.get("type").toString(), "object")) {
                    generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties." + fieldName), fieldName + ".");
                }else{
                    generateField(fieldValuesMap, fieldPrefixPath, requiredFields, fieldName, properties);
                }
            });
        } else if(StringUtils.equals(type, "array")) {
            String itemType = getDictPath(dictPrefixPath, "") + ".items.type";
            if(!StringUtils.equals(itemType, "object") && !StringUtils.equals(itemType, "array")) {
                generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties.items"),"[0]");
            }else{
                generateFieldValues(dict, fieldValuesMap, getDictPath(dictPrefixPath, "properties.items"), "[0].");
            }
        } else {
            Boolean must = dict.getByPath(getDictPath(dictPrefixPath, "must"));
            if(must != null && must) {
                generateField(fieldValuesMap, "", Collections.singletonList(fieldPrefixPath), fieldPrefixPath, dict.getByPath(dictPrefixPath));
            }
        }
    }

    public static void generateField(Map<String, List<String>> fieldValuesMap, String fieldPrefixPath, List<String> requiredFields, Object fieldName, LinkedHashMap properties) {
        List<String> values = new ArrayList<>();
        if(requiredFields == null) {
            requiredFields = new ArrayList<>();
        }
        String fieldType = properties.get("type").toString();
        if (requiredFields.contains(fieldName)) {
            values.add("~NULL");
            if (StringUtils.equals(fieldType, "string")) {
                values.add("~EMPTY");
            }
        } else {
            if (StringUtils.equals(fieldType, "string")) {
                values.add("NULL");
                values.add("EMPTY");
            }else{
                values.add("NULL");
            }
        }
        if (properties.get("enums") != null) {
            List enums = (List) properties.get("enums");
            for (int i = 0; i < enums.size(); i++) {
                values.add(enums.get(i).toString());
            }
            values.add("~ENUM_INVALID");
        } else {
            if(StringUtils.equals(fieldType, "string")) {
                Integer minLength = (Integer) properties.get("minLength");
                if(minLength != null && minLength > 1) {
                    values.add("~MINLENGTH_SUB");
                }
                Integer maxLength = (Integer) properties.get("maxLength");
                if(maxLength != null && maxLength > 1 && minLength != maxLength) {
                    values.add("~MAXLENGTH_PLUS");
                }
            } else if(StringUtils.equals(fieldType, "integer")) {
                Integer min = (Integer) properties.get("min");
                if(min != null) {
                    values.add("~MIN_SUB");
                }
                Integer max = (Integer) properties.get("max");
                if(max != null && max > 1 && min != max) {
                    values.add("~MAX_PLUS");
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
            if(StringUtils.equals(fieldAbstractValue, "NULL") || StringUtils.equals(fieldAbstractValue, "~NULL")) {
                fieldTrueValue = "__NULL__";
                if(StringUtils.equals(fieldAbstractValue, "~NULL")) {
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " 是NULL");
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "EMPTY") || StringUtils.equals(fieldAbstractValue, "~EMPTY")) {
                fieldTrueValue = "";
                if(StringUtils.equals(fieldAbstractValue, "~EMPTY")) {
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " 是EMPTY");
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "~ENUM_INVALID")) {
                fieldValues.put(CASE_DESCRIPTION, fieldName + " 是非法的枚举值");
                if(StringUtils.equals(fieldDefine.getType(), "string")) {
                    fieldTrueValue = RandomStringUtils.random(3, SEEDS);
                }else if(StringUtils.equals(fieldDefine.getType(), "integer")) {
                    fieldTrueValue = RandomUtil.randomInt(10, 20);
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "VALID")) {
                if(StringUtils.equals(fieldDefine.getType(), "string")) {
                    if(StringUtils.isNoneBlank(fieldDefine.getRegex())) {
                        Generex generex = new Generex(fieldDefine.getRegex());
                        fieldTrueValue = generex.random();
                    }else if(fieldDefine.getMaxLength() != null) {
                        fieldTrueValue = RandomStringUtils.random(fieldDefine.getMaxLength(), SEEDS);
                    }
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "~INVALID")) {
                if(StringUtils.equals(fieldDefine.getType(), "string")) {
                    if(StringUtils.isNoneBlank(fieldDefine.getRegex())) {
                        Generex generex = new Generex(fieldDefine.getRegex());
                        fieldTrueValue = StringUtils.reverse(generex.random());
                        fieldValues.put(CASE_DESCRIPTION, fieldName + " 不符合配置的正则表达式");
                    }else if(fieldDefine.getMaxLength() != null) {
                        fieldTrueValue = RandomStringUtils.random(fieldDefine.getMaxLength() + 1, SEEDS);
                        fieldValues.put(CASE_DESCRIPTION, fieldName + " string长度向上越界");
                    }
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "~MAXLENGTH_PLUS")) {
                if(StringUtils.equals(fieldDefine.getType(), "string")) {
                    fieldTrueValue = RandomStringUtils.random(fieldDefine.getMaxLength() + 1, SEEDS);
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " string长度向上越界");
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "~MINLENGTH_SUB")) {
                if(StringUtils.equals(fieldDefine.getType(), "string")) {
                    fieldTrueValue = RandomStringUtils.random(fieldDefine.getMinLength() - 1, SEEDS);
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " string长度向下越界");
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "~MAX_PLUS")) {
                if(StringUtils.equals(fieldDefine.getType(), "integer")) {
                    fieldTrueValue = fieldDefine.getMax() + 1;
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " integer值向上越界");
                }
            }
            if(StringUtils.equals(fieldAbstractValue, "~MIN_SUB")) {
                if(StringUtils.equals(fieldDefine.getType(), "integer")) {
                    fieldTrueValue = fieldDefine.getMin() - 1;
                    fieldValues.put(CASE_DESCRIPTION, fieldName + " integer值向下越界");
                }
            }
            if(ReUtil.isMatch(StepNode.TEMPLATE_CASE_VARIABLE_PATTERN, fieldAbstractValue)) {
                String modelDataPath = fieldAbstractValue.substring(2, fieldAbstractValue.length() - 1);
                log.trace("元数据路径: {}", modelDataPath);
                ModelDataDefine modelDataDefine = dictApiDefine.getByPath(modelDataPath, ModelDataDefine.class);
                if(modelDataDefine == null) {
                    throw new RuntimeException("缺少[" + modelDataPath + "] model data定义");
                }
                Connection connection = null;
                try {
                    connection = UserTestContext.getInstance().getConnection(modelDataDefine.getDatasource());
                    log.info("SQL: {}", modelDataDefine.getSql());
                    List<Map<String, Object>> maps = JDBCUtils.doQuery(connection, modelDataDefine.getSql());
                    log.info("SQL执行结果: {}", JSONObject.toJSONString(maps));
                    fieldTrueValue = JsonPath.read(maps, modelDataDefine.getExtractPath());
                } catch (Exception e) {
                    e.printStackTrace();
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
            fieldValues.put("$." + fieldName, fieldTrueValue);
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

    public static String getApiDefineYmlPath(String fileName) {
        List<File> files = FileUtil.loopFiles(System.getProperty("user.dir"), pathname -> pathname.getName().equals(fileName));
        if(files != null && !files.isEmpty()) {
            return files.get(0).getAbsolutePath();
        }
        return null;
    }
}

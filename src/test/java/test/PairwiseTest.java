package test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.setting.yaml.YamlUtil;
import com.alibaba.fastjson.JSONObject;
import com.autotest.framework.builder.BaseAutoCaseBuilder;
import com.autotest.framework.utils.JDBCUtils;
import com.jayway.jsonpath.JsonPath;
import com.mifmif.common.regex.Generex;
import com.autotest.framework.context.UserTestContext;
import com.autotest.framework.nodes.StepNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.zeroturnaround.exec.ProcessExecutor;
import test.antlr.core.ConstraintsLexer;
import test.antlr.core.ConstraintsParser;
import test.antlr.expr.ConstraintsVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest(classes = {PairwiseTest.class})
public class PairwiseTest {
    public static final String SEEDS = "QWERTYUIOPASDFGHJKLZXCVBNM_qwertyuiopasdfghjklzxcvbnm_1234567890_中文哈哈哈";
    public static final String PICT_FOLDER_DIR = "C:\\Users\\zengb\\Documents\\pict\\";
    public static final Map<String, FieldDefine> FIELD_DEFINE_MAP = new HashMap<>();
    public static final String EXPECTED_RESULT = "__EXPECTED_RESULT";
    public static final String CASE_DESCRIPTION = "__CASE_DESCRIPTION";
    public Dict dictAddMetadata;
    public Dict dictAddStrategy;

    @BeforeClass
    public void before() {
        dictAddMetadata = YamlUtil.loadByPath("E:\\java\\workspace\\olymatp\\olymatp-dbencplt\\src\\test\\java\\test\\add_metadata.yml");
        dictAddStrategy = YamlUtil.loadByPath("E:\\java\\workspace\\olymatp\\olymatp-dbencplt\\src\\test\\java\\test\\add_strategy.yml");
    }

    @Test(testName = "测试添加元数据", dataProvider = "allMetadataRows")
    public void testAddMetadata(Map<String, String> row) throws Exception {
        Map<String, Object> fieldValues = getFieldValues(dictAddMetadata, row);
        String expectedResult = (String) fieldValues.get(EXPECTED_RESULT);

        new BaseAutoCaseBuilder(UserTestContext.getInstance())
                .updateVariable(fieldValues)
                .updateVariable("expectedResult", expectedResult)
                .postWithCompanySuperAdmin("添加元数据", "/database_meta/metadata/v2/add", "{\n" +
                        "                         \"databaseName\": \"${$.databaseName}\",\n" +
                        "                         \"schemaName\": \"${$.schemaName}\",\n" +
                        "                         \"databaseType\": \"${$.databaseType}\",\n" +
                        "                         \"tableName\": \"${$.tableName}\",\n" +
                        "                         \"columnName\": \"${$.columnName}\",\n" +
                        "                         \"columnType\": \"${$.columnType}\"\n" +
                        "                     }")
                .beginIf("如果预期成功", "${expectedResult} == 'success'")
                .assertSuccess()
                .endIf("预期成功结束")
                .beginIf("如果预期失败", "${expectedResult} == 'fail'")
                .assertFail()
                .endIf("预期失败结束")
                .run();
    }

    @Test(testName = "测试添加策略", dataProvider = "allStrategyRows")
    public void testAddStrategy(Map<String, String> row) throws Exception {
        Map<String, Object> fieldValues = getFieldValues(dictAddStrategy, row);
        String expectedResult = (String) fieldValues.get(EXPECTED_RESULT);

        new BaseAutoCaseBuilder(UserTestContext.getInstance())
                .updateVariable(fieldValues)
                .updateVariable("expectedResult", expectedResult)
                .postWithCompanySuperAdmin("添加元数据", "/database_meta/policy/v2/add", "{\n" +
                        "    \"applicationId\": \"${$.applicationId}\",\n" +
                        "    \"metadataStructureIds\": [\n" +
                        "        \"${$.metadataStructureIds[0]}\"\n" +
                        "    ],\n" +
                        "    \"encryptPolicy\": {\n" +
                        "        \"secretKeyCode\": \"${$.encryptPolicy.secretKeyCode}\",\n" +
                        "        \"encryptAlgorithm\": \"${$.encryptPolicy.encryptAlgorithm}\",\n" +
                        "        \"typeCode\": \"${$.encryptPolicy.typeCode}\",\n" +
                        "        \"encryptMode\": \"${$.encryptPolicy.encryptMode}\",\n" +
                        "        \"columnFormat\": \"${$.encryptPolicy.columnFormat}\",\n" +
                        "        \"stage\": \"${$.encryptPolicy.stage}\",\n" +
                        "        \"hasEncryptField\": \"${$.encryptPolicy.hasEncryptField}\"\n" +
                        "    },\n" +
                        "    \"hashPolicy\": {\n" +
                        "        \"hashAlgorithm\": \"${$.hashPolicy.hashAlgorithm}\",\n" +
                        "        \"hashSecretKeyCode\": \"${$.hashPolicy.hashSecretKeyCode}\",\n" +
                        "        \"hashLen\": \"${$.hashPolicy.hashLen}\",\n" +
                        "        \"hashVerify\": \"${$.hashPolicy.hashVerify}\",\n" +
                        "        \"hasHashField\": \"${$.hashPolicy.hasHashField}\"\n" +
                        "    }\n" +
                        "}")
                .beginIf("如果预期成功", "${expectedResult} == 'success'")
                .assertSuccess()
                .endIf("预期成功结束")
                .beginIf("如果预期失败", "${expectedResult} == 'fail'")
                .assertFail()
                .endIf("预期失败结束")
                .run();
    }

    @DataProvider(name = "allMetadataRows")
    public Object[][] getAllMetadataRows() throws Exception {
        return getMetadataProviderObjects("C:\\Users\\zengb\\Documents\\java-workspace\\tm-dev-3.0.0\\autotest-framework\\src\\test\\java\\test\\add_metadata.yml");
    }

    @DataProvider(name = "allStrategyRows")
    public Object[][] getAllStrategyRows() throws Exception {
        return getMetadataProviderObjects("C:\\Users\\zengb\\Documents\\java-workspace\\tm-dev-3.0.0\\autotest-framework\\src\\test\\java\\test\\add_strategy.yml");
    }

    public static Object[][] getMetadataProviderObjects(String apiDefineYmlPath) throws Exception {
        List<LinkedHashMap> testGroups = generateTestGroups(apiDefineYmlPath);

        Object[][] objects = new Object[testGroups.size()][1];
        for (int i = 0; i < testGroups.size(); i++) {
            objects[i][0] = testGroups.get(i);
        }
        return objects;
    }

    // 根据接口参数定义生成具体的值
    public static Map<String, Object> getFieldValues(Dict dictApiDefine, Map<String, String> group) {
        log.info("组合详情: {}", JSONObject.toJSONString(group));
        String expectedResult = "success";
        for (String value : group.values()) {
            if(value.startsWith("~")) {
                expectedResult = "fail";
                break;
            }
        }
        log.info("预期结果: {}", expectedResult);

        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put(EXPECTED_RESULT, expectedResult);
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
                log.info("元数据路径: {}", modelDataPath);
                ModelDataDefine modelDataDefine = dictApiDefine.getByPath(modelDataPath, ModelDataDefine.class);
                if(modelDataDefine == null) {
                    throw new RuntimeException("缺少[" + modelDataPath + "] model data定义");
                }
                Connection connection = null;
                try {
                    connection = UserTestContext.getInstance().getConnection(modelDataDefine.getDatasource());
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
        log.info("该组合测试点: {}", fieldValues.get(CASE_DESCRIPTION));
        log.info("参数取值: {}", JSONObject.toJSON(fieldValues));
        return fieldValues;
    }

    @Data
    public static class FieldDefine {
        public String description;
        public String type;
        public Integer minLength;
        public Integer maxLength;
        public Integer min;
        public Integer max;
        public String example;
        public String regex;
        public List<String> enums;
    }

    @Data
    public static class ModelDataDefine {
        public String datasource;
        public String sql;
        public String extractPath;
    }

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        generateTestGroups("C:\\Users\\zengb\\Documents\\java-workspace\\tm-dev-3.0.0\\autotest-framework\\src\\test\\java\\test\\add_strategy.yml");
        //generateTestGroups("E:\\java\\workspace\\olymatp\\olymatp-dbencplt\\src\\test\\java\\test\\add_metadata.yml");
    }

    public static List<LinkedHashMap> generateTestGroups(String apiDefineYmlPath) throws IOException, InterruptedException, TimeoutException {
        Dict dict = YamlUtil.loadByPath(apiDefineYmlPath);
        Map<String, List> fieldValuesMap = new LinkedHashMap<>();

        generateFieldValues(dict, fieldValuesMap, "", "");

        List<String> requiredFields = new ArrayList<>();
        fieldValuesMap.forEach((k, v) -> {
            if(v.indexOf("~NULL") > -1 || v.indexOf("~EMPTY") > -1) {
                requiredFields.add(k);
            }
        });

        log.info("参数的取值: {}", JSONObject.toJSON(fieldValuesMap));
        StringBuilder builder = new StringBuilder();
        List<String> fieldNames = new ArrayList<>();
        fieldValuesMap.forEach((k, v) -> {
            fieldNames.add(k);
            builder.append(k).append(": ").append(StringUtils.join(v, ",")).append("\r\n");
        });

        String fileName = "complete_" + new File(apiDefineYmlPath).getName().replaceAll(".yml", ".txt");
        // 生成全量组合
        final List<LinkedHashMap> completeGroups = getPictGroups(fileName, builder, fieldNames, "2");
        log.info("全量组合大小: {}", completeGroups.size());

        // 处理约束
        List<String> constraints = dict.getByPath("constraints");
        if(constraints != null && !constraints.isEmpty()) {
            builder.append("\r\n");
            for (String constraint : constraints) {
                builder.append(constraint).append(";\r\n");
            }
        }

        fileName = new File(apiDefineYmlPath).getName().replaceAll(".yml", ".txt");
        final List<LinkedHashMap> groups = getPictGroups(fileName, builder, fieldNames, "1");
        List<LinkedHashMap> distinctList = groups;

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

        List<LinkedHashMap> constraintCounterexampleList = new ArrayList<>();

        if(constraints != null && !constraints.isEmpty()) {
            for (LinkedHashMap group : completeGroups) {
                for (int i = 0; i < constraints.size(); i++) {
                    String constraint = constraints.get(i);
                    int trueCount = 0;
                    int falseCount = 0;
                    for (int j = 0; j < constraints.size(); j++) {
                        CharStream input = CharStreams.fromString(constraint);
                        ConstraintsLexer lexer = new ConstraintsLexer(input);
                        CommonTokenStream tokens = new CommonTokenStream(lexer);
                        ConstraintsParser parser = new ConstraintsParser(tokens);
                        ConstraintsParser.ExpressionContext context = parser.expression();
                        ConstraintsVisitor visitor = new ConstraintsVisitor(dict, fieldValuesMap, constraint, group);
                        try {
                            final Boolean visitResult = (Boolean) visitor.visit(context);
                            if(j != i && visitResult) {
                                trueCount++;
                            } else {
                                if (!visitResult) {
                                    falseCount++;
                                }
                            }
                        } catch (Exception e) {
                            log.trace(e.getMessage());
                        }
                    }
                    if(trueCount == constraints.size() - 1 && falseCount < 2) {
                        constraintCounterexampleList.add(group);
                    }
                }
            }
        }
        constraintCounterexampleList = constraintCounterexampleList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> m.hashCode()))),
                        ArrayList::new
                ));
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
        log.info("约束反例详情: {}", JSONObject.toJSONString(constraintCounterexampleList));

        for (int i = 0; i < fieldNames.size(); i++) {
            final String key = fieldNames.get(i);
            distinctList = distinctList.stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> {
                                String value = ((Map<String, String>) m).get(key);
                                if(value.startsWith("~")) {
                                    return value;
                                }
                                return RandomUtil.randomString(32);
                            }))),
                            ArrayList::new
                    ));
        }

        distinctList.addAll(constraintCounterexampleList);

        log.info("组合数: {}", distinctList.size());
        log.info("组合详情: {}", JSONObject.toJSONString(distinctList));
        return distinctList;
    }

    public static List<LinkedHashMap> getPictGroups(String fileName, StringBuilder builder, List<String> fieldNames, String o) throws IOException, InterruptedException, TimeoutException {
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
        List<LinkedHashMap> groups = new ArrayList<>();

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

    public static void generateFieldValues(Dict dict, Map<String, List> fieldValuesMap, String dictPrefixPath, String fieldPrefixPath) {
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
            if(must != null && must == true) {
                generateField(fieldValuesMap, "", Arrays.asList(fieldPrefixPath), fieldPrefixPath, dict.getByPath(dictPrefixPath));
            }
        }
    }

    public static void generateField(Map<String, List> fieldValuesMap, String fieldPrefixPath, List<String> requiredFields, Object fieldName, LinkedHashMap properties) {
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
        FIELD_DEFINE_MAP.put(fieldPrefixPath + fieldName, JSONObject.parseObject(JSONObject.toJSONString(properties),FieldDefine.class));
        fieldValuesMap.put(fieldPrefixPath + fieldName, values);
    }
}

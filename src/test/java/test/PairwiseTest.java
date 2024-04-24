package test;

import cn.hutool.core.lang.Dict;
import cn.hutool.setting.yaml.YamlUtil;
import com.autotest.framework.builder.BaseAutoCaseBuilder;
import com.autotest.framework.context.UserTestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static com.autotest.framework.utils.PairwiseTestUtils.*;

@Slf4j
@SpringBootTest(classes = {PairwiseTest.class})
public class PairwiseTest {
    public Dict dictAddMetadata;
    public Dict dictUpdateMetadata;
    public Dict dictAddStrategy;

    @BeforeClass
    public void before() {
        dictAddMetadata = YamlUtil.loadByPath(getApiDefineYmlPath("add_metadata.yml"));
        dictUpdateMetadata = YamlUtil.loadByPath(getApiDefineYmlPath("update_metadata.yml"));
        dictAddStrategy = YamlUtil.loadByPath(getApiDefineYmlPath("add_strategy.yml"));
    }

    @Test(testName = "测试添加元数据", dataProvider = "allMetadataRows")
    public void testAddMetadata(Map<String, String> row) throws Exception {
        Map<String, Object> fieldValues = getFieldValues(dictAddMetadata, row);
        String expectedResult = (String) fieldValues.get(EXPECTED_RESULT);
        log.info("预期结果: {}", expectedResult);
        log.info("用例描述: {}", row.get(CASE_DESCRIPTION));

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

    @Test(testName = "测试修改元数据", dataProvider = "allUpdateMetadataRows")
    public void testUpdateMetadata(Map<String, String> row) throws Exception {
        Map<String, Object> fieldValues = getFieldValues(dictUpdateMetadata, row);
        String expectedResult = (String) fieldValues.get(EXPECTED_RESULT);
        log.info("预期结果: {}", expectedResult);
        log.info("用例描述: {}", row.get(CASE_DESCRIPTION));

        new BaseAutoCaseBuilder(UserTestContext.getInstance())
                .updateVariable(fieldValues)
                .updateVariable("expectedResult", expectedResult)
                .postWithCompanySuperAdmin("修改元数据", "/database_meta/metadata/v2/update", "{\n" +
                        "                         \"metadataStructureId\": \"${$.metadataStructureId}\",\n" +
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
        return getApiProviderObjects(getApiDefineYmlPath("add_metadata.yml"));
    }

    @DataProvider(name = "allUpdateMetadataRows")
    public Object[][] getAllUpdateMetadataRows() throws Exception {
        return getApiProviderObjects(getApiDefineYmlPath("update_metadata.yml"));
    }

    @DataProvider(name = "allStrategyRows")
    public Object[][] getAllStrategyRows() throws Exception {
        return getApiProviderObjects(getApiDefineYmlPath("add_strategy.yml"));
    }

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        generateTestGroups("update_metadata.yml");
        //generateTestGroups("add_strategy.yml");
        //generateTestGroups("add_metadata.yml");
    }
}

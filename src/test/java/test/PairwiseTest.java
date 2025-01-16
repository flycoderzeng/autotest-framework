package test;

import cn.hutool.core.lang.Dict;
import cn.hutool.setting.yaml.YamlUtil;
import com.autotest.framework.builder.BaseAutoCaseBuilder;
import com.autotest.framework.context.UserTestContext;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static com.autotest.framework.utils.PairwiseTestUtils.*;

@Slf4j
public class PairwiseTest {
    public Dict dictAddDbTableColumn;

    @BeforeClass
    public void before() {
        dictAddDbTableColumn = YamlUtil.loadByPath(getFilePathWithName("add_db_table_column.yml"));
    }

    @Test(testName = "测试添加库表列", dataProvider = "allDbTableColumnRows")
    public void testAddDbTableColumn(Map<String, String> row) throws Exception {
        Map<String, Object> fieldValues = getFieldValues(dictAddDbTableColumn, row);
        String expectedResult = (String) fieldValues.get(EXPECTED_RESULT);
        log.info("预期结果: {}", expectedResult);
        log.info("用例描述: {}", row.get(CASE_DESCRIPTION));

        new BaseAutoCaseBuilder(UserTestContext.getInstance())
                .updateVariable(fieldValues)
                .updateVariable("expectedResult", expectedResult)
                .post("添加库表列", "/dbTableColumn/add", """
                        {
                            "databaseName": "${$.databaseName}",
                            "schemaName": "${$.schemaName}",
                            "databaseType": "${$.databaseType}",
                            "tableName": "${$.tableName}",
                            "columnName": "${$.columnName}",
                            "columnType": "${$.columnType}"
                        }
                        """)
                .beginIf("如果预期成功", "${expectedResult} == 'success'")
                .assertSuccess()
                .endIf("预期成功结束")
                .beginIf("如果预期失败", "${expectedResult} == 'fail'")
                .assertFail()
                .endIf("预期失败结束")
                .run();
    }

    @DataProvider(name = "allDbTableColumnRows")
    public Object[][] getAllDbTableColumnRows() throws Exception {
        return getApiProviderObjects("add_db_table_column.yml");
    }

    public static void main(String[] args) throws Exception {
        generateTestGroups("add_db_table_column.yml");
    }
}

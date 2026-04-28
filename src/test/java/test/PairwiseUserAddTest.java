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
public class PairwiseUserAddTest {
    public Dict dictAddUser;

    @BeforeClass
    public void before() {
        dictAddUser = YamlUtil.loadByPath(getFilePathWithName("add_users.yml"));
    }

    @Test(testName = "测试添加用户", dataProvider = "allAddUserRows")
    public void testAddUserRights(Map<String, String> row) throws Exception {
        Map<String, Object> fieldValues = getFieldValues(dictAddUser, row);
        String expectedResult = (String) fieldValues.get(EXPECTED_RESULT);
        log.info("预期结果: {}", expectedResult);
        log.info("用例描述: {}", row.get(CASE_DESCRIPTION));

        new BaseAutoCaseBuilder(UserTestContext.getInstance())
                .updateVariable(fieldValues)
                .updateVariable("expectedResult", expectedResult)
                .post("添加用户", "/user/addUsers", """
                        {
                            "userList": [{"name":"${$.userList_0_name}", "sex":"${$.userList_0_sex}"}]
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

    @Test(testName = "测试添加用户", dataProvider = "allAddUserRows")
    public void testAddUserRights2(Map<String, String> row) throws Exception {
        runGroup(row, dictAddUser, "添加用户", "/user/addUsers", """
                        {
                            "userList": [{"name":"${$.userList_0_name}", "sex":"${$.userList_0_sex}"}]
                        }
                        """, UserTestContext.getInstance());
    }

    @DataProvider(name = "allAddUserRows")
    public Object[][] getAllAddUserRows() throws Exception {
        return getApiProviderObjects("add_users.yml");
    }

    public static void main(String[] args) throws Exception {
        generateTestGroups("add_users.yml");
    }
}

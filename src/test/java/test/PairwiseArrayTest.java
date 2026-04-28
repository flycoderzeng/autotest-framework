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
public class PairwiseArrayTest {
    public Dict dictAddUserRights;

    @BeforeClass
    public void before() {
        dictAddUserRights = YamlUtil.loadByPath(getFilePathWithName("add_rights.yml"));
    }

    @Test(testName = "测试添加权限", dataProvider = "allAddUserRightsRows")
    public void testAddUserRights(Map<String, String> row) throws Exception {
        Map<String, Object> fieldValues = getFieldValues(dictAddUserRights, row);
        String expectedResult = (String) fieldValues.get(EXPECTED_RESULT);
        log.info("预期结果: {}", expectedResult);
        log.info("用例描述: {}", row.get(CASE_DESCRIPTION));

        new BaseAutoCaseBuilder(UserTestContext.getInstance())
                .updateVariable(fieldValues)
                .updateVariable("expectedResult", expectedResult)
                .post("添加用户权限", "/user/addRights", """
                        {
                            "userId": "${$.userId}",
                            "rightIds": ["${$.rightIds_0}", "${$.rightIds_1}"]
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

    @DataProvider(name = "allAddUserRightsRows")
    public Object[][] getAllAddUserRightsRows() throws Exception {
        return getApiProviderObjects("add_rights.yml");
    }

    public static void main(String[] args) throws Exception {
        generateTestGroups("add_rights.yml");
    }
}

package yml_read_test;

import com.alibaba.fastjson.JSONObject;
import com.autotest.framework.common.entities.JdbcConfig;
import com.autotest.framework.context.UserTestContext;

import java.io.File;

public class YmlReadTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        // 获取ClassLoader
        ClassLoader classLoader = YmlReadTest.class.getClassLoader();

        // 获取文件
        File file = new File(classLoader.getResource("application.yml").getFile());

        // 获取文件路径
        String filePath = file.getAbsolutePath();

        System.out.println(filePath);
        UserTestContext userTestContext = UserTestContext.getInstance();
        System.out.println(userTestContext.getTestEnvName());
        Object byPath = userTestContext.testVariablesDict.getByPath("datasource.cipherPlatform");
        System.out.println(JSONObject.parseObject(JSONObject.toJSONString(byPath), JdbcConfig.class));
    }
}

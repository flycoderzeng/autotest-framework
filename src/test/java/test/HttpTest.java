package test;

import com.autotest.framework.builder.BaseAutoCaseBuilder;
import com.autotest.framework.context.UserTestContext;

public class HttpTest {
    public static void main(String[] args) throws Exception {
        new BaseAutoCaseBuilder(UserTestContext.getInstance())
                .post("发送开会请求", "/api/open", """
                        {"name": "Alice", "mobile": "13188990011"}
                        """)
                .assertResponseExpression("检查响应内容", "[$.code] == 0 AND [$.msg] == ''")
                .run();
    }
}

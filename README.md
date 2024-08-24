# autotest-framework

#### 介绍
```text
java autotest framework
链式调用编写自动化用例
根据接口定义生成自动化测试
```
#### 示例
```java
new BaseAutoCaseBuilder(UserTestContext.getInstance())
    .post("发送开户请求", "/api/openAccount", """
            {"name": "Alice", "mobile": "13188990011"}
            """)
    .assertResponseExpression("检查响应内容", "[$.code] == 0 AND [$.msg] == '成功'")
    .run();
```

#### 依赖
- Python 3.11.9以上
- 安装xeger模块
```xeger
pip install xeger -i http://mirrors.aliyun.com/pypi/simple/ --trusted-host mirrors.aliyun.com
```
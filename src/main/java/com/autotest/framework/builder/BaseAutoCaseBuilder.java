package com.autotest.framework.builder;

import com.alibaba.fastjson.JSON;
import com.autotest.framework.AutoTestContext;
import com.autotest.framework.common.enums.RelationOperatorEnum;
import com.autotest.framework.context.UserTestContext;
import com.autotest.framework.nodes.StepNode;
import com.autotest.framework.nodes.core.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.net.HttpCookie;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static com.autotest.framework.nodes.StepNode.BUILTIN_VARIABLE_STOP_RUN_CASE;


@Slf4j
public class BaseAutoCaseBuilder {

    protected final Map<String, Object> variables = new HashMap<>();
    protected final AutoTestContext autoTestContext;
    protected final Deque<StepNode> caseStepQueue = new ArrayDeque<>();

    protected final Map<String, String> headers = new HashMap<>();

    protected final Collection<HttpCookie> cookies = new ArrayList<>();

    protected final AtomicInteger stepSeqGenerator = new AtomicInteger(0);

    public BaseAutoCaseBuilder(UserTestContext userTestContext) {
        autoTestContext = new AutoTestContext(userTestContext, variables, headers, cookies);
    }

    public BaseAutoCaseBuilder postWithSuperAdmin(String stepName, String uri, Object body) {
        return postWithSuperAdmin(stepName, uri, JSON.toJSONString(body));
    }

    public BaseAutoCaseBuilder postWithSuperAdmin(String stepName, String uri, String body) {
        return this;
    }

    public BaseAutoCaseBuilder formWithSuperAdmin(String stepName, String uri, Map<String, Object> requestMap) {
        return this;
    }

    public BaseAutoCaseBuilder getWithSuperAdmin(String stepName, String uri) {
        return this;
    }

    public BaseAutoCaseBuilder postWithCompanySuperAdmin(String stepName, String uri, Object body) {
        return postWithCompanySuperAdmin(stepName, uri, JSON.toJSONString(body));
    }

    public BaseAutoCaseBuilder postWithCompanySuperAdmin(String stepName, String uri, String body) {
        return this;
    }

    public BaseAutoCaseBuilder postWithCompanySuperAdmin(String stepName, @NotEmpty String uri, String body, String saveFileName) {
        return this;
    }

    public BaseAutoCaseBuilder formWithCompanySuperAdmin(String stepName, String uri, Map<String, Object> requestMap) {
        return this;
    }

    public BaseAutoCaseBuilder getWithCompanySuperAdmin(String stepName, String uri) {
        return this;
    }

    public BaseAutoCaseBuilder getWithCompanySuperAdmin(String stepName, @NotBlank String uri, String saveFileName) {
        return this;
    }

    public BaseAutoCaseBuilder postWithApplicationToken(String stepName, String uri, Object body) {
        return postWithCompanySuperAdmin(stepName, uri, JSON.toJSONString(body));
    }

    public BaseAutoCaseBuilder postWithApplicationToken(String stepName, String uri, String body) {
        return this;
    }

    public BaseAutoCaseBuilder getWithApplicationToken(String stepName, String uri) {
        return this;
    }

    public BaseAutoCaseBuilder getWithCompanySystemAdmin(String stepName, @NotEmpty String uri) {
        return this;
    }

    public BaseAutoCaseBuilder getWithCompanyBusinessAdmin(String stepName, @NotEmpty String uri) {
        return this;
    }

    public BaseAutoCaseBuilder getWithCompanyAuditAdmin(String stepName, @NotEmpty String uri) {
        return this;
    }

    public BaseAutoCaseBuilder headers(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public BaseAutoCaseBuilder bearerTokenVariable(String stepName, String variableName) {
        caseStepQueue.offer(new BearerTokenVariableNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, variableName));
        return this;
    }

    public BaseAutoCaseBuilder removeHeader(String key) {
        headers.remove(key);
        return this;
    }

    public BaseAutoCaseBuilder post(String stepName, String uri, String body) {
        caseStepQueue.offer(new HttpStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, null, "POST", uri, body));
        return this;
    }

    public BaseAutoCaseBuilder get(String stepName, String uri) {
        caseStepQueue.offer(new HttpStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, null, "GET", uri));
        return this;
    }

    public BaseAutoCaseBuilder getWithMessagesDigest(String stepName, String uri) {
        caseStepQueue.offer(new HttpStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, "MessagesDigest", "GET", uri));
        return this;
    }

    public BaseAutoCaseBuilder postWithMessagesDigest(String stepName, String uri, String body) {
        caseStepQueue.offer(new HttpStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, "MessagesDigest", "POST", uri, body));
        return this;
    }

    public BaseAutoCaseBuilder assertResponse(String stepName) {
        caseStepQueue.offer(new AssertNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext));
        return this;
    }

    public BaseAutoCaseBuilder assertResponse(String stepName, String expectedResponseBody) {
        caseStepQueue.offer(new AssertNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, expectedResponseBody));
        return this;
    }

    public BaseAutoCaseBuilder assertResponse(String stepName, String path, Object expectedValue) {
        caseStepQueue.offer(new AssertNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, path, expectedValue));
        return this;
    }

    public BaseAutoCaseBuilder assertResponseAndVariable(String stepName, String path, RelationOperatorEnum relationOperator, String variableName) {
        caseStepQueue.offer(new AssertNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, 2, path, relationOperator, variableName));
        return this;
    }

    public BaseAutoCaseBuilder assertVariableAndVariable(String stepName, String variableName1, RelationOperatorEnum relationOperator, String variableName2) {
        caseStepQueue.offer(new AssertNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, 3, variableName1, relationOperator, variableName2));
        return this;
    }

    public BaseAutoCaseBuilder assertResponse(String stepName, String path, RelationOperatorEnum relationOperator, Object expectedValue) {
        caseStepQueue.offer(new AssertNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, 1, path, relationOperator, expectedValue));
        return this;
    }

    public BaseAutoCaseBuilder assertResponse(String stepName, String path, RelationOperatorEnum relationOperator) {
        caseStepQueue.offer(new AssertNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, 1, path, relationOperator, null));
        return this;
    }

    public BaseAutoCaseBuilder sleep(String stepName, int seconds) {
        caseStepQueue.offer(new SleepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, seconds));
        return this;
    }

    /**
     * 更新用例执行过程中存储的变量值
     *
     * @param variableName
     * @param variableValue
     * @return
     */
    public BaseAutoCaseBuilder updateVariable(String variableName, Object variableValue) {
        caseStepQueue.offer(new UpdateVariableNode(stepSeqGenerator.incrementAndGet(), "更新变量[" + variableName + "]的值", autoTestContext, variableName, variableValue));
        return this;
    }

    public BaseAutoCaseBuilder updateVariable(Map<String, Object> variables) {
        caseStepQueue.offer(new UpdateVariableNode(stepSeqGenerator.incrementAndGet(), "更新变量[" + variables.keySet() + "]的值", autoTestContext, variables));
        return this;
    }

    /**
     * 更新用例执行过程中存储的全局变量值
     *
     * @param variableName
     * @param variableValue
     * @return
     */
    public BaseAutoCaseBuilder updateGlobalVariable(String variableName, Object variableValue) {
        caseStepQueue.offer(new UpdateGlobalVariableNode(stepSeqGenerator.incrementAndGet(), "更新全局变量[" + variableName + "]的值", autoTestContext, variableName, variableValue));
        return this;
    }

    /**
     * 提取http响应的参数的值,存入用例变量
     *
     * @param stepName
     * @param path         json参数路径
     * @param variableName 变量名
     * @return
     */
    public BaseAutoCaseBuilder extractResponse(String stepName, String path, String variableName) {
        caseStepQueue.offer(new ExtractResponseNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, path, variableName, 1));
        return this;
    }

    /**
     * 提取http响应的参数的值,存入全局变量
     *
     * @param stepName
     * @param path         json参数路径
     * @param variableName 变量名
     * @return
     */
    public BaseAutoCaseBuilder extractResponseToGlobal(String stepName, String path, String variableName) {
        caseStepQueue.offer(new ExtractResponseNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, path, variableName, 2));
        return this;
    }

    /**
     * 执行断言表达式
     * 支持 == != > >= < <= AND OR 逻辑操作
     * 如: [$.statusCode] == 200 OR [$.comments] == '应用名称不能重复' OR ${v_companyName} == '应用名称不能重复'
     * 如: [$.statusCode] == true
     * 如: [$.statusCode] == false
     * 如: [$.statusCode] == null
     * 中括号: [表示前一个http请求响应的json参数路径]
     * 美元符大括号: ${表示变量名}
     * 单引号: '字符串'
     *
     * @param stepName
     * @param assertExpression
     * @return
     */
    public BaseAutoCaseBuilder assertResponseExpression(String stepName, String assertExpression) {
        caseStepQueue.offer(new AssertResponseExpressionNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, assertExpression));
        return this;
    }

    public BaseAutoCaseBuilder assertSuccess() {
        caseStepQueue.offer(new AssertResponseExpressionNode(stepSeqGenerator.incrementAndGet(), "断言statusCode==200", autoTestContext, "[$.statusCode] == 200"));
        return this;
    }

    public BaseAutoCaseBuilder assertFail() {
        caseStepQueue.offer(new AssertResponseExpressionNode(stepSeqGenerator.incrementAndGet(), "断言statusCode!=200", autoTestContext, "[$.statusCode] != 200"));
        return this;
    }


    public BaseAutoCaseBuilder beginIf(String stepName, String conditionExpression) {
        caseStepQueue.offer(new BeginIfNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, conditionExpression));
        return this;
    }


    public BaseAutoCaseBuilder endIf(String stepName) {
        caseStepQueue.offer(new EndIfNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext));
        return this;
    }

    public BaseAutoCaseBuilder executePlatformSql(String stepName, String sql) {
        caseStepQueue.offer(new JdbcStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, sql).setDatasourceName("datasource.cipherPlatform"));
        return this;
    }

    /**
     * 执行自定义的代码
     *
     * @param stepName
     * @param action
     * @return
     */
    public BaseAutoCaseBuilder executeCustomCode(String stepName, Consumer<AutoTestContext> action) {
        caseStepQueue.offer(new ExecuteCustomCodeNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, action));
        return this;
    }

    public BaseAutoCaseBuilder execShell(String stepName,
                                             String ip, Integer port, String username, String password, String cmd) {
        caseStepQueue.offer(new ExecShellNode(stepSeqGenerator.incrementAndGet(), stepName,
                autoTestContext, ip, port, username, password, cmd));
        return this;
    }

    public BaseAutoCaseBuilder executeAviatorExpression(String stepName, String expression) {
        caseStepQueue.offer(new ExecuteAviatorExpressionNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, expression));
        return this;
    }

    public BaseAutoCaseBuilder ifTrueCaseExit(String stepName, String aviatorExpression) {
        caseStepQueue.offer(new IfTrueCaseExitNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, aviatorExpression));
        return this;
    }

    public BaseAutoCaseBuilder executeSqlAutoIncrementId(String stepName, String datasourceName, String sql) {
        caseStepQueue.offer(new JdbcStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, sql)
                .setDatasourceName(datasourceName)
                .setAutoIncrementColumnName("ID"));
        return this;
    }

    public BaseAutoCaseBuilder executeSqlAutoIncrementId(String stepName, String datasourceName, String sql, Object... params) {
        caseStepQueue.offer(new JdbcStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, sql, params)
                .setDatasourceName(datasourceName)
                .setAutoIncrementColumnName("ID"));
        return this;
    }

    public BaseAutoCaseBuilder executeSql(String stepName, String datasourceName, String sql) {
        caseStepQueue.offer(new JdbcStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, sql)
                .setDatasourceName(datasourceName));
        return this;
    }

    public BaseAutoCaseBuilder executeSql(String stepName, String datasourceName, String sql, Object... params) {
        caseStepQueue.offer(new JdbcStepNode(stepSeqGenerator.incrementAndGet(), stepName, autoTestContext, sql, params)
                .setDatasourceName(datasourceName));
        return this;
    }

    public void runCase(Deque<StepNode> caseStepQueue) throws Exception {
        for (; ; ) {
            StepNode stepNode = caseStepQueue.poll();
            if (stepNode == null) {
                break;
            }
            Boolean stopRunCase = (Boolean) autoTestContext.getVariableObject(BUILTIN_VARIABLE_STOP_RUN_CASE);
            if (stopRunCase != null && stopRunCase) {
                log.info("[{}] 用例已经被停止执行", autoTestContext.taskId);
                break;
            }
            stepNode.run();
        }
    }

    public BaseAutoCaseBuilder executeDbEncDecModeSql(String stepName, String sql) {
        return this;
    }

    public BaseAutoCaseBuilder executeDbEncDecModeSql(String stepName, String sql, Object... params) {
        return this;
    }

    public BaseAutoCaseBuilder executeProxySqlAutoIncrementId(String stepName, String sql) {
        return this;
    }

    public BaseAutoCaseBuilder executeProxySqlAutoIncrementId(String stepName, String sql, Object... params) {
        return this;
    }

    public BaseAutoCaseBuilder executeProxyJdbcSql(String stepName, String sql) {
        return this;
    }

    public BaseAutoCaseBuilder executeProxyJdbcSql(String stepName, String sql, Object... params) {
        return this;
    }

    public BaseAutoCaseBuilder executeOlymJdbcSqlAutoIncrementId(String stepName, String sql) {
        return this;
    }

    public BaseAutoCaseBuilder executeOlymJdbcSqlAutoIncrementId(String stepName, String sql, Object... params) {
        return this;
    }

    public BaseAutoCaseBuilder executeOlymJdbcSql(String stepName, String sql) {
        return this;
    }

    public BaseAutoCaseBuilder executeOlymJdbcSql(String stepName, String sql, Object... params) {
        return this;
    }

    public BaseAutoCaseBuilder executeDbEncDecModeSqlAutoIncrementId(String stepName, String sql) {
        return this;
    }

    public BaseAutoCaseBuilder executeDbEncDecModeSqlAutoIncrementId(String stepName, String sql, Object... params) {
        return this;
    }

    public BaseAutoCaseBuilder  executeDbEncDecDirectSql(String stepName, String sql) {
        return this;
    }

    public BaseAutoCaseBuilder  executeDbEncDecDirectSql(String stepName, String sql, Object... params) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbEncrypt(String stepName,
                                            String plaintext,
                                            String database, String schema, String tableName,
                                            String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                            byte[] outCipherBuffer, int[] outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbEncryptTimestamp(String stepName,
                                                     String plaintext,
                                                     String database, String schema, String tableName, String fieldName,
                                                     long[] outCipher) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbDecryptTimestamp(String stepName,
                                                     String ciphertext,
                                                     String database, String schema, String tableName, String fieldName,
                                                     long[] outCipher) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbEncryptTimestampEx(String stepName,
                                                       String plaintext,
                                                       String database, String schema, String tableName, String fieldName,
                                                       long[] outCipher) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbDecryptTimestampEx(String stepName,
                                                       String ciphertext,
                                                       String database, String schema, String tableName, String fieldName,
                                                       long[] outCipher) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbCalculateQueries(String stepName,
                                                     String plaintext,
                                                     String database, String schema, String tableName,
                                                     String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                                     byte[] outCipherBuffer, int[] outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbCalculateCondition(String stepName,
                                                       String plaintext,
                                                       String database, String schema, String tableName,
                                                       String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                                       byte[] outCipherBuffer, int[] outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbCalculateQueriesEx(String stepName,
                                                       String plaintext,
                                                       String database, String schema, String tableName,
                                                       String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                                       byte[] outCipherBuffer, int[] outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbCalculateConditionEx(String stepName,
                                                         String plaintext,
                                                         String database, String schema, String tableName,
                                                         String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                                         byte[] outCipherBuffer, int[] outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbEncryptAndHash(String stepName,
                                                   String plaintext,
                                                   String database, String schema, String tableName,
                                                   String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                                   byte[] outCipherBuffer, int[] outCipherLen, byte[] hashBuffer, int[] hashLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbHash(String stepName,
                                         String plaintext,
                                         String database, String schema, String tableName,
                                         String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                         byte[] outCipherBuffer, int[] outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbHashEx(String stepName,
                                           String plaintext,
                                           String database, String schema, String tableName,
                                           String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                           byte[] outCipherBuffer, int[] outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbEncryptEx(String stepName,
                                              String plaintext,
                                              String database, String schema, String tableName,
                                              String fieldName, byte[] inPlaintextBuffer, long inPlaintextLen,
                                              byte[] outCipherBuffer, int[] outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbDecrypt(String stepName,
                                            String plaintextLen,
                                            String inCipher, String inCipherLen,
                                            String outPlaintextBufferSize, String outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbDecryptEx(String stepName,
                                              String database, String schema, String tableName, String fieldName,
                                              String plaintextLen,
                                              String inCipher, String inCipherLen,
                                              String outPlaintextBufferSize, String outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder DMDbKeyDecryptEx(String stepName,
                                                 String database, String schema, String tableName, String fieldName,
                                                 String plaintextLen,
                                                 String inCipher, String inCipherLen,
                                                 String outPlaintextBufferSize, String outCipherLen) {
        return this;
    }

    public BaseAutoCaseBuilder getCipherPltToken(String stepName, String username, String password) {
        return this;
    }


    public BaseAutoCaseBuilder run() throws Exception {
        runCase(caseStepQueue);
        return this;
    }

    public BaseAutoCaseBuilder pairwiseTest() throws Exception {
        return this;
    }

    public BaseAutoCaseBuilder nullTest() throws Exception {
        return this;
    }

    public BaseAutoCaseBuilder emptyTest() throws Exception {
        return this;
    }
}


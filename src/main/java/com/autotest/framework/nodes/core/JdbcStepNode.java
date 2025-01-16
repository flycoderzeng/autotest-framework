package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import com.autotest.framework.utils.JDBCUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Data
public class JdbcStepNode extends StepNode {
    private String sql;
    private Object[] params;
    private String datasourceName;
    private String autoIncrementColumnName;

    public JdbcStepNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String sql) {
        super(stepSeq, stepName, autoTestContext);
        this.sql = sql;
    }

    public JdbcStepNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String sql, Object... params) {
        super(stepSeq, stepName, autoTestContext);
        this.sql = sql;
        this.params = params;
    }

    public JdbcStepNode setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
        return this;
    }

    public JdbcStepNode setAutoIncrementColumnName(String autoIncrementColumnName) {
        this.autoIncrementColumnName = autoIncrementColumnName;
        return this;
    }

    @Override
    public void run() throws Exception {
        log.info(stepName);
        if (skip()) {
            return;
        }
        Connection connection = autoTestContext.userTestContext.getConnection(this.datasourceName);
        log.info("datasourceName: {}", this.datasourceName);
        if (connection == null) {
            throw new Exception("数据库连接是null");
        }

        String actualSql = replacePlaceholder(sql);
        log.info("[{}] 实际的sql语句: {}", autoTestContext.taskId, actualSql);

        if (this.params != null && this.params.length > 0) {
            for (int i = 0; i < this.params.length; i++) {
                if (Pattern.matches(TEMPLATE_CASE_VARIABLE_PATTERN.pattern(), this.params[i] + "")) {
                    List<String> listVariableName = extractVariableName(this.params[i] + "", TEMPLATE_CASE_VARIABLE_PATTERN);
                    if (listVariableName != null && !listVariableName.isEmpty()) {
                        this.params[i] = autoTestContext.getVariableObject(listVariableName.get(0));
                    }
                }
            }
        }
        try {
            execSql(actualSql, connection);
        } catch (Exception e) {
            throw e;
        } finally {
            connection.close();
        }
    }

    public void execSql(String actualSql, Connection connection) throws Exception {
        if (actualSql.toLowerCase().startsWith("select")) {
            List<Map<String, Object>> maps;
            if (this.params != null && this.params.length > 0) {
                maps = JDBCUtils.doQuery(connection, actualSql, this.params);
            } else {
                maps = JDBCUtils.doQuery(connection, actualSql);
            }
            log.info("[{}] SQL执行结果: {}", autoTestContext.taskId, gson.toJson(maps));
            autoTestContext.putVariableObject(BUILTIN_VARIABLE_RESPONSE, gson.toJson(maps));
        }
        if (actualSql.toLowerCase().startsWith("update") || actualSql.toLowerCase().startsWith("delete")) {
            int effectedRows;
            if (this.params != null && this.params.length > 0) {
                effectedRows = JDBCUtils.doUpdate(connection, actualSql, this.params);
            } else {
                effectedRows = JDBCUtils.doExecuteUpdate(connection, actualSql);
            }
            log.info("[{}] 影响了 {} 行数据", autoTestContext.taskId, effectedRows);
            autoTestContext.putVariableObject(BUILTIN_VARIABLE_UPDATED_ROWS, effectedRows);
        }
        if (actualSql.toLowerCase().startsWith("insert")) {
            if (StringUtils.isNoneBlank(this.autoIncrementColumnName)) {
                long generatedKey;
                if (this.params != null && this.params.length > 0) {
                    generatedKey = JDBCUtils.insertReturnId(connection, actualSql, this.autoIncrementColumnName, this.params);
                } else {
                    generatedKey = JDBCUtils.insertReturnId(connection, actualSql, this.autoIncrementColumnName);
                }
                log.info("[{}], 自增主键值是: {}", autoTestContext.taskId, generatedKey);
                autoTestContext.putVariableObject(BUILTIN_VARIABLE_GENERATED_KEY, generatedKey);
            } else {
                if (this.params != null && this.params.length > 0) {
                    JDBCUtils.doUpdate(connection, actualSql, this.params);
                } else {
                    JDBCUtils.doExecute(connection, actualSql);
                }
            }
        }
    }
}

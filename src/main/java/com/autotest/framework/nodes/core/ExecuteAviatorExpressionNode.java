package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class ExecuteAviatorExpressionNode extends StepNode {
    Logger logger = LoggerFactory.getLogger(ExecuteAviatorExpressionNode.class);
    private final String expression;

    public ExecuteAviatorExpressionNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String expression) {
        super(stepSeq, stepName, autoTestContext);
        this.expression = expression;
    }

    @Override
    public void run() throws Exception {
        if (skip()) {
            return;
        }
        logger.info("[{}]===========>执行步骤: {}", autoTestContext.taskId, stepName);
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Boolean result = (Boolean) compiledExp.execute(autoTestContext.variables);
        if (!result) {
            logger.info("[{}] 表达式: {} 结果是false, 变量内容: {}", autoTestContext.taskId, expression, gson.toJson(autoTestContext.variables));
        }
        Assert.assertTrue(result);
    }
}

package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IfTrueCaseExitNode extends StepNode {
    Logger logger = LoggerFactory.getLogger(IfTrueCaseExitNode.class);
    private final String aviatorExpression;

    public IfTrueCaseExitNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String aviatorExpression) {
        super(stepSeq, stepName, autoTestContext);
        this.aviatorExpression = aviatorExpression;
    }

    @Override
    public void run() throws Exception {
        if (skip()) {
            return;
        }
        logger.info("[{}]===========>执行步骤: {}", autoTestContext.taskId, stepName);
        Expression compiledExp = AviatorEvaluator.compile(aviatorExpression, true);
        Boolean result = (Boolean) compiledExp.execute(autoTestContext.variables);
        logger.info("[{}] 表达式: {} 结果是 {}, 变量内容: {}", autoTestContext.taskId, aviatorExpression, result, gson.toJson(autoTestContext.variables));
        if (result) {
            logger.info("[{}] 用例停止执行", autoTestContext.taskId);
            autoTestContext.putVariableObject(BUILTIN_VARIABLE_STOP_RUN_CASE, true);
        }
    }
}

package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

@Slf4j
@Data
public class AssertResponseExpressionNode extends StepNode {
    private String assertExpression;

    public AssertResponseExpressionNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String assertExpression) {
        super(stepSeq, stepName, autoTestContext);
        this.assertExpression = assertExpression;
    }

    @Override
    public void run() throws Exception {
        log.info("===========>执行步骤: {}", stepName);
        if (skip()) {
            return;
        }
        assertExpression = replacePathPlaceholder(assertExpression);
        log.info(assertExpression);
        Object result = execExpression(assertExpression);
        log.info("断言表达式结果: {}", result);
        Assert.assertTrue((Boolean) result);
    }
}

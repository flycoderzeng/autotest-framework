package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
@Data
public class BeginIfNode extends StepNode {
    private String conditionExpression;

    public BeginIfNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String conditionExpression) {
        super(stepSeq, stepName, autoTestContext);
        this.conditionExpression = conditionExpression;
    }

    @Override
    public void run() throws Exception {
        log.info("===========>执行步骤: begin if {}", stepName);
        if (skip()) {
            Stack<Boolean> ifLogicStack = (Stack<Boolean>) autoTestContext.getVariableObject(BUILTIN_VARIABLE_IN_IF_RESULT);
            if(ifLogicStack != null) {
                ifLogicStack.add(false);
            }
            autoTestContext.putVariableObject(BUILTIN_VARIABLE_IN_IF_RESULT, ifLogicStack);
            return;
        }
        log.info(conditionExpression);
        autoTestContext.putVariableObject(BUILTIN_VARIABLE_IN_IF_LOGIC, true);
        Object result = execExpression(conditionExpression);
        log.info("条件表达式结果: {}", result);
        Stack<Boolean> ifLogicStack = (Stack<Boolean>) autoTestContext.getVariableObject(BUILTIN_VARIABLE_IN_IF_RESULT);
        if(ifLogicStack == null) {
            ifLogicStack = new Stack<>();
        }
        ifLogicStack.add((Boolean) result);
        autoTestContext.putVariableObject(BUILTIN_VARIABLE_IN_IF_RESULT, ifLogicStack);
    }
}

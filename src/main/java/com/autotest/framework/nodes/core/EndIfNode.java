package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
@Data
public class EndIfNode extends StepNode {

    public EndIfNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext) {
        super(stepSeq, stepName, autoTestContext);
    }

    @Override
    public void run() throws Exception {
        log.info("===========>执行步骤: end if, {}", stepName);
        Stack<Boolean> ifLogicStack = (Stack<Boolean>) autoTestContext.getVariableObject(BUILTIN_VARIABLE_IN_IF_RESULT);
        if(ifLogicStack == null || ifLogicStack.isEmpty()) {
            autoTestContext.putVariableObject(BUILTIN_VARIABLE_IN_IF_LOGIC, false);
        }
        if(ifLogicStack != null && !ifLogicStack.isEmpty()) {
            log.info("弹出一个if");
            ifLogicStack.pop();
        }
        autoTestContext.putVariableObject(BUILTIN_VARIABLE_IN_IF_RESULT, ifLogicStack);
    }
}

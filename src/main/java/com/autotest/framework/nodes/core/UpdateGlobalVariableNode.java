package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateGlobalVariableNode extends StepNode {
    Logger logger = LoggerFactory.getLogger(UpdateGlobalVariableNode.class);
    private final String variableName;
    private final Object variableValue;

    public UpdateGlobalVariableNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext,
                                    String variableName, Object variableValue) {
        super(stepSeq, stepName, autoTestContext);
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    @Override
    public void run() throws Exception {
        logger.info("===========>执行步骤: {}", stepName);
        logger.info("新的变量值: {}", variableValue);
        autoTestContext.putGlobalVariableObject(variableName, variableValue);
    }
}

package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UpdateVariableNode extends StepNode {
    Logger logger = LoggerFactory.getLogger(UpdateVariableNode.class);
    public String variableName;
    public Object variableValue;
    public Map<String, Object> variables;

    public UpdateVariableNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext,
                              String variableName, Object variableValue) {
        super(stepSeq, stepName, autoTestContext);
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    public UpdateVariableNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, Map<String, Object> variables) {
        super(stepSeq, stepName, autoTestContext);
        this.variables = variables;
    }

    @Override
    public void run() throws Exception {
        logger.info("===========>执行步骤: {}", stepName);
        logger.info("新的变量值: {}", variableValue);
        if(variables == null) {
            autoTestContext.putVariableObject(variableName, variableValue);
        }else{
            variables.forEach((k, v) -> {
                autoTestContext.putVariableObject(k, v);
            });
        }
    }
}

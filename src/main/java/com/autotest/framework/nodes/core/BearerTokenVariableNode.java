package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BearerTokenVariableNode extends StepNode {
    private String variableName;

    public BearerTokenVariableNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String variableName) {
        super(stepSeq, stepName, autoTestContext);
        this.variableName = variableName;
    }

    @Override
    public void run() throws Exception {
        autoTestContext.putHttpHeader("Authorization", "Bearer " + autoTestContext.getVariableString(variableName));
    }
}

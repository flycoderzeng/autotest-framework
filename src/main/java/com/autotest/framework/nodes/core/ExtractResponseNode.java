package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
public class ExtractResponseNode extends StepNode {
    private final String path;
    private final String variableName;
    private final int type;

    public ExtractResponseNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String path, String variableName, int type) {
        super(stepSeq, stepName, autoTestContext);
        this.path = path;
        this.variableName = variableName;
        this.type = type;
    }

    @Override
    public void run() throws Exception {
        if (skip()) {
            return;
        }
        log.info("===========>执行步骤: {}", stepName);
        if (path != null) {
            Object result = JsonPath.read(autoTestContext.getVariableString(BUILTIN_VARIABLE_RESPONSE), path);
            Object object = result;
            if (result != null && result instanceof List && !(((List<?>) result).isEmpty())) {
                object = ((List<?>) result).get(0);
            }
            log.info("提取路径: {}, 提取结果: {}", path, object);
            if (type == 1) {
                autoTestContext.putVariableObject(variableName, object);
            }
            if (type == 2) {
                autoTestContext.putGlobalVariableObject(variableName, object);
            }
            return;
        }
    }
}

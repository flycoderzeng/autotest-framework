package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
@Data
public class ExecuteCustomCodeNode extends StepNode {
    Consumer<AutoTestContext> action;

    public ExecuteCustomCodeNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, Consumer<AutoTestContext> action) {
        super(stepSeq, stepName, autoTestContext);
        this.action = action;
    }

    @Override
    public void run() throws Exception {
        log.info("执行自定义执行代码步骤: {}", stepName);
        if (skip()) {
            return;
        }
        action.accept(autoTestContext);
    }
}

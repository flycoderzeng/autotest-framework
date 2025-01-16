package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class SleepNode extends StepNode {
    Logger logger = LoggerFactory.getLogger(SleepNode.class);

    int seconds;

    public SleepNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, int seconds) {
        super(stepSeq, stepName, autoTestContext);
        this.seconds = seconds;
    }

    @Override
    public void run() throws Exception {
        logger.info("===========>执行休眠步骤: {}", stepName);
        if (skip()) {
            return;
        }
        TimeUnit.SECONDS.sleep(seconds);
    }
}

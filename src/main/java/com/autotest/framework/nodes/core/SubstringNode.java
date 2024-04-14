package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.nodes.StepNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubstringNode extends StepNode {
    Logger logger = LoggerFactory.getLogger(SubstringNode.class);
    private final String srcData;
    private final String outVariableName;
    private final int beginIndex;
    private final int endIndex;

    public SubstringNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext,
                         String srcData, String outVariableName,
                         int beginIndex, int endIndex) {
        super(stepSeq, stepName, autoTestContext);
        this.srcData = srcData;
        this.outVariableName = outVariableName;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() throws Exception {
        logger.info("===========>执行字符串截取步骤: {}", stepName);
        String actualSrcData = replacePlaceholder(srcData);
        logger.info("原始数据: {}", actualSrcData);
        final String substring = actualSrcData.substring(beginIndex, endIndex);
        logger.info("截取后数据: {}", substring);
        logger.info("将结果存入用例变量: {}", outVariableName);
        autoTestContext.putVariableObject(outVariableName, substring);
    }
}

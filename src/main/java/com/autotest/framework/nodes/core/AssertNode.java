package com.autotest.framework.nodes.core;

import com.autotest.framework.AutoTestContext;
import com.autotest.framework.common.enums.RelationOperatorEnum;
import com.autotest.framework.nodes.StepNode;
import com.autotest.framework.utils.AssertUtils;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

@Slf4j
@Data
public class AssertNode extends StepNode {
    private String path;
    private Object expectedValue;
    private String variableName;
    private int rightOperandType;
    private String expectedResponseBody;
    private RelationOperatorEnum relationOperator;

    public AssertNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext) {
        super(stepSeq, stepName, autoTestContext);
    }

    public AssertNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String expectedResponseBody) {
        super(stepSeq, stepName, autoTestContext);
        this.expectedResponseBody = expectedResponseBody;
    }

    public AssertNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, String path, Object expectedValue) {
        super(stepSeq, stepName, autoTestContext);
        this.rightOperandType = 1;
        this.path = path;
        this.relationOperator = RelationOperatorEnum.EQUAL;
        this.expectedValue = expectedValue;
    }

    public AssertNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, int rightOperandType,
                      String path, RelationOperatorEnum relationOperator, Object expectedValue) {
        super(stepSeq, stepName, autoTestContext);
        this.rightOperandType = rightOperandType;
        this.path = path;
        this.relationOperator = relationOperator;
        this.expectedValue = expectedValue;
    }

    public AssertNode(Integer stepSeq, String stepName, AutoTestContext autoTestContext, int rightOperandType,
                      String path, RelationOperatorEnum relationOperator, String variableName) {
        super(stepSeq, stepName, autoTestContext);
        this.rightOperandType = rightOperandType;
        this.path = path;
        this.relationOperator = relationOperator;
        this.variableName = variableName;
    }

    @Override
    public void run() throws Exception {
        if (skip()) {
            return;
        }
        log.info("===========>执行步骤: {}", stepName);
        if (StringUtils.isNoneBlank(expectedResponseBody)) {
            Assert.assertEquals(autoTestContext.getVariableString(BUILTIN_VARIABLE_RESPONSE), expectedResponseBody);
            return;
        }
        if (path != null && relationOperator != null) {
            path = replacePathPlaceholder(path);
            Object result;
            if (rightOperandType == 3) {
                result = autoTestContext.getVariableString(path);
            } else {
                if (path.startsWith("$.") && !RelationOperatorEnum.PATH_NOT_EXISTS.equals(relationOperator)) {
                    result = JsonPath.read(autoTestContext.getVariableString(BUILTIN_VARIABLE_RESPONSE), path);
                } else if (path.startsWith("$.") && RelationOperatorEnum.PATH_NOT_EXISTS.equals(relationOperator)) {
                    try {
                        result = JsonPath.read(autoTestContext.getVariableString(BUILTIN_VARIABLE_RESPONSE), path);
                    } catch (com.jayway.jsonpath.PathNotFoundException e) {
                        result = e;
                    }
                } else if (path.startsWith("${")) {
                    result = replacePlaceholder(path);
                } else {
                    result = path;
                }
            }
            Object actualExpectedValue = expectedValue;
            if (rightOperandType == 2 || rightOperandType == 3) {
                actualExpectedValue = autoTestContext.getVariableString(variableName);
            }
            if (expectedValue instanceof String) {
                actualExpectedValue = replacePlaceholder(String.valueOf(expectedValue));
            }
            String info = "参数路径: " + path + ", 值: " + result + " " + relationOperator.desc() + " " + actualExpectedValue;
            log.info(info);
            Assert.assertTrue(AssertUtils.compare(result, relationOperator, AssertUtils.objectToString(actualExpectedValue)));
            return;
        }
        if (!StringUtils.equals(autoTestContext.getVariableString(BUILTIN_VARIABLE_RESPONSE), "{\"comments\":\"成功\",\"statusCode\":200}")) {
            Assert.assertEquals(autoTestContext.getVariableString(BUILTIN_VARIABLE_RESPONSE), "{\"statusCode\":200,\"comments\":\"成功\"}");
        }
    }
}

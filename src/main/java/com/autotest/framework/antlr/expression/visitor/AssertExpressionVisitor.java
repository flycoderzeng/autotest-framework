package com.autotest.framework.antlr.expression.visitor;

import cn.hutool.core.util.ReUtil;
import com.autotest.framework.AutoTestContext;
import com.autotest.framework.antlr.expression.core.ExpressionBaseVisitor;
import com.autotest.framework.antlr.expression.core.ExpressionLexer;
import com.autotest.framework.antlr.expression.core.ExpressionParser;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class AssertExpressionVisitor extends ExpressionBaseVisitor<Object> {
    protected AutoTestContext autoTestContext;

    public AssertExpressionVisitor(AutoTestContext autoTestContext) {
        this.autoTestContext = autoTestContext;
    }

    @Override
    public Object visitExpressionWithBr(ExpressionParser.ExpressionWithBrContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public Object visitExpressionWithString(ExpressionParser.ExpressionWithStringContext ctx) {
        String left = ctx.getChild(0).getText();
        String right = ctx.getChild(2).getText();

        if (ctx.op != null) {
            log.info("原式: {} {} {}", left, ctx.op.getText(), right);
            String leftOperand = null;
            String rightOperand = null;

            if (left.startsWith("[")) {
                leftOperand = extract(left) + "";
            } else if (left.startsWith("${")) {
                leftOperand = getVariableValue(left) + "";
            }

            if (right.startsWith("[")) {
                rightOperand = extract(right) + "";
            } else if (right.startsWith("${")) {
                rightOperand = getVariableValue(right) + "";
            } else if (right.equals("true")) {
                rightOperand = "true";
            } else if (right.equals("false")) {
                rightOperand = "false";
            } else if (right.equals("null")) {
                rightOperand = "null";
            } else if (ctx.STRING() != null) {
                rightOperand = ctx.STRING().getText();
                rightOperand = rightOperand.substring(1, rightOperand.length() - 1);
            }
            log.info("实际: {} {} {}", leftOperand, ctx.op.getText(), rightOperand);

            switch (ctx.op.getType()) {
                case ExpressionLexer.EQUAL:
                    return StringUtils.equals(leftOperand, rightOperand);
                case ExpressionLexer.NOT_EQUAL:
                    return !StringUtils.equals(leftOperand, rightOperand);
                default:
                    throw new RuntimeException("unsupported operator type");
            }
        }
        return super.visitExpressionWithString(ctx);
    }

    @Override
    public Object visitExpressionWithNumber(ExpressionParser.ExpressionWithNumberContext ctx) {
        String left = ctx.getChild(0).getText();
        String right = ctx.getChild(2).getText();

        if (ctx.op != null) {
            log.info("原式: {} {} {}", left, ctx.op.getText(), right);
            Double leftOperand = null;
            Double rightOperand = Double.valueOf(right);
            Object leftString = null;
            if (left.startsWith("[")) {
                leftString = extract(left);
            } else if (left.startsWith("${")) {
                leftString = getVariableValue(left);
            } else {
                leftString = left;
            }
            if (leftString != null && ReUtil.isMatch("([0-9][0-9]*)+(.[0-9]+)?", leftString.toString())) {
                leftOperand = Double.valueOf(leftString.toString());
            }
            log.info("实际: {} {} {}", leftOperand, ctx.op.getText(), rightOperand);
            if (leftOperand == null) {
                return false;
            }

            switch (ctx.op.getType()) {
                case ExpressionLexer.EQUAL:
                    return rightOperand.equals(leftOperand);
                case ExpressionLexer.NOT_EQUAL:
                    return !rightOperand.equals(leftOperand);
                case ExpressionLexer.LESS_THAN:
                    return leftOperand < rightOperand;
                case ExpressionLexer.LESS_EQUAL:
                    return leftOperand <= rightOperand;
                case ExpressionLexer.LARGER_THAN:
                    return leftOperand > rightOperand;
                case ExpressionLexer.LARGER_EQUAL:
                    return leftOperand >= rightOperand;
                default:
                    throw new RuntimeException("unsupported operator type");
            }
        }
        return super.visitExpressionWithNumber(ctx);
    }

    @Override
    public Object visitOrOperation(ExpressionParser.OrOperationContext ctx) {
        Object left = visit(ctx.getChild(0));
        Object right = visit(ctx.getChild(2));
        log.info("{} {} {}", left, ctx.OR().getText(), right);
        if (left instanceof Boolean && right instanceof Boolean) {
            return (Boolean) left || (Boolean) right;
        }
        return false;
    }

    @Override
    public Object visitAndOperation(ExpressionParser.AndOperationContext ctx) {
        Object left = visit(ctx.getChild(0));
        Object right = visit(ctx.getChild(2));
        log.info("{} {} {}", left, ctx.AND().getText(), right);
        if (left instanceof Boolean && right instanceof Boolean) {
            return (Boolean) left && (Boolean) right;
        }
        return false;
    }

    public Object extract(String path) {
        if (path.startsWith("[")) {
            path = path.substring(1, path.length() - 1);
        }
        return JsonPath.read(autoTestContext.getVariableString("__response"), path);
    }

    public Object getVariableValue(String variableName) {
        if (variableName.startsWith("${")) {
            variableName = variableName.substring(2, variableName.length() - 1);
        }
        return autoTestContext.getVariableObject(variableName);
    }
}

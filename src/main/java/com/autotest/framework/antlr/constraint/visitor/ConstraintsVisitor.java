package com.autotest.framework.antlr.constraint.visitor;

import com.autotest.framework.antlr.constraint.core.ConstraintsBaseVisitor;
import com.autotest.framework.antlr.constraint.core.ConstraintsLexer;
import com.autotest.framework.antlr.constraint.core.ConstraintsParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class ConstraintsVisitor extends ConstraintsBaseVisitor<Object> {
    // 用例组合
    private final Map<String, String> group;
    private String stage = "begin";

    public ConstraintsVisitor(Map<String, String> group) {
        this.group = group;
    }

    @Override
    public Object visitCompareString(ConstraintsParser.CompareStringContext ctx) {
        if(StringUtils.equals(stage, "begin")) {
            stage = "expr";
        }
        String left = ctx.getChild(0).getText();
        String right = ctx.getChild(2).getText();
        if (ctx.op != null) {
            log.trace("原式: {} {} {}", left, ctx.op.getText(), right);
        }
        left = left.substring(1, left.length() - 1);
        right = right.substring(1, right.length() - 1);
        final String leftString = group.get(left);
        switch (ctx.op.getType()) {
            case ConstraintsLexer.EQUAL -> {
                return StringUtils.equals(leftString, right);
            }
            case ConstraintsLexer.NOT_EQUAL -> {
                return !StringUtils.equals(leftString, right);
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public Object visitExpressionWithBr(ConstraintsParser.ExpressionWithBrContext ctx) {
        if(StringUtils.equals(stage, "begin")) {
            stage = "expr";
        }
        return visit(ctx.getChild(1));
    }

    @Override
    public Object visitInOperator(ConstraintsParser.InOperatorContext ctx) {
        if(StringUtils.equals(stage, "begin")) {
            stage = "expr";
        }
        String left = ctx.getChild(0).getText();
        String right = ctx.getChild(2).getText();
        log.trace("原式: {} IN {}", left, right);
        left = left.substring(1, left.length() - 1);
        final String fieldValue = group.get(left);
        right = right.substring(1, right.length() - 1);
        final String[] values = right.split(",");
        for (int i = 0; i < values.length; i++) {
            if(values[i].startsWith("\"")) {
                values[i] = values[i].substring(1);
            }
            if(values[i].startsWith("~")) {
                values[i] = values[i].substring(1);
            }
            if(values[i].endsWith("\"")) {
                values[i] = values[i].substring(0, values[i].length()-1);
            }
        }
        return Arrays.stream(values).anyMatch(v -> StringUtils.equals(v, fieldValue));
    }

    @Override
    public Object visitNotOperator(ConstraintsParser.NotOperatorContext ctx) {
        if(StringUtils.equals(stage, "begin")) {
            stage = "expr";
        }
        return super.visitNotOperator(ctx);
    }

    @Override
    public Object visitCompareNumber(ConstraintsParser.CompareNumberContext ctx) {
        if(StringUtils.equals(stage, "begin")) {
            stage = "expr";
        }
        return super.visitCompareNumber(ctx);
    }

    @Override
    public Object visitIfThenStatement(ConstraintsParser.IfThenStatementContext ctx) {
        stage = "if";
        final Boolean visit = (Boolean) visit(ctx.getChild(1));
        if(Boolean.FALSE.equals(visit)) {
            throw new RuntimeException("IF 条件不成立");
        }
        stage = "then";
        return visit(ctx.getChild(3));
    }

    @Override
    public Object visitAndOperator(ConstraintsParser.AndOperatorContext ctx) {
        Object left = visit(ctx.getChild(0));
        Object right = visit(ctx.getChild(2));
        log.trace("{} {} {}", left, ctx.AND().getText(), right);
        if (left instanceof Boolean leftBoolean && right instanceof Boolean rightBoolean) {
            return leftBoolean && rightBoolean;
        }
        return false;
    }

    @Override
    public Object visitOrOperator(ConstraintsParser.OrOperatorContext ctx) {
        Object left = visit(ctx.getChild(0));
        Object right = visit(ctx.getChild(2));
        log.trace("{} {} {}", left, ctx.OR().getText(), right);
        if (left instanceof Boolean leftBoolean && right instanceof Boolean rightBoolean) {
            return leftBoolean || rightBoolean;
        }
        return false;
    }

    @Override
    public Object visitLikeOperator(ConstraintsParser.LikeOperatorContext ctx) {
        if(StringUtils.equals(stage, "begin")) {
            stage = "expr";
        }
        return super.visitLikeOperator(ctx);
    }

    @Override
    public Object visitCompareField(ConstraintsParser.CompareFieldContext ctx) {
        if(StringUtils.equals(stage, "begin")) {
            stage = "expr";
        }
        return super.visitCompareField(ctx);
    }
}

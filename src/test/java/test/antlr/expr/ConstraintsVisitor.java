package test.antlr.expr;

import cn.hutool.core.lang.Dict;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import test.antlr.core.ConstraintsBaseVisitor;
import test.antlr.core.ConstraintsLexer;
import test.antlr.core.ConstraintsParser;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ConstraintsVisitor extends ConstraintsBaseVisitor<Object> {
    // 接口定义
    private Dict apiDict;
    // 接口参数取值
    private Map<String, List> fieldValuesMap;
    // 约束
    private String constraint;
    // 用例组合
    private LinkedHashMap group;
    private String stage = "begin";

    public ConstraintsVisitor(Dict apiDict, Map<String, List> fieldValuesMap, String constraint, LinkedHashMap group) {
        this.apiDict = apiDict;
        this.fieldValuesMap = fieldValuesMap;
        this.constraint = constraint;
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
        final String leftString = (String) group.get(left);
        switch (ctx.op.getType()) {
            case ConstraintsLexer.EQUAL -> {
                if(StringUtils.equals(leftString, right)) {
                    return true;
                }
                return false;
            }
            case ConstraintsLexer.NOT_EQUAL -> {
                if(StringUtils.equals(leftString, right)) {
                    return false;
                }
                return true;
            }
        }
        return super.visitCompareString(ctx);
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
        final String fieldValue = (String) group.get(left);
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
        if(Arrays.stream(values).anyMatch(v -> StringUtils.equals(v, fieldValue))) {
            return true;
        }
        return false;
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
        if(!visit) {
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
        if (left instanceof Boolean && right instanceof Boolean) {
            return (Boolean) left && (Boolean) right;
        }
        return false;
    }

    @Override
    public Object visitOrOperator(ConstraintsParser.OrOperatorContext ctx) {
        Object left = visit(ctx.getChild(0));
        Object right = visit(ctx.getChild(2));
        log.trace("{} {} {}", left, ctx.OR().getText(), right);
        if (left instanceof Boolean && right instanceof Boolean) {
            return (Boolean) left || (Boolean) right;
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

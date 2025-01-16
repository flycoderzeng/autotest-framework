// Generated from C:/Users/zengb/Documents/java-workspace/tm-dev-3.0.0/autotest-framework/src/test/java/test/antlr/expr\Constraints.g4 by ANTLR 4.12.0

// 定义package
package com.autotest.framework.antlr.constraint.core;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ConstraintsParser}.
 */
public interface ConstraintsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code compareString}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompareString(ConstraintsParser.CompareStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compareString}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompareString(ConstraintsParser.CompareStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrOperator(ConstraintsParser.OrOperatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrOperator(ConstraintsParser.OrOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionWithBr}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionWithBr(ConstraintsParser.ExpressionWithBrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionWithBr}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionWithBr(ConstraintsParser.ExpressionWithBrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterInOperator(ConstraintsParser.InOperatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitInOperator(ConstraintsParser.InOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotOperator(ConstraintsParser.NotOperatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotOperator(ConstraintsParser.NotOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compareNumber}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompareNumber(ConstraintsParser.CompareNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compareNumber}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompareNumber(ConstraintsParser.CompareNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compareField}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompareField(ConstraintsParser.CompareFieldContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compareField}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompareField(ConstraintsParser.CompareFieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifThenStatement}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIfThenStatement(ConstraintsParser.IfThenStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifThenStatement}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIfThenStatement(ConstraintsParser.IfThenStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndOperator(ConstraintsParser.AndOperatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndOperator(ConstraintsParser.AndOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code likeOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLikeOperator(ConstraintsParser.LikeOperatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code likeOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLikeOperator(ConstraintsParser.LikeOperatorContext ctx);
}
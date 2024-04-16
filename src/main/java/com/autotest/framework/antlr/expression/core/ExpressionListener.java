// Generated from E:/java/workspace/db-enc-dec-autotest/src/test/java\Expression.g4 by ANTLR 4.12.0

// 定义package
package com.autotest.framework.antlr.expression.core;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code andOperation}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndOperation(ExpressionParser.AndOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andOperation}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndOperation(ExpressionParser.AndOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionWithBr}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionWithBr(ExpressionParser.ExpressionWithBrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionWithBr}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionWithBr(ExpressionParser.ExpressionWithBrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionWithString}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionWithString(ExpressionParser.ExpressionWithStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionWithString}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionWithString(ExpressionParser.ExpressionWithStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionWithNumber}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionWithNumber(ExpressionParser.ExpressionWithNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionWithNumber}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionWithNumber(ExpressionParser.ExpressionWithNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orOperation}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrOperation(ExpressionParser.OrOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orOperation}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrOperation(ExpressionParser.OrOperationContext ctx);
}
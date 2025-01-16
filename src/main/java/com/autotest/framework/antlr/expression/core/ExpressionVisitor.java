// Generated from E:/java/workspace/db-enc-dec-autotest/src/test/java\Expression.g4 by ANTLR 4.12.0

// 定义package
package com.autotest.framework.antlr.expression.core;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code andOperation}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndOperation(ExpressionParser.AndOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionWithBr}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionWithBr(ExpressionParser.ExpressionWithBrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionWithString}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionWithString(ExpressionParser.ExpressionWithStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionWithNumber}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionWithNumber(ExpressionParser.ExpressionWithNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orOperation}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrOperation(ExpressionParser.OrOperationContext ctx);
}
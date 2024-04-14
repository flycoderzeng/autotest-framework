// Generated from C:/Users/zengb/Documents/java-workspace/tm-dev-3.0.0/autotest-framework/src/test/java/test/antlr/expr\Constraints.g4 by ANTLR 4.12.0

// 定义package
package test.antlr.core;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ConstraintsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ConstraintsVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code compareString}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareString(ConstraintsParser.CompareStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrOperator(ConstraintsParser.OrOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionWithBr}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionWithBr(ConstraintsParser.ExpressionWithBrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInOperator(ConstraintsParser.InOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotOperator(ConstraintsParser.NotOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compareNumber}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareNumber(ConstraintsParser.CompareNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compareField}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareField(ConstraintsParser.CompareFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifThenStatement}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenStatement(ConstraintsParser.IfThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndOperator(ConstraintsParser.AndOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code likeOperator}
	 * labeled alternative in {@link ConstraintsParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLikeOperator(ConstraintsParser.LikeOperatorContext ctx);
}
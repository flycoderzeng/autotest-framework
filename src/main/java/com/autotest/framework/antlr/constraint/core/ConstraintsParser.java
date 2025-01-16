// Generated from C:/Users/zengb/Documents/java-workspace/tm-dev-3.0.0/autotest-framework/src/test/java/test/antlr/expr\Constraints.g4 by ANTLR 4.12.0

// 定义package
package com.autotest.framework.antlr.constraint.core;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ConstraintsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, STRING=2, FIELD_NAME=3, NUMBER=4, LPAREN=5, RPAREN=6, EQUAL=7, NOT_EQUAL=8, 
		LESS_THAN=9, LESS_EQUAL=10, LARGER_THAN=11, LARGER_EQUAL=12, AND=13, OR=14, 
		IF=15, THEN=16, IN=17, LIKE=18, NOT=19, VALUES=20, SEMI=21;
	public static final int
		RULE_expression = 0;
	private static String[] makeRuleNames() {
		return new String[] {
			"expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'('", "')'", "'='", "'<>'", "'<'", "'<='", 
			"'>'", "'>='", "'AND'", "'OR'", "'IF'", "'THEN'", "'IN'", "'LIKE'", "'NOT'", 
			null, "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "STRING", "FIELD_NAME", "NUMBER", "LPAREN", "RPAREN", "EQUAL", 
			"NOT_EQUAL", "LESS_THAN", "LESS_EQUAL", "LARGER_THAN", "LARGER_EQUAL", 
			"AND", "OR", "IF", "THEN", "IN", "LIKE", "NOT", "VALUES", "SEMI"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Constraints.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ConstraintsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompareStringContext extends ExpressionContext {
		public Token op;
		public TerminalNode FIELD_NAME() { return getToken(ConstraintsParser.FIELD_NAME, 0); }
		public TerminalNode STRING() { return getToken(ConstraintsParser.STRING, 0); }
		public TerminalNode EQUAL() { return getToken(ConstraintsParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ConstraintsParser.NOT_EQUAL, 0); }
		public CompareStringContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener) ((ConstraintsListener)listener).enterCompareString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitCompareString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor) return ((ConstraintsVisitor<? extends T>)visitor).visitCompareString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrOperatorContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(ConstraintsParser.OR, 0); }
		public OrOperatorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterOrOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitOrOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitOrOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionWithBrContext extends ExpressionContext {
		public TerminalNode LPAREN() { return getToken(ConstraintsParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ConstraintsParser.RPAREN, 0); }
		public ExpressionWithBrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterExpressionWithBr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitExpressionWithBr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitExpressionWithBr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InOperatorContext extends ExpressionContext {
		public TerminalNode FIELD_NAME() { return getToken(ConstraintsParser.FIELD_NAME, 0); }
		public TerminalNode IN() { return getToken(ConstraintsParser.IN, 0); }
		public TerminalNode VALUES() { return getToken(ConstraintsParser.VALUES, 0); }
		public InOperatorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterInOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitInOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitInOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotOperatorContext extends ExpressionContext {
		public TerminalNode NOT() { return getToken(ConstraintsParser.NOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotOperatorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterNotOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitNotOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitNotOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompareNumberContext extends ExpressionContext {
		public Token op;
		public TerminalNode FIELD_NAME() { return getToken(ConstraintsParser.FIELD_NAME, 0); }
		public TerminalNode NUMBER() { return getToken(ConstraintsParser.NUMBER, 0); }
		public TerminalNode EQUAL() { return getToken(ConstraintsParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ConstraintsParser.NOT_EQUAL, 0); }
		public TerminalNode LESS_THAN() { return getToken(ConstraintsParser.LESS_THAN, 0); }
		public TerminalNode LESS_EQUAL() { return getToken(ConstraintsParser.LESS_EQUAL, 0); }
		public TerminalNode LARGER_THAN() { return getToken(ConstraintsParser.LARGER_THAN, 0); }
		public TerminalNode LARGER_EQUAL() { return getToken(ConstraintsParser.LARGER_EQUAL, 0); }
		public CompareNumberContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterCompareNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitCompareNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitCompareNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CompareFieldContext extends ExpressionContext {
		public Token op;
		public List<TerminalNode> FIELD_NAME() { return getTokens(ConstraintsParser.FIELD_NAME); }
		public TerminalNode FIELD_NAME(int i) {
			return getToken(ConstraintsParser.FIELD_NAME, i);
		}
		public TerminalNode EQUAL() { return getToken(ConstraintsParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ConstraintsParser.NOT_EQUAL, 0); }
		public TerminalNode LESS_THAN() { return getToken(ConstraintsParser.LESS_THAN, 0); }
		public TerminalNode LESS_EQUAL() { return getToken(ConstraintsParser.LESS_EQUAL, 0); }
		public TerminalNode LARGER_THAN() { return getToken(ConstraintsParser.LARGER_THAN, 0); }
		public TerminalNode LARGER_EQUAL() { return getToken(ConstraintsParser.LARGER_EQUAL, 0); }
		public CompareFieldContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterCompareField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitCompareField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitCompareField(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfThenStatementContext extends ExpressionContext {
		public TerminalNode IF() { return getToken(ConstraintsParser.IF, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode THEN() { return getToken(ConstraintsParser.THEN, 0); }
		public IfThenStatementContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterIfThenStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitIfThenStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitIfThenStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndOperatorContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(ConstraintsParser.AND, 0); }
		public AndOperatorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterAndOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitAndOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitAndOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LikeOperatorContext extends ExpressionContext {
		public TerminalNode FIELD_NAME() { return getToken(ConstraintsParser.FIELD_NAME, 0); }
		public TerminalNode LIKE() { return getToken(ConstraintsParser.LIKE, 0); }
		public TerminalNode STRING() { return getToken(ConstraintsParser.STRING, 0); }
		public LikeOperatorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).enterLikeOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ConstraintsListener ) ((ConstraintsListener)listener).exitLikeOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ConstraintsVisitor ) return ((ConstraintsVisitor<? extends T>)visitor).visitLikeOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				_localctx = new ExpressionWithBrContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(3);
				match(LPAREN);
				setState(4);
				expression(0);
				setState(5);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new CompareStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(7);
				match(FIELD_NAME);
				setState(8);
				((CompareStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((CompareStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(9);
				match(STRING);
				}
				break;
			case 3:
				{
				_localctx = new CompareNumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(10);
				match(FIELD_NAME);
				setState(11);
				((CompareNumberContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8064L) != 0)) ) {
					((CompareNumberContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(12);
				match(NUMBER);
				}
				break;
			case 4:
				{
				_localctx = new CompareFieldContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(13);
				match(FIELD_NAME);
				setState(14);
				((CompareFieldContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8064L) != 0)) ) {
					((CompareFieldContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(15);
				match(FIELD_NAME);
				}
				break;
			case 5:
				{
				_localctx = new InOperatorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(16);
				match(FIELD_NAME);
				setState(17);
				match(IN);
				setState(18);
				match(VALUES);
				}
				break;
			case 6:
				{
				_localctx = new LikeOperatorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(19);
				match(FIELD_NAME);
				setState(20);
				match(LIKE);
				setState(21);
				match(STRING);
				}
				break;
			case 7:
				{
				_localctx = new IfThenStatementContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(22);
				match(IF);
				setState(23);
				expression(0);
				setState(24);
				match(THEN);
				setState(25);
				expression(4);
				}
				break;
			case 8:
				{
				_localctx = new NotOperatorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(27);
				match(NOT);
				setState(28);
				expression(3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(39);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(37);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new AndOperatorContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(31);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(32);
						match(AND);
						setState(33);
						expression(3);
						}
						break;
					case 2:
						{
						_localctx = new OrOperatorContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(34);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(35);
						match(OR);
						setState(36);
						expression(2);
						}
						break;
					}
					} 
				}
				setState(41);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0015+\u0002\u0000\u0007\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0003\u0000\u001e\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0005\u0000&\b\u0000\n\u0000\f\u0000)\t"+
		"\u0000\u0001\u0000\u0000\u0001\u0000\u0001\u0000\u0000\u0002\u0001\u0000"+
		"\u0007\b\u0001\u0000\u0007\f2\u0000\u001d\u0001\u0000\u0000\u0000\u0002"+
		"\u0003\u0006\u0000\uffff\uffff\u0000\u0003\u0004\u0005\u0005\u0000\u0000"+
		"\u0004\u0005\u0003\u0000\u0000\u0000\u0005\u0006\u0005\u0006\u0000\u0000"+
		"\u0006\u001e\u0001\u0000\u0000\u0000\u0007\b\u0005\u0003\u0000\u0000\b"+
		"\t\u0007\u0000\u0000\u0000\t\u001e\u0005\u0002\u0000\u0000\n\u000b\u0005"+
		"\u0003\u0000\u0000\u000b\f\u0007\u0001\u0000\u0000\f\u001e\u0005\u0004"+
		"\u0000\u0000\r\u000e\u0005\u0003\u0000\u0000\u000e\u000f\u0007\u0001\u0000"+
		"\u0000\u000f\u001e\u0005\u0003\u0000\u0000\u0010\u0011\u0005\u0003\u0000"+
		"\u0000\u0011\u0012\u0005\u0011\u0000\u0000\u0012\u001e\u0005\u0014\u0000"+
		"\u0000\u0013\u0014\u0005\u0003\u0000\u0000\u0014\u0015\u0005\u0012\u0000"+
		"\u0000\u0015\u001e\u0005\u0002\u0000\u0000\u0016\u0017\u0005\u000f\u0000"+
		"\u0000\u0017\u0018\u0003\u0000\u0000\u0000\u0018\u0019\u0005\u0010\u0000"+
		"\u0000\u0019\u001a\u0003\u0000\u0000\u0004\u001a\u001e\u0001\u0000\u0000"+
		"\u0000\u001b\u001c\u0005\u0013\u0000\u0000\u001c\u001e\u0003\u0000\u0000"+
		"\u0003\u001d\u0002\u0001\u0000\u0000\u0000\u001d\u0007\u0001\u0000\u0000"+
		"\u0000\u001d\n\u0001\u0000\u0000\u0000\u001d\r\u0001\u0000\u0000\u0000"+
		"\u001d\u0010\u0001\u0000\u0000\u0000\u001d\u0013\u0001\u0000\u0000\u0000"+
		"\u001d\u0016\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000"+
		"\u001e\'\u0001\u0000\u0000\u0000\u001f \n\u0002\u0000\u0000 !\u0005\r"+
		"\u0000\u0000!&\u0003\u0000\u0000\u0003\"#\n\u0001\u0000\u0000#$\u0005"+
		"\u000e\u0000\u0000$&\u0003\u0000\u0000\u0002%\u001f\u0001\u0000\u0000"+
		"\u0000%\"\u0001\u0000\u0000\u0000&)\u0001\u0000\u0000\u0000\'%\u0001\u0000"+
		"\u0000\u0000\'(\u0001\u0000\u0000\u0000(\u0001\u0001\u0000\u0000\u0000"+
		")\'\u0001\u0000\u0000\u0000\u0003\u001d%\'";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
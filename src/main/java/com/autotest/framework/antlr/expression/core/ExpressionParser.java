// Generated from E:/java/workspace/db-enc-dec-autotest/src/test/java\Expression.g4 by ANTLR 4.12.0

// 定义package
package com.autotest.framework.antlr.expression.core;

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
public class ExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, STRING=2, EXTRACT_PATH=3, VARIABLE_NAME=4, NUMBER=5, LPAREN=6, RPAREN=7, 
		EQUAL=8, NOT_EQUAL=9, LESS_THAN=10, LESS_EQUAL=11, LARGER_THAN=12, LARGER_EQUAL=13, 
		AND=14, OR=15, TRUE=16, FALSE=17, NULL=18;
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
			null, null, null, null, null, null, "'('", "')'", "'=='", "'!='", "'<'", 
			"'<='", "'>'", "'>='", "'AND'", "'OR'", "'true'", "'false'", "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "STRING", "EXTRACT_PATH", "VARIABLE_NAME", "NUMBER", "LPAREN", 
			"RPAREN", "EQUAL", "NOT_EQUAL", "LESS_THAN", "LESS_EQUAL", "LARGER_THAN", 
			"LARGER_EQUAL", "AND", "OR", "TRUE", "FALSE", "NULL"
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
	public String getGrammarFileName() { return "Expression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExpressionParser(TokenStream input) {
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
	public static class AndOperationContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(ExpressionParser.AND, 0); }
		public AndOperationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener) ((ExpressionListener)listener).enterAndOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitAndOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor) return ((ExpressionVisitor<? extends T>)visitor).visitAndOperation(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionWithBrContext extends ExpressionContext {
		public TerminalNode LPAREN() { return getToken(ExpressionParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(ExpressionParser.RPAREN, 0); }
		public ExpressionWithBrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpressionWithBr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpressionWithBr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitExpressionWithBr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionWithStringContext extends ExpressionContext {
		public Token op;
		public List<TerminalNode> EXTRACT_PATH() { return getTokens(ExpressionParser.EXTRACT_PATH); }
		public TerminalNode EXTRACT_PATH(int i) {
			return getToken(ExpressionParser.EXTRACT_PATH, i);
		}
		public TerminalNode STRING() { return getToken(ExpressionParser.STRING, 0); }
		public TerminalNode EQUAL() { return getToken(ExpressionParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ExpressionParser.NOT_EQUAL, 0); }
		public List<TerminalNode> VARIABLE_NAME() { return getTokens(ExpressionParser.VARIABLE_NAME); }
		public TerminalNode VARIABLE_NAME(int i) {
			return getToken(ExpressionParser.VARIABLE_NAME, i);
		}
		public List<TerminalNode> TRUE() { return getTokens(ExpressionParser.TRUE); }
		public TerminalNode TRUE(int i) {
			return getToken(ExpressionParser.TRUE, i);
		}
		public List<TerminalNode> FALSE() { return getTokens(ExpressionParser.FALSE); }
		public TerminalNode FALSE(int i) {
			return getToken(ExpressionParser.FALSE, i);
		}
		public List<TerminalNode> NULL() { return getTokens(ExpressionParser.NULL); }
		public TerminalNode NULL(int i) {
			return getToken(ExpressionParser.NULL, i);
		}
		public ExpressionWithStringContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpressionWithString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpressionWithString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitExpressionWithString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionWithNumberContext extends ExpressionContext {
		public Token op;
		public TerminalNode EXTRACT_PATH() { return getToken(ExpressionParser.EXTRACT_PATH, 0); }
		public List<TerminalNode> NUMBER() { return getTokens(ExpressionParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(ExpressionParser.NUMBER, i);
		}
		public TerminalNode EQUAL() { return getToken(ExpressionParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(ExpressionParser.NOT_EQUAL, 0); }
		public TerminalNode LESS_THAN() { return getToken(ExpressionParser.LESS_THAN, 0); }
		public TerminalNode LESS_EQUAL() { return getToken(ExpressionParser.LESS_EQUAL, 0); }
		public TerminalNode LARGER_THAN() { return getToken(ExpressionParser.LARGER_THAN, 0); }
		public TerminalNode LARGER_EQUAL() { return getToken(ExpressionParser.LARGER_EQUAL, 0); }
		public TerminalNode VARIABLE_NAME() { return getToken(ExpressionParser.VARIABLE_NAME, 0); }
		public ExpressionWithNumberContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterExpressionWithNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitExpressionWithNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitExpressionWithNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrOperationContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode OR() { return getToken(ExpressionParser.OR, 0); }
		public OrOperationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).enterOrOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionListener ) ((ExpressionListener)listener).exitOrOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionVisitor ) return ((ExpressionVisitor<? extends T>)visitor).visitOrOperation(this);
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
			setState(85);
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
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(7);
				match(EXTRACT_PATH);
				setState(8);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
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
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(10);
				match(EXTRACT_PATH);
				setState(11);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(12);
				match(VARIABLE_NAME);
				}
				break;
			case 4:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(13);
				match(EXTRACT_PATH);
				setState(14);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(15);
				match(EXTRACT_PATH);
				}
				break;
			case 5:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(16);
				match(EXTRACT_PATH);
				setState(17);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(18);
				match(TRUE);
				}
				break;
			case 6:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(19);
				match(EXTRACT_PATH);
				setState(20);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(21);
				match(FALSE);
				}
				break;
			case 7:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(22);
				match(EXTRACT_PATH);
				setState(23);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(24);
				match(NULL);
				}
				break;
			case 8:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(25);
				match(NULL);
				setState(26);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(27);
				match(NULL);
				}
				break;
			case 9:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(28);
				match(TRUE);
				setState(29);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(30);
				match(TRUE);
				}
				break;
			case 10:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(31);
				match(FALSE);
				setState(32);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(33);
				match(FALSE);
				}
				break;
			case 11:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(34);
				match(TRUE);
				setState(35);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(36);
				match(FALSE);
				}
				break;
			case 12:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(37);
				match(FALSE);
				setState(38);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(39);
				match(TRUE);
				}
				break;
			case 13:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(40);
				match(NULL);
				setState(41);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(42);
				match(TRUE);
				}
				break;
			case 14:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(43);
				match(NULL);
				setState(44);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(45);
				match(FALSE);
				}
				break;
			case 15:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(46);
				match(TRUE);
				setState(47);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(48);
				match(NULL);
				}
				break;
			case 16:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(49);
				match(FALSE);
				setState(50);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(51);
				match(NULL);
				}
				break;
			case 17:
				{
				_localctx = new ExpressionWithNumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(52);
				match(EXTRACT_PATH);
				setState(53);
				((ExpressionWithNumberContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16128L) != 0)) ) {
					((ExpressionWithNumberContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(54);
				match(NUMBER);
				}
				break;
			case 18:
				{
				_localctx = new ExpressionWithNumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(55);
				match(NUMBER);
				setState(56);
				((ExpressionWithNumberContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16128L) != 0)) ) {
					((ExpressionWithNumberContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(57);
				match(EXTRACT_PATH);
				}
				break;
			case 19:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(58);
				match(VARIABLE_NAME);
				setState(59);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(60);
				match(STRING);
				}
				break;
			case 20:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(61);
				match(VARIABLE_NAME);
				setState(62);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(63);
				match(EXTRACT_PATH);
				}
				break;
			case 21:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(64);
				match(VARIABLE_NAME);
				setState(65);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(66);
				match(VARIABLE_NAME);
				}
				break;
			case 22:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(67);
				match(VARIABLE_NAME);
				setState(68);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(69);
				match(TRUE);
				}
				break;
			case 23:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(70);
				match(VARIABLE_NAME);
				setState(71);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(72);
				match(FALSE);
				}
				break;
			case 24:
				{
				_localctx = new ExpressionWithStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(73);
				match(VARIABLE_NAME);
				setState(74);
				((ExpressionWithStringContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
					((ExpressionWithStringContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(75);
				match(NULL);
				}
				break;
			case 25:
				{
				_localctx = new ExpressionWithNumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(76);
				match(VARIABLE_NAME);
				setState(77);
				((ExpressionWithNumberContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16128L) != 0)) ) {
					((ExpressionWithNumberContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(78);
				match(NUMBER);
				}
				break;
			case 26:
				{
				_localctx = new ExpressionWithNumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(79);
				match(NUMBER);
				setState(80);
				((ExpressionWithNumberContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16128L) != 0)) ) {
					((ExpressionWithNumberContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(81);
				match(VARIABLE_NAME);
				}
				break;
			case 27:
				{
				_localctx = new ExpressionWithNumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(82);
				match(NUMBER);
				setState(83);
				((ExpressionWithNumberContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 16128L) != 0)) ) {
					((ExpressionWithNumberContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(84);
				match(NUMBER);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(95);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(93);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new AndOperationContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(87);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(88);
						match(AND);
						setState(89);
						expression(3);
						}
						break;
					case 2:
						{
						_localctx = new OrOperationContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(90);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(91);
						match(OR);
						setState(92);
						expression(2);
						}
						break;
					}
					} 
				}
				setState(97);
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
		"\u0004\u0001\u0012c\u0002\u0000\u0007\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0003\u0000V\b\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000^\b"+
		"\u0000\n\u0000\f\u0000a\t\u0000\u0001\u0000\u0000\u0001\u0000\u0001\u0000"+
		"\u0000\u0002\u0001\u0000\b\t\u0001\u0000\b\r}\u0000U\u0001\u0000\u0000"+
		"\u0000\u0002\u0003\u0006\u0000\uffff\uffff\u0000\u0003\u0004\u0005\u0006"+
		"\u0000\u0000\u0004\u0005\u0003\u0000\u0000\u0000\u0005\u0006\u0005\u0007"+
		"\u0000\u0000\u0006V\u0001\u0000\u0000\u0000\u0007\b\u0005\u0003\u0000"+
		"\u0000\b\t\u0007\u0000\u0000\u0000\tV\u0005\u0002\u0000\u0000\n\u000b"+
		"\u0005\u0003\u0000\u0000\u000b\f\u0007\u0000\u0000\u0000\fV\u0005\u0004"+
		"\u0000\u0000\r\u000e\u0005\u0003\u0000\u0000\u000e\u000f\u0007\u0000\u0000"+
		"\u0000\u000fV\u0005\u0003\u0000\u0000\u0010\u0011\u0005\u0003\u0000\u0000"+
		"\u0011\u0012\u0007\u0000\u0000\u0000\u0012V\u0005\u0010\u0000\u0000\u0013"+
		"\u0014\u0005\u0003\u0000\u0000\u0014\u0015\u0007\u0000\u0000\u0000\u0015"+
		"V\u0005\u0011\u0000\u0000\u0016\u0017\u0005\u0003\u0000\u0000\u0017\u0018"+
		"\u0007\u0000\u0000\u0000\u0018V\u0005\u0012\u0000\u0000\u0019\u001a\u0005"+
		"\u0012\u0000\u0000\u001a\u001b\u0007\u0000\u0000\u0000\u001bV\u0005\u0012"+
		"\u0000\u0000\u001c\u001d\u0005\u0010\u0000\u0000\u001d\u001e\u0007\u0000"+
		"\u0000\u0000\u001eV\u0005\u0010\u0000\u0000\u001f \u0005\u0011\u0000\u0000"+
		" !\u0007\u0000\u0000\u0000!V\u0005\u0011\u0000\u0000\"#\u0005\u0010\u0000"+
		"\u0000#$\u0007\u0000\u0000\u0000$V\u0005\u0011\u0000\u0000%&\u0005\u0011"+
		"\u0000\u0000&\'\u0007\u0000\u0000\u0000\'V\u0005\u0010\u0000\u0000()\u0005"+
		"\u0012\u0000\u0000)*\u0007\u0000\u0000\u0000*V\u0005\u0010\u0000\u0000"+
		"+,\u0005\u0012\u0000\u0000,-\u0007\u0000\u0000\u0000-V\u0005\u0011\u0000"+
		"\u0000./\u0005\u0010\u0000\u0000/0\u0007\u0000\u0000\u00000V\u0005\u0012"+
		"\u0000\u000012\u0005\u0011\u0000\u000023\u0007\u0000\u0000\u00003V\u0005"+
		"\u0012\u0000\u000045\u0005\u0003\u0000\u000056\u0007\u0001\u0000\u0000"+
		"6V\u0005\u0005\u0000\u000078\u0005\u0005\u0000\u000089\u0007\u0001\u0000"+
		"\u00009V\u0005\u0003\u0000\u0000:;\u0005\u0004\u0000\u0000;<\u0007\u0000"+
		"\u0000\u0000<V\u0005\u0002\u0000\u0000=>\u0005\u0004\u0000\u0000>?\u0007"+
		"\u0000\u0000\u0000?V\u0005\u0003\u0000\u0000@A\u0005\u0004\u0000\u0000"+
		"AB\u0007\u0000\u0000\u0000BV\u0005\u0004\u0000\u0000CD\u0005\u0004\u0000"+
		"\u0000DE\u0007\u0000\u0000\u0000EV\u0005\u0010\u0000\u0000FG\u0005\u0004"+
		"\u0000\u0000GH\u0007\u0000\u0000\u0000HV\u0005\u0011\u0000\u0000IJ\u0005"+
		"\u0004\u0000\u0000JK\u0007\u0000\u0000\u0000KV\u0005\u0012\u0000\u0000"+
		"LM\u0005\u0004\u0000\u0000MN\u0007\u0001\u0000\u0000NV\u0005\u0005\u0000"+
		"\u0000OP\u0005\u0005\u0000\u0000PQ\u0007\u0001\u0000\u0000QV\u0005\u0004"+
		"\u0000\u0000RS\u0005\u0005\u0000\u0000ST\u0007\u0001\u0000\u0000TV\u0005"+
		"\u0005\u0000\u0000U\u0002\u0001\u0000\u0000\u0000U\u0007\u0001\u0000\u0000"+
		"\u0000U\n\u0001\u0000\u0000\u0000U\r\u0001\u0000\u0000\u0000U\u0010\u0001"+
		"\u0000\u0000\u0000U\u0013\u0001\u0000\u0000\u0000U\u0016\u0001\u0000\u0000"+
		"\u0000U\u0019\u0001\u0000\u0000\u0000U\u001c\u0001\u0000\u0000\u0000U"+
		"\u001f\u0001\u0000\u0000\u0000U\"\u0001\u0000\u0000\u0000U%\u0001\u0000"+
		"\u0000\u0000U(\u0001\u0000\u0000\u0000U+\u0001\u0000\u0000\u0000U.\u0001"+
		"\u0000\u0000\u0000U1\u0001\u0000\u0000\u0000U4\u0001\u0000\u0000\u0000"+
		"U7\u0001\u0000\u0000\u0000U:\u0001\u0000\u0000\u0000U=\u0001\u0000\u0000"+
		"\u0000U@\u0001\u0000\u0000\u0000UC\u0001\u0000\u0000\u0000UF\u0001\u0000"+
		"\u0000\u0000UI\u0001\u0000\u0000\u0000UL\u0001\u0000\u0000\u0000UO\u0001"+
		"\u0000\u0000\u0000UR\u0001\u0000\u0000\u0000V_\u0001\u0000\u0000\u0000"+
		"WX\n\u0002\u0000\u0000XY\u0005\u000e\u0000\u0000Y^\u0003\u0000\u0000\u0003"+
		"Z[\n\u0001\u0000\u0000[\\\u0005\u000f\u0000\u0000\\^\u0003\u0000\u0000"+
		"\u0002]W\u0001\u0000\u0000\u0000]Z\u0001\u0000\u0000\u0000^a\u0001\u0000"+
		"\u0000\u0000_]\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000`\u0001"+
		"\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000\u0003U]_";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
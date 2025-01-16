// Generated from E:/java/workspace/db-enc-dec-autotest/src/test/java\Expression.g4 by ANTLR 4.12.0

// 定义package
package com.autotest.framework.antlr.expression.core;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, STRING=2, EXTRACT_PATH=3, VARIABLE_NAME=4, NUMBER=5, LPAREN=6, RPAREN=7, 
		EQUAL=8, NOT_EQUAL=9, LESS_THAN=10, LESS_EQUAL=11, LARGER_THAN=12, LARGER_EQUAL=13, 
		AND=14, OR=15, TRUE=16, FALSE=17, NULL=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WS", "STRING", "EXTRACT_PATH", "VARIABLE_NAME", "NUMBER", "LPAREN", 
			"RPAREN", "EQUAL", "NOT_EQUAL", "LESS_THAN", "LESS_EQUAL", "LARGER_THAN", 
			"LARGER_EQUAL", "AND", "OR", "TRUE", "FALSE", "NULL"
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


	public ExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0012\u0087\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0001\u0000\u0004\u0000\'\b\u0000\u000b\u0000\f\u0000(\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0005\u0001/\b\u0001\n\u0001\f\u00012\t"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0004\u00028\b"+
		"\u0002\u000b\u0002\f\u00029\u0003\u0002<\b\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0004\u0003D\b\u0003"+
		"\u000b\u0003\f\u0003E\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0005\u0004L\b\u0004\n\u0004\f\u0004O\t\u0004\u0004\u0004Q\b\u0004\u000b"+
		"\u0004\f\u0004R\u0001\u0004\u0001\u0004\u0004\u0004W\b\u0004\u000b\u0004"+
		"\f\u0004X\u0003\u0004[\b\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0000\u0000\u0012"+
		"\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r"+
		"\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e"+
		"\u001d\u000f\u001f\u0010!\u0011#\u0012\u0001\u0000\u0005\u0003\u0000\t"+
		"\n\r\r  \u0003\u0000\n\n\r\r\'\'\b\u0000$$\')..09A[]]__az\u0003\u0000"+
		"$${{}}\u0001\u000009\u008f\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003"+
		"\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007"+
		"\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001"+
		"\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000"+
		"\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000"+
		"\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000"+
		"\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000"+
		"\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000"+
		"\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000"+
		"\u0001&\u0001\u0000\u0000\u0000\u0003,\u0001\u0000\u0000\u0000\u00055"+
		"\u0001\u0000\u0000\u0000\u0007?\u0001\u0000\u0000\u0000\tP\u0001\u0000"+
		"\u0000\u0000\u000b\\\u0001\u0000\u0000\u0000\r^\u0001\u0000\u0000\u0000"+
		"\u000f`\u0001\u0000\u0000\u0000\u0011c\u0001\u0000\u0000\u0000\u0013f"+
		"\u0001\u0000\u0000\u0000\u0015h\u0001\u0000\u0000\u0000\u0017k\u0001\u0000"+
		"\u0000\u0000\u0019m\u0001\u0000\u0000\u0000\u001bp\u0001\u0000\u0000\u0000"+
		"\u001dt\u0001\u0000\u0000\u0000\u001fw\u0001\u0000\u0000\u0000!|\u0001"+
		"\u0000\u0000\u0000#\u0082\u0001\u0000\u0000\u0000%\'\u0007\u0000\u0000"+
		"\u0000&%\u0001\u0000\u0000\u0000\'(\u0001\u0000\u0000\u0000(&\u0001\u0000"+
		"\u0000\u0000()\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*+\u0006"+
		"\u0000\u0000\u0000+\u0002\u0001\u0000\u0000\u0000,0\u0005\'\u0000\u0000"+
		"-/\b\u0001\u0000\u0000.-\u0001\u0000\u0000\u0000/2\u0001\u0000\u0000\u0000"+
		"0.\u0001\u0000\u0000\u000001\u0001\u0000\u0000\u000013\u0001\u0000\u0000"+
		"\u000020\u0001\u0000\u0000\u000034\u0005\'\u0000\u00004\u0004\u0001\u0000"+
		"\u0000\u00005;\u0005[\u0000\u000068\u0007\u0002\u0000\u000076\u0001\u0000"+
		"\u0000\u000089\u0001\u0000\u0000\u000097\u0001\u0000\u0000\u00009:\u0001"+
		"\u0000\u0000\u0000:<\u0001\u0000\u0000\u0000;7\u0001\u0000\u0000\u0000"+
		";<\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=>\u0005]\u0000\u0000"+
		">\u0006\u0001\u0000\u0000\u0000?@\u0005$\u0000\u0000@A\u0005{\u0000\u0000"+
		"AC\u0001\u0000\u0000\u0000BD\b\u0003\u0000\u0000CB\u0001\u0000\u0000\u0000"+
		"DE\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001\u0000\u0000"+
		"\u0000FG\u0001\u0000\u0000\u0000GH\u0005}\u0000\u0000H\b\u0001\u0000\u0000"+
		"\u0000IM\u0007\u0004\u0000\u0000JL\u0007\u0004\u0000\u0000KJ\u0001\u0000"+
		"\u0000\u0000LO\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000MN\u0001"+
		"\u0000\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000\u0000"+
		"PI\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000"+
		"\u0000RS\u0001\u0000\u0000\u0000SZ\u0001\u0000\u0000\u0000TV\t\u0000\u0000"+
		"\u0000UW\u0007\u0004\u0000\u0000VU\u0001\u0000\u0000\u0000WX\u0001\u0000"+
		"\u0000\u0000XV\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000Y[\u0001"+
		"\u0000\u0000\u0000ZT\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000"+
		"[\n\u0001\u0000\u0000\u0000\\]\u0005(\u0000\u0000]\f\u0001\u0000\u0000"+
		"\u0000^_\u0005)\u0000\u0000_\u000e\u0001\u0000\u0000\u0000`a\u0005=\u0000"+
		"\u0000ab\u0005=\u0000\u0000b\u0010\u0001\u0000\u0000\u0000cd\u0005!\u0000"+
		"\u0000de\u0005=\u0000\u0000e\u0012\u0001\u0000\u0000\u0000fg\u0005<\u0000"+
		"\u0000g\u0014\u0001\u0000\u0000\u0000hi\u0005<\u0000\u0000ij\u0005=\u0000"+
		"\u0000j\u0016\u0001\u0000\u0000\u0000kl\u0005>\u0000\u0000l\u0018\u0001"+
		"\u0000\u0000\u0000mn\u0005>\u0000\u0000no\u0005=\u0000\u0000o\u001a\u0001"+
		"\u0000\u0000\u0000pq\u0005A\u0000\u0000qr\u0005N\u0000\u0000rs\u0005D"+
		"\u0000\u0000s\u001c\u0001\u0000\u0000\u0000tu\u0005O\u0000\u0000uv\u0005"+
		"R\u0000\u0000v\u001e\u0001\u0000\u0000\u0000wx\u0005t\u0000\u0000xy\u0005"+
		"r\u0000\u0000yz\u0005u\u0000\u0000z{\u0005e\u0000\u0000{ \u0001\u0000"+
		"\u0000\u0000|}\u0005f\u0000\u0000}~\u0005a\u0000\u0000~\u007f\u0005l\u0000"+
		"\u0000\u007f\u0080\u0005s\u0000\u0000\u0080\u0081\u0005e\u0000\u0000\u0081"+
		"\"\u0001\u0000\u0000\u0000\u0082\u0083\u0005n\u0000\u0000\u0083\u0084"+
		"\u0005u\u0000\u0000\u0084\u0085\u0005l\u0000\u0000\u0085\u0086\u0005l"+
		"\u0000\u0000\u0086$\u0001\u0000\u0000\u0000\n\u0000(09;EMRXZ\u0001\u0006"+
		"\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
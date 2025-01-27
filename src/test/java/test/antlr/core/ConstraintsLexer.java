// Generated from C:/Users/zengb/Documents/java-workspace/tm-dev-3.0.0/autotest-framework/src/test/java/test/antlr/expr\Constraints.g4 by ANTLR 4.12.0

// 定义package
package test.antlr.core;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ConstraintsLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, STRING=2, FIELD_NAME=3, NUMBER=4, LPAREN=5, RPAREN=6, EQUAL=7, NOT_EQUAL=8, 
		LESS_THAN=9, LESS_EQUAL=10, LARGER_THAN=11, LARGER_EQUAL=12, AND=13, OR=14, 
		IF=15, THEN=16, IN=17, LIKE=18, NOT=19, VALUES=20, SEMI=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WS", "STRING", "FIELD_NAME", "NUMBER", "LPAREN", "RPAREN", "EQUAL", 
			"NOT_EQUAL", "LESS_THAN", "LESS_EQUAL", "LARGER_THAN", "LARGER_EQUAL", 
			"AND", "OR", "IF", "THEN", "IN", "LIKE", "NOT", "VALUES", "SEMI"
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


	public ConstraintsLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Constraints.g4"; }

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
		"\u0004\u0000\u0015\u009d\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0001\u0000\u0004\u0000-\b\u0000\u000b\u0000\f\u0000.\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0005\u00015\b\u0001\n\u0001\f\u00018\t"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0004\u0002>\b"+
		"\u0002\u000b\u0002\f\u0002?\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0005\u0003F\b\u0003\n\u0003\f\u0003I\t\u0003\u0004\u0003K\b\u0003"+
		"\u000b\u0003\f\u0003L\u0001\u0003\u0001\u0003\u0004\u0003Q\b\u0003\u000b"+
		"\u0003\f\u0003R\u0003\u0003U\b\u0003\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0004\u0013"+
		"\u0088\b\u0013\u000b\u0013\f\u0013\u0089\u0001\u0013\u0001\u0013\u0005"+
		"\u0013\u008e\b\u0013\n\u0013\f\u0013\u0091\t\u0013\u0001\u0013\u0001\u0013"+
		"\u0005\u0013\u0095\b\u0013\n\u0013\f\u0013\u0098\t\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001?\u0000\u0015\u0001\u0001\u0003\u0002"+
		"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013"+
		"\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012%\u0013\'\u0014)\u0015\u0001\u0000\u0005\u0003\u0000\t\n\r\r  "+
		"\u0003\u0000\n\n\r\r\"\"\u0001\u000009\u0001\u0000,,\u0002\u0000\t\t "+
		" \u00a8\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000"+
		"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
		"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000"+
		"\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000"+
		"\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000"+
		"\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000"+
		"\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000"+
		"\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000"+
		"!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001"+
		"\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000"+
		"\u0000\u0000\u0001,\u0001\u0000\u0000\u0000\u00032\u0001\u0000\u0000\u0000"+
		"\u0005;\u0001\u0000\u0000\u0000\u0007J\u0001\u0000\u0000\u0000\tV\u0001"+
		"\u0000\u0000\u0000\u000bX\u0001\u0000\u0000\u0000\rZ\u0001\u0000\u0000"+
		"\u0000\u000f\\\u0001\u0000\u0000\u0000\u0011_\u0001\u0000\u0000\u0000"+
		"\u0013a\u0001\u0000\u0000\u0000\u0015d\u0001\u0000\u0000\u0000\u0017f"+
		"\u0001\u0000\u0000\u0000\u0019i\u0001\u0000\u0000\u0000\u001bm\u0001\u0000"+
		"\u0000\u0000\u001dp\u0001\u0000\u0000\u0000\u001fs\u0001\u0000\u0000\u0000"+
		"!x\u0001\u0000\u0000\u0000#{\u0001\u0000\u0000\u0000%\u0080\u0001\u0000"+
		"\u0000\u0000\'\u0084\u0001\u0000\u0000\u0000)\u009b\u0001\u0000\u0000"+
		"\u0000+-\u0007\u0000\u0000\u0000,+\u0001\u0000\u0000\u0000-.\u0001\u0000"+
		"\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000/0\u0001"+
		"\u0000\u0000\u000001\u0006\u0000\u0000\u00001\u0002\u0001\u0000\u0000"+
		"\u000026\u0005\"\u0000\u000035\b\u0001\u0000\u000043\u0001\u0000\u0000"+
		"\u000058\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u000067\u0001\u0000"+
		"\u0000\u000079\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u00009:\u0005"+
		"\"\u0000\u0000:\u0004\u0001\u0000\u0000\u0000;=\u0005[\u0000\u0000<>\t"+
		"\u0000\u0000\u0000=<\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000"+
		"?@\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000"+
		"\u0000AB\u0005]\u0000\u0000B\u0006\u0001\u0000\u0000\u0000CG\u0007\u0002"+
		"\u0000\u0000DF\u0007\u0002\u0000\u0000ED\u0001\u0000\u0000\u0000FI\u0001"+
		"\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000"+
		"HK\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000JC\u0001\u0000\u0000"+
		"\u0000KL\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000LM\u0001\u0000"+
		"\u0000\u0000MT\u0001\u0000\u0000\u0000NP\t\u0000\u0000\u0000OQ\u0007\u0002"+
		"\u0000\u0000PO\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000\u0000RP\u0001"+
		"\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000SU\u0001\u0000\u0000\u0000"+
		"TN\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000U\b\u0001\u0000\u0000"+
		"\u0000VW\u0005(\u0000\u0000W\n\u0001\u0000\u0000\u0000XY\u0005)\u0000"+
		"\u0000Y\f\u0001\u0000\u0000\u0000Z[\u0005=\u0000\u0000[\u000e\u0001\u0000"+
		"\u0000\u0000\\]\u0005<\u0000\u0000]^\u0005>\u0000\u0000^\u0010\u0001\u0000"+
		"\u0000\u0000_`\u0005<\u0000\u0000`\u0012\u0001\u0000\u0000\u0000ab\u0005"+
		"<\u0000\u0000bc\u0005=\u0000\u0000c\u0014\u0001\u0000\u0000\u0000de\u0005"+
		">\u0000\u0000e\u0016\u0001\u0000\u0000\u0000fg\u0005>\u0000\u0000gh\u0005"+
		"=\u0000\u0000h\u0018\u0001\u0000\u0000\u0000ij\u0005A\u0000\u0000jk\u0005"+
		"N\u0000\u0000kl\u0005D\u0000\u0000l\u001a\u0001\u0000\u0000\u0000mn\u0005"+
		"O\u0000\u0000no\u0005R\u0000\u0000o\u001c\u0001\u0000\u0000\u0000pq\u0005"+
		"I\u0000\u0000qr\u0005F\u0000\u0000r\u001e\u0001\u0000\u0000\u0000st\u0005"+
		"T\u0000\u0000tu\u0005H\u0000\u0000uv\u0005E\u0000\u0000vw\u0005N\u0000"+
		"\u0000w \u0001\u0000\u0000\u0000xy\u0005I\u0000\u0000yz\u0005N\u0000\u0000"+
		"z\"\u0001\u0000\u0000\u0000{|\u0005L\u0000\u0000|}\u0005I\u0000\u0000"+
		"}~\u0005K\u0000\u0000~\u007f\u0005E\u0000\u0000\u007f$\u0001\u0000\u0000"+
		"\u0000\u0080\u0081\u0005N\u0000\u0000\u0081\u0082\u0005O\u0000\u0000\u0082"+
		"\u0083\u0005T\u0000\u0000\u0083&\u0001\u0000\u0000\u0000\u0084\u0087\u0005"+
		"{\u0000\u0000\u0085\u0088\u0003\u0003\u0001\u0000\u0086\u0088\u0003\u0007"+
		"\u0003\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0087\u0086\u0001\u0000"+
		"\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089\u0087\u0001\u0000"+
		"\u0000\u0000\u0089\u008a\u0001\u0000\u0000\u0000\u008a\u0096\u0001\u0000"+
		"\u0000\u0000\u008b\u008f\u0007\u0003\u0000\u0000\u008c\u008e\u0007\u0004"+
		"\u0000\u0000\u008d\u008c\u0001\u0000\u0000\u0000\u008e\u0091\u0001\u0000"+
		"\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000"+
		"\u0000\u0000\u0090\u0092\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000"+
		"\u0000\u0000\u0092\u0095\u0003\u0003\u0001\u0000\u0093\u0095\u0003\u0007"+
		"\u0003\u0000\u0094\u008b\u0001\u0000\u0000\u0000\u0094\u0093\u0001\u0000"+
		"\u0000\u0000\u0095\u0098\u0001\u0000\u0000\u0000\u0096\u0094\u0001\u0000"+
		"\u0000\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u0099\u0001\u0000"+
		"\u0000\u0000\u0098\u0096\u0001\u0000\u0000\u0000\u0099\u009a\u0005}\u0000"+
		"\u0000\u009a(\u0001\u0000\u0000\u0000\u009b\u009c\u0005;\u0000\u0000\u009c"+
		"*\u0001\u0000\u0000\u0000\r\u0000.6?GLRT\u0087\u0089\u008f\u0094\u0096"+
		"\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
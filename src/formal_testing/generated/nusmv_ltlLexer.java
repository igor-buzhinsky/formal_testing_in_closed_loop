// Generated from /home/buzhinsky/repos/formal_testing_in_closed_loop/nusmv_ltl.g4 by ANTLR 4.6
package formal_testing.generated;

import formal_testing.formula.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class nusmv_ltlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, WS=23, TRUE=24, FALSE=25, 
		INT_CONST=26, ID=27;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "WS", "TRUE", "FALSE", "INT_CONST", 
		"ID"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'!'", "'X'", "'G'", "'F'", "'='", "'!='", "'>'", "'>='", "'<'", 
		"'<='", "'.'", "'['", "']'", "'('", "')'", "'&'", "'|'", "'xor'", "'xnor'", 
		"'<->'", "'->'", "'LTLSPEC'", null, "'TRUE'", "'FALSE'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, "WS", 
		"TRUE", "FALSE", "INT_CONST", "ID"
	};
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


	public nusmv_ltlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "nusmv_ltl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\35\u00a0\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3"+
		"\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\5\30y\n\30\3\30\6\30"+
		"|\n\30\r\30\16\30}\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\33\5\33\u008e\n\33\3\33\3\33\3\33\7\33\u0093\n\33\f\33"+
		"\16\33\u0096\13\33\5\33\u0098\n\33\3\34\3\34\7\34\u009c\n\34\f\34\16\34"+
		"\u009f\13\34\2\2\35\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\35\3\2\5\4\2\13\13\"\"\5\2C\\aac|\6\2\62;C\\aac|\u00a6\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61"+
		"\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\39\3\2\2\2\5;\3\2\2\2"+
		"\7=\3\2\2\2\t?\3\2\2\2\13A\3\2\2\2\rC\3\2\2\2\17F\3\2\2\2\21H\3\2\2\2"+
		"\23K\3\2\2\2\25M\3\2\2\2\27P\3\2\2\2\31R\3\2\2\2\33T\3\2\2\2\35V\3\2\2"+
		"\2\37X\3\2\2\2!Z\3\2\2\2#\\\3\2\2\2%^\3\2\2\2\'b\3\2\2\2)g\3\2\2\2+k\3"+
		"\2\2\2-n\3\2\2\2/{\3\2\2\2\61\u0081\3\2\2\2\63\u0086\3\2\2\2\65\u008d"+
		"\3\2\2\2\67\u0099\3\2\2\29:\7#\2\2:\4\3\2\2\2;<\7Z\2\2<\6\3\2\2\2=>\7"+
		"I\2\2>\b\3\2\2\2?@\7H\2\2@\n\3\2\2\2AB\7?\2\2B\f\3\2\2\2CD\7#\2\2DE\7"+
		"?\2\2E\16\3\2\2\2FG\7@\2\2G\20\3\2\2\2HI\7@\2\2IJ\7?\2\2J\22\3\2\2\2K"+
		"L\7>\2\2L\24\3\2\2\2MN\7>\2\2NO\7?\2\2O\26\3\2\2\2PQ\7\60\2\2Q\30\3\2"+
		"\2\2RS\7]\2\2S\32\3\2\2\2TU\7_\2\2U\34\3\2\2\2VW\7*\2\2W\36\3\2\2\2XY"+
		"\7+\2\2Y \3\2\2\2Z[\7(\2\2[\"\3\2\2\2\\]\7~\2\2]$\3\2\2\2^_\7z\2\2_`\7"+
		"q\2\2`a\7t\2\2a&\3\2\2\2bc\7z\2\2cd\7p\2\2de\7q\2\2ef\7t\2\2f(\3\2\2\2"+
		"gh\7>\2\2hi\7/\2\2ij\7@\2\2j*\3\2\2\2kl\7/\2\2lm\7@\2\2m,\3\2\2\2no\7"+
		"N\2\2op\7V\2\2pq\7N\2\2qr\7U\2\2rs\7R\2\2st\7G\2\2tu\7E\2\2u.\3\2\2\2"+
		"v|\t\2\2\2wy\7\17\2\2xw\3\2\2\2xy\3\2\2\2yz\3\2\2\2z|\7\f\2\2{v\3\2\2"+
		"\2{x\3\2\2\2|}\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\177\3\2\2\2\177\u0080\b\30"+
		"\2\2\u0080\60\3\2\2\2\u0081\u0082\7V\2\2\u0082\u0083\7T\2\2\u0083\u0084"+
		"\7W\2\2\u0084\u0085\7G\2\2\u0085\62\3\2\2\2\u0086\u0087\7H\2\2\u0087\u0088"+
		"\7C\2\2\u0088\u0089\7N\2\2\u0089\u008a\7U\2\2\u008a\u008b\7G\2\2\u008b"+
		"\64\3\2\2\2\u008c\u008e\7/\2\2\u008d\u008c\3\2\2\2\u008d\u008e\3\2\2\2"+
		"\u008e\u0097\3\2\2\2\u008f\u0098\7\62\2\2\u0090\u0094\4\63;\2\u0091\u0093"+
		"\4\62;\2\u0092\u0091\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094"+
		"\u0095\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0097\u008f\3\2"+
		"\2\2\u0097\u0090\3\2\2\2\u0098\66\3\2\2\2\u0099\u009d\t\3\2\2\u009a\u009c"+
		"\t\4\2\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d"+
		"\u009e\3\2\2\2\u009e8\3\2\2\2\u009f\u009d\3\2\2\2\n\2x{}\u008d\u0094\u0097"+
		"\u009d\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
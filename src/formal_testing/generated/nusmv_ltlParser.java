// Generated from /home/buzhinsky/repos/formal_testing_in_closed_loop/nusmv_ltl.g4 by ANTLR 4.6
package formal_testing.generated;

import formal_testing.formula.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class nusmv_ltlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, WS=23, TRUE=24, FALSE=25, 
		INT_CONST=26, ID=27;
	public static final int
		RULE_unary_operator_sign = 0, RULE_comparison_operator_sign = 1, RULE_constant = 2, 
		RULE_composite_id = 3, RULE_proposition = 4, RULE_atom = 5, RULE_unary_operator = 6, 
		RULE_priority1_binary_operator = 7, RULE_priority2_binary_operator = 8, 
		RULE_priority3_binary_operator = 9, RULE_priority4_binary_operator = 10, 
		RULE_priority5_binary_operator = 11, RULE_formula = 12;
	public static final String[] ruleNames = {
		"unary_operator_sign", "comparison_operator_sign", "constant", "composite_id", 
		"proposition", "atom", "unary_operator", "priority1_binary_operator", 
		"priority2_binary_operator", "priority3_binary_operator", "priority4_binary_operator", 
		"priority5_binary_operator", "formula"
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

	@Override
	public String getGrammarFileName() { return "nusmv_ltl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }



	public nusmv_ltlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Unary_operator_signContext extends ParserRuleContext {
		public String value;
		public Unary_operator_signContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_operator_sign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterUnary_operator_sign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitUnary_operator_sign(this);
		}
	}

	public final Unary_operator_signContext unary_operator_sign() throws RecognitionException {
		Unary_operator_signContext _localctx = new Unary_operator_signContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_unary_operator_sign);
		try {
			setState(34);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(26);
				match(T__0);
				 ((Unary_operator_signContext)_localctx).value =  "!"; 
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(28);
				match(T__1);
				 ((Unary_operator_signContext)_localctx).value =  "X"; 
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 3);
				{
				setState(30);
				match(T__2);
				 ((Unary_operator_signContext)_localctx).value =  "G"; 
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 4);
				{
				setState(32);
				match(T__3);
				 ((Unary_operator_signContext)_localctx).value =  "F"; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Comparison_operator_signContext extends ParserRuleContext {
		public String value;
		public Comparison_operator_signContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison_operator_sign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterComparison_operator_sign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitComparison_operator_sign(this);
		}
	}

	public final Comparison_operator_signContext comparison_operator_sign() throws RecognitionException {
		Comparison_operator_signContext _localctx = new Comparison_operator_signContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_comparison_operator_sign);
		try {
			setState(48);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(36);
				match(T__4);
				 ((Comparison_operator_signContext)_localctx).value =  "="; 
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(38);
				match(T__5);
				 ((Comparison_operator_signContext)_localctx).value =  "!="; 
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 3);
				{
				setState(40);
				match(T__6);
				 ((Comparison_operator_signContext)_localctx).value =  ">"; 
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 4);
				{
				setState(42);
				match(T__7);
				 ((Comparison_operator_signContext)_localctx).value =  ">="; 
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 5);
				{
				setState(44);
				match(T__8);
				 ((Comparison_operator_signContext)_localctx).value =  "<"; 
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 6);
				{
				setState(46);
				match(T__9);
				 ((Comparison_operator_signContext)_localctx).value =  "<="; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public String value;
		public Token INT_CONST;
		public TerminalNode INT_CONST() { return getToken(nusmv_ltlParser.INT_CONST, 0); }
		public TerminalNode TRUE() { return getToken(nusmv_ltlParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(nusmv_ltlParser.FALSE, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitConstant(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_constant);
		try {
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT_CONST:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				((ConstantContext)_localctx).INT_CONST = match(INT_CONST);
				 ((ConstantContext)_localctx).value =  (((ConstantContext)_localctx).INT_CONST!=null?((ConstantContext)_localctx).INT_CONST.getText():null); 
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(52);
				match(TRUE);
				 ((ConstantContext)_localctx).value =  "TRUE"; 
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(54);
				match(FALSE);
				 ((ConstantContext)_localctx).value =  "FALSE"; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Composite_idContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(nusmv_ltlParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(nusmv_ltlParser.ID, i);
		}
		public TerminalNode INT_CONST() { return getToken(nusmv_ltlParser.INT_CONST, 0); }
		public Composite_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_composite_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterComposite_id(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitComposite_id(this);
		}
	}

	public final Composite_idContext composite_id() throws RecognitionException {
		Composite_idContext _localctx = new Composite_idContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_composite_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(ID);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(59);
				match(T__10);
				setState(60);
				match(ID);
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(66);
				match(T__11);
				setState(67);
				match(INT_CONST);
				setState(68);
				match(T__12);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropositionContext extends ParserRuleContext {
		public LTLFormula f;
		public Composite_idContext composite_id;
		public Comparison_operator_signContext comparison_operator_sign;
		public ConstantContext constant;
		public TerminalNode TRUE() { return getToken(nusmv_ltlParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(nusmv_ltlParser.FALSE, 0); }
		public Composite_idContext composite_id() {
			return getRuleContext(Composite_idContext.class,0);
		}
		public Comparison_operator_signContext comparison_operator_sign() {
			return getRuleContext(Comparison_operator_signContext.class,0);
		}
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public PropositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_proposition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterProposition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitProposition(this);
		}
	}

	public final PropositionContext proposition() throws RecognitionException {
		PropositionContext _localctx = new PropositionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_proposition);
		try {
			setState(85);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				match(TRUE);
				 ((PropositionContext)_localctx).f =  LTLFormula.TRUE; 
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(73);
				match(FALSE);
				 ((PropositionContext)_localctx).f =  LTLFormula.FALSE; 
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				{
				 String c = "TRUE"; String op = "="; 
				setState(76);
				((PropositionContext)_localctx).composite_id = composite_id();
				setState(81);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(77);
					((PropositionContext)_localctx).comparison_operator_sign = comparison_operator_sign();
					setState(78);
					((PropositionContext)_localctx).constant = constant();
					 op = ((PropositionContext)_localctx).comparison_operator_sign.value; c = ((PropositionContext)_localctx).constant.value; 
					}
					break;
				}
				 ((PropositionContext)_localctx).f =  new Proposition((((PropositionContext)_localctx).composite_id!=null?_input.getText(((PropositionContext)_localctx).composite_id.start,((PropositionContext)_localctx).composite_id.stop):null), op, c); 
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public LTLFormula f;
		public PropositionContext proposition;
		public Priority5_binary_operatorContext priority5_binary_operator;
		public PropositionContext proposition() {
			return getRuleContext(PropositionContext.class,0);
		}
		public Priority5_binary_operatorContext priority5_binary_operator() {
			return getRuleContext(Priority5_binary_operatorContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_atom);
		try {
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
			case FALSE:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				((AtomContext)_localctx).proposition = proposition();
				 ((AtomContext)_localctx).f =  ((AtomContext)_localctx).proposition.f; 
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				match(T__13);
				setState(91);
				((AtomContext)_localctx).priority5_binary_operator = priority5_binary_operator();
				setState(92);
				match(T__14);
				 ((AtomContext)_localctx).f =  ((AtomContext)_localctx).priority5_binary_operator.f; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Unary_operatorContext extends ParserRuleContext {
		public LTLFormula f;
		public Unary_operator_signContext unary_operator_sign;
		public Unary_operatorContext unary_operator;
		public AtomContext atom;
		public Unary_operator_signContext unary_operator_sign() {
			return getRuleContext(Unary_operator_signContext.class,0);
		}
		public Unary_operatorContext unary_operator() {
			return getRuleContext(Unary_operatorContext.class,0);
		}
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public Unary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterUnary_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitUnary_operator(this);
		}
	}

	public final Unary_operatorContext unary_operator() throws RecognitionException {
		Unary_operatorContext _localctx = new Unary_operatorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_unary_operator);
		try {
			setState(104);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(97);
				((Unary_operatorContext)_localctx).unary_operator_sign = unary_operator_sign();
				setState(98);
				((Unary_operatorContext)_localctx).unary_operator = unary_operator();
				 ((Unary_operatorContext)_localctx).f =  new UnaryOperator(((Unary_operatorContext)_localctx).unary_operator_sign.value, ((Unary_operatorContext)_localctx).unary_operator.f); 
				}
				break;
			case T__13:
			case TRUE:
			case FALSE:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				((Unary_operatorContext)_localctx).atom = atom();
				 ((Unary_operatorContext)_localctx).f =  ((Unary_operatorContext)_localctx).atom.f; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Priority1_binary_operatorContext extends ParserRuleContext {
		public LTLFormula f;
		public Unary_operatorContext f1;
		public Unary_operatorContext f2;
		public List<Unary_operatorContext> unary_operator() {
			return getRuleContexts(Unary_operatorContext.class);
		}
		public Unary_operatorContext unary_operator(int i) {
			return getRuleContext(Unary_operatorContext.class,i);
		}
		public Priority1_binary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_priority1_binary_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterPriority1_binary_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitPriority1_binary_operator(this);
		}
	}

	public final Priority1_binary_operatorContext priority1_binary_operator() throws RecognitionException {
		Priority1_binary_operatorContext _localctx = new Priority1_binary_operatorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_priority1_binary_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			((Priority1_binary_operatorContext)_localctx).f1 = unary_operator();
			 ((Priority1_binary_operatorContext)_localctx).f =  ((Priority1_binary_operatorContext)_localctx).f1.f; 
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4 || _la==T__5) {
				{
				setState(116);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__4:
					{
					setState(108);
					match(T__4);
					setState(109);
					((Priority1_binary_operatorContext)_localctx).f2 = unary_operator();
					 ((Priority1_binary_operatorContext)_localctx).f =  new BinaryOperator("<->", _localctx.f, ((Priority1_binary_operatorContext)_localctx).f2.f); 
					}
					break;
				case T__5:
					{
					setState(112);
					match(T__5);
					setState(113);
					((Priority1_binary_operatorContext)_localctx).f2 = unary_operator();
					 ((Priority1_binary_operatorContext)_localctx).f =  new BinaryOperator("!=", _localctx.f, ((Priority1_binary_operatorContext)_localctx).f2.f); 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Priority2_binary_operatorContext extends ParserRuleContext {
		public LTLFormula f;
		public Priority1_binary_operatorContext f1;
		public Priority1_binary_operatorContext f2;
		public List<Priority1_binary_operatorContext> priority1_binary_operator() {
			return getRuleContexts(Priority1_binary_operatorContext.class);
		}
		public Priority1_binary_operatorContext priority1_binary_operator(int i) {
			return getRuleContext(Priority1_binary_operatorContext.class,i);
		}
		public Priority2_binary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_priority2_binary_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterPriority2_binary_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitPriority2_binary_operator(this);
		}
	}

	public final Priority2_binary_operatorContext priority2_binary_operator() throws RecognitionException {
		Priority2_binary_operatorContext _localctx = new Priority2_binary_operatorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_priority2_binary_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			((Priority2_binary_operatorContext)_localctx).f1 = priority1_binary_operator();
			 ((Priority2_binary_operatorContext)_localctx).f =  ((Priority2_binary_operatorContext)_localctx).f1.f; 
			setState(129);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15) {
				{
				{
				setState(123);
				match(T__15);
				setState(124);
				((Priority2_binary_operatorContext)_localctx).f2 = priority1_binary_operator();
				 ((Priority2_binary_operatorContext)_localctx).f =  new BinaryOperator("&", _localctx.f, ((Priority2_binary_operatorContext)_localctx).f2.f); 
				}
				}
				setState(131);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Priority3_binary_operatorContext extends ParserRuleContext {
		public LTLFormula f;
		public Priority2_binary_operatorContext f1;
		public Priority2_binary_operatorContext f2;
		public List<Priority2_binary_operatorContext> priority2_binary_operator() {
			return getRuleContexts(Priority2_binary_operatorContext.class);
		}
		public Priority2_binary_operatorContext priority2_binary_operator(int i) {
			return getRuleContext(Priority2_binary_operatorContext.class,i);
		}
		public Priority3_binary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_priority3_binary_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterPriority3_binary_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitPriority3_binary_operator(this);
		}
	}

	public final Priority3_binary_operatorContext priority3_binary_operator() throws RecognitionException {
		Priority3_binary_operatorContext _localctx = new Priority3_binary_operatorContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_priority3_binary_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			((Priority3_binary_operatorContext)_localctx).f1 = priority2_binary_operator();
			 ((Priority3_binary_operatorContext)_localctx).f =  ((Priority3_binary_operatorContext)_localctx).f1.f; 
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << T__18))) != 0)) {
				{
				setState(146);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__16:
					{
					setState(134);
					match(T__16);
					setState(135);
					((Priority3_binary_operatorContext)_localctx).f2 = priority2_binary_operator();
					 ((Priority3_binary_operatorContext)_localctx).f =  new BinaryOperator("|", _localctx.f, ((Priority3_binary_operatorContext)_localctx).f2.f); 
					}
					break;
				case T__17:
					{
					setState(138);
					match(T__17);
					setState(139);
					((Priority3_binary_operatorContext)_localctx).f2 = priority2_binary_operator();
					 ((Priority3_binary_operatorContext)_localctx).f =  new BinaryOperator("!=", _localctx.f, ((Priority3_binary_operatorContext)_localctx).f2.f); 
					}
					break;
				case T__18:
					{
					setState(142);
					match(T__18);
					setState(143);
					((Priority3_binary_operatorContext)_localctx).f2 = priority2_binary_operator();
					 ((Priority3_binary_operatorContext)_localctx).f =  new BinaryOperator("<->", _localctx.f, ((Priority3_binary_operatorContext)_localctx).f2.f); 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Priority4_binary_operatorContext extends ParserRuleContext {
		public LTLFormula f;
		public Priority3_binary_operatorContext f1;
		public Priority3_binary_operatorContext f2;
		public List<Priority3_binary_operatorContext> priority3_binary_operator() {
			return getRuleContexts(Priority3_binary_operatorContext.class);
		}
		public Priority3_binary_operatorContext priority3_binary_operator(int i) {
			return getRuleContext(Priority3_binary_operatorContext.class,i);
		}
		public Priority4_binary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_priority4_binary_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterPriority4_binary_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitPriority4_binary_operator(this);
		}
	}

	public final Priority4_binary_operatorContext priority4_binary_operator() throws RecognitionException {
		Priority4_binary_operatorContext _localctx = new Priority4_binary_operatorContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_priority4_binary_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			((Priority4_binary_operatorContext)_localctx).f1 = priority3_binary_operator();
			 ((Priority4_binary_operatorContext)_localctx).f =  ((Priority4_binary_operatorContext)_localctx).f1.f; 
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(153);
				match(T__19);
				setState(154);
				((Priority4_binary_operatorContext)_localctx).f2 = priority3_binary_operator();
				 ((Priority4_binary_operatorContext)_localctx).f =  new BinaryOperator("<->", _localctx.f, ((Priority4_binary_operatorContext)_localctx).f2.f); 
				}
				}
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Priority5_binary_operatorContext extends ParserRuleContext {
		public LTLFormula f;
		public Priority4_binary_operatorContext f1;
		public Priority4_binary_operatorContext f2;
		public List<Priority4_binary_operatorContext> priority4_binary_operator() {
			return getRuleContexts(Priority4_binary_operatorContext.class);
		}
		public Priority4_binary_operatorContext priority4_binary_operator(int i) {
			return getRuleContext(Priority4_binary_operatorContext.class,i);
		}
		public Priority5_binary_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_priority5_binary_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterPriority5_binary_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitPriority5_binary_operator(this);
		}
	}

	public final Priority5_binary_operatorContext priority5_binary_operator() throws RecognitionException {
		Priority5_binary_operatorContext _localctx = new Priority5_binary_operatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_priority5_binary_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			((Priority5_binary_operatorContext)_localctx).f1 = priority4_binary_operator();
			 List<LTLFormula> formulas = new ArrayList<>(); formulas.add(((Priority5_binary_operatorContext)_localctx).f1.f); 
			setState(170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__20) {
				{
				{
				setState(164);
				match(T__20);
				setState(165);
				((Priority5_binary_operatorContext)_localctx).f2 = priority4_binary_operator();
				 formulas.add(((Priority5_binary_operatorContext)_localctx).f2.f); 
				}
				}
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

			        ((Priority5_binary_operatorContext)_localctx).f =  formulas.get(formulas.size() - 1);
			        for (int i = formulas.size() - 2; i >= 0; i--) {
			            ((Priority5_binary_operatorContext)_localctx).f =  new BinaryOperator("->", formulas.get(i), _localctx.f);
			        }
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormulaContext extends ParserRuleContext {
		public LTLFormula f;
		public Priority5_binary_operatorContext priority5_binary_operator;
		public Priority5_binary_operatorContext priority5_binary_operator() {
			return getRuleContext(Priority5_binary_operatorContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof nusmv_ltlListener ) ((nusmv_ltlListener)listener).exitFormula(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_formula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(T__21);
			setState(176);
			((FormulaContext)_localctx).priority5_binary_operator = priority5_binary_operator();
			 ((FormulaContext)_localctx).f =  ((FormulaContext)_localctx).priority5_binary_operator.f; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\35\u00b6\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2%\n"+
		"\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\63\n\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\5\4;\n\4\3\5\3\5\3\5\7\5@\n\5\f\5\16\5C\13\5\3\5\3\5"+
		"\3\5\5\5H\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6T\n\6\3\6\3\6"+
		"\5\6X\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7b\n\7\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\5\bk\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\tw\n\t\f\t"+
		"\16\tz\13\t\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u0082\n\n\f\n\16\n\u0085\13\n"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\7\13\u0095\n\13\f\13\16\13\u0098\13\13\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u00a0"+
		"\n\f\f\f\16\f\u00a3\13\f\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00ab\n\r\f\r\16"+
		"\r\u00ae\13\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\2\2\17\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\2\2\u00c1\2$\3\2\2\2\4\62\3\2\2\2\6:\3\2\2\2\b<\3\2\2"+
		"\2\nW\3\2\2\2\fa\3\2\2\2\16j\3\2\2\2\20l\3\2\2\2\22{\3\2\2\2\24\u0086"+
		"\3\2\2\2\26\u0099\3\2\2\2\30\u00a4\3\2\2\2\32\u00b1\3\2\2\2\34\35\7\3"+
		"\2\2\35%\b\2\1\2\36\37\7\4\2\2\37%\b\2\1\2 !\7\5\2\2!%\b\2\1\2\"#\7\6"+
		"\2\2#%\b\2\1\2$\34\3\2\2\2$\36\3\2\2\2$ \3\2\2\2$\"\3\2\2\2%\3\3\2\2\2"+
		"&\'\7\7\2\2\'\63\b\3\1\2()\7\b\2\2)\63\b\3\1\2*+\7\t\2\2+\63\b\3\1\2,"+
		"-\7\n\2\2-\63\b\3\1\2./\7\13\2\2/\63\b\3\1\2\60\61\7\f\2\2\61\63\b\3\1"+
		"\2\62&\3\2\2\2\62(\3\2\2\2\62*\3\2\2\2\62,\3\2\2\2\62.\3\2\2\2\62\60\3"+
		"\2\2\2\63\5\3\2\2\2\64\65\7\34\2\2\65;\b\4\1\2\66\67\7\32\2\2\67;\b\4"+
		"\1\289\7\33\2\29;\b\4\1\2:\64\3\2\2\2:\66\3\2\2\2:8\3\2\2\2;\7\3\2\2\2"+
		"<A\7\35\2\2=>\7\r\2\2>@\7\35\2\2?=\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2"+
		"\2BG\3\2\2\2CA\3\2\2\2DE\7\16\2\2EF\7\34\2\2FH\7\17\2\2GD\3\2\2\2GH\3"+
		"\2\2\2H\t\3\2\2\2IJ\7\32\2\2JX\b\6\1\2KL\7\33\2\2LX\b\6\1\2MN\b\6\1\2"+
		"NS\5\b\5\2OP\5\4\3\2PQ\5\6\4\2QR\b\6\1\2RT\3\2\2\2SO\3\2\2\2ST\3\2\2\2"+
		"TU\3\2\2\2UV\b\6\1\2VX\3\2\2\2WI\3\2\2\2WK\3\2\2\2WM\3\2\2\2X\13\3\2\2"+
		"\2YZ\5\n\6\2Z[\b\7\1\2[b\3\2\2\2\\]\7\20\2\2]^\5\30\r\2^_\7\21\2\2_`\b"+
		"\7\1\2`b\3\2\2\2aY\3\2\2\2a\\\3\2\2\2b\r\3\2\2\2cd\5\2\2\2de\5\16\b\2"+
		"ef\b\b\1\2fk\3\2\2\2gh\5\f\7\2hi\b\b\1\2ik\3\2\2\2jc\3\2\2\2jg\3\2\2\2"+
		"k\17\3\2\2\2lm\5\16\b\2mx\b\t\1\2no\7\7\2\2op\5\16\b\2pq\b\t\1\2qw\3\2"+
		"\2\2rs\7\b\2\2st\5\16\b\2tu\b\t\1\2uw\3\2\2\2vn\3\2\2\2vr\3\2\2\2wz\3"+
		"\2\2\2xv\3\2\2\2xy\3\2\2\2y\21\3\2\2\2zx\3\2\2\2{|\5\20\t\2|\u0083\b\n"+
		"\1\2}~\7\22\2\2~\177\5\20\t\2\177\u0080\b\n\1\2\u0080\u0082\3\2\2\2\u0081"+
		"}\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2"+
		"\u0084\23\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0087\5\22\n\2\u0087\u0096"+
		"\b\13\1\2\u0088\u0089\7\23\2\2\u0089\u008a\5\22\n\2\u008a\u008b\b\13\1"+
		"\2\u008b\u0095\3\2\2\2\u008c\u008d\7\24\2\2\u008d\u008e\5\22\n\2\u008e"+
		"\u008f\b\13\1\2\u008f\u0095\3\2\2\2\u0090\u0091\7\25\2\2\u0091\u0092\5"+
		"\22\n\2\u0092\u0093\b\13\1\2\u0093\u0095\3\2\2\2\u0094\u0088\3\2\2\2\u0094"+
		"\u008c\3\2\2\2\u0094\u0090\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2"+
		"\2\2\u0096\u0097\3\2\2\2\u0097\25\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009a"+
		"\5\24\13\2\u009a\u00a1\b\f\1\2\u009b\u009c\7\26\2\2\u009c\u009d\5\24\13"+
		"\2\u009d\u009e\b\f\1\2\u009e\u00a0\3\2\2\2\u009f\u009b\3\2\2\2\u00a0\u00a3"+
		"\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\27\3\2\2\2\u00a3"+
		"\u00a1\3\2\2\2\u00a4\u00a5\5\26\f\2\u00a5\u00ac\b\r\1\2\u00a6\u00a7\7"+
		"\27\2\2\u00a7\u00a8\5\26\f\2\u00a8\u00a9\b\r\1\2\u00a9\u00ab\3\2\2\2\u00aa"+
		"\u00a6\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2"+
		"\2\2\u00ad\u00af\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af\u00b0\b\r\1\2\u00b0"+
		"\31\3\2\2\2\u00b1\u00b2\7\30\2\2\u00b2\u00b3\5\30\r\2\u00b3\u00b4\b\16"+
		"\1\2\u00b4\33\3\2\2\2\22$\62:AGSWajvx\u0083\u0094\u0096\u00a1\u00ac";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
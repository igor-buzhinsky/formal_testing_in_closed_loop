// Generated from /home/buzhinsky/repos/formal_testing_in_closed_loop/nusmv_ltl.g4 by ANTLR 4.6
package formal_testing.generated;

import formal_testing.formula.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link nusmv_ltlParser}.
 */
public interface nusmv_ltlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#unary_operator_sign}.
	 * @param ctx the parse tree
	 */
	void enterUnary_operator_sign(nusmv_ltlParser.Unary_operator_signContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#unary_operator_sign}.
	 * @param ctx the parse tree
	 */
	void exitUnary_operator_sign(nusmv_ltlParser.Unary_operator_signContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#comparison_operator_sign}.
	 * @param ctx the parse tree
	 */
	void enterComparison_operator_sign(nusmv_ltlParser.Comparison_operator_signContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#comparison_operator_sign}.
	 * @param ctx the parse tree
	 */
	void exitComparison_operator_sign(nusmv_ltlParser.Comparison_operator_signContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(nusmv_ltlParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(nusmv_ltlParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#composite_id}.
	 * @param ctx the parse tree
	 */
	void enterComposite_id(nusmv_ltlParser.Composite_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#composite_id}.
	 * @param ctx the parse tree
	 */
	void exitComposite_id(nusmv_ltlParser.Composite_idContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#proposition}.
	 * @param ctx the parse tree
	 */
	void enterProposition(nusmv_ltlParser.PropositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#proposition}.
	 * @param ctx the parse tree
	 */
	void exitProposition(nusmv_ltlParser.PropositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(nusmv_ltlParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(nusmv_ltlParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#unary_operator}.
	 * @param ctx the parse tree
	 */
	void enterUnary_operator(nusmv_ltlParser.Unary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#unary_operator}.
	 * @param ctx the parse tree
	 */
	void exitUnary_operator(nusmv_ltlParser.Unary_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#priority1_binary_operator}.
	 * @param ctx the parse tree
	 */
	void enterPriority1_binary_operator(nusmv_ltlParser.Priority1_binary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#priority1_binary_operator}.
	 * @param ctx the parse tree
	 */
	void exitPriority1_binary_operator(nusmv_ltlParser.Priority1_binary_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#priority2_binary_operator}.
	 * @param ctx the parse tree
	 */
	void enterPriority2_binary_operator(nusmv_ltlParser.Priority2_binary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#priority2_binary_operator}.
	 * @param ctx the parse tree
	 */
	void exitPriority2_binary_operator(nusmv_ltlParser.Priority2_binary_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#priority3_binary_operator}.
	 * @param ctx the parse tree
	 */
	void enterPriority3_binary_operator(nusmv_ltlParser.Priority3_binary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#priority3_binary_operator}.
	 * @param ctx the parse tree
	 */
	void exitPriority3_binary_operator(nusmv_ltlParser.Priority3_binary_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#priority4_binary_operator}.
	 * @param ctx the parse tree
	 */
	void enterPriority4_binary_operator(nusmv_ltlParser.Priority4_binary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#priority4_binary_operator}.
	 * @param ctx the parse tree
	 */
	void exitPriority4_binary_operator(nusmv_ltlParser.Priority4_binary_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#priority5_binary_operator}.
	 * @param ctx the parse tree
	 */
	void enterPriority5_binary_operator(nusmv_ltlParser.Priority5_binary_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#priority5_binary_operator}.
	 * @param ctx the parse tree
	 */
	void exitPriority5_binary_operator(nusmv_ltlParser.Priority5_binary_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link nusmv_ltlParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(nusmv_ltlParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link nusmv_ltlParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(nusmv_ltlParser.FormulaContext ctx);
}
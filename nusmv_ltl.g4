grammar nusmv_ltl;

options {
  language = Java;
}

@header {
import formal_testing.formula.*;
}

@parser::members {
}

unary_operator_sign returns[String value]
    : '!' { $value = "!"; }
    | 'X' { $value = "X"; }
    | 'G' { $value = "G"; }
    | 'F' { $value = "F"; }
    ;

comparison_operator_sign returns[String value]
    : '=' { $value = "="; }
    | '!=' { $value = "!="; }
    | '>' { $value = ">"; }
    | '>=' { $value = ">="; }
    | '<' { $value = "<"; }
    | '<=' { $value = "<="; }
    ;

constant returns[String value]
    : INT_CONST { $value = $INT_CONST.text; }
    | TRUE { $value = "TRUE"; }
    | FALSE  { $value = "FALSE"; }
    ;

composite_id
    : ID ('.' ID)* ('[' INT_CONST']')?
    ;

proposition returns[LTLFormula f]
    : TRUE { $f = LTLFormula.TRUE; }
    | FALSE { $f = LTLFormula.FALSE; }
    | ({ String c = "TRUE"; String op = "="; }
      composite_id (comparison_operator_sign constant { op = $comparison_operator_sign.value; c = $constant.value; })?
      { $f = new Proposition($composite_id.text, op, c); })
    ;

atom returns[LTLFormula f]
    : proposition { $f = $proposition.f; }
    | '(' priority5_binary_operator ')' { $f = $priority5_binary_operator.f; }
    ;

unary_operator returns[LTLFormula f]
    : unary_operator_sign unary_operator { $f = new UnaryOperator($unary_operator_sign.value, $unary_operator.f); }
    | atom { $f = $atom.f; }
    ;

priority1_binary_operator returns[LTLFormula f]
    : f1=unary_operator { $f = $f1.f; }
      ( '=' f2=unary_operator { $f = new BinaryOperator("<->", $f, $f2.f); }
      | '!=' f2=unary_operator { $f = new BinaryOperator("!=", $f, $f2.f); }
      )*
    ;

priority2_binary_operator returns[LTLFormula f]
    : f1=priority1_binary_operator { $f = $f1.f; }
      ('&' f2=priority1_binary_operator { $f = new BinaryOperator("&", $f, $f2.f); })*
    ;

priority3_binary_operator returns[LTLFormula f]
    : f1=priority2_binary_operator { $f = $f1.f; }
      ( '|' f2=priority2_binary_operator { $f = new BinaryOperator("|", $f, $f2.f); }
      | 'xor' f2=priority2_binary_operator { $f = new BinaryOperator("!=", $f, $f2.f); }
      | 'xnor' f2=priority2_binary_operator { $f = new BinaryOperator("<->", $f, $f2.f); }
      )*
    ;

priority4_binary_operator returns[LTLFormula f]
    : f1=priority3_binary_operator { $f = $f1.f; }
      ('<->' f2=priority3_binary_operator { $f = new BinaryOperator("<->", $f, $f2.f); })*
    ;

priority5_binary_operator returns[LTLFormula f]
    : f1=priority4_binary_operator { List<LTLFormula> formulas = new ArrayList<>(); formulas.add($f1.f); }
      ('->' f2=priority4_binary_operator { formulas.add($f2.f); })*
      {
        $f = formulas.get(formulas.size() - 1);
        for (int i = formulas.size() - 2; i >= 0; i--) {
            $f = new BinaryOperator("->", formulas.get(i), $f);
        }
      }
    ;

formula returns[LTLFormula f]
    : 'LTLSPEC' priority5_binary_operator { $f = $priority5_binary_operator.f; }
    ;

// operator sequences
WS : (' ' | '\t' | ('\r'? '\n'))+ -> channel(HIDDEN);

// constants
TRUE : 'TRUE'; FALSE : 'FALSE';
INT_CONST : '-'? ('0' | ('1'..'9' ('0'..'9')*));

// ids
ID : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
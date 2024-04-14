// Expression.g4
grammar Expression;
@header {
// 定义package
package antlr.core;
}
// Lexer rules
WS : [ \t\r\n]+ -> skip;
STRING : '\'' (~['\r\n])* '\'';
EXTRACT_PATH : '[' (([0-9a-zA-Z.$[\]_()'])+)? ']';
VARIABLE_NAME : '${' ~[${}]+ '}';
NUMBER : ([0-9][0-9]*)+(.[0-9]+)?;
LPAREN : '(';
RPAREN : ')';
EQUAL : '==';
NOT_EQUAL : '!=';
LESS_THAN : '<';
LESS_EQUAL : '<=';
LARGER_THAN : '>';
LARGER_EQUAL : '>=';
AND : 'AND';
OR : 'OR';
TRUE: 'true';
FALSE: 'false';
NULL: 'null';

// Parser rules
expression
           : LPAREN expression RPAREN   #expressionWithBr
           | EXTRACT_PATH op=(EQUAL | NOT_EQUAL) STRING   #expressionWithString
           | EXTRACT_PATH op=(EQUAL | NOT_EQUAL) VARIABLE_NAME   #expressionWithString
           | EXTRACT_PATH op=(EQUAL | NOT_EQUAL) EXTRACT_PATH   #expressionWithString
           | EXTRACT_PATH op=(EQUAL | NOT_EQUAL) TRUE   #expressionWithString
           | EXTRACT_PATH op=(EQUAL | NOT_EQUAL) FALSE   #expressionWithString
           | EXTRACT_PATH op=(EQUAL | NOT_EQUAL) NULL   #expressionWithString
           | NULL op=(EQUAL | NOT_EQUAL) NULL   #expressionWithString
           | TRUE op=(EQUAL | NOT_EQUAL) TRUE   #expressionWithString
           | FALSE op=(EQUAL | NOT_EQUAL) FALSE   #expressionWithString
           | TRUE op=(EQUAL | NOT_EQUAL) FALSE   #expressionWithString
           | FALSE op=(EQUAL | NOT_EQUAL) TRUE   #expressionWithString
           | NULL op=(EQUAL | NOT_EQUAL) TRUE   #expressionWithString
           | NULL op=(EQUAL | NOT_EQUAL) FALSE   #expressionWithString
           | TRUE op=(EQUAL | NOT_EQUAL) NULL   #expressionWithString
           | FALSE op=(EQUAL | NOT_EQUAL) NULL   #expressionWithString
           | EXTRACT_PATH op=(EQUAL | NOT_EQUAL | LESS_THAN | LESS_EQUAL | LARGER_THAN | LARGER_EQUAL) NUMBER   #expressionWithNumber
           | NUMBER op=(EQUAL | NOT_EQUAL | LESS_THAN | LESS_EQUAL | LARGER_THAN | LARGER_EQUAL) EXTRACT_PATH   #expressionWithNumber
           | VARIABLE_NAME op=(EQUAL | NOT_EQUAL) STRING   #expressionWithString
           | VARIABLE_NAME op=(EQUAL | NOT_EQUAL) EXTRACT_PATH   #expressionWithString
           | VARIABLE_NAME op=(EQUAL | NOT_EQUAL) VARIABLE_NAME   #expressionWithString
           | VARIABLE_NAME op=(EQUAL | NOT_EQUAL) TRUE   #expressionWithString
           | VARIABLE_NAME op=(EQUAL | NOT_EQUAL) FALSE   #expressionWithString
           | VARIABLE_NAME op=(EQUAL | NOT_EQUAL) NULL   #expressionWithString
           | VARIABLE_NAME op=(EQUAL | NOT_EQUAL | LESS_THAN | LESS_EQUAL | LARGER_THAN | LARGER_EQUAL) NUMBER   #expressionWithNumber
           | NUMBER op=(EQUAL | NOT_EQUAL | LESS_THAN | LESS_EQUAL | LARGER_THAN | LARGER_EQUAL) VARIABLE_NAME   #expressionWithNumber
           | NUMBER op=(EQUAL | NOT_EQUAL | LESS_THAN | LESS_EQUAL | LARGER_THAN | LARGER_EQUAL) NUMBER   #expressionWithNumber
           | expression AND expression   #andOperation
           | expression OR expression   #orOperation
           ;



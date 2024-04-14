// Expression.g4
grammar Constraints;
@header {
// 定义package
package antlr.core;
}
// Lexer rules
WS : [ \t\r\n]+ -> skip;
STRING : '"' (~["\r\n])* '"';
FIELD_NAME : '[' (.)+? ']';
NUMBER : ([0-9][0-9]*)+(.[0-9]+)?;
LPAREN : '(';
RPAREN : ')';
EQUAL : '=';
NOT_EQUAL : '<>';
LESS_THAN : '<';
LESS_EQUAL : '<=';
LARGER_THAN : '>';
LARGER_EQUAL : '>=';
AND : 'AND';
OR : 'OR';
IF: 'IF';
THEN: 'THEN';
IN: 'IN';
LIKE: 'LIKE';
NOT: 'NOT';
VALUES: '{' (STRING|NUMBER)+([,][ \t]*STRING|NUMBER)* '}';
SEMI: ';';


// Parser rules
expression
           : LPAREN expression RPAREN   #expressionWithBr
           | FIELD_NAME op=(EQUAL | NOT_EQUAL) STRING   #compareString
           | FIELD_NAME op=(EQUAL | NOT_EQUAL | LESS_THAN | LESS_EQUAL | LARGER_THAN | LARGER_EQUAL) NUMBER #compareNumber
           | FIELD_NAME op=(EQUAL | NOT_EQUAL | LESS_THAN | LESS_EQUAL | LARGER_THAN | LARGER_EQUAL) FIELD_NAME #compareField
           | FIELD_NAME IN VALUES  #inOperator
           | FIELD_NAME LIKE STRING     #likeOperator
           | IF expression THEN expression   #ifThenStatement
           | NOT expression #notOperator
           | expression AND expression   #andOperator
           | expression OR expression   #orOperator
           ;

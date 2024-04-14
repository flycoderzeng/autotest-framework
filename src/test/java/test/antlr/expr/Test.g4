// Calculator.g4
grammar Test;

// Lexer rules
WS : [ \t\r\n]+ -> skip;
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
LPAREN : '(';
RPAREN : ')';
INT : [0-9]+;

// Parser rules
expr : term
     | expr ADD term
     | expr SUB term
     ;

term : factor
     | term MUL factor
     | term DIV factor
     ;

factor : INT
       | LPAREN expr RPAREN
       ;

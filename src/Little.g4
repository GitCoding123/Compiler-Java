grammar Little;

/* Program */
program           : PROGRAM id BEGIN pgm_body END+ EOF;
id                : IDENTIFIER;
pgm_body          : decl func_declarations;
decl          : string_decl decl | var_decl decl | ;


/* Global String Declaration */
string_decl       : STRING id ASSIGN_OP str SEMICOLON;
str               : STRINGLITERAL;

/* Variable Declaration */
var_decl          : var_type id_list SEMICOLON;
var_type         : FLOAT | INT ;
any_type          : var_type | VOID ;
id_list           : id id_tail ;
id_tail           : COMMA id id_tail |;

/* Function Paramater List */
param_decl_list   : param_decl param_decl_tail | ;
param_decl        : var_type id;
param_decl_tail   : COMMA param_decl param_decl_tail | ;

/* Function Declarations */
func_declarations : func_decl func_declarations | ;
func_decl         : FUNCTION any_type id OPENPAR_OP param_decl_list CLOSEDPAR_OP BEGIN func_body END;
func_body         : decl stmt_list;

/* Statement List */
stmt_list         : stmt stmt_list | ;
stmt              : base_stmt | if_stmt | while_stmt;
base_stmt         : assign_stmt | read_stmt | write_stmt | return_stmt;

/* Basic Statements */
assign_stmt       : assign_expr SEMICOLON;
assign_expr       : id ASSIGN_OP expr;
read_stmt         : READ OPENPAR_OP id_list CLOSEDPAR_OP SEMICOLON;
write_stmt        : WRITE OPENPAR_OP id_list CLOSEDPAR_OP SEMICOLON;
return_stmt       : RETURN expr SEMICOLON;

/* Expressions */
expr              : expr_prefix factor;
expr_prefix       : expr_prefix factor addop | ;
factor            : factor_prefix postfix_expr;
factor_prefix     : factor_prefix postfix_expr mulop | ;
postfix_expr      : primary | call_expr;
call_expr         : id OPENPAR_OP expr_list CLOSEDPAR_OP;
expr_list         : expr expr_list_tail | ;
expr_list_tail    : COMMA expr expr_list_tail | ;
primary           : OPENPAR_OP expr CLOSEDPAR_OP | id | INTLITERAL | FLOATLITERAL;
addop             : PLUS_OP | MINUS_OP;
mulop             : MULT_OP | DIV_OP;

/* Complex Statements and Condition */
if_stmt           : IF OPENPAR_OP cond CLOSEDPAR_OP decl stmt_list else_part ENDIF;
else_part         : ELSE decl stmt_list |;
cond              : expr compop expr;
compop            : LESST_OP | GREATERT_OP | EQUAL_OP | NOTEQUAL_OP | LESSEQ_OP | GREATEREQ_OP;

/* While statements */
while_stmt       : WHILE OPENPAR_OP cond CLOSEDPAR_OP decl stmt_list ENDWHILE;


//prule: (KEYWORD | IDENTIFIER | INTLITERAL | FLOATLITERAL | STRINGLITERAL | OPERATOR | COMMENT)*;
fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];

WS: [ \n\t\r]+ -> skip;

PROGRAM: 'PROGRAM';
BEGIN: 'BEGIN';

FUNCTION: 'FUNCTION';
READ: 'READ';
WRITE: 'WRITE';
IF: 'IF';
ELSE: 'ELSE';
ENDIF: 'ENDIF';
WHILE: 'WHILE';
ENDWHILE: 'ENDWHILE';
CONTINUE: 'CONTINUE';
BREAK: 'BREAK';
RETURN: 'RETURN';
INT: 'INT';
VOID: 'VOID';
STRING: 'STRING';
FLOAT: 'FLOAT';
END: 'END';

//OPERATORS
SEMICOLON: ';';
ASSIGN_OP: ':=';
COMMA: ',';
OPENPAR_OP: '(';
CLOSEDPAR_OP: ')';
PLUS_OP: '+';
MINUS_OP: '-';
MULT_OP: '*';
DIV_OP: '/';
LESST_OP: '<';
GREATERT_OP: '>';
LESSEQ_OP: '<=';
GREATEREQ_OP: '>=';
EQUAL_OP: '=';
NOTEQUAL_OP: '!=';

KEYWORD: PROGRAM | BEGIN | END | FUNCTION | READ | WRITE | IF | ELSE | ENDIF | WHILE | ENDWHILE | CONTINUE | BREAK | RETURN | INT | VOID | STRING | FLOAT;
IDENTIFIER : LETTER(LETTER|DIGIT)*;
STRINGLITERAL: '"'.*?'"';
INTLITERAL: DIGIT+;
FLOATLITERAL: (DIGIT*'.'DIGIT*);
COMMENT: '--' ~('\r' | '\n')* { skip(); };
//OPERATOR: ':=' | '+' | '-' | '*' | '/' | '=' | '!=' | '<' | '>' | '(' | ')' | ';' | ',' | '<=' | '>=';

grammar Kiss;

@header {
package com.github.sgdan.kiss;
}

script
    : function* expression
    ;

function
    : NAME '{' expression '}'
    ;

expression
    : NAME expression                           # Call
    | expression SYMBOL expression              # Operation
    | '(' expression ')'                        # Braces
    | expression '?' expression ':' expression  # Conditional
    | INTEGER                                   # Literal
    | VARIABLE                                  # Variable
    ;

NAME: [a-zA-Z]+;
INTEGER: [0-9]+;
SYMBOL: '+' | '-' | '*' | '/';
VARIABLE: '$';

WS: [ \r\n] -> skip;
COMMENT: '#' ~( '\r' | '\n' )* -> skip;

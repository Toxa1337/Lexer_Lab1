
* lang -> expr+
    * expr -> (assign_expr | if | while | for | print)
        * assign_expr -> init ';'
            * init -> IDENT ASSIGN_OP value
                * value -> value_types
                    * value_types -> (Compare | Condition | Add_Sub | Mul_Div | Brackets | UnValue)
                    * Compare -> value (comp_token value)?
                        * comp_token -> (COMP_LESS | COMP_L_EQ | COMP_MORE | COMP_M_EQ | COMP_EQ | COMP_NEQ)
                    * condition -> value comp_token value
                    * Add_Sub -> value (ADD_OP | SUB_OP value)*
                    * Mul_Div -> value (MUL_OP | DIV_OP value)*
                    * Brackets -> '(' value ')'
                    * UnValue -> INT | IDENT | STRING
        * print -> T_WRITE value ';'
        * for -> T_FOR '(' init ';' condition ';' expr ')' body
            * body -> '{' expr+ '}'
        * while -> T_WHILE '(' condition ')' body
        * if -> T_IF '(' condition ')' body else?
            * else -> T_ELSE body
* Упрощения:
    * '(')' -> ‘L’R’ _BRACKET
    * '{'}' -> ‘L’R’ _BRACE
    * ';' -> END_LINE










package DSL;

import DSL.NODES.*;
import DSL.TOKENS.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Parser {
    ArrayList<Token> tokenList;
    int position = 0; // Позиция в списке токенов

    //public Parser() { this.tokenList = new Lexer().getTokens(); } // МОЖНО УБРАТЬ
    public Parser(ArrayList<Token> tokenList) { this.tokenList = tokenList; }

    private Token seekToken(String[] sampleTypes) {
        // Перебор подходящих токенов для текущей позиции
        if (position < tokenList.size()) {
            Token curToken = tokenList.get(position);
            for (String sampleTokenType : sampleTypes) {
                if (sampleTokenType.equals(curToken.type())) {
                    position++;
                    return curToken;
                }
            }
            //if (curToken.type().equals("OL_COMMENT")) { // УБРАТЬ, Т.К. ОБРАБОТКА ЕСТЬ В ЛЕКСЕРЕ
                //position++;
                //return seekToken(sampleTypes);
            //}
        }
        // Если все указанные типы не подошли
        return null;
    }
    private Token expect(String[] expected) {
        Token seekToken = seekToken(expected);
        if (seekToken == null) {
            // Ошибка, токены не найдены
            throw new Error('\n' +
                    "\nLINE:" + tokenList.get(position).line() + ':' +
                    "\n " + Arrays.toString(expected) + "\n expected at position " + position + ' ' +
                    "(after " + tokenList.get(position - 1).insteadWas() + ")" +
                    ",\n instead was [" + tokenList.get(position).insteadWas() + "]!\n");
        }
        return seekToken;
    }
    // Основной метод
    public Root parseLang() {
        // Основной метод обработки грамматики
        // lang -> expr+
        Root root = new Root();
        while (position < tokenList.size()) {
            Node codeChainNode = parseExpr();
            root.addNode(codeChainNode);
        }
        return root;
    }
    //
    private Node parseExpr() {
        // expr -> assign_expr | stmt_if | loop_while | loop_for | print
        Token expectToken = expect(new String[]{"IDENT", "T_WRITE", "T_FOR", "T_WHILE", "T_IF"});
        switch (expectToken.type()) {
            case "IDENT" -> { // assign_expr -> init ';'
                position--;
                Node assign_expr = parseInit();
                expect(new String[]{"SEP_END_LINE"});
                return assign_expr;
            }
            case "T_FOR" -> { // for
                return parseLoopFor();
            }
            case "T_WHILE" -> { // while
                return parseLoopWhile();
            }
            case "T_IF" -> { // if
                return parseStmtIf();
            }
            case "T_WRITE" -> { // print -> T_WRITE value ';'
                Node valueToPrint = parseValue();
                expect(new String[]{"SEP_END_LINE"});
                return new UnOpNode(expectToken, valueToPrint);
            }
        }
        return null;
    }
    //
    private Node parseInit() {
        // init_expr -> IDENT ASSIGN_OP value
        IdNode idNode = new IdNode(expect(new String[]{"IDENT"}));
        Token assign = expect(new String[]{"ASSIGN_OP"});
        Node asValue = parseValue();
        return new BinOpNode(assign, idNode, asValue);
    }
    private Node parseValue() {
        // value -> value_types
        //  value_types -> (Compare | Add_Sub | Mul_Div | Brackets | UnValue | Condition)
        // Организовано в порядке приоритета операций:
        // * Возвращает операцию сравнения,
        // * * либо операцию слож-выч,
        // * * * либо операцию умн-дел,
        // * * * * либо узел в скобках,
        // * * * * * либо одиночное значение
        return parseValue(new String[]{"Compare", "AddSub", "MulDiv", "Brackets", "UnVal", "Condition"}, 0);
    }
    private Node parseValue(String select) {
        String[] operations = new String[] {"Compare", "AddSub", "MulDiv", "Brackets", "UnVal", "Condition"};
        return parseValue(operations, Arrays.asList(operations).indexOf(select));
    }
    HashMap<String, String[]> operators = new HashMap<>();
    {
        operators.put("Compare", new String[]{"COMP_LESS", "COMP_L_EQ", "COMP_MORE", "COMP_M_EQ", "COMP_EQ", "COMP_NEQ"});
        operators.put("Condition", operators.get("Compare"));
        operators.put("AddSub", new String[]{"ADD_OP", "SUB_OP"});
        operators.put("MulDiv", new String[]{"MUL_OP", "DIV_OP"});
        operators.put("UnVal", new String[]{"INT", "IDENT", "STRING"});
    }
    private Node parseValue(String[] expected, int select) {
        String op = expected[select];
        switch (op) {
            case "AddSub", "MulDiv" -> { // abc + (cde * efg)
                Node leftOperand = parseValue(expected, select + 1); // abc
                Token operator = seekToken(operators.get(op)); // +
                while (operator != null) {
                    Node rightOperand = parseValue(expected, select + 1); // cde * efg
                    leftOperand = new BinOpNode(operator, leftOperand, rightOperand);
                    operator = seekToken(operators.get(op));
                }
                return leftOperand;
            }
            case "Compare" -> { // Compare ?? a > b > c > d...
                Node leftOperand = parseValue(expected, select + 1);
                Token operator = seekToken(operators.get(op));
                if (operator != null) {
                    Node rightOperand = parseValue(expected, select + 1);
                    leftOperand = new BinOpNode(operator, leftOperand, rightOperand);
                }
                return leftOperand;
            }
            case "Brackets" -> {
                if (seekToken(new String[]{"SEP_L_BRACKET"}) != null) {
                    Node inner = parseValue(); // cde * efg
                    expect(new String[]{"SEP_R_BRACKET"});
                    return inner;
                }
                return parseValue(expected, select + 1);
            }
            case "UnVal" -> {
                Token expectToken = expect(operators.get(op));
                switch (expectToken.type()) {
                    case "INT" -> { return new IntNode(expectToken); }
                    case "IDENT" -> { return new IdNode(expectToken); }
                    case "STRING" -> { return new StringNode(expectToken); }
                }
            }
            case "Condition" -> { // хз.. нужно было для For-While-If.. Но.. хз
                Node leftOperand = parseValue("AddSub");
                Token operator = expect(operators.get(op));
                Node rightOperand = parseValue("AddSub");
                return new BinOpNode(operator, leftOperand, rightOperand);
            }
        }
        return null;
    }
    //
    private Node parseLoopFor() {
        // for -> T_FOR '(' Init ';' Condition ';' expr ')' body
        expect(new String[]{"SEP_L_BRACKET"});
        Node init = parseInit();
        expect(new String[]{"SEP_END_LINE"});
        Node condition = parseValue("Condition");
        expect(new String[]{"SEP_END_LINE"});
        Node expr = parseInit();
        expect(new String[]{"SEP_R_BRACKET"});
        ForNode forNode = new ForNode(init, condition, expr);
        //
        expect(new String[]{"SEP_L_BRACE"});
        while (seekToken(new String[]{"SEP_R_BRACE"}) == null) {
            Node codeChainNode = parseExpr();
            forNode.addExpr(codeChainNode);
        }
        return forNode;
    }
    private Node parseLoopWhile() {
        // while -> T_WHILE '(' condition ')' body
        expect(new String[]{"SEP_L_BRACKET"});
//        Node condition = parseCondition();
        Node condition = parseValue("Condition");
        expect(new String[]{"SEP_R_BRACKET"});
        WhileNode whileNode = new WhileNode(condition);
        //
        expect(new String[]{"SEP_L_BRACE"});
        while (seekToken(new String[]{"SEP_R_BRACE"}) == null) {
            Node codeChainNode = parseExpr();
            whileNode.addExpr(codeChainNode);
        }
        return whileNode;
    }
    private Node parseStmtIf() {
        // if -> T_IF '(' condition ')' body else?
        // else -> T_ELSE body
        expect(new String[]{"SEP_L_BRACKET"});
        Node condition = parseValue("Condition");
        expect(new String[]{"SEP_R_BRACKET"});
        IfElseNode ifElseNode = new IfElseNode(condition);
        //
        expect(new String[]{"SEP_L_BRACE"});
        while (seekToken(new String[]{"SEP_R_BRACE"}) == null) {
            Node codeChainNode = parseExpr();
            ifElseNode.addIfExpr(codeChainNode);
        }
        //
        Token tElse = seekToken(new String[]{"T_ELSE"});
        if (tElse != null) {
            expect(new String[]{"SEP_L_BRACE"});
            while (seekToken(new String[]{"SEP_R_BRACE"}) == null) {
                Node codeChainNode = parseExpr();
                ifElseNode.addElseExpr(codeChainNode);
            }
        }
        return ifElseNode;
    }
}

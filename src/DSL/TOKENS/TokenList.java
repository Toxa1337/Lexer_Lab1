package DSL.TOKENS;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class TokenList {
    // BASE TOKEN LIST:
    private final LinkedHashMap<String, Pattern> baseTokenList = new LinkedHashMap<>();
    {
        baseTokenList.put("COMMENT", Pattern.compile("#.*"));
        baseTokenList.put("SPACE", Pattern.compile("\s+"));
        baseTokenList.put("T_WHILE", Pattern.compile("while"));
        baseTokenList.put("T_DO", Pattern.compile("do"));
        baseTokenList.put("T_IF", Pattern.compile("if"));
        baseTokenList.put("T_ELSE", Pattern.compile("else"));
        baseTokenList.put("T_FOR", Pattern.compile("for"));
        //
        baseTokenList.put("T_BREAK", Pattern.compile("break"));
        baseTokenList.put("T_READ", Pattern.compile("read"));
        baseTokenList.put("T_WRITE", Pattern.compile("write"));
        baseTokenList.put("T_LOGIC_TRUE", Pattern.compile("True"));
        baseTokenList.put("T_LOGIC_FALSE", Pattern.compile("False"));
        //
        baseTokenList.put("IDENT", Pattern.compile("[a-z]([_a-zA-Z\\d])*"));
        baseTokenList.put("INT", Pattern.compile("0|(-?[1-9](\\d)*)"));
        baseTokenList.put("STRING", Pattern.compile("\"(.*)\""));
        baseTokenList.put("MUL_OP", Pattern.compile("\\*"));
        baseTokenList.put("DIV_OP", Pattern.compile("/"));
        baseTokenList.put("ADD_OP", Pattern.compile("\\+"));
        baseTokenList.put("SUB_OP", Pattern.compile("-"));
        //
        baseTokenList.put("COMP_L_EQ", Pattern.compile("<="));
        baseTokenList.put("COMP_M_EQ", Pattern.compile(">="));
        baseTokenList.put("COMP_LESS", Pattern.compile("<"));
        baseTokenList.put("COMP_MORE", Pattern.compile(">"));
        baseTokenList.put("COMP_EQ", Pattern.compile("=="));
        baseTokenList.put("COMP_NEQ", Pattern.compile("!="));
        //
        baseTokenList.put("B_AND", Pattern.compile("&"));
        baseTokenList.put("B_XOR", Pattern.compile("\\^"));
        baseTokenList.put("B_OR", Pattern.compile("\\|"));
        baseTokenList.put("B_NOT", Pattern.compile("~"));
        baseTokenList.put("ASSIGN_OP", Pattern.compile("="));
        //
        baseTokenList.put("SEP_END_LINE", Pattern.compile(";"));
        baseTokenList.put("SEP_L_BRACKET", Pattern.compile("\\("));
        baseTokenList.put("SEP_R_BRACKET", Pattern.compile("\\)"));
        baseTokenList.put("SEP_L_BRACE", Pattern.compile("\\{"));
        baseTokenList.put("SEP_R_BRACE", Pattern.compile("}"));
        //baseTokenList.put("SEP_SEMICOLON", Pattern.compile(";\s"));
        baseTokenList.put("SEP_COMMA", Pattern.compile(","));
        //
    }
    public LinkedHashMap<String, Pattern> getBaseTokenList() {
        return baseTokenList;
    }
}

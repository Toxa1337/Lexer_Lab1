public class TokenType {
    String typeName;
    String reg;
    public static TokenType[] tokenTypeList={
            new TokenType("T_NUM", "^0|[1-9][0-9]*"),

            new TokenType("T_SPACE", "\\ "),
            new TokenType("T_END_L", "[\\n]"),
            new TokenType("T_K", "[\\r]"),

            new TokenType("T_ASSIGN", "[=]"),
            new TokenType("T_PL", "[+]"),
            new TokenType("T_MIN", "[-]"),
            new TokenType("T_MULT", "[*]"),
            new TokenType("T_DIV", "[/]"),

            new TokenType("T_L", "[<]"),
            new TokenType("T_M", "[>]"),
            new TokenType("T_EQ", "(?i)=="),

            new TokenType("T_PRINT", "(?i)print"),
            new TokenType("T_FOR", "(?i)for"),
            new TokenType("T_WHILE","(?i)while"),

            new TokenType("T_END", "[;]"),
            new TokenType("T_LBR", "[(]"),
            new TokenType("T_RBR", "[)]"),
            new TokenType("T_LRP", "[{]"),
            new TokenType("T_RRP", "[}]"),

            new TokenType("T_VAR", "[a-z][a-z]*"),
            //float string лист
    };
    public TokenType(String typeName, String reg) {
        this.typeName = typeName;
        this.reg = reg;
    }
}
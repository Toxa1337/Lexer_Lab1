package DSL.node;

import DSL.token.Token;

public class IntNode extends Node {
    Token number;
    public IntNode(Token number) { this.number = number; }

    public Token getNumber() {
        return number;
    }
}

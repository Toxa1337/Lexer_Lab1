package DSL.node;

import DSL.token.Token;

public class StringNode extends Node {
    Token string;

    public StringNode(Token string) { this.string = string; }

    public Token getString() { return string; }
}

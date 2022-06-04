package DSL.node;

import DSL.token.Token;

public class IdNode extends Node {
    Token ident;
    public IdNode(Token ident) { this.ident = ident; }

    public Token getIdent() { return ident; }
}
package DSL.NODES;

import java.util.ArrayList;

public class IfElseNode extends Node {
    Node condition;
    ArrayList<Node> ifExprs = new ArrayList<>();
    ArrayList<Node> elseExprs = new ArrayList<>();

    public IfElseNode(Node condition) {
        this.condition = condition;
    }

    public void addIfExpr(Node expr) { ifExprs.add(expr); }
    public void addElseExpr(Node expr) { elseExprs.add(expr); }

    public Node getCondition() {
        return condition;
    }

    public ArrayList<Node> getIfExprs() {
        return ifExprs;
    }

    public ArrayList<Node> getElseExprs() {
        return elseExprs;
    }
}

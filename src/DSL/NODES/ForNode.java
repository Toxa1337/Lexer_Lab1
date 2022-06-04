package DSL.NODES;

import java.util.ArrayList;

public class ForNode extends Node {
    Node init;
    Node condition;
    Node expr;
    ArrayList<Node> exprs = new ArrayList<>();

//    public ForNode(Node init, Token operator, Node leftOperand, Node rightOperand, Node step) {
    public ForNode(Node init, Node condition, Node expr) {
        this.init = init;
        this.condition = condition;
        this.expr = expr;
    }

    public void addExpr(Node expr) { exprs.add(expr); }

    public Node getInit() {
        return init;
    }

    public Node getCondition() {
        return condition;
    }

    public Node getExpr() {
        return expr;
    }

    public ArrayList<Node> getExprs() {
        return exprs;
    }
}

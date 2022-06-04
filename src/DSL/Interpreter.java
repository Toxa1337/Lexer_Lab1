package DSL;

import DSL.NODES.*;

import java.util.HashMap;

public class Interpreter {
    Root root;
    HashMap<String, String> scope = new HashMap<>();

    public Interpreter(Root root) { this.root = root; }

    // Основной метод
    public void execute() {
        for (Node node : root.getCodeChain()) {
            executeNode(node);
        }
    }

    private String executeNode(Node node) {
        //
        if (node.getClass().equals(IntNode.class)) {
            return ((IntNode) node).getNumber().value();
        }
        if (node.getClass().equals(StringNode.class)) {
            return ((StringNode) node).getString().value();
        }
        if (node.getClass().equals(IdNode.class)) {
            return scope.get(((IdNode) node).getIdent().value());
        }
        if (node.getClass().equals(UnOpNode.class)) {
            if (((UnOpNode) node).getOperator().type().equals("T_WRITE")) {
                System.out.println(executeNode(((UnOpNode) node).getArg()));
            }
        }
        if (node.getClass().equals(BinOpNode.class)) {
            if ("ASSIGN_OP".equals(((BinOpNode) node).getOperator().type())) {
                Node idNode = ((BinOpNode) node).getLeftOperand();
                String asValue = executeNode(((BinOpNode) node).getRightOperand());
                scope.put(((IdNode) idNode).getIdent().value(), asValue);
            } else {
                int leftOperand = Integer.parseInt(executeNode(((BinOpNode) node).getLeftOperand()));
                int rightOperand = Integer.parseInt(executeNode(((BinOpNode) node).getRightOperand()));
                switch (((BinOpNode) node).getOperator().type()) {
                    case "ADD_OP" -> { return String.valueOf(leftOperand + rightOperand); }
                    //
                    case "SUB_OP" -> { return String.valueOf(leftOperand - rightOperand); }
                    case "MUL_OP" -> { return String.valueOf(leftOperand * rightOperand); }
                    case "DIV_OP" -> { return String.valueOf(leftOperand / rightOperand); }
                    //
                    case "COMP_L_EQ" -> { return String.valueOf(leftOperand <= rightOperand); }
                    case "COMP_M_EQ" -> { return String.valueOf(leftOperand >= rightOperand); }
                    case "COMP_LESS" -> { return String.valueOf(leftOperand < rightOperand); }
                    case "COMP_MORE" -> { return String.valueOf(leftOperand > rightOperand); }
                    case "COMP_EQ" -> { return String.valueOf(leftOperand == rightOperand); }
                    case "COMP_NEQ" -> { return String.valueOf(leftOperand != rightOperand); }
                }
            }
        }
        // FOR, WHILE, IF-ELSE
        if (node.getClass().equals(IfElseNode.class)) {
            if ("true".equals(executeNode(((IfElseNode) node).getCondition()))) {
                for (Node ifExpr : ((IfElseNode) node).getIfExprs()) {
                    executeNode(ifExpr);
                }
            } else {
                if ("false".equals(executeNode(((IfElseNode) node).getCondition()))) {
                    for (Node elseExpr : ((IfElseNode) node).getElseExprs()) {
                        executeNode(elseExpr);
                    }
                }
            }
        }
        if (node.getClass().equals(ForNode.class)) {
            executeNode(((ForNode) node).getInit());
            while (executeNode(((ForNode) node).getCondition()).equals("true")) {
                for (Node expr : ((ForNode) node).getExprs()) executeNode(expr);
                executeNode(((ForNode) node).getExpr());
            }
        }
        if (node.getClass().equals(WhileNode.class)) {
            while (executeNode(((WhileNode) node).getCondition()).equals("true")) {
                for (Node expr : ((WhileNode) node).getExprs()) executeNode(expr);
            }
        }
        return "";
    }
}

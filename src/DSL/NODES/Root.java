package DSL.NODES;

import java.util.ArrayList;

public class Root extends Node {
    ArrayList<Node> codeChain = new ArrayList<>();

    public void addNode(Node node) { codeChain.add(node); }

    public ArrayList<Node> getCodeChain() { return codeChain; }
}

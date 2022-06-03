import java.util.ArrayList;

public class ForSimpleClass extends SimpleClass {
    Token operator;
    SimpleClass leftVal;
    SimpleClass rightVal;
    SimpleClass action;
    public ArrayList<SimpleClass> operations = new ArrayList<>();

    public ForSimpleClass(Token operator, SimpleClass leftVal, SimpleClass rightVal, SimpleClass action) {
        this.operator = operator;
        this.leftVal = leftVal;
        this.rightVal = rightVal;
        this.action=action;
    }
    public void addOperations(SimpleClass op){
        operations.add(op);
    }
}
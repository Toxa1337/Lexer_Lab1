import java.util.ArrayList;

public class WhileSimpleClass extends SimpleClass {
    Token operator;
    SimpleClass leftVal;
    SimpleClass rightVal;
    public ArrayList<SimpleClass> operations = new ArrayList<>();

    public WhileSimpleClass(Token operator, SimpleClass leftVal, SimpleClass rightVal) {
        this.operator = operator;
        this.leftVal = leftVal;
        this.rightVal = rightVal;
    }
    public void addOperations(SimpleClass op){
        operations.add(op);
    }
}
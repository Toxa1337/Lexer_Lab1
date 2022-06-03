public class BinOpSimpleClass extends SimpleClass {
    Token operator;
    SimpleClass leftVal;
    SimpleClass rightVal;

    public BinOpSimpleClass(Token operator, SimpleClass leftVal, SimpleClass rightVal) {
        this.operator = operator;
        this.leftVal = leftVal;
        this.rightVal = rightVal;
    }
}
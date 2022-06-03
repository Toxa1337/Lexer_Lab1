public class UnOpSimpleClass extends SimpleClass {
    Token operator;
    SimpleClass value;

    public UnOpSimpleClass(Token operator, SimpleClass value) {
        this.operator = operator;
        this.value = value;
    }
}
import java.util.ArrayList;

public class RootSimpleClass extends SimpleClass {
    ArrayList<SimpleClass> codeStr=new ArrayList<>();
    public void addNode(SimpleClass simpleClass){
        codeStr.add(simpleClass);
    }
}
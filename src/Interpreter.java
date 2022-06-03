import java.util.HashMap;

public class Interpreter {
    HashMap<String,String> scope=new HashMap<>();

    public String run(SimpleClass simpleClass){
        if (simpleClass.getClass()== UnOpSimpleClass.class) {
            if (((UnOpSimpleClass) simpleClass).operator.type.typeName.equals("T_PRINT")) {
                System.out.println(this.run(((UnOpSimpleClass) simpleClass).value));
            }
        }
        if (simpleClass.getClass()== BinOpSimpleClass.class) {
            if (((BinOpSimpleClass) simpleClass).operator.type.typeName.equals("T_ASSIGN"))
            {
                String res = this.run(((BinOpSimpleClass) simpleClass).rightVal);
                VarSimpleClass varNode = (VarSimpleClass) (((BinOpSimpleClass) simpleClass).leftVal);
                this.scope.put(varNode.var.value, res);
                return res;
            }
            else
            {
                int left=Integer.parseInt(this.run(((BinOpSimpleClass) simpleClass).leftVal));
                int right=Integer.parseInt(this.run(((BinOpSimpleClass) simpleClass).rightVal));
                switch (((BinOpSimpleClass) simpleClass).operator.type.typeName){
                    case "T_PL":
                        return String.valueOf(left+right);
                    case "T_MIN":
                        return String.valueOf(left-right);
                    case "T_MULT":
                        return String.valueOf(left*right);
                    case "T_DIV":
                        return String.valueOf(left/right);
                    case "T_ASSIGN":
                }
            }
        }
        if (simpleClass.getClass()== VarSimpleClass.class) {
            return scope.get(((VarSimpleClass) simpleClass).var.value);
        }
        if (simpleClass.getClass()== NumberSimpleClass.class) {
            return ((NumberSimpleClass) simpleClass).number.value;
        }
        if (simpleClass.getClass()== WhileSimpleClass.class){
            int left=Integer.parseInt(this.run(((WhileSimpleClass) simpleClass).leftVal));
            int right=Integer.parseInt(this.run(((WhileSimpleClass) simpleClass).rightVal));
            switch (((WhileSimpleClass) simpleClass).operator.type.typeName) {
                case "T_L":
                    while (left < right) {
                        for (int i = 0; i < ((WhileSimpleClass) simpleClass).operations.size(); i++)
                            this.run(((WhileSimpleClass) simpleClass).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileSimpleClass) simpleClass).leftVal));
                        right = Integer.parseInt(this.run(((WhileSimpleClass) simpleClass).rightVal));
                    }
                    break;
                case "T_M":
                    while (left > right) {
                        for (int i = 0; i < ((WhileSimpleClass) simpleClass).operations.size(); i++)
                            this.run(((WhileSimpleClass) simpleClass).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileSimpleClass) simpleClass).leftVal));
                        right = Integer.parseInt(this.run(((WhileSimpleClass) simpleClass).rightVal));
                    }
                    break;
                case "T_EQ":
                    while (left == right) {
                        for (int i = 0; i < ((WhileSimpleClass) simpleClass).operations.size(); i++)
                            this.run(((WhileSimpleClass) simpleClass).operations.get(i));
                        left = Integer.parseInt(this.run(((WhileSimpleClass) simpleClass).leftVal));
                        right = Integer.parseInt(this.run(((WhileSimpleClass) simpleClass).rightVal));
                    }
                    break;
            }
        }
        if (simpleClass.getClass()== ForSimpleClass.class){
            int left=Integer.parseInt(this.run(((ForSimpleClass) simpleClass).leftVal));
            int right=Integer.parseInt(this.run(((ForSimpleClass) simpleClass).rightVal));
            switch (((ForSimpleClass) simpleClass).operator.type.typeName) {
                case "T_L":
                    while (left < right) {
                        for (int i = 0; i < ((ForSimpleClass) simpleClass).operations.size(); i++)
                            this.run(((ForSimpleClass) simpleClass).operations.get(i));
                        this.run(((ForSimpleClass) simpleClass).action);
                        left = Integer.parseInt(this.run(((ForSimpleClass) simpleClass).leftVal));
                        right = Integer.parseInt(this.run(((ForSimpleClass) simpleClass).rightVal));
                    }
                    break;
                case "T_M":
                    while (left > right) {
                        for (int i = 0; i < ((ForSimpleClass) simpleClass).operations.size(); i++)
                            this.run(((ForSimpleClass) simpleClass).operations.get(i));
                        this.run(((ForSimpleClass) simpleClass).action);
                        left = Integer.parseInt(this.run(((ForSimpleClass) simpleClass).leftVal));
                        right = Integer.parseInt(this.run(((ForSimpleClass) simpleClass).rightVal));
                    }
                    break;
                case "T_EQ":
                    while (left == right) {
                        for (int i = 0; i < ((ForSimpleClass) simpleClass).operations.size(); i++)
                            this.run(((ForSimpleClass) simpleClass).operations.get(i));
                        this.run(((ForSimpleClass) simpleClass).action);
                        left = Integer.parseInt(this.run(((ForSimpleClass) simpleClass).leftVal));
                        right = Integer.parseInt(this.run(((ForSimpleClass) simpleClass).rightVal));
                    }
                    break;
            }
        }
        return "";
    }
}
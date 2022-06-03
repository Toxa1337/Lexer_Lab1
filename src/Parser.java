import java.util.ArrayList;

public class Parser {
    ArrayList<Token> tokens;
    int pos=0;

    public Parser(ArrayList<Token> tokens) { //получение токенов
        this.tokens = tokens;
    }

    public Token receive(String[] need){
        Token curToken;
        if (pos<tokens.size()) {
            curToken = tokens.get(pos);
            for (String tokenTypeName : need)
                if (tokenTypeName.equals(curToken.type.typeName)) {
                    pos++;
                    return curToken;
                }
        }
        return null;
    }

    public void need(String[] expected){ // информирование о возможной ошибке
        Token token= receive(expected);
        if(token==null){
            throw new Error("\nНа позииции ("+pos+") ожидается "+expected[0]);
        }
    }

    public SimpleClass parseVarNum(){
        if (tokens.get(pos).type.typeName.equals("T_NUM")){
            pos++;
            return new NumberSimpleClass(tokens.get(pos-1));
        }
        if (tokens.get(pos).type.typeName.equals("T_VAR")){
            pos++;
            return new VarSimpleClass(tokens.get(pos-1));
        }

        throw new Error("\nОжидается переменная или число на позиции ("+pos+")\n");
    }

    public SimpleClass parsePar(){
        if (tokens.get(pos).type.typeName.equals("T_LBR")){
            pos++;
            SimpleClass simpleClass = parseFormula();
            need(new String[]{"T_RBR"});
            return simpleClass;
        }
        else
            return parseVarNum();
    }

    public SimpleClass parseMultDiv(){
        SimpleClass leftVal= parsePar();
        Token operator= receive(new String[]{"T_MULT","T_DIV"});
        while (operator!=null){
            SimpleClass rightVal= parsePar();
            leftVal=new BinOpSimpleClass(operator,leftVal,rightVal);
            operator= receive(new String[]{"T_MULT","T_DIV"});
        }
        return leftVal;
    }

    public SimpleClass parseFormula(){
        SimpleClass leftVal= parseMultDiv();
        Token operator= receive(new String[]{"T_PL","T_MIN"});
        while (operator!=null){
            SimpleClass rightVal= parseMultDiv();
            leftVal=new BinOpSimpleClass(operator,leftVal,rightVal);
            operator= receive(new String[]{"T_PL","T_MIN"});
        }
        return leftVal;
    }

    public SimpleClass parseString(){
        if (tokens.get(pos).type.typeName.equals("T_VAR")) {
            SimpleClass varSimpleClass = parseVarNum();
            Token assign = receive(new String[]{"T_ASSIGN"});
            if (assign != null) {
                SimpleClass rightVal = parseFormula();
                return new BinOpSimpleClass(assign, varSimpleClass, rightVal);
            }
            throw new Error("\nПосле переменной ожидается = на позиции ("+pos+")\n");
        }
        else if (tokens.get(pos).type.typeName.equals("T_PRINT")){
            pos++;
            return new UnOpSimpleClass(tokens.get(pos-1), this.parseFormula());
        }
        else if(tokens.get(pos).type.typeName.equals("T_WHILE")){
            pos++;
            return  parseWhile();
        }
        else if(tokens.get(pos).type.typeName.equals("T_FOR"))
        {
            pos++;
            return parseFor();
        }
        throw new Error("\nОшибка на позиции ("+pos+"). Ожидалось действие или переменная");
    }

    public SimpleClass parseFor(){
        SimpleClass leftVal=parseFormula();
        Token operator=receive(new String[]{"T_L","T_M","T_EQ"});
        SimpleClass rightVal=parseFormula();

        need(new String[]{"T_END"});

        SimpleClass varSimpleClass = parseVarNum();
        Token assign = receive(new String[]{"T_ASSIGN"});
        SimpleClass rightActVal = parseFormula();
        BinOpSimpleClass action= new BinOpSimpleClass(assign, varSimpleClass, rightActVal);
        if (assign == null)
            throw new Error("\nПосле переменной ожидается = на позиции ("+pos+")\n");
        ForSimpleClass forNode= new ForSimpleClass(operator,leftVal,rightVal,action);
        need(new String[]{"T_LRP"});
        while(!tokens.get(pos).type.typeName.equals("T_RRP")) {
            forNode.addOperations(getOperations());
            if (pos==tokens.size())
                throw new Error("\nОшибка, ожидалось }");
        }
        pos++;
        return forNode;
    }

    public SimpleClass parseWhile(){
        SimpleClass leftVal=parseFormula();
        Token operator=receive(new String[]{"T_L","T_M","T_EQ"});
        SimpleClass rightVal=parseFormula();
        WhileSimpleClass whileNode=new WhileSimpleClass(operator,leftVal,rightVal);
        need(new String[]{"T_LRP"});
        while(!tokens.get(pos).type.typeName.equals("T_RRP")) {
            whileNode.addOperations(getOperations());
            if (pos==tokens.size())
                throw new Error("\nОшибка, ожидалось }");
        }
        pos++;
        return whileNode;
    }

    public SimpleClass getOperations(){
        SimpleClass codeStringSimpleClass =parseString();
        need(new String[]{"T_END"});
        return codeStringSimpleClass;
    }

    public RootSimpleClass parseTokens(){
        RootSimpleClass root=new RootSimpleClass();
        while (pos<tokens.size()){
            SimpleClass codeStringSimpleClass = parseString();
            need(new String[]{"T_END"});
            root.addNode(codeStringSimpleClass);
        }
        return root;
    }

}
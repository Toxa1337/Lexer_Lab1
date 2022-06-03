import java.io.FileReader;
import java.io.IOException;
public class Try {

    public static String checkFile(){
        String code="";
        try(FileReader reader = new FileReader("C:\\Text\\Input.txt"))
        {
            int c;
            while((c=reader.read())!=-1){

                code=code.concat(String.valueOf((char)c));
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return code;
    }

    public static void main(String[] args) {
        String s=checkFile();
        System.out.println("\n\nИсходный код:");
        System.out.println(s);
        System.out.println("\nТокены лексера:");
        Lexer lexer=new Lexer(s);
        Parser parser=new Parser(lexer.get());
        System.out.println("(n) - номер токена данных.");
        System.out.println("\nРезультат:");
        RootSimpleClass root=parser.parseTokens();
        Interpreter interpreter =new Interpreter();
        for(int i = 0; i<root.codeStr.size(); i++) {
            interpreter.run(root.codeStr.get(i));
        }
    }
}
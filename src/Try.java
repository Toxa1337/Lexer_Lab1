/* public class Try {

    public static void main(String[] args) {

        Lexer lexer = new Lexer("C:/Text/Input.txt");

        while (!lexer.isExausthed()) {
            System.out.printf("%-18s :  %s \n",lexer.currentLexema() , lexer.currentToken());
            lexer.moveAhead();
        }

        if (lexer.isSuccessful()) {
            System.out.println("КОНЕЦ ПРОВЕРКИ");
        } else {
            System.out.println(lexer.errorMessage());
        }
    }
} */
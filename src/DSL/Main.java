package DSL;

import DSL.node.Root;
import DSL.token.Token;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<String> readInput() {
        //
        Scanner sc = null;
        ArrayList<String> strList = new ArrayList<>();
        int i = 0;
        //
        try {
            sc = new Scanner(new File("src/DSL/somecode.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //
        while (sc != null && sc.hasNextLine()) {
            strList.add(i, sc.nextLine());
            if (strList.get(i++).equals("END")) {
                strList.remove(i - 1);
                break;
            }
        }
        //
        return strList;
    }

    public static void main(String[] args) {
        // Ввод
        ArrayList<String> code = readInput(); //new CodeReader().readInput(); Прочитали код ПЕРЕДЕЛАТЬ ПОД СЕБЯ
        System.out.println("\nВведенный код:");
        int lineCnt = 0;
        for (String line : code) System.out.println(++lineCnt + ".  " + line);

        // Лексер
        ArrayList<Token> tokenList = new Lexer(code).getTokens(); // Получили токены от лексера
        System.out.println("\nНайденные токены:");
        for (Token token : tokenList) System.out.println(token);

        // Парсер
        Root root = new Parser(tokenList).parseLang();
        System.out.println("\nСинтаксический анализ не выявил проблем!");

        // Интерпретатор
        System.out.println("\nРезультат работы программы:");
        new Interpreter(root).execute();
        //
    }
}

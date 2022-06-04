package DSL;
import DSL.token.Token;
import DSL.token.TokenList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final ArrayList<String> code; // Массив строк введенного кода
    private int position = 0; // Количество токенов

    // Список используемых токенов
    private final LinkedHashMap<String, Pattern> baseTokenList = new TokenList().getBaseTokenList();

    public Lexer(ArrayList<String> code) { this.code = code; }

    // Основной метод
    public ArrayList<Token> getTokens() {
        ArrayList<Token> tokenList = new ArrayList<>();
        int lineNum = 0; // Номер текущей строки
        int lineCursor; // Позиция в текущей строке
        for (String line : code) {
            lineNum++;
            lineCursor = 0;
            while (lineCursor < line.length()) {
                int cursorPosSeek = lineCursor;
                int tokenFound = 0;
                for (String tokenName : baseTokenList.keySet()) {
                    Pattern rgxPtn = baseTokenList.get(tokenName);
                    Matcher rgxMtr = rgxPtn.matcher(line);
                    if (rgxMtr.find(cursorPosSeek)) {
                        if (rgxMtr.start() == cursorPosSeek) {
                            tokenFound++;
                            lineCursor = rgxMtr.end();
                            String nTokenValue = line.substring(cursorPosSeek, lineCursor);
                            if (!(tokenName.equals("SPACE") | tokenName.equals("COMMENT"))) { // Ненужные элементы исходного кода
                                Token nToken = new Token(tokenName, nTokenValue, ++position, lineNum); // Не попадут в лист токенов
                                tokenList.add(nToken); // Они только помешают обработке исходного кода и не имеют необходимости
                            }
                            break;
                        }
                    }
                }
            }
        }
        return tokenList;
    }
}

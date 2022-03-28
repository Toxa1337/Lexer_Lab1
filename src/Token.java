import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {

    TK_MINUS ("-"),
    TK_PLUS ("\\+"),
    TK_MUL ("\\*"),
    TK_DIV ("/"),
    TK_NOT ("~"),
    TK_AND ("&"),
    TK_OR ("\\|"),
    TK_LESS ("<"),
    TK_LEG ("<="),
    TK_GT (">"),
    TK_GEQ (">="),
    TK_EQ ("=="),
    TK_ASSIGN ("="),
    TK_OPEN ("\\("),
    TK_CLOSE ("\\)"),
    TK_SEMI (";"),
    TK_COMMA (","),
    TK_KEY_DEFINE ("ОПРЕДЕЛИТЬ"),
    TK_KEY_AS ("КАК"),
    TK_KEY_IS ("ЕСТЬ"),
    TK_KEY_IF ("ЕСЛИ"),
    TK_KEY_THEN ("ТОГДА"),
    TK_KEY_ELSE ("ИНАЧЕ"),
    TK_KEY_ENDIF ("ИНАЧЕСЛИ"),
    TK_KEY_RETURN ("ВЕРНУТЬ"),
    OPEN_BRACKET ("\\{"),
    CLOSE_BRACKET ("\\}"),
    DIFFERENT ("<>"),
    STRING ("\"[^\"]+\""),
    REAL ("(\\d*)\\.\\d+"),
    INTEGER ("\\d+"),
    IDENTIFIER ("\\w+");

    private final Pattern pattern;

    Token(String regex) {
        pattern = Pattern.compile("^" + regex); // конструктор
    }

    int endOfMatch(String s) {
        Matcher m = pattern.matcher(s); // конструктор

        if (m.find()) { // поиск подстроки с регулярным выражением
            return m.end(); // возвращаем смещение строки после распознавания
        }
        return -1;
    }
}

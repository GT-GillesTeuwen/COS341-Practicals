package Lexing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {
    DOLLAR("\\$"),
    COMMA(","),
    PROC("p"),
    P_DIGIT("[1-9]"),
    SEMICOLON(";"),
    HALT("h"),
    CALL("c\\s*p"),
    ASSIGN(":\\s*="),
    START_WHILE("w\\s*\\("),
    WHILE_BODY("\\)\\s*\\{"),
    START_IF("i\\s*\\("),
    IF_BODY("\\)\\s*t\\s*\\{"),
    START_ELSE("e\\s*\\{"),
    DEC_NUMVAR("n"),
    DEC_BOOLVAR("b"),
    DEC_STRINGVAR("s"),
    START_ADD("a\\s*\\("),
    START_MUL("m\\s*\\("),
    START_DIV("d\\s*\\("),
    DEC_ZERO("0\\s*.\\s*0\\s*0"),
    MINUS("-"),
    DEC_POINT("\\."),
    LOGIC_CONST("(T|F)"),
    START_AND("\\^\\s*\\("),
    START_OR("v\\s*\\("),
    START_NOT("!\\s*\\("),
    START_EQ("E\\s*\\("),
    START_LT("<\\s*\\("),
    START_GT(">\\s*\\("),

    GET("g"),
    VALUE("o"),
    ZERO("0"),
    TEXT("r"),
    OPEN_BRACE("\\{"),
    CLOSE_BRACE("\\}"),
    CLOSE_BRACKET("\\)"),
    ASCII("@"),
    QUOTE("\""),
    STAR("\\*");

    private final Pattern pattern;

    Token(String regex) {
        pattern = Pattern.compile("^" + regex);
    }

    int endOfMatch(String s) {
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            return m.end();
        }

        return -1;
    }
}

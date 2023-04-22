import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Lexer {
    private StringBuilder input = new StringBuilder();
    private Symbol token;
    private String lexema;
    private boolean hasMore = true;
    private String errorMessage = "";
    private Set<Character> blankChars = new HashSet<Character>();
    private ArrayList<Symbol> allTokens;

    public Lexer(String filePath) throws Exception {
        blankChars.add('\r');
        blankChars.add('\n');
        blankChars.add((char) 8);
        blankChars.add((char) 9);
        blankChars.add((char) 11);
        blankChars.add((char) 12);
        blankChars.add((char) 32);

        allTokens = new ArrayList<>();
        try (Stream<String> st = Files.lines(Paths.get(filePath))) {
            st.forEach(input::append);
            String trimmed=input.toString().trim();
            input=new StringBuilder(trimmed);
        } catch (IOException ex) {
            hasMore = false;
            errorMessage = "Could not read file: " + filePath;
            return;
        }
        

        moveAhead();
    }

    public void moveAhead() throws Exception {
        if (!hasMore) {
            return;
        }
        if (input.length() == 0) {
            hasMore = false;
            return;
        }
        removeWhiteSpace();
        if (findNextToken()) {
            return;
        }
        hasMore = false;
        if (input.length() > 0) {
            errorMessage = "Unexpected symbol during lexing: '" + input.charAt(0) + "'";
        }
    }


    private boolean findNextToken() throws Exception {
        for (Token t : Token.values()) {
            int end = t.endOfMatch(input.toString());

            if (end != -1) {

                lexema = input.substring(0, end);
                token = new Symbol(lexema, t);
                allTokens.add(token);
                input.delete(0, end);
                if (t == Token.QUOTE) {
                    String content = "";
                    for (int i = 0; i < 15; i++) {
                        if (input.toString().length() == 0) {
                            throw new Exception("String error: Too short\tContent: " + content);
                        }
                        
                        lexema = input.substring(0, 1);
                        if (lexema.equals("\"")) {
                            throw new Exception("String error: Too short\tContent: " + content);
                        }
                        token = new Symbol(lexema, Token.ASCII);
                        content += lexema;
                        allTokens.add(token);
                        input.delete(0, end);
                    }
                    if (input.toString().length() == 0) {
                        throw new Exception("String error: Too short\tContent: " + content);
                    }
                    lexema = input.substring(0, 1);
                    if (!lexema.equals("\"")) {
                        throw new Exception("String error: Too long\tContent: " + content);
                    } else {
                        token = new Symbol(lexema, Token.QUOTE);
                        allTokens.add(token);
                        input.delete(0, end);
                    }

                }
                if (t == Token.STAR) {
                    String content = "";
                    for (int i = 0; i < 15; i++) {
                        if (input.toString().length() == 0) {
                            throw new Exception("Comment error: Too short\tContent: " + content);
                        }
                        lexema = input.substring(0, 1);
                        if (lexema.equals("*")) {
                            throw new Exception("Comment error: Too short\tContent: " + content);
                        }
                        content += lexema;
                        token = new Symbol(lexema, Token.ASCII);
                        allTokens.add(token);
                        input.delete(0, end);
                    }
                    if (input.toString().length() == 0) {
                        throw new Exception("Comment error: Too short\tContent: " + content);
                    }
                    lexema = input.substring(0, 1);
                    if (!lexema.equals("*")) {
                        throw new Exception("Comment error: Too long\tContent: " + content);
                    } else {
                        token = new Symbol(lexema, Token.STAR);
                        allTokens.add(token);
                        input.delete(0, end);
                    }

                }
                return true;
            }
        }

        return false;
    }

    public boolean isSuccessful() {
        return errorMessage.isEmpty();
    }

    public String errorMessage() {
        return errorMessage;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public ArrayList<Symbol> getAllTokens() {
        return allTokens;
    }

    private void removeWhiteSpace() {
        while (blankChars.contains(input.charAt(0))) {
            input.delete(0, 1);
        }

    }
}
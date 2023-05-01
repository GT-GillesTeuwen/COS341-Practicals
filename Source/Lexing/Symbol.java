package Lexing;

public class Symbol {
    private String symbol;
    private Token token;

    public Symbol(String symbol, Token token) {
        this.symbol = symbol.replaceAll(" ", "");
        this.token = token;
    }

    public String getSymbol() {
        return symbol;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return String.format("%-1s (%s)", token, symbol);
    }
}

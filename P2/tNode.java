public class tNode extends Node{
    private Symbol symbol;

    public tNode(Symbol symbol){
        super(symbol.getSymbol());
        this.symbol=symbol;
    }

    public tNode(){
        super("ε");
        symbol=null;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Node reduce(){
        return this;
    }
}

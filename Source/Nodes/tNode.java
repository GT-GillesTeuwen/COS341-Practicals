
package Nodes;

import java.awt.Color;

import Lexing.Symbol;

public class tNode extends Node {
    private Symbol symbol;

    public tNode(Symbol symbol) {
        super(symbol.getSymbol());
        this.symbol = symbol;
        this.color = Color.BLUE;
    }

    public tNode() {
        super("ε");
        symbol = null;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSubtreeColour(Color color,boolean force,boolean killing){
        if(killing){
            this.dead=true;
        }
        this.color=color;
    }

    public Node reduceOneStepDerivations() {
        return this;
    }

    public Node reduceType() {
        return this;
    }
}

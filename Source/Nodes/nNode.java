package Nodes;

import java.awt.Color;
import java.util.ArrayList;
import Nodes.Strategies.ReducePROC;
import Nodes.Strategies.ReducePROCDEFS;
import Nodes.Strategies.ReduceSEQ;
import Nodes.Strategies.ReduceSTRI;
import Nodes.Strategies.ReduceTEXT;
import Nodes.Strategies.ReduceVALUE;
import Nodes.Strategies.ReduceVAR;
import Nodes.Strategies.NodeReductionStrategy;
import Nodes.Strategies.ReduceALGO;
import Nodes.Strategies.ReduceASSIGN;
import Nodes.Strategies.ReduceCALL;
import Nodes.Strategies.ReduceCMPR;
import Nodes.Strategies.ReduceDIGITS;
import Nodes.Strategies.ReduceELSE;
import Nodes.Strategies.ReduceFLOW;
import Nodes.Strategies.ReduceINPUT;
import Nodes.Strategies.ReduceINT;
import Nodes.Strategies.ReduceLOGIC;
import Nodes.Strategies.ReduceNUMEXPR;
import Nodes.Strategies.ReducePOSandNEG;

public class nNode extends Node {
    private boolean allowComments = false;
    private Node[] children;
    private String data;

    public nNode(String displayName, Node[] children) {
        super(displayName);
        this.children = children;
        data = "";
    }

    public Node[] getChildren() {
        return children;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setChildren(Node[] nodes) {
        this.children = nodes;
    }

    public Node reduceOneStepDerivations() {
        for (int i = 0; i < children.length; i++) {
            children[i] = children[i].reduceOneStepDerivations();
        }
        children = reworkChildren();
        if (children.length == 0 && data.equals("") && !displayName.equals("ALGO")) {
            return null;
        }
        if (children.length == 1 && data.equals("") && !displayName.equals("ALGO")) {
            return children[0];
        }
        return this;

    }

    public Node reduceType() {
        for (int i = 0; i < children.length; i++) {
            children[i].reduceType();
        }
        NodeReductionStrategy reductionStrategy = new NodeReductionStrategy();
        switch (this.displayName) {
            case "PROCDEFS":
                reductionStrategy = new ReducePROCDEFS();
                break;
            case "PROC":
                reductionStrategy = new ReducePROC();
                break;
            case "DIGITS":
                reductionStrategy = new ReduceDIGITS();
                break;
            case "ALGO":
                reductionStrategy = new ReduceALGO();
                break;
            case "SEQ":
                reductionStrategy = new ReduceSEQ();
                break;
            case "CALL":
                reductionStrategy = new ReduceCALL();
                break;
            case "ASSIGN":
                reductionStrategy = new ReduceASSIGN();
                break;
            case "LOOP":
            case "BRANCH":
                reductionStrategy = new ReduceFLOW();
                break;
            case "ELSE":
                reductionStrategy = new ReduceELSE();
                break;
            case "NUMVAR":
            case "BOOLVAR":
            case "STRINGV":
                reductionStrategy = new ReduceVAR();
                break;
            case "NUMEXPR":
                reductionStrategy = new ReduceNUMEXPR();
                break;
            case "POS":
            case "NEG":
                reductionStrategy = new ReducePOSandNEG();
                break;
            case "INT":
                reductionStrategy = new ReduceINT();
                break;
            case "LOGIC":
                reductionStrategy = new ReduceLOGIC();
                break;
            case "CMPR":
                reductionStrategy = new ReduceCMPR();
                break;
            case "STRI":
                reductionStrategy = new ReduceSTRI();
                break;
            case "INPUT":
                reductionStrategy = new ReduceINPUT();
                break;
            case "VALUE":
                reductionStrategy = new ReduceVALUE();
                break;
            case "TEXT":
                reductionStrategy = new ReduceTEXT();
                break;

            default:
                break;
        }
        reductionStrategy.handle(this);
        return this;

    }

    private Node[] reworkChildren() {
        ArrayList<Node> validChildren = new ArrayList<>();
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null && !children[i].getDisplayName().equals("ε")
                    && !(!allowComments && children[i].getDisplayName().equals("COMMENT"))) {
                validChildren.add(children[i]);
            }
        }
        Node[] updatedChildren = new Node[validChildren.size()];
        for (int i = 0; i < validChildren.size(); i++) {
            updatedChildren[i] = validChildren.get(i);
        }
        return updatedChildren;
    }

    public void setSubtreeColour(Color colour, boolean force) {
        if (force) {
            this.setColor(colour);
        } else {
            if (this.displayName.equals("CALL")) {
            }
            if (this.color.getRed() != 255) {
                this.setColor(colour);
            }
        }

        for (int i = 0; i < children.length; i++) {
            if (force) {
                children[i].setColor(colour);
            } else {
                if (this.children[i].getColor().getRed() != 255) {
                    children[i].setColor(colour);
                }
            }

            if (children[i] instanceof nNode) {
                ((nNode) children[i]).setSubtreeColour(colour, force);
            }
        }
    }
}

package Nodes;

import java.awt.Color;
import java.util.ArrayList;

import Exceptions.InvalidConditionException;
import Exceptions.InvalidOutputException;
import Exceptions.InvalidVarAssignmentException;
import Exceptions.ProcedureNotDeclaredException;
import Nodes.AssignmnetStrategies.AssignmentCheckingStrategy;
import Nodes.AssignmnetStrategies.CheckALGO;
import Nodes.AssignmnetStrategies.CheckASSIGN;
import Nodes.AssignmnetStrategies.CheckBRANCH;
import Nodes.AssignmnetStrategies.CheckCALL;
import Nodes.AssignmnetStrategies.CheckINPUT;
import Nodes.AssignmnetStrategies.CheckLOOP;
import Nodes.AssignmnetStrategies.CheckOUTPUT;
import Nodes.AssignmnetStrategies.CheckPROC;
import Nodes.AssignmnetStrategies.KillAllAfterCall;
import Nodes.HaltCheckingStrategies.HaltCheckALGO;
import Nodes.HaltCheckingStrategies.HaltCheckingStrategy;
import Nodes.ReductionStrategies.NodeReductionStrategy;
import Nodes.ReductionStrategies.ReduceALGO;
import Nodes.ReductionStrategies.ReduceASSIGN;
import Nodes.ReductionStrategies.ReduceCALL;
import Nodes.ReductionStrategies.ReduceCMPR;
import Nodes.ReductionStrategies.ReduceDIGITS;
import Nodes.ReductionStrategies.ReduceELSE;
import Nodes.ReductionStrategies.ReduceFLOW;
import Nodes.ReductionStrategies.ReduceINPUT;
import Nodes.ReductionStrategies.ReduceINT;
import Nodes.ReductionStrategies.ReduceLOGIC;
import Nodes.ReductionStrategies.ReduceNUMEXPR;
import Nodes.ReductionStrategies.ReducePOSandNEG;
import Nodes.ReductionStrategies.ReducePROC;
import Nodes.ReductionStrategies.ReducePROCDEFS;
import Nodes.ReductionStrategies.ReduceSEQ;
import Nodes.ReductionStrategies.ReduceSTRI;
import Nodes.ReductionStrategies.ReduceTEXT;
import Nodes.ReductionStrategies.ReduceVALUE;
import Nodes.ReductionStrategies.ReduceVAR;

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
            if (children[i] == null) {
                System.out.println("a");
            }
            children[i] = children[i].reduceOneStepDerivations();
        }
        children = reworkChildren();
        if (children.length == 0 && data.equals("") && !displayName.equals("ALGO") && !displayName.equals("PROC")) {
            return null;
        }
        if (children.length == 1 && data.equals("") && !displayName.equals("ALGO") && !displayName.equals("PROC")) {
            return children[0];
        }
        return this;

    }

    public nNode getNodeParent(int id) {
        // Return self if looking for my child
        for (Node node : children) {
            if (node.id == id) {
                return this;
            }
        }

        // Ask children if they have the child we seek
        nNode found = null;
        for (Node node : children) {
            if (node instanceof nNode) {
                nNode childFound = ((nNode) node).getNodeParent(id);
                if (childFound != null) {
                    found = childFound;
                }
            }
        }

        return found;
    }

    public void checkAssignments()
            throws ProcedureNotDeclaredException, InvalidOutputException, InvalidConditionException,
            InvalidVarAssignmentException {
        AssignmentCheckingStrategy assignmentCheckingStrategy = new AssignmentCheckingStrategy();
        switch (this.displayName) {
            case "PROC":
                assignmentCheckingStrategy = new CheckPROC();
                break;
            case "LOOP":
                assignmentCheckingStrategy = new CheckLOOP();
                break;
            case "BRANCH":
                assignmentCheckingStrategy = new CheckBRANCH();
                break;
            case "ASSIGN":
                assignmentCheckingStrategy = new CheckASSIGN();
                break;
            case "INPUT":
                assignmentCheckingStrategy = new CheckINPUT();
                break;
            case "CALL":
                assignmentCheckingStrategy = new CheckCALL();
                break;
            case "VALUE":
            case "TEXT":
                assignmentCheckingStrategy = new CheckOUTPUT();
                break;
        }
        assignmentCheckingStrategy.handle(this);

        for (Node node : children) {
            if (!node.dead && !this.getDisplayName().equals("LOOP") && !this.getDisplayName().equals("BRANCH")) {
                node.checkAssignments();
            }
        }
    }

    public void checkWhereMainHalts() throws ProcedureNotDeclaredException, InvalidVarAssignmentException {
        HaltCheckingStrategy haltCheckingStrategy = new HaltCheckingStrategy();
        switch (this.displayName) {
            case "ALGO":
                haltCheckingStrategy = new HaltCheckALGO();
                break;
        }
        boolean b = haltCheckingStrategy.handle(this);

        for (Node node : children) {
            if (!node.dead && !node.getDisplayName().equals("PROCDEFS")) {
                node.checkWhereMainHalts();
            }
        }
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

    public void setSubtreeColour(Color colour, boolean force, boolean killing) {
        if (killing) {
            this.dead = true;
        }
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
            if (killing) {
                children[i].dead = true;
            }
            if (force) {
                children[i].setColor(colour);
            } else {
                if (this.children[i].getColor().getRed() != 255) {
                    children[i].setColor(colour);
                }
            }

            if (children[i] instanceof nNode) {
                ((nNode) children[i]).setSubtreeColour(colour, force, killing);
            }
        }
    }

    public void setSelfColour(Color colour, boolean force) {
        if (force) {
            prevColor = this.color;
            this.setColor(colour);
        } else {
            if (this.color.getRed() != 255) {
                prevColor = this.color;
                this.setColor(colour);
            }
        }
    }

    public void killAfterAllCall(String data)
            throws ProcedureNotDeclaredException, InvalidOutputException, InvalidConditionException,
            InvalidVarAssignmentException {
        AssignmentCheckingStrategy kill = new KillAllAfterCall(data);

        kill.handle(this);

    }

}

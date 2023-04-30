import javax.swing.JFrame;

import Exceptions.AmbiguousDeclarationException;
import Exceptions.ProcedureNotDeclaredException;
import Exceptions.TreeCreationException;
import Nodes.nNode;
import Visualisation.Tree;
import Visualisation.TreeVisualiser;

public class TreeTest {
    public static void main(String[] args)
            throws AmbiguousDeclarationException, TreeCreationException, ProcedureNotDeclaredException {

        nNode[] eNNodes = new nNode[0];
        nNode n19 = new nNode("19", eNNodes);
        nNode n18 = new nNode("18", eNNodes);
        nNode[] n7Children = { n18, n19 };

        nNode n17 = new nNode("17", eNNodes);
        nNode n16 = new nNode("16", eNNodes);
        nNode[] n6Children = { n16, n17 };

        nNode n15 = new nNode("15", eNNodes);
        nNode n14 = new nNode("14", eNNodes);
        nNode[] n5Children = { n14, n15 };

        nNode n13 = new nNode("13", eNNodes);
        nNode n12 = new nNode("12", eNNodes);
        nNode[] n4Children = { n12, n13 };

        nNode n11 = new nNode("11", eNNodes);
        nNode n10 = new nNode("10", eNNodes);
        nNode n9 = new nNode("9", eNNodes);
        nNode n8 = new nNode("8", eNNodes);
        nNode[] n3Children = { n8, n9, n10, n11 };

        nNode n7 = new nNode("7", n7Children);
        nNode n6 = new nNode("6", n6Children);
        nNode n5 = new nNode("5", eNNodes);
        nNode n4 = new nNode("4", n4Children);
        nNode[] n2Children = { n4, n5, n6, n7 };

        nNode n3 = new nNode("3", n3Children);
        nNode n2 = new nNode("2", n2Children);
        nNode[] n1Children = { n2, n3 };

        nNode n1 = new nNode("1", n1Children);

        Tree t = new Tree(n1);
        JFrame f = new JFrame("null");
        f.add(new TreeVisualiser(t));
        f.setSize(200, 200);
        f.setVisible(true);
    }
}
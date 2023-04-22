import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Tree {
    private Node root;
    private ArrayList<Node> allNodes;
    private int[] nodesPerLevel;

    public Tree(Node root) throws Exception {
        if (root == null) {
            throw new Exception("Tree Error: No tree to build");
        }
        root.reduce();
        this.root = root;
        allNodes = new ArrayList<>();
        int depth = maxDepth();
        nodesPerLevel = new int[depth];
        nodesPerLevel[0] = 1;
        countLevel(root, 1);
        for (int i = 1; i <= depth; i++)
            addLevel(root, i);
    }

    public void addLevel(Node root, int level) {
        if (root instanceof tNode && level == 1) {
            allNodes.add(root);
            return;
        }
        if (root instanceof tNode) {
            return;
        }
        if (level == 1)
            allNodes.add(root);
        else if (level > 1) {
            nNode nRoot = (nNode) (root);
            for (int i = 0; i < nRoot.getChildren().length; i++) {
                addLevel(nRoot.getChildren()[i], level - 1);
            }

        }
    }

    public void countLevel(Node cur, int level) {
        if (cur instanceof nNode) {
            nNode curN = ((nNode) (cur));
            for (Node node : curN.getChildren()) {

                nodesPerLevel[level]++;
            }
            for (Node node : curN.getChildren()) {
                countLevel(node, level + 1);
            }
        } else {
            return;
        }
    }

    public ArrayList<Node> getAllNodes() {
        return allNodes;
    }

    public int[] getNodesPerLevel() {
        return nodesPerLevel;
    }

    public int maxDepth() {
        return getDepth(root);
    }

    private int getDepth(Node node) {
        if (node instanceof tNode)
            return 1;
        else {
            nNode nodeN = (nNode) node;
            /* compute the depth of each subtree */
            int[] depths = new int[nodeN.getChildren().length];
            for (int i = 0; i < depths.length; i++) {
                depths[i] = getDepth(nodeN.getChildren()[i]);
            }
            int max = -1;
            for (int i = 0; i < depths.length; i++) {
                if (depths[i] > max) {
                    max = depths[i];
                }
            }
            return max + 1;
        }
    }

    public String toSpecXML() {
        String xml = "<Tree>\n";
        for (Node node : allNodes) {
            String children = getNodeChildrenXML(node);
            String content = node.getDisplayName().replace(">", "&gt;");
            content = content.replace("<", "&lt;");
            xml += "\t" + "<Node>\n";
            xml += "\t\t" + "<ID>" + node.getId() + "</ID>\n";
            xml += "\t\t" + "<Contents>" + content + "</Contents>\n";
            xml += "\t\t" + "<Children>\n" + children + "\t\t</Children>\n";
            xml += "\t" + "</Node>\n";
        }
        xml += "</Tree>";
        return xml;
    }

    private String getNodeChildrenXML(Node node) {
        if (node instanceof tNode) {
            return "";
        }
        String childXML = "";
        nNode nodeN = (nNode) node;
        for (Node child : nodeN.getChildren()) {
            childXML += "\t\t\t<ChildID>" + child.getId() + "</ChildID>\n";
        }
        return childXML;
    }
}

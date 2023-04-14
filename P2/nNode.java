import java.util.ArrayList;

public class nNode extends Node{
    private boolean allowComments=true;
    private Node[] children;

    public nNode(String displayName,Node[] children){
        super(displayName);
        this.children=children;
    }


    public Node[] getChildren() {
        return children;
    }

    public Node reduce(){
        for (int i = 0; i < children.length; i++) {
            children[i]=children[i].reduce();
        }
        children=reworkChildren();
        if(children.length==0){
            return null;
        }
        if(children.length==1){
            return children[0];
        }
            return this;
        
    }

    private Node[] reworkChildren(){
        ArrayList<Node> validChildren=new ArrayList<>();
        for (int i = 0; i < children.length; i++) {
            if(children[i]!=null && !children[i].getDisplayName().equals("ε") && !(!allowComments && children[i].getDisplayName().equals("COMMENT"))){
                validChildren.add(children[i]);
            }
        }
        Node[] updatedChildren=new Node[validChildren.size()];
        for (int i = 0; i < validChildren.size(); i++) {
            updatedChildren[i]=validChildren.get(i);
        }
        return updatedChildren;
    }
}


public class Node {
    private String displayName;
    private int id;
    private static int idCount = 0;

    public Node(String displayName) {
        this.displayName = displayName;
        id = idCount++;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }

    public Node reduce() {
        return null;
    }
}

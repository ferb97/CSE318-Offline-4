import java.util.ArrayList;
import java.util.List;

public class Node {

    private String label;
    private boolean isLeaf;
    private List<Edge> childEdgeList = new ArrayList<>();

    public Node(String label) {
        this.label = label;
        this.isLeaf = false;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Edge> getChildEdgeList() {
        return childEdgeList;
    }

    public void setChildEdgeList(List<Edge> childEdgeList) {
        this.childEdgeList = childEdgeList;
    }

    public void addEdge(Edge edge){
        this.childEdgeList.add(edge);
    }
}

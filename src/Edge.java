public class Edge {

    private String branch;
    private Node attributeNext;

    public Edge(String branch, Node attributeNext) {
        this.branch = branch;
        this.attributeNext = attributeNext;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Node getAttributeNext() {
        return attributeNext;
    }

    public void setAttributeNext(Node attributeNext) {
        this.attributeNext = attributeNext;
    }
}

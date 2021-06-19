

public class Node {
    private int number;
    private Node nextNode;
    private Node backNode;

    public Node(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getBackNode() {
        return backNode;
    }

    public void setBackNode(Node backNode) {
        this.backNode = backNode;
    }
}

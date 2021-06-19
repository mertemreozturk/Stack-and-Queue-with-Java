

public class MyStack {
    private Node top;

    public MyStack() {
        this.top = null;
    }

    public boolean isEmpty(){
        if ( top == null){
            return true;
        }else{
            return false;
        }
    }

    public void push(int number){
        Node newNode = new Node(number);
        if ( isEmpty() ){
            top = newNode;   // first element
        }else{
            newNode.setNextNode(top);
            top.setBackNode(newNode);
            top = newNode;   // new top and added new element
        }
    }

    public void pop() {
        if (top.getNextNode() == null) {  // stack have only one element
            top = null;    // remove last element from stack and stack is empty
        } else {
            top = top.getNextNode();  // new top
            top.setBackNode(null);    // removed one element
        }
    }

    public Node getTop() {
        return top;
    }

    public void setTop(Node top) {
        this.top = top;
    }
}

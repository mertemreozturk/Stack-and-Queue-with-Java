

public class MyQueue {
    private Node front;
    private Node rear;

    public MyQueue() {
        this.front = null;
        this.rear = null;
    }

    public boolean isEmpty(){
        if ( front == null){
            return true;
        }else{
            return false;
        }
    }

    public void enqueue(int number){
        Node newNode = new Node(number);
        if ( isEmpty() ){     // first element
            front = newNode;
            rear = newNode;
        }else{
            Node temp = rear;
            rear.setNextNode(newNode);
            rear = newNode;   // new rear and added new element
            newNode.setBackNode(temp);
        }
    }

    public void dequeue(){
        if ( front == rear){   // queue have only one element
            front = null;     // remove last element from queue and queue is empty
            rear = null;
        }else{
            front = front.getNextNode();   // new front
            front.setBackNode(null);     // removed one element
        }
    }

    public Node getFront() {
        return front;
    }

    public void setFront(Node front) {
        this.front = front;
    }

    public Node getRear() {
        return rear;
    }

    public void setRear(Node rear) {
        this.rear = rear;
    }
}

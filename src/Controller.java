

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Controller {
    private ArrayList<String> stackOut;
    private ArrayList<String> queueOut;
    private String[] text;
    private String[] textStack;
    private String[] textQueue;
    private MyStack myStack;
    private MyQueue myQueue;
    private MyStack tempStack;
    private MyQueue tempQueue;

    public Controller(String command) {
        this.tempStack = new MyStack();
        this.tempQueue = new MyQueue();
        this.myStack = new MyStack();
        this.myQueue = new MyQueue();
        this.stackOut = new ArrayList<String>();
        this.queueOut = new ArrayList<String>();
        ReadFromFile file = new ReadFromFile();
        this.text = file.readFile(command);
        this.textStack = file.readFile("stack.txt");
        this.textQueue = file.readFile("queue.txt");
    }

    public void create(){
        for (String line: textStack){    // create stack according to stack.txt
            String[] stackFromFile = line.split(" ");
            for (int i = stackFromFile.length-1; i >= 0; i--) {
                myStack.push(Integer.parseInt(stackFromFile[i]));
            }
        }
        for ( String line: textQueue){   // create queue according to queue.txt
            String[] queueFromFile = line.split(" ");
            for ( String q : queueFromFile){
                myQueue.enqueue(Integer.parseInt(q));
            }
        }
    }

    public void control(){
        Random random = new Random();
        for (String lines: text){     // each iteration for one operation
            String[] line = lines.split(" ");
            if ( line[0].equals("S")){   // do it for stack
                switch (line[1]){
                    case "removeGreater":
                        while ( myStack.getTop() != null){
                            if ( myStack.getTop().getNumber() <= Integer.parseInt(line[2])){
                                tempStack.push(myStack.getTop().getNumber());  // store tempStack according to numbers less than value
                            }
                            myStack.pop();    // clear stack
                        }
                        forwardTempStack();   // clear tempStack and store real stack
                        stackOut.add("After removeGreater " + line[2]+":\n" + printStack());
                        break;
                    case "addOrRemove":
                        if ( Integer.parseInt(line[2]) > 0){
                            for ( int rn = 0; rn < Integer.parseInt(line[2]); rn++) {
                                myStack.push(random.nextInt(50));  // add random number to top of stack
                            }
                        }else{
                            for ( int x = 0; x < Math.abs(Integer.parseInt(line[2])); x++){
                                myStack.pop();    // remove number from top
                            }
                        }
                        stackOut.add("After addOrRemove " + line[2] + ":\n" + printStack());
                        break;
                    case "reverse":
                        ArrayList<Integer> reverseList = new ArrayList<Integer>();
                        int currentIndex = 0;
                        while ( myStack.getTop() != null){
                            reverseList.add(myStack.getTop().getNumber()); // add list number until currentIndex equal to value
                            currentIndex++;  // forward index
                            myStack.pop();   // remove those numbers from real stack
                            if ( currentIndex == Integer.parseInt(line[2])){
                                break;     // if currentIndex is equal to value exit loop
                            }
                        }
                        for ( Integer i:reverseList){
                            myStack.push(i);   // again add reversed numbers to top of stack
                        }
                        stackOut.add("After reverse " + line[2] + ":\n" + printStack());
                        break;
                    case "sortElements":
                        ArrayList<Integer> sortList = new ArrayList<Integer>();
                        while ( myStack.getTop() != null){
                            sortList.add(myStack.getTop().getNumber());   //  add all numbers to list
                            myStack.pop();    // clear stack
                        }
                        Collections.sort(sortList);
                        Collections.reverse(sortList);    // those operation are necessary because  stack always add to top
                        for ( Integer i:sortList){
                            myStack.push(i);  // again add sorted numbers to top of stack
                        }
                        stackOut.add("After sortElements:\n" + printStack());
                        break;
                    case "calculateDistance":
                        ArrayList<Integer> distanceList = new ArrayList<Integer>();
                        while ( myStack.getTop() != null){   // add numbers list and tempStack
                            tempStack.push(myStack.getTop().getNumber());
                            distanceList.add(myStack.getTop().getNumber());
                            myStack.pop();   // clear stack each iteration
                        }
                        Collections.reverse(distanceList);
                        int sumOuter = 0;
                        while ( tempStack.getTop() != null){
                            myStack.push(tempStack.getTop().getNumber());  // again add numbers to real stack from tempStack
                            int sumInner = 0;  // calculate distance between top element other element for each iteration
                            for ( Integer i: distanceList){
                                sumInner += Math.abs(tempStack.getTop().getNumber() - i);
                            }
                            distanceList.remove(0);  // update top element for list
                            sumOuter += sumInner;   // find sum for top element
                            tempStack.pop();  // clear tempStack for each iteration
                        }
                        stackOut.add("After calculateDistance:" + "\nTotal distance=" + sumOuter);
                        break;
                    default:
                        ArrayList<Integer> distinctList = new ArrayList<Integer>();
                        while ( myStack.getTop() != null){
                            tempStack.push(myStack.getTop().getNumber());   // add element for each iteration
                            if ( distinctList.contains(myStack.getTop().getNumber()) ){  // do nothing
                            }else {
                                distinctList.add(myStack.getTop().getNumber()); // add distinct element to list
                            }
                            myStack.pop();   // clear real stack for iteration
                        }
                        forwardTempStack();   // clear tempStack and store real stack
                        stackOut.add("After distinctElements:" + "\nTotal distinct elements=" + distinctList.size());
                }
            }else {
                switch (line[1]) {
                    case "removeGreater":
                        while ( myQueue.getFront() != null){
                            if ( myQueue.getFront().getNumber() <= Integer.parseInt(line[2])){
                                tempQueue.enqueue(myQueue.getFront().getNumber());
                            }
                            myQueue.dequeue();   // clear real queue
                        }
                        forwardTempQueue();    // clear tempQueue and store real queue
                        queueOut.add("After removeGreater " + line[2] + ":\n" + printQueue());
                        break;
                    case "addOrRemove":
                        if ( Integer.parseInt(line[2]) > 0){
                            for ( int rn = 0; rn < Integer.parseInt(line[2]); rn++) {
                                myQueue.enqueue(random.nextInt(50));    // add random element to rear of queue
                            }
                        }else{
                            for ( int x = 0; x < Math.abs(Integer.parseInt(line[2])); x++){
                                myQueue.dequeue();   // remove element from front of queue
                            }
                        }
                        queueOut.add("After addOrRemove " + line[2] + ":\n" + printQueue());
                        break;
                    case "reverse":
                        ArrayList<Integer> reverseList =  new ArrayList<Integer>();
                        int currentIndex = 0;
                        while ( myQueue.getFront() != null){ // queue always add rear therefore we need all element
                            reverseList.add(myQueue.getFront().getNumber());
                            currentIndex++;
                            if ( currentIndex == Integer.parseInt(line[2])){
                                Collections.reverse(reverseList);
                            }
                            myQueue.dequeue();
                        }
                        for ( Integer i:reverseList){
                            myQueue.enqueue(i);    // add reverse element to queue
                        }
                        queueOut.add("After reverse " + line[2] + ":\n" + printQueue());
                        break;
                    case "sortElements":
                        ArrayList<Integer> sortList = new ArrayList<Integer>();
                        while ( myQueue.getFront() != null){
                            sortList.add(myQueue.getFront().getNumber());
                            myQueue.dequeue();
                        }
                        Collections.sort(sortList);
                        for ( Integer i:sortList){
                            myQueue.enqueue(i);
                        }
                        queueOut.add("After sortElements:\n" + printQueue());
                        break;
                    case "calculateDistance":
                        ArrayList<Integer> distanceList = new ArrayList<Integer>();
                        while ( myQueue.getFront() != null){   // add elements to tempQueue and list
                            tempQueue.enqueue(myQueue.getFront().getNumber());
                            distanceList.add(myQueue.getFront().getNumber());
                            myQueue.dequeue();   // clear real queue
                        }
                        int sumOuter = 0;
                        while ( tempQueue.getFront() != null){
                            myQueue.enqueue(tempQueue.getFront().getNumber());
                            int sumInner = 0;
                            for ( Integer i: distanceList){
                                sumInner += Math.abs(tempQueue.getFront().getNumber() - i);
                            }
                            distanceList.remove(0);   // update front for list
                            sumOuter += sumInner;
                            tempQueue.dequeue();   // update front of tempQueue
                        }
                        queueOut.add("After calculateDistance:" + "\nTotal distance=" + sumOuter);
                        break;
                    default:
                        ArrayList<Integer> distinctList = new ArrayList<Integer>();
                        while ( myQueue.getFront() != null){
                            tempQueue.enqueue(myQueue.getFront().getNumber());
                            if ( distinctList.contains(myQueue.getFront().getNumber()) ){
                            }else {
                                distinctList.add(myQueue.getFront().getNumber());
                            }
                            myQueue.dequeue();
                        }
                        forwardTempQueue();
                        queueOut.add("After distinctElements:" + "\nTotal distinct elements=" + distinctList.size());
                }
            }
        }
        writeToFile(queueOut, "queueOut.txt");
        writeToFile(stackOut, "stackOut.txt");
        writeToFile(new ArrayList<String>(Collections.singleton(printStack())),"stack.txt");
        writeToFile(new ArrayList<String>(Collections.singleton(printQueue())),"queue.txt");
    }

    public String printStack(){
        String s = "";
        while ( myStack.getTop() != null){
            tempStack.push(myStack.getTop().getNumber());
            if ( myStack.getTop().getNextNode() != null){
                s += myStack.getTop().getNumber() + " ";
            }else{    // if stack have one element
                s += myStack.getTop().getNumber();
            }
            myStack.pop();
        }
        forwardTempStack();
        return s;
    }

    public String printQueue(){
        String q = "";
        while ( myQueue.getFront() != null){
            tempQueue.enqueue(myQueue.getFront().getNumber());
            if ( myQueue.getFront().getNextNode() != null){
                q += myQueue.getFront().getNumber() + " ";
            }else{   // if queue have one element
                q += myQueue.getFront().getNumber();
            }
            myQueue.dequeue();
        }
        forwardTempQueue();
        return q;
    }

    public void forwardTempStack(){   // add numbers to real stack from tempStack
        while ( tempStack.getTop() != null ){
            myStack.push(tempStack.getTop().getNumber());
            tempStack.pop();
        }
    }

    public void forwardTempQueue(){   // add numbers to real queue from tempQueue
        while ( tempQueue.getFront() != null ){
            myQueue.enqueue(tempQueue.getFront().getNumber());
            tempQueue.dequeue();
        }
    }

    public void writeToFile(ArrayList<String> lists,String fileName) {
        try {
            FileWriter fWriter = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fWriter);
            for (int i = 0; i < lists.size(); i++) {    // write to file according to filename
                if ( i != lists.size() - 1){
                    writer.write(lists.get(i)+"\n");
                }else{
                    writer.write(lists.get(i));
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

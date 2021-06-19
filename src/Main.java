

public class Main {

    public static void main(String[] args) {
	   Controller controller = new Controller(args[0]);
	   controller.create();    //  take data from stack and queue txt files and initialize them
	   controller.control();    // processing

    }
}

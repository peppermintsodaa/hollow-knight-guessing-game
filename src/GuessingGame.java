import java.util.Scanner;
import java.io.*;

/**
 * Program in the form of the 20 Questions guessing game.
 * Person thinks of an answer from list of items and computer will try to guess it.
 * 
 * @author Bhavika Shrestha
 * @version 4/22/2020
 *
 */
public class GuessingGame {
	protected Scanner sc = new Scanner(System.in);
	protected String itemList = "";
	
	/**
	 * Constructs a new GuessingGame object.
	 * In other words, starts the guessing game.
	 * Continues to replay the game so long as user says "yes" or "y"
	 * 
	 * @param tree binary tree containing string items
	 * @param fileName name of file to be used in getFileName method
	 */
	protected GuessingGame(GameTree<String> tree, String fileName) throws NullPointerException {
		String response = "";			// response to first and second question
		String againResponse = "";		// response when wanting to play again
		
		System.out.println("Hello! Do you want to play 20 Questions? [y/n]");
		response = sc.next();
			
		if (response.equals("n")) {
			System.exit(0);
		}
		else if (response.equals("y")) {
			// first does prompt, then loops it again after finishing game while againResponse is yes
			do {
				printInstructions();														// prints instructions based on type of program
				System.out.println(getFileName(fileName) + ": " + listOfAnswers(tree));		// gets file name without file extension and list of items
				System.out.println();
				response = "";
				
				while (!(response.equals("yes")) && !(response.equals("y"))) {
					System.out.println("Are you ready to begin?");
					if (getFileName(fileName).equals("hollow_knight_charas")) {		// I just want to implement this since some of the questions are spoilers lol
						System.out.println("WARNING: Spoilers Ahead");
					}
					response = sc.next();
				}
				
				theActualGame(tree.getRoot());
				
				System.out.println("Do you want to play again?");
				againResponse = sc.next();
			} while (againResponse.equals("yes") || againResponse.equals("y"));
			
		}
	}
	
	/**
	 * Prints instructions so that subclass can override it
	 */
	protected void printInstructions() {
		System.out.println("Alright! You will think of an answer and I will try to guess it!");
		System.out.println("All you have to do is answer 'yes' or 'no'!");
		System.out.println("Now think of an item from this set.");
	}
	
	/**
	 * Actually starts the game.
	 * Is overridden in subclass to implement different procedures as necessary.
	 * 
	 * @param node current node
	 */
	protected void theActualGame(BinaryTreeNode<String> node) {
		String answer = "";
		
		// if the node is an answer
		if (node.isLeaf()) {
			System.out.println("Were you thinking of " + node.getData() + "?");
			answer = sc.next();
			
			if (answer.equals("yes")) {
				System.out.println("Yay I guessed it! :D");
			}
			else if (answer.equals("no")) {
				System.out.println("Aw man :(");
			}
		}
		else {
			actualGameHelper(node);
		}
	}
	
	/**
	 * Helper method for theActualGame method.
	 * Repeatedly calls theActualGame until it reaches an answer.
	 * 
	 * @param node current node
	 */
	protected void actualGameHelper(BinaryTreeNode<String> node) {
		BinaryTreeNode<String> yes = node.getLeftChild();		// left child is yes
		BinaryTreeNode<String> no = node.getRightChild();		// right child is no
		String answer = "";
		
		System.out.println(node.getData());
		answer = sc.next();
		
		if (answer.equals("yes")) {
			theActualGame(yes);
		}
		if (answer.equals("no")) {
			theActualGame(no);
		}
	}
	
	/**
	 * Gets name of file without the extension.
	 * Note that this only works for three-character-long file extensions.
	 * 
	 * @param fileName name of file
	 * @return file name without extension
	 */
	protected String getFileName(String fileName) {
		String fileNameWithoutEx = "";
		String[] array = fileName.split("");			// splits string into array so that it can iterate over
		for (int i = 0; i < array.length-4; i++) {		// adds to new string without last four characters
			fileNameWithoutEx += array[i];
		}
		return fileNameWithoutEx;
	}
	
	/**
	 * Creates a list of items to display during the game
	 * 
	 * @param tree binary tree containing game backbone
	 * @return list of items as string
	 */
	protected String listOfAnswers(GameTree<String> tree) {
		if (!(tree.isEmpty())) {
			itemList = "[ ";		// start of list
			BinaryTreeNode<String> node = tree.getRoot();
			modifiedInOrderTraversal(node);
			itemList += "]";		// end of list
		}
		return itemList;
	}
	
	/**
	 * Helper method for listOfAnswers method.
	 * Is modified in a way that it traverses through binary tree until it finds a leaf, 
	 * in which it adds to item list
	 * 
	 * @param node current node
	 */
	public void modifiedInOrderTraversal(BinaryTreeNode<String> node) {
		BinaryTreeNode<String> left = node.getLeftChild();
		BinaryTreeNode<String> right = node.getRightChild();
		
		if (left != null) {
			modifiedInOrderTraversal(left);
		}
		
		if (node.isLeaf()) {
			itemList += node.getData() + ", ";	// adds item to list
		}
		
		if (right != null) {
			modifiedInOrderTraversal(right);
		}
		
	}
	
	/**
	 * Executes the program on runtime.
	 * 
	 * @param args file name (xml)
	 * @throws FileNotFoundException if file is not found
	 */
	public static void main (String[] args) throws FileNotFoundException {
		GameTreeReader game = new GameTreeReader(args[0]);	// reads file
		GameTree<String> gameTree = game.buildGameTree();	// builds tree out of file
		new GuessingGame(gameTree, args[0]);				// creates new game object
	}
}

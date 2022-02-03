import java.io.*;

/**
 * Program in the form of the 20 Questions guessing game.
 * Inherits from GuessingGame.java
 * Person thinks of an answer either from list of items or from themselves, and computer will try to guess it.
 *
 * @author Bhavika Shrestha
 * @version 4/22/2020
 *
 */
public class UnrestrictedGuessingGame extends GuessingGame {
	/**
	 * Constructs a new UnrestrictedGuessingGame object, which uses the GuessingGame superclass.
	 * In other words, starts the guessing game.
	 *
	 * @param tree binary tree containing string items
	 * @param fileName name of file to be used in getFileName method from superclass
	 */
	public UnrestrictedGuessingGame(GameTree<String> tree, String fileName) {
		super(tree, fileName);
	}

	/**
	 * Overrides printInstructions method in superclass to print new set of instructions
	 */
	protected void printInstructions() {
		System.out.println("Alright! You will think of an answer and I will try to guess it!");
		System.out.println("All you have to do is answer 'yes' or 'no'!");
		System.out.println("Now think of an item either from this set or from elsewhere, from an item not listed that you know about.");
	}

	/**
	 * Overrides theActualGame method in superclass to learn item after
	 * computer fails to guess item correctly
	 *
	 * @param node current node
	 */
	protected void theActualGame(BinaryTreeNode<String> node) throws NullPointerException {
		String answer = "";

		if (node.isLeaf()) {
			System.out.println("Were you thinking of " + node.getData() + "?");
			answer = sc.next();

			if (answer.equals("yes")) {
				System.out.println("Yay I guessed it! :D");
			}
			else if (answer.equals("no")) {
				sc.nextLine();		// this is to skip to next line without producing any errors and allow program to read strings with spaces
				System.out.println("What were you thinking of?");
				String newItem = sc.nextLine();

				System.out.println("Please give me a yes/no question that would have determined your thing");
				String newQuestion = sc.nextLine();

				System.out.println("Is the answer to your question yes or no?");
				String newAnswer = sc.next();

				modifyTree(node, newItem, newQuestion, newAnswer);
			}
		}
		else {
			super.actualGameHelper(node);
		}
	}

	/**
	 * Modifies binary tree so that new item with new question is added
	 * after computer fails to guess item correctly
	 *
	 * @param node current node
	 * @param item item to be added
	 * @param question question to be added
	 * @param answer answer to determine which child to implement
	 */
	protected void modifyTree(BinaryTreeNode<String> node, String item, String question, String answer) {
		BinaryTreeNode<String> newItem = new GameTreeNode<String>(item);
		BinaryTreeNode<String> currentItem = new GameTreeNode<String>(node.getData());	// puts current item in new variable

		// sets left child as new item and right child as current item if answer is yes; this is reversed otherwise
		if (answer.equals("yes")) {
			node.setLeftChild(newItem);
			node.setRightChild(currentItem);
		}
		else if (answer.equals("no")) {
			node.setLeftChild(currentItem);
			node.setRightChild(newItem);
		}
		node.setData(question);
	}

	/**
	 * Executes program in runtime.
	 *
	 * @param args name of file (xml)
	 * @throws FileNotFoundException if file is not found
	 */
	public static void main (String[] args) throws FileNotFoundException {
		GameTreeReader game = new GameTreeReader(args[0]);
		GameTree<String> gameTree = game.buildGameTree();
		new UnrestrictedGuessingGame(gameTree, args[0]);
	}
}

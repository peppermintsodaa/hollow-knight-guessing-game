/**
 * GameTree.java implements the binary tree interface.
 *
 * @author ponbarry
 */

public class GameTree<T> implements BinaryTree<T> {

	private BinaryTreeNode<T> root;

	/**2
	 * Constructor creates an empty Game tree.
	 */
	public GameTree() {
		root = null;
	}

	/**
	 * Get the root node for this tree.
	 *
	 * @return root or null if tree is empty.
	 */
	public BinaryTreeNode<T> getRoot() {
		return root;
	}

	/**
	 * Set the root node for this tree.
	 */
	public void setRoot(BinaryTreeNode<T> root) {
		this.root = root;
	}

	/**
	 * Test if the tree is empty.
	 *
	 * @return true if tree has no data.
	 */
	public boolean isEmpty() {
		return root == null;
	}
}

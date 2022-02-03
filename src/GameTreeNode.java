/**
 * GameTreeNode.java implements a basic binary tree node,
 * with data and pointers to left and right children.
 * @author ponbarry
 */

public class GameTreeNode<T> implements BinaryTreeNode<T> {
    private T data;
    private BinaryTreeNode<T> leftChild;
    private BinaryTreeNode<T> rightChild;

    /**
     * Constructor creates a node with data.
     */
    public GameTreeNode(T data) {
        this.data = data;
    }

    /**
     * Get the data stored at this node.
     * @return Object data.
     */
    public T getData() {
        return data;
    }

    /**
     * Set the data stored at this node.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Get the left child.
     * @return BinaryTreeNode that is left child,
     * or null if no child.
     */
    public BinaryTreeNode<T> getLeftChild() {
        return leftChild;
    }

    /**
     * Get the right child.
     * @return BinaryTreeNode that is right child,
     * or null if no child.
     */
    public BinaryTreeNode<T> getRightChild() {
        return rightChild;
    }

    /**
     * Set the left child.
     */
    public void setLeftChild(BinaryTreeNode<T> left) {
        leftChild = left;
    }

    /**
     * Set the right child.
     */
    public void setRightChild(BinaryTreeNode<T> right) {
        rightChild = right;
    }

    /**
     * Tests if this node is a leaf (has no children).
     * @return true if leaf node.
     */
    public boolean isLeaf() {
        return (leftChild == null) && (rightChild == null);
    }
}

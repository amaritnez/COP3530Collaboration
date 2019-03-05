package lab4;
/*
 * Main Class: SortedTree.java
 * Date last modified: 3/16/2018 @ 10:38PM EST
 * Version: 1.0
 */

class TreeNode {
  public int info;
  public TreeNode left, right;

  public TreeNode(int x) {
    info = x;
    left = right = null;
  }
}

public class SortedTree {
  public final static int SIZE_INPUT = 8;
  TreeNode root;

  /**
   * Receives a sorted sequence of integers to build a binary tree. Given any
   * node nd in this binary tree, let its subtrees be denoted t1 and t2. The
   * number of nodes in t1 and t2 differ by no more than one.
   */
  public SortedTree(int[] input) {
    root = null;
    if (!verifyInput(input)) {// if input is not sorted return
      return;
    } else {
      root = buildTree(input, 0, input.length - 1);
    }
  }

  /**
   * Builds a binary using the objects from input[start] to input[end].
   * 
   * See the constructor for the requirements for this binary tree
   */
  private TreeNode buildTree(int[] input, int start, int end) {
    int mid = (start + end) / 2; //uses int division to calculate mid
    TreeNode midNode = new TreeNode(input[mid]); //create a node with the mid value
    if (start > end) {//this is effectively reached when we close our range of start-end
      return null; //return nothing because there are no values left in between
    }
    midNode.left = buildTree(input, start, mid - 1); //the left child of this node is between start and mid-1
    midNode.right = buildTree(input, mid + 1, end); //the right child of this node is between mid+1 and end
    return midNode; //this is the child node of the previous method's node, or the root node in the base case
  }

  //verifies if the input is sorted; returns true if sorted, false otherwise
  private boolean verifyInput(int[] input) {
    if (input == null) {
      return false;
    }
    for (int i = 0; i < input.length - 1; i++) {
      if (input[i] >= input[i + 1]) {
        return false;
      }
    }
    return true;
  }

  // calls the recursive depth method
  public int depth() {
    return _depth(root);
  }

  /*
   * Recursively tracks the depth of the tree. Counts backwards, starting from
   * the bottom of the tree and working its way up
   */
  public int _depth(TreeNode currentNode) {
    // base case where the node is null
    if (currentNode == null) {
      return -1;
    } else if (currentNode.left == null && currentNode.right == null) {
      // if the node is a leaf; also a base case of a tree with one node
      return 0;
    } else if (currentNode.right == null) { //return the left side count
      return _depth(currentNode.left) + 1;
    } else if (currentNode.left == null) { //return the right side count
      return _depth(currentNode.right) + 1;
    } else { //where a node has two children; compare the left and right side, and return the greater side
      int leftDepth = _depth(currentNode.left) + 1;
      int rightDepth = _depth(currentNode.right) + 1;
      if (leftDepth > rightDepth) {
        return leftDepth;
      } else {
        return rightDepth;
      }
    }
  }

  //calls the recursive traverse method
  public void traverse() {
    traverseNodes(root);
  }

  //traverse the tree in in-order fashion; also prints the nodes' info in-order
  private void traverseNodes(TreeNode currentNode) {
    if (currentNode != null) { //if the node is null, don't bother continuing to traverse
      traverseNodes(currentNode.left); // traverse the left subtree
      System.out.println(currentNode.info); // print the node
      traverseNodes(currentNode.right); // traverse the right subtree
    }
  }

  public static void main(String args[]) {
    //initialize main input
    int[] input = new int[SIZE_INPUT];
    for (int i = 0; i < SIZE_INPUT; i++) {
      input[i] = i;
    }
    // another input array we saw in class:
    // int[] input2 = { 4, 9, 15, 20, 22, 24, 35, 87 };

    // 1 - create the binary search tree given the sorted input
    SortedTree st = new SortedTree(input);
    // SortedTree st2 = new SortedTree(input2);

    // 2 - print its depth
    System.out.println("The depth of the tree is " + st.depth());
    // System.out.println("The depth of the tree2 is " + st2.depth());

    // 3 - print the tree nodes in ascending order
    System.out.println("The contents of the tree are as follows: ");
    st.traverse();
    //st2.traverse();
  }
}

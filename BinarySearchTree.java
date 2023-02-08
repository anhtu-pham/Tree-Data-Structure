import java.util.*;
// Class BinarySearchTree is the tree in which each node k being larger than k's left subtree's nodes and smaller than or equal to k's right subtree's nodes
public class BinarySearchTree<T  extends Comparable<? super T>,V> implements TreeWithComparableKey<T,V>{
  
  // Class Node is used for the node in the binary search tree
  private class Node<T,V>{
    
    // the key stored in the node (the key is comparable)
    private T key;
    
    // the value stored in the node
    private V value;
    
    // the left child of the node
    private Node<T,V> left;
    
    // the right child of the node
    private Node<T,V> right;
    
    /**
     * Initialize the node with initialization of node's key and node's value
     */
    private Node(T key, V value){
      this.key = key;
      this.value = value;
    }
  }
  
  // the root of the binary search tree
  private Node<T,V> root;
  
  /**
   * Initialize the binary search tree with root being null
   */
  public BinarySearchTree(){
    root = null;
  }
  
  /**
   * Insert a node with key and associated value to the binary search tree
   * Time complexity: O(logN) if tree is balanced; O(N) in the worst case (N is number of nodes in the binary search tree)
   */
  public void insert(T key, V value){
    // When the binary search tree is empty, set the binary search tree's root as a new node with key and value from input
    // Time complexity: O(1)
    if(root == null){
      root = new Node<T,V>(key, value);
    }
    // Otherwise, the binary search tree is not empty
    // Time complexity: best case: O(logN) (if tree is balanced), the worst case: O(N) (N is number of nodes in the binary search tree)
    else{
      // pointer is used to go down in the binary search tree based on the key from input
      Node<T,V> pointer = root;
      // parent is used to point at the last node that pointer points at
      Node<T,V> parent = null;
      /**
       * Use pointer to go down in the binary search tree based on the key from input until it is null (if key from input is smaller than pointer's key, go the the left; otherwise, go to the right);
       * make parent point at the last node that pointer points at in each iteration
       */
      while(pointer != null){
        parent = pointer;
        if(key.compareTo(pointer.key) < 0){
          pointer = pointer.left;
        }
        else{
          pointer = pointer.right;
        }
      }
      // Create the node with key and value from input and set that node as the parent's child (if key from input is smaller than pointer's key, set as left child; otherwise, set as right child)
      Node<T,V> nodeToInsert = new Node<T,V>(key, value);
      if(key.compareTo(parent.key) < 0){
        parent.left = nodeToInsert;
      }
      else{
        parent.right = nodeToInsert;
      }
    }
  }
  
  /**
   * Help method search(T key) by using recursion, continue calling itself until root from input is null (return null) or reaching the node that has same key as key from input (return that node)
   * Time complexity: O(logN) if tree is balanced; O(N) in the worst case (N is number of nodes in the binary search tree)
   */
  private Node<T,V> recurSearch(Node<T,V> root, T key){
    // If root from input is null, return null
    // Time complexity: O(1)
    if(root == null){
      return null;
    }
    // Otherwise, if key from input is equal to key of root from input, return root
    else if(key.compareTo(root.key) == 0){
      return root;
    }
    // Otherwise, if key from input is larger than key of root from input, continue searching with the root's right and key from input
    else if(key.compareTo(root.key) > 0){
      return recurSearch(root.right, key);
    }
    // Otherwise, continue searching with the root's left and key from input
    else{
      return recurSearch(root.left, key);
    }
  }
  
  /**
   * Search for a first node encountered which has key that is equal to the key from input, return that node's value or return null if there is no such node like that
   * Time complexity: O(logN) if tree is balanced; O(N) in the worst case (N is number of nodes in the binary search tree)
   */
  public V search(T key){
    // If there is no such node which has key that is equal to key from input, return null
    // Time complexity: O(1)
    if(recurSearch(root, key) == null){
      return null;
    }
    // Otherwise, call recurSearch(Node<T,V> root, T key) and return value in node returned by that method
    // Time complexity: best case: O(logN) (if tree is balanced), the worst case: O(N) (N is number of nodes in the binary search tree)
    else{
      return recurSearch(root, key).value;
    }
  }
  
  /**
   * Help method delete(T key) by deleting the node to delete when having that node and that node's parent
   * Time complexity: O(logN) if tree is balanced; O(N) in the worst case (N is number of nodes in the binary search tree)
   */
  private void helpDelete(Node<T,V> nodeToDelete, Node<T,V> parent){
    // If node to delete has less than 2 children, set child of parent of node to delete as child of node to delete
    // Time complexity: O(1)
    if(nodeToDelete.left == null || nodeToDelete.right == null){
      Node<T,V> childOfNodeToDelete = (nodeToDelete.left != null) ? nodeToDelete.left : nodeToDelete.right;
      if(nodeToDelete == root){
        root = childOfNodeToDelete;
      }
      else if(nodeToDelete.key.compareTo(parent.key) < 0){
        parent.left = childOfNodeToDelete;
      }
      else{
        parent.right = childOfNodeToDelete;
      }
    }
    /**
     * If node to delete has 2 children, find node with the smallest key in right subtree of node to delete;
     * use key and value of node found as key and value of node to delete respectively;
     * recursively call this helper method to delete the node found
     */
    // Time complexity: best case: O(logN) (if tree is balanced), the worst case: O(N) (N is number of nodes in the binary search tree)
    else{
      Node<T,V> nodeToReplace = nodeToDelete.right;
      Node<T,V> parentOfNodeToReplace = nodeToDelete;
      while(nodeToReplace.left != null){
        parentOfNodeToReplace = nodeToReplace;
        nodeToReplace = nodeToReplace.left;
      }
      nodeToDelete.key = nodeToReplace.key;
      nodeToDelete.value = nodeToReplace.value;
      helpDelete(nodeToReplace, parentOfNodeToReplace);
    }
  }
  
  /**
   * Delete the node having the key that is equal to key from input if that node exists
   * Time complexity: O(logN) if tree is balanced; O(N) in the worst case (N is number of nodes in the binary search tree)
   */
  public void delete(T key){
    // If the binary search tree is empty, do nothing; time complexity is O(1) in this case
    /**
     * If the binary search tree is not empty, use pointer to go down in the binary search tree based on the key from input until it is null or reaching the node having the key that is equal to key from input
     * (if key from input is smaller than pointer's key, go the the left; otherwise, go to the right);
     * make parent point at the last node that pointer points at in each iteration
     */
    // Time complexity: best case: O(logN) (if tree is balanced), the worst case: O(N) (N is number of nodes in the binary search tree)
    if(root != null){
      // pointer is used to go down in the binary search tree based on the key from input
      Node<T,V> pointer = root;
      // parent is used to point at the last node that pointer points at
      Node<T,V> parent = null;
      while(pointer != null && key.compareTo(pointer.key) != 0){
        parent = pointer;
        if(key.compareTo(pointer.key) < 0){
          pointer = pointer.left;
        }
        else{
          pointer = pointer.right;
        }
      }
      // If pointer is not null, call helpDelete(Node<T,V> nodeToDelete, Node<T,V> parent) using pointer and parent in input
      if(pointer != null){
        helpDelete(pointer, parent);
      }
    }
  }
  
  /**
   * Use recursion to help inorderRec(), add value in each node in inorder traversal of the binary search tree to list
   * Time complexity: O(N) (N is number of nodes in the binary search tree)
   */
  private void recurInorder(Node<T,V> root, List<V> list){
    // If left child of root from input is not null, recursively call this method with left child of root from input and list from input
    if(root.left != null){
      recurInorder(root.left, list);
    }
    // Add value of root from input to list's end
    list.add(root.value);
    // If right child of root from input is not null, recursively call this method with right child of root from input and list from input
    if(root.right != null){
      recurInorder(root.right, list);
    }
  }
  
  /**
   * Return the list of values in inorder traversal of the binary search tree
   * Time complexity: O(N) (N is number of nodes in the binary search tree)
   */
  public List<V> inorderRec(){
    // list is used to store the value in each node in inorder traversal of the binary search tree
    List<V> list = new LinkedList<V>();
    // If root is null, just return list, and time complexity is O(1) in this case
    // If root is not null, call recurInorder(Node<T,V> root, List<V> list) with root and list in input to use recursion, and time complexity in this case is O(N) (N is number of nodes in the binary search tree)
    if(root != null){
      recurInorder(root, list);
    }
    // Return list, which stores the value in each node in inorder traversal of the binary search tree
    return list;
  }
  
  /**
   * Find the kth smallest element in the binary search tree by using inorderRec()
   * Time complexity: O(N) (N is number of nodes in the binary search tree)
   */
  public V kthSmallest(int k){
    // list is used to store the value in each node in inorder traversal of the binary search tree
    List<V> list = inorderRec();
    // Return value with index (k - 1) in list
    return list.get(k - 1);
  }
}
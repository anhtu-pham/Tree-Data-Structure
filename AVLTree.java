import java.util.*;
// Class AVLTree is balanced tree in which each node k being larger than k's left subtree's nodes and smaller than or equal to k's right subtree's nodes
public class AVLTree<T extends Comparable<? super T>,V> implements TreeWithComparableKey<T,V>{
  
  // Class AVLNode is used for the node in the AVL tree
  private class AVLNode<T,V>{
    
    // the key stored in the node (the key is comparable)
    private T key;
    
    // the value stored in the node
    private V value;
    
    // the height of the node
    private int height;
    
    // the left child of the node
    private AVLNode<T,V> left;
    
    // the right child of the node
    private AVLNode<T,V> right;
    
    /**
     * Initialize the node with initialization of node's key and node's value
     */
    private AVLNode(T key, V value){
      this.key = key;
      this.value = value;
    }
  }
  
  // the root of the AVL tree
  private AVLNode<T,V> root;
  
  /**
   * Initialize the AVL tree with root being null
   */
  public AVLTree(){
    root = null;
  }
  
  /**
   * Return the height of the node in input
   * Time complexity: O(1)
   */
  private int getNodeHeight(AVLNode<T,V> node){
    // If node in input is null, return -1
    if(node == null){
      return -1;
    }
    // Otherwie, return node's height
    else{
      return node.height;
    }
  }
  
  /**
   * Update the height of the node in input after making change to tree (should be updated after that node's children's heights are updated)
   * Time complexity: O(1)
   */
  private void updateNodeHeight(AVLNode<T,V> node){
    node.height = Math.max(getNodeHeight(node.left), getNodeHeight(node.right)) + 1;
  }
  
  /**
   * Return the balance of the node in input
   * Time complexity: O(1)
   */
  private int getNodeBalance(AVLNode<T,V> node){
    // If node in input is null, that node's balance is 0, so 0 should be returned
    if(node == null){
      return 0;
    }
    // Otherwise, take the result after subtracting height of left child of node in input from height of right child of node in input
    else{
      return getNodeHeight(node.right) - getNodeHeight(node.left);
    }
  }
  
  /**
   * Do a left rotation around the node in input
   * Time complexity: O(1)
   */
  private AVLNode<T,V> leftRotate(AVLNode<T,V> node){
    AVLNode<T,V> rotatedNode = node.right;
    AVLNode<T,V> movedNode = rotatedNode.left;
    rotatedNode.left = node;
    node.right = movedNode;
    updateNodeHeight(node);
    updateNodeHeight(rotatedNode);
    return rotatedNode;
  }
  
  /**
   * Do a right rotation around the node in input
   * Time complexity: O(1)
   */
  private AVLNode<T,V> rightRotate(AVLNode<T,V> node){
    AVLNode<T,V> rotatedNode = node.left;
    AVLNode<T,V> movedNode = rotatedNode.right;
    rotatedNode.right = node;
    node.left = movedNode;
    updateNodeHeight(node);
    updateNodeHeight(rotatedNode);
    return rotatedNode;
  }
  
  /**
   * Rebalance the node in input when that node's balance is smaller than -1 or larger than 1
   * Time complexity: O(1)
   */
  private AVLNode<T,V> rebalanceNode(AVLNode<T,V> node){
    // Update the height of node in input and store the balance of node in input in nodeBalance
    updateNodeHeight(node);
    int nodeBalance = getNodeBalance(node);
    /**
     * If nodeBalance is smaller than -1:
     * do left rotation around left node of input node if height of left child of input node's left child is smaller than or equal to height of right child of input node's left child;
     * do right rotation around the input node
     */
    if(nodeBalance < -1){
      if(getNodeHeight(node.left.left) <= getNodeHeight(node.left.right)){
        node.left = leftRotate(node.left);
      }
      node = rightRotate(node);
    }
    /**
     * If nodeBalance is larger than 1:
     * do right rotation around right node of input node if height of left child of input node's right child is larger than or equal to height of right child of input node's right child;
     * do left rotation around the input node
     */
    else if(nodeBalance > 1){
      if(getNodeHeight(node.right.left) >= getNodeHeight(node.right.right)){
        node.right = rightRotate(node.right);
      }
      node = leftRotate(node);
    }
    return node;
  }
  
  /**
   * Help method insert(T key, V value) by using recursion, continue calling itself until input node is null and return a new node with input key and input value
   * Time complexity: O(logN) (N is number of nodes in the AVL tree)
   */
  private AVLNode<T,V> recurInsert(AVLNode<T,V> node, T key, V value){
    // If input node is null, return a new node with input key and input value
    if(node == null){
      return new AVLNode<T,V>(key, value);
    }
    // Otherwise, if input key is larger than or equal to input node's key, continue calling this helper method with input node's right child, input key, and input value
    else if(key.compareTo(node.key) >= 0){
      node.right = recurInsert(node.right, key, value);
    }
    // Otherwise, continue calling this helper method with input node's left child, input key, and input value
    else{
      node.left = recurInsert(node.left, key, value);
    }
    // Rebalance node to make sure that each node has balanced from -1 to 1
    return rebalanceNode(node);
  }
  
  /**
   * Insert a node with key and associated value to the AVL tree by calling recurInsert(AVLNode<T,V> node, T key, V value) with root, input key, and input value
   * Time complexity: O(logN) (N is number of nodes in the AVL tree)
   */
  public void insert(T key, V value){
    root = recurInsert(root, key, value);
  }
  
  /**
   * Help method search(T key) by using recursion, continue calling itself until root from input is null (return null) or reaching the node that has same key as key from input (return that node)
   * Time complexity: O(logN) (N is number of nodes in the AVL tree)
   */
  private AVLNode<T,V> recurSearch(AVLNode<T,V> root, T key){
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
   * Time complexity: O(logN) (N is number of nodes in the AVL tree)
   */
  public V search(T key){
    // If there is no such node which has key that is equal to key from input, return null
    // Time complexity: O(1)
    if(recurSearch(root, key) == null){
      return null;
    }
    // Otherwise, call recurSearch(Node<T,V> root, T key) and return value in node returned by that method
    // Time complexity: O(logN) (N is number of nodes in the AVL tree)
    else{
      return recurSearch(root, key).value;
    }
  }
  
  /**
   * Help recurDelete(AVLNode<T,V> node, T key) by finding and returning the smallest descendant of the node in input
   * Time complexity: O(logN) (N is number of nodes in the AVL tree)
   */
  private AVLNode<T,V> findSmallestDescendant(AVLNode<T,V> node){
    // If left child of node in input is null, return node
    // Time complexity: O(1)
    if(node.left == null){
      return node;
    }
    // If left child of node in input is not null, continue with the left child of node in input
    else{
      return findSmallestDescendant(node.left);
    }
  }
  
  /**
   * Help method recurDelete(AVLNode<T,V> node, T key) by using recursion to delete the node having key that is equal to input key
   * Time complexity: O(logN) (N is number of nodes in the AVL tree)
   */
  private AVLNode<T,V> recurDelete(AVLNode<T,V> node, T key){
    // If input node is null, return the input node
    // Time complexity: O(1)
    if(node == null){
      return node;
    }
    // Otherwise, if input key is smaller than input node's key, continue calling this helper method with input node's left child and input key
    else if(key.compareTo(node.key) < 0){
      node.left = recurDelete(node.left, key);
    }
    // Otherwise, if input key is larger than input node's key, continue calling this helper method with input node's right child and input key
    else if(key.compareTo(node.key) > 0){
      node.right = recurDelete(node.right, key);
    }
    /**
     * Otherwise, if input node has 2 children, find the descendant with smallest key of input node's right child,
     * use key and value of that descendant as key and value of input node respectively;
     * then recursively call this helper method using right child and key of input node after its key and value is updated
     */
    else if(node.left != null && node.right != null){
      AVLNode<T,V> rightSmallestDescendant  = findSmallestDescendant(node.right);
      node.key = rightSmallestDescendant.key;
      node.value = rightSmallestDescendant.value;
      node.right = recurDelete(node.right, node.key);
    }
    // Otherwise, if input node has less than 2 children, set input node as input node's left child if input node's left child is not null or node's right child otherwise
    else{
      node = (node.left != null) ? node.left : node.right;
    }
    // If node is not null, rebalance node
    if(node != null){
      node = rebalanceNode(node);
    }
    return node;
  }
  
  /**
   * Delete the node having the key that is equal to key from input if that node exists by calling recurDelete(AVLNode<T,V> node, T key) with root and input key
   * Time complexity: O(logN) (N is number of nodes in the AVL tree)
   */
  public void delete(T key){
    root = recurDelete(root, key);
  }
  
  /**
   * Add value in each node in inorder traversal of the AVL tree to list by using recursion to help inorderRec()
   * Time complexity: O(N) (N is number of nodes in the AVL tree)
   */
  private void recurInorder(AVLNode<T,V> root, List<V> list){
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
   * Return the list of values in inorder traversal of the AVL tree
   * Time complexity: O(N) (N is number of nodes in the AVL tree)
   */
  public List<V> inorderRec(){
    // list is used to store the value in each node in inorder traversal of the AVL tree
    List<V> list = new LinkedList<V>();
    // If root is null, just return list, and time complexity is O(1) in this case
    // If root is not null, call recurInorder(Node<T,V> root, List<V> list) with root and list in input to use recursion, and time complexity in this case is O(N) (N is number of nodes in the AVL tree)
    if(root != null){
      recurInorder(root, list);
    }
    // Return list, which stores the value in each node in inorder traversal of the AVL tree
    return list;
  }
  
  /**
   * Find the kth smallest element in the AVL tree by using inorderRec()
   * Time complexity: O(N) (N is number of nodes in the AVL tree)
   */
  public V kthSmallest(int k){
    // list is used to store the value in each node in inorder traversal of the AVL tree
    List<V> list = inorderRec();
    // Return value with index (k - 1) in list
    return list.get(k - 1);
  }
}
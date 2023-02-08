import java.util.*;
public interface TreeWithComparableKey<T  extends Comparable<? super T>,V>{
  
  /**
   * Insert a node with key and associated value to the tree
   */
  void insert(T key, V value);
  
  /**
   * Search for a first node encountered which has key that is equal to the key from input, return that node's value or return null if there is no such node like that
   */
  V search(T key);
  
  /**
   * Delete the node having the key that is equal to key from input if that node exists
   */
  void delete(T key);
  
  /**
   * Return the list of values in inorder traversal of the tree
   */
  List<V> inorderRec();
  
  /**
   * Find the kth smallest element in the tree
   */
  V kthSmallest(int k);
}
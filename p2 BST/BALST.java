//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           BALST
// Course:          CS 400
//
// Author:          Andrew Fix
// Email:           afix@wisc.edu
// Lecture Number: 	001
// Description: Creates an AVL tree that is able to insert nodes, balance, and
//						remove nodes
// 
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;


/**
 * 
 * Class to implement a BalanceSearchTree. Can be of type AVL or Red-Black. Note
 * which tree you implement here and as a comment when you submit.
 * 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {

	private BSTNode<K, V> root;

	private int numKeys;

	/**
	 * BSTNode inner class creates a node with many features to be added
	 * to the AVL tree
	 * 
	 * @author andrewfix
	 *
	 * @param <K>	generic type for the key
	 * @param <V>	generic type for the value
	 */
	private class BSTNode<K, V> {
		K key;
		V value;
		BSTNode<K, V> left;
		BSTNode<K, V> right;
		int balanceFactor;
		int height;

		/** 
		 * @param key
		 * @param value
		 * @param leftChild
		 * @param rightChild
		 */
		private BSTNode(K key, V value, BSTNode<K, V> leftChild, BSTNode<K, V> rightChild) {
			this.key = key;
			this.value = value;
			this.left = leftChild;
			this.right = rightChild;
			this.height = 0;
			this.balanceFactor = 0;
		}
		/**
		 * BSTNode constructor to create a node with key and value parameters
		 * 
		 * @param key		the key of the node
		 * @param value		the value of the node
		 */
		private BSTNode(K key, V value) {
			this(key, value, null, null);
		}
	}
	
	/**
	 * BALST constructor creating the AVL tree
	 */
	public BALST() {
		root = null;
		numKeys = 0;
	}

	@Override
	/**
	 * Gets the root's key
	 * 
	 * @return	the root's key
	 */
	public K getKeyAtRoot() {
		// if root is null, return null
		if (root == null)
			return null;
		return root.key;
	}

	@Override
	/**
	 * Gets the left child of the node that matches the key
	 * passed through the parameters
	 * 
	 * @param key	the key
	 * @throws IllegalNullKeyException	when key is null
	 * @throws KeyNotFoundException		when the key is not found
	 * 
	 * @return key of the left child
	 */
	public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// if key is null, throw exception
		if (key == null)
			throw new IllegalNullKeyException();
		// if the key is already in tree, throw exception
		if (!contains(key))
			throw new KeyNotFoundException();

		return getNode(key).left.key;	// call recursive get node method
	}

	@Override
	/**
	 * Gets the key of the left child of the node that contains the key
	 * passed through the parameter
	 * 
	 * @param key	the key
	 * @throws IllegalNullKeyException	when key is null
	 * @throws KeyNotFoundException		when the key is not found 
	 * 
	 * @return key of the right child
	 */
	public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// if key is null throw exception
		if (key == null)
			throw new IllegalNullKeyException();
		// if key is not in the tree throw exception
		if (!contains(key))
			throw new KeyNotFoundException();

		return getNode(key).right.key;	// call recursive get node method
	}

	/**
	 * Gets the node of the key passed through
	 * 
	 * @param key	key of the node to find
	 * @return	node that contains the key passed through
	 * @throws IllegalNullKeyException	when the key is null
	 * @throws KeyNotFoundException		when the key is not found
	 */
	private BSTNode<K, V> getNode(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// if key is null throw exception
		if (key == null)
			throw new IllegalNullKeyException();
		// if key isnt in the tree throw exception
		if (!contains(key))
			throw new KeyNotFoundException();

		return getNodeHelp(root, key);	// call recursive helper method
	}

	/**
	 * Helper method for the getNode method to help provide the recursion needed
	 * to find the correct node
	 * 
	 * @param node	node being compared to the key
	 * @param key	key needed to be found in tree
	 * @return	node that has the correct key
	 */
	private BSTNode<K, V> getNodeHelp(BSTNode<K, V> node, K key) {
		// if the node's key matches the key
		if (node.key.equals(key))
			return node;	// return the node
		// if the key is less than the nodes key
		if (key.compareTo(node.key) < 0) {
			// recursively call the method with the node's left child
			return getNodeHelp(node.left, key);
		} else {
			// else recursively call the method with the right child
			return getNodeHelp(node.right, key);
		}
	}

	@Override

	/**
	 * 
	 * Returns the height of this BST
	 * 
	 * @return the number of levels that contain keys in this AVL tree
	 * 
	 */
	public int getHeight() {
		return height(root);	// call recursive helper height
	}

	/**
	 * Recursive method to determine the height of the tree
	 * 
	 * @param node	holds the location of where the recursion is in tree
	 * @return	height (int)
	 */
	private int height(BSTNode<K, V> node) {
		// if node is null, return 0
		if (node == null)
			return 0;
		// if the node is a leaf, return 1
		if (node.left == null && node.right == null)
			return 1;
		// call method recursively with the max of the left and right nodes
		return 1 + Math.max(height(node.left), height(node.right));
	}

	@Override
	/**
	 * 
	 * Returns the keys of the data structure in sorted order. In the case of
	 * binary search trees, the visit order is: L V R
	 *
	 * If the SearchTree is empty, an empty list is returned.
	 *
	 * @return List of Keys in-order
	 * 
	 */
	public List<K> getInOrderTraversal() {
		List<K> list = new LinkedList<K>();	// list to be returned
		
		return inOrderHelper(list, root);	// calls recursive helper
	}
	/**
	 * Helper method for the getInOrderTraversal() method
	 * 
	 * @param list	list that stores the nodes in order
	 * @param node	node being checked each recursion loop
	 * @return	list of keys in order
	 */
	private List<K> inOrderHelper(List<K> list, BSTNode<K, V> node){
		// if the node has a left child
		if(node.left != null)
			// recurse with the left child of node (Left)
			list = inOrderHelper(list, node.left);
		
		list.add(node.key);	// add the node's key (Visit)

		// if the node has a right child
		if(node.right != null)
			// recurse with the right child of node
			list = inOrderHelper(list, node.right);
		return list;
	}

	@Override

	/**
	 * Puts the keys in pre-order(V L R) list
	 * 
	 * @return	list of keys in pre-order
	 */
	public List<K> getPreOrderTraversal() {
		List<K> list = new LinkedList<K>();	// list holding keys
		return preHelper(root, list);	// call recursion helper
	}

	/**
	 * Helper method for getPreOrderTraversal (V L R)
	 * 
	 * @param node	node being checked each recursion loop
	 * @param list	list holding all the keys
	 * @return	list in pre-order
	 */
	private List<K> preHelper(BSTNode<K, V> node, List<K> list) {
		list.add(node.key);	// add key (Visit)

		// if node has left child
		if (node.left != null)
			// recursively call helper on the left child of node
			list = preHelper(node.left, list);
		// if node has a right child
		if (node.right != null)
			// recursively call helper on the right child of node
			list = preHelper(node.right, list);
		return list;
	}

	@Override
	/**
	 * Puts the keys of the list in post-order(L R V)
	 * 
	 * @return	list in post-order
	 */
	public List<K> getPostOrderTraversal() {
		List<K> list = new LinkedList<K>();	// list holding keys
		return postHelper(root, list);	// call recursive helper
	}

	/**
	 * Recursive helper method for the getPostOrderTraversal() method
	 * 
	 * @param node	node being checked each recursion loop
	 * @param list	list holding keys in post-order
	 * @return	list in post-order
	 */
	private List<K> postHelper(BSTNode<K, V> node, List<K> list) {
		// if node has left child
		if (node.left != null)
			// recursively call helper on left child (Left)
			list = postHelper(node.left, list);
		// if node has right child
		if (node.right != null)
			// recursively call helper on right child (Right)
			list = postHelper(node.right, list);

		list.add(node.key);	// add node (Visit)
		
		return list;
	}
	
	@Override
	/**
	 * 
	 */
	public List<K> getLevelOrderTraversal() {
		ArrayList<K> rList = new ArrayList<K>();
		if (root == null)
			return rList;
		ArrayList<BSTNode<K, V>> list = new ArrayList<BSTNode<K, V>>();
		list.add(root);

		while (!list.isEmpty()) {
			int size = list.size();
			ArrayList<K> holdList = new ArrayList<>(size);

			for (int i = 0; i < size; i++) {
				BSTNode<K, V> holdNode = list.remove(0);
				holdList.add(holdNode.key);

				if (holdNode.left != null) 
					list.add(holdNode.left);
				

				if (holdNode.right != null) 
					list.add(holdNode.right);
			}
			for (int i = 0; i < holdList.size(); i++) 
				rList.add(holdList.get(i));
		}
		return rList;
	}
	
    /** 
     * Add the key,value pair to the data structure and increase the number of keys.
     * If key is null, throw IllegalNullKeyException;
     * If key is already in data structure, throw DuplicateKeyException(); 
     * Do not increase the num of keys in the structure, if key,value pair is not added.
     * 
     * @param	key		key being passed through
     * @param	value	value being passed through
     */
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		//	if key is null throw exception
		if(key == null) 
			throw new IllegalNullKeyException();
		
		root = insertHelper(key, value, root);	// call recursive method 
	}
	
	/**
	 * Helper method for the insert method
	 * 
	 * @param key		key of node being inserted
	 * @param value		value of node being inserted
	 * @param node		node being compared on each recursion
	 * @return	node
	 * @throws DuplicateKeyException	when key is already in the tree
	 */
	private BSTNode<K, V> insertHelper(K key, V value, BSTNode<K, V> node) throws DuplicateKeyException{
		// if the node is null, insert and increment numKeys
		if(node == null) {
			numKeys++;
			return new BSTNode<K, V>(key, value);
		}
		//	if the key is less than the node's key
		if(key.compareTo(node.key) < 0) {
			// recursively call helper with the node's left child
			node.left = insertHelper(key, value, node.left);
			
		//	if the key is greater than the node's key
		} else if(key.compareTo(node.key) > 0) {
			// recursively call helper with the node's right child
			node.right = insertHelper(key, value, node.right);
		} else {
			throw new DuplicateKeyException();
		}
		
		node.height = height(node);	// set height
		node.balanceFactor = height(node.left) - height(node.right);	// set balance factor
		
		// if node is not balanced on left side
		if(node.balanceFactor >= 2)
			// if the left node has a right node
			if(node.left.balanceFactor < 0) {
				// left right rotate
				node = leftRight(node);
			} else {
				// else right rotate
				node = rightRotate(node);
			}
		// if node is not balanced on right side
		if(node.balanceFactor <= -2)
			// if there is a left child of the right child
			if(node.right.balanceFactor > 0) {
				// right left rotate
				node = rightLeft(node);
			}else {
				// else left rotate
				node = leftRotate(node);
			}
		return node;
	}
	
	/**
	 * Performs a right rotate for a group of three nodes that are not 
	 * balanced on the left side
	 * 
	 * @param node	node that isn't balanced
	 * @return	root of the balanced nodes
	 */
	private BSTNode<K, V> rightRotate(BSTNode<K, V> node){
	    BSTNode<K, V> temp = node.left;	// hold left child's value
	    node.left = temp.right;	// set the left child of node to the right child of temp
	    temp.right = node;	// set the right child of temp to the node
	    return temp;	// return temp
	}

	/**
	 * Performs a left rotate for a group of three nodes that are 
	 * not balanced on the right side
	 * 
	 * @param node
	 * @return
	 */
	private BSTNode<K, V> leftRotate(BSTNode<K, V> node) {
		BSTNode<K, V> temp = node.right;	// hold node's right child in temp
		node.right = temp.left;	// set right child of node to left child of temp
		temp.left = node;	// set temp's left child to node
		return temp;	// return temp
	}
	
	/**
	 * Performs a left right rotate on groups of nodes that are not balanced
	 * and don't lineup for a right rotate
	 * 
	 * @param node	node that is not balanced
	 * @return	new root of balanced nodes
	 */
	private BSTNode<K, V> leftRight(BSTNode<K, V> node){
		BSTNode<K, V> temp = node.left;	// holds nodes left child
		node.left = node.left.right;	// set left child to the right child of the left child
		node.left.left = temp;	// set the left child of the left child to temp
		return rightRotate(node);	// perform a right rotate on the new arrangement of nodes
	}
	
	/**
	 * Performs a left right rotate on groups of nodes that are not balanced
	 * and don't lineup for a left rotate
	 * 
	 * @param node	node that is not balanced
	 * @return	new root of balanced nodes
	 */
	private BSTNode<K, V> rightLeft(BSTNode<K, V> node){
		BSTNode<K, V> temp = node.right;	// holds right child of node
		node.right = node.right.left; // sets right child to the left of the right child
		node.right.right = temp;	// sets the right of the right child to temp
		return leftRotate(node);	// left rotates on new arrangement of nodes
	}

	@Override
	/**
	 * Removes the node that contains the key passed through
	 * 
	 * @param	key 	key of node to be removed
	 * @return	true if removed
	 */
	public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
		//	if key is null, throw exception
		if (key == null) 
			throw new IllegalNullKeyException();
		// if root is null, return false
		if (root == null)
			return false;
		// if key isn't in the tree, throw exception
		if (!contains(key))
			throw new KeyNotFoundException();
		root = removeHelper(root, key);	// set root to the remove 
		numKeys--;	// decrement numKeys
		return true;
	}

	/**
	 * Helper method for the remove method to recursively loop through the
	 * tree and find the node needing to be removed
	 * 
	 * @param node	node being compared each iteration
	 * @param key	key trying to be removed
	 * @return	node that was removed
	 */
	private BSTNode<K, V> removeHelper(BSTNode<K, V> node, K key) {
		// if node is null, return null
		if(node == null)
			return null;
		// if key is less than the node's key
		if (key.compareTo(node.key) < 0) {
			// call remove helper method on the left child of node
			// and assign it to the right child
			node.right = removeHelper(node.left, key);
		} else {
			// if the node is a leaf, return null
			if(node.right == null && node.left == null) {
				node = null;
			//	if just the left node is null
			} else if(node.left == null) {
				// set node to the right child
				node = node.right;
			// if just the right node is null
			} else if(node.right == null) {
				// set node to the left child
				node = node.left;
			} else {
				BSTNode<K, V> m = max(node.left);	// max called on left child
				node = m;	// set node to the max
				// call remove helper method on the right child of node
				// and assign it to the left child
				node.left = removeHelper(node.left, node.key);
			}
		}
		return node;
	}
	
	/**
	 * Finds the max node
	 * 
	 * @param node	node to compare in recursive iterations
	 * @return	max node
	 */
	private BSTNode<K, V> max(BSTNode<K, V> node){
		BSTNode<K, V> m = node;	// holds node value
		// if node has right child
		if(node.right != null)
			// recursively call max on right child
			m = max(node.right);
		return m;
	}

	@Override
	/**
	 * Gets the value of the node that has the key being passed through
	 * 
	 * @param key	key that needs to be found in tree
	 * @return	value of the node with the correct key
	 */
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		return getHelper(root, key);	// calls recursive get helper
	}

	private V getHelper(BSTNode<K, V> node, K key) throws IllegalNullKeyException, KeyNotFoundException {
		// if key is null, throw exception
		if (key == null)
			throw new IllegalNullKeyException();
		// if the key is not in the tree, throw exception
		if (!contains(key)) 
			throw new KeyNotFoundException();
		// if the node key equals the key, return it's value
		if (node.key.equals(key))
			return node.value;
		// if key is less than the nodes key
		if (key.compareTo(node.key) < 0) {
			// iterate through the left child of node
			return getHelper(node.left, key);
		} else {
			// else iterate through the right child of node
			return getHelper(node.right, key);
		}
	}

	/**
	 * Checks if a node with a specified key exists in the tree
	 * 
	 * @param key	key that is being checked if it is in the tree
	 */
	public boolean contains(K key) throws IllegalNullKeyException {
		return containshelp(root, key);	// call recursive contains method
	}

	/**
	 * Helper method for the contains method
	 * 
	 * @param node	node being compared in each recursion
	 * @param key	key being checked if it is in the tree
	 * @return	true if key is found, false if not
	 * @throws IllegalNullKeyException	when key is null
	 */
	private boolean containshelp(BSTNode<K, V> node, K key) throws IllegalNullKeyException {
		// if key is null, throw exception
		if (key == null)
			throw new IllegalNullKeyException();
		// if node is null, return false
		if (node == null)
			return false;
		// if the node's key equals the key, return true (key found)
		if (node.key.equals(key))
			return true;
		// if key is less than node's key
		if (key.compareTo(node.key) < 0) {
			// recursively call helper on left child
			return containshelp(node.left, key);
		} else {
			// else recursively call helper on right child
			return containshelp(node.right, key);
		}
	}

	@Override
	/**
	 * Returns the number of keys in the tree
	 * 
	 * @return number of keys in tree
	 */
	public int numKeys() {
		return numKeys;
	}
	@Override
	/**
	 * Prints out the tree
	 */
	public void print() {
		String tree = printHelper(root);	// calls print helper recursively
		System.out.println(tree);	// prints tree once finished
	}
	/**
	 * Helper method for the print method
	 * 
	 * @param node	node being compared each recursion
	 * @return	String to be printed
	 */
	private String printHelper(BSTNode<K, V> node) {
		String s = "";	// String to hold what will be printed
		// list containing a level ordered traversal
		List<K> list = getLevelOrderTraversal();	
		// iterates through tree and adds to string
		for (int i = 0; i < list.size(); i++) 
			s += (String) list.get(i);
		return s;
	}
}

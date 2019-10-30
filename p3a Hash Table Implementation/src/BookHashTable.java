//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           BookHashTable.java
// Course:          CS 400
//
// Author:          Andrew Fix
// Email:           afix@wisc.edu
// Lecture Number: 	001
// Description: Creates a hash table out of an array to organize keys and books
//				in an efficient way. This class uses inner class Pair to keep
//				track of the keys and books that are in the hash table
// 
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// TODO: comment and complete your HashTableADT implementation
//
// TODO: implement all required methods
// DO ADD REQUIRED PUBLIC METHODS TO IMPLEMENT interfaces
//
// DO NOT ADD ADDITIONAL PUBLIC MEMBERS TO YOUR CLASS 
// (no public or package methods that are not in implemented interfaces)
//
// TODO: describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// if open addressing: describe probe sequence 
// if buckets: describe data structure for each bucket
//
// TODO: explain your hashing algorithm here 
// NOTE: you are not required to design your own algorithm for hashing,
//       since you do not know the type for K,
//       you must use the hashCode provided by the <K key> object

/** HashTable implementation that uses:
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTable implements HashTableADT<String, Book> {

    /**
     * Inner class of BookHashTable called Pair designed to hold the
     * key and book pairs being inserted into the hash
     * table
     * 
     * @author andrewfix
     *
     */
	private class Pair {
		private String key;	// key variable
		private Book book;	// book variable
		
		/**
		 * Constructor for the pair class that assigns a key 
		 * and book pair to the instance variables. This is
		 * designed to keep track of both values in the hash
		 * table and make them easily accessible
		 * 
		 * @param key	key being assigned
		 * @param book	book being assigned
		 */
		public Pair(String key, Book book) {
			this.key = key;
			this.book = book;
		}
		
		/**
		 * Gets the key of the pair object
		 * 
		 * @return	the key
		 */
		private String getKey() {
			return this.key;
		}
		
		/**
		 * Gets the book of the pair object
		 * 
		 * @return	the book
		 */
		private Book getBook() {
			return this.book;
		}
	}
	
	
	/** The initial capacity that is used if none is specified user */
    static final int DEFAULT_CAPACITY = 101;
    
    /** The load factor that is used if none is specified by user */
    static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;
    
    private int initialCapacity;	// capacity of the hash table
    private double loadFactorThreshold;	// load factor threshold to resize
    private int numKeys;	// number of keys in the hash table
    private double loadFactor;
    private Pair hash[];
    
    /**
     * REQUIRED default no-arg constructor
     * Uses default capacity and sets load factor threshold 
     * for the newly created hash table.
     */
    public BookHashTable() {
        this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR_THRESHOLD);
    }
    
    /**
     * Creates an empty hash table with the specified capacity 
     * and load factor.
     * @param initialCapacity number of elements table should hold at start.
     * @param loadFactorThreshold the ratio of items/capacity that causes table to resize and rehash
     */
    public BookHashTable(int initialCapacity, double loadFactorThreshold) {
    	numKeys = 0;	// number of keys in the hash table set at 0
    	this.initialCapacity = initialCapacity;	// sets the capacity
    	this.loadFactorThreshold = loadFactorThreshold;	// sets threshold
    	hash = new Pair[this.initialCapacity];	// sets hash to a pair array with the set capacity
    	this.loadFactor = 0.0;
    }
    /**
     * Creates a hash index for the key being passed through
     * 
     * @param key	the key that needs the hash index
     * @return	the hash index of the key
     */
    private int hashFunction(String key) {
    	// returns the hash code modded with the capacity to get the true hash index
    	return Math.abs(key.hashCode() % initialCapacity);	
    }

	@Override
	/**
	 * Inserts the key and book pair into the hash table, 
	 * uses the hashFunction method to get its index. If
	 * the index is already full, it searches for an empty
	 * index
	 * 
	 * @param key	key being inserted
	 * @param value	book being inserted
	 * @throws IllegalNullKeyException	when key is null
	 * @throws DuplicateKeyException	when the key is already in the hash table
	 */
	public void insert(String key, Book value) throws IllegalNullKeyException, DuplicateKeyException {
		if(key == null)	// if key is null, throw exception
			throw new IllegalNullKeyException();
		Pair pair = new Pair(key, value);	// create a new pair with the key and book
		int index = hashFunction(key);	// hold the hash index in variable index
		
		this.loadFactor = ((double)(numKeys)) / ((double) (initialCapacity));
		if(loadFactor >= loadFactorThreshold) {
			resize();
		}
		try {
			get(key);	// if calling get on key doesn't throw a key not found exception
			// throw a duplicate key exception because the key is already in the hash table
			throw new DuplicateKeyException();	
		} catch (KeyNotFoundException e) {
			// if the code reaches here, the key is not in the table yet
		}
		// if it is null at the hash index
		if(hash[index] == null) {
			hash[index] = pair;	// insert the pair to that spot
			numKeys++;	// increment the number of keys
		} else {
			// if there is a value at the hash index, we need to find an empty index
			while(hash[index] != null) {
				// if the key holds a delete value, break
				if(hash[index].getKey().equals("Delete"))
					break;
				index = (index + 1) % initialCapacity;	// circulate the indexing
			}
			// index will leave the while loop with the open index to insert at
			hash[index] = pair;
			numKeys++;	// increment numKeys
		}
		this.loadFactor = ((double)(numKeys)) / ((double) (initialCapacity));

	}
	
	/**
	 * Resizes the hash table array when the load factor exceeds the 
	 * load factor threshold
	 * 
	 * @throws IllegalNullKeyException	when a null key is found
	 * @throws DuplicateKeyException	when a duplicate key is found
	 */
	private void resize() throws IllegalNullKeyException, DuplicateKeyException {
		initialCapacity = 2 * initialCapacity + 1;	// resizes the capacity
		Pair[] hold = new Pair[initialCapacity];	// new array with the resized capacity
		// loops through the hash table to insert them into the new resized array
		for(int i = 0; i < hash.length; i++) {
			if(hash[i] != null) {	// check if each hash element is not null
				// get the hashed index of each key in hash
				int index = hashFunction(hash[i].getKey());	
				if (hold[index] == null) {	// if the new array is empty at index
					hold[index] = hash[i];	// insert that pair at index
				} else {
					// linear probe to find new open index
					while(hold[index] != null) {
						index = (index + 1) % initialCapacity;	// circularly index
					}
					// once desired index is found, set pair to hold
					hold[index] = hash[i];	
				}
			}
		}
		// update the load factor
		this.hash = hold;	// set hash to the resized array
	}

	@Override
	/**
	 * Removes a specific key from the hash table and replaces it with
	 * a key that marks that it was deleted. For each key that is removed 
	 * numKeys is decremented. 
	 * 
	 * @param key	the key attached to the pair that is going to be removed
	 * @throws IllegalNullKeyException	when a null key is being passed
	 * 
	 * @return	true if key was removed
	 */
	public boolean remove(String key) throws IllegalNullKeyException {
		if(key == null)	// if the key is null, throw exception
			throw new IllegalNullKeyException();
		if(numKeys == 0)	// if there is no keys in the table
			return false;	// return false
		
		int index = hashFunction(key);	// holds the hash index of key
		if(hash[index].getKey().equals(key)) {	// if the key is found
			// Assign the element to a pair that marks it deleted
			hash[index] = new Pair("Delete", new Book("Delete", "", "", "", "", "", "", ""));
			numKeys--;	// decrement keys
			return true;
		} else {
			// linear probe to find key to be deleted
			while(hash[index] != null) {
				index = (index + 1) % initialCapacity;	// circularly index
				if(hash[index].getKey().equals(key)) {	// if key is found
					// delete key, same as above
					hash[index] = new Pair("Delete", new Book("Delete", "", "", "", "", "", "", ""));
					numKeys--;
					return true;
				} 
			}
		}
		return false;	// if key is never deleted, return false
	}

	@Override
	/**
	 * Gets the book in the hash table that is assigned to a 
	 * specific key
	 * 
	 * @param key	the key being searched for
	 * @throws IllegalNullKeyException	when the key is null
	 * @throws KeyNotFoundException		when the key is not found
	 * 
	 * @return	the book that is assigned to the specified key
	 */
	public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null)	// if key is null, throw exception
			throw new IllegalNullKeyException();
		if(numKeys == 0) // if the table is empty, throw exception
			throw new KeyNotFoundException();
		// loop through hash table
		for(int i = 0; i < hash.length; i++) {
			if(hash[i] == null)	// continue the loop if the element is null
				continue;
			if(hash[i].getKey().equals(key))	// if key is found
				return hash[i].getBook();	// return the book assigned to the key
		}
		throw new KeyNotFoundException();	// if key is never found, throw exception
	}

	@Override
	/**
	 * Gets the number of keys that are in the hash table
	 * 
	 * @return	number of keys in the hash table 
	 */
	public int numKeys() {
		return numKeys;
	}

	@Override
	/**
	 * Gets the load factor threshold of the hash table
	 * 
	 * @return the load factor threshold
	 */
	public double getLoadFactorThreshold() {
		return loadFactorThreshold;
	}
	
	/**
	 * Gets the load factor of the table
	 * 
	 * @return	the load factor
	 */
	public double getLoadFactor() {
		return loadFactor;
	}

	@Override
	/**
	 * Gets the current capacity of the hash table
	 * 
	 * @return	 the capacity of the hash table
	 */
	public int getCapacity() {
		// TODO Auto-generated method stub
		return initialCapacity;
	}

	@Override
	/**
	 * Decides which method of collision handling this project uses
	 * 
	 * @return	number assigned to linear probing
	 */
	public int getCollisionResolutionScheme() {
		return 1;
	}

}
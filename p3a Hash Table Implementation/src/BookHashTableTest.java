/**

 * Filename:   TestHashTableDeb.java
 * Project:    p3
 * Authors:    Debra Deppeler (deppeler@cs.wisc.edu)
 * 
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   before 10pm on 10/29
 * Version:    1.0
 * 
 * Credits:    None so far
 * 
 * Bugs:       TODO: add any known bugs, or unsolved problems here
 */

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           BookHashTableTest.java
// Course:          CS 400
//
// Author:          Andrew Fix
// Email:           afix@wisc.edu
// Lecture Number: 	001
// Description: Tests different aspects of the BookHashTable class to help 
//				ensure efficiency
// 
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/** 
 * Test HashTable class implementation to ensure that required 
 * functionality works for all cases.
 */
public class BookHashTableTest {

    // Default name of books data file
    public static final String BOOKS = "books.csv";

    // Empty hash tables that can be used by tests
    static BookHashTable bookObject;
    static ArrayList<Book> bookTable;

    static final int INIT_CAPACITY = 2;
    static final double LOAD_FACTOR_THRESHOLD = 0.49;
       
    static Random RNG = new Random(0);  // seeded to make results repeatable (deterministic)

    /** Create a large array of keys and matching values for use in any test */
    @BeforeAll
    public static void beforeClass() throws Exception{
        bookTable = BookParser.parse(BOOKS);
    }
    
    /** Initialize empty hash table to be used in each test */
    @BeforeEach
    public void setUp() throws Exception {
         bookObject = new BookHashTable(INIT_CAPACITY,LOAD_FACTOR_THRESHOLD);
    }

    /** Not much to do, just make sure that variables are reset     */
    @AfterEach
    public void tearDown() throws Exception {
        bookObject = null;
    }

    /**
     * Inserts all values from bookTable to the bookObject
     * 
     * @param bookTable					table of values to be inserted
     * @throws IllegalNullKeyException	when key is null
     * @throws DuplicateKeyException	when there's a duplicate key
     */
    private void insertMany(ArrayList<Book> bookTable) 
        throws IllegalNullKeyException, DuplicateKeyException {
        for (int i=0; i < bookTable.size(); i++ ) {
            bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
        }
    }

    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_collision_scheme() {
        if (bookObject == null)
        	fail("Gg");
    	int scheme = bookObject.getCollisionResolutionScheme();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }


    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_IsEmpty() {
        //"size with 0 entries:"
        assertEquals(0, bookObject.numKeys());
    }

    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that a HashTable is not empty after adding one (key,book) pair
     * @throws DuplicateKeyException 
     * @throws IllegalNullKeyException 
     */
    @Test
    public void test001_IsNotEmpty() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
        String expected = ""+1;
        //"size with one entry:"
        assertEquals(expected, ""+bookObject.numKeys());
    }
    
    /** IMPLEMENTED AS EXAMPLE FOR YOU 
    * Test if the hash table  will be resized after adding two (key,book) pairs
    * given the load factor is 0.49 and initial capacity to be 2.
    */
    
    @Test 
    public void test002_Resize() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    	int cap1 = bookObject.getCapacity();
    	bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    	int cap2 = bookObject.getCapacity();
        //"size with one entry:"
        assertTrue(cap2 > cap1 & cap1 ==2);
    }
    @Test
    /**
     * Inserts three keys and removes one and sees if it is done 
     * correctly
     * 
     * @throws IllegalNullKeyException	when the key is null
     * @throws DuplicateKeyException	when a duplicate key is added
     */
    public void test003_InsertThreeRemoveOne() throws IllegalNullKeyException, DuplicateKeyException {
	    try {	
    		bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));	// insert test values
	    	bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
	    	bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
	    	
	    	bookObject.remove(bookTable.get(0).getKey());	// remove a value
	    	bookObject.get(bookTable.get(0).getKey());	// check if that value is still in the 
	    	fail("Found a removed key");
	    } catch(KeyNotFoundException e) {
	    	// pass
	    }

    }
    
    @Test
    /**
     * Tests the get method if multiple keys are inserted and
     * you try to get one of them
     * 
     * @throws IllegalNullKeyException
     * @throws DuplicateKeyException
     */
    public void test004_InsertAndGet() throws IllegalNullKeyException, DuplicateKeyException {
    	try {	
    		bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));	// insert test keys
	    	bookObject.insert(bookTable.get(5).getKey(),bookTable.get(5));
	    	bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
	    	
	    	bookObject.get(bookTable.get(5).getKey());	// test the get method
	    	// if an exception is thrown, test fails
	    } catch(KeyNotFoundException e) {
	    	fail("key not found exception thrown");
	    }
    }
    
    @Test
    /**
     * Test to see if you insert many and get many if there are any exceptions thrown
     * 
     */
    public void test005_InsertManyGetMany() {
    	try {
    		// insert everything from bookTable
	    	for (int i = 0; i < bookTable.size(); i++) {
	    		bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
	    	}
	    	// get everything from bookTable
	    	for (int i = 0; i < bookTable.size(); i++) {
	    		bookObject.get(bookTable.get(i).getKey());
	    	}
    
	    	// check if any exceptions are thrown and fail if so
    	} catch (DuplicateKeyException e) {
    		fail("duplicate key exception thrown.");
    	} catch (IllegalNullKeyException e) {
    		fail ("illegal null key exception thrown.");
    	} catch (KeyNotFoundException e) {
    		fail("key not found exception thrown.");
    	}
    }    
    /**
     * Tests capacity after inserting three keys
     * 
     * @throws IllegalNullKeyException	when the key is null
     * @throws DuplicateKeyException	when a key is duplicated in the table
     */
    public void test006_InsertThreeTestCapacity() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));	// insert test keys
    	bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    	bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
    	
    	assertEquals(3, bookObject.getCapacity());	// check capacity to see if it is 3
    }
    
    @Test
    /**
     * Tests the remove method when it is called multiple times
     * 
     * @throws IllegalNullKeyException	when key is null
     * @throws DuplicateKeyException	when a duplicate key is added
     */
    public void test007_RemoveMoreThanOneKey() throws IllegalNullKeyException, DuplicateKeyException {
    	 try {	
     		bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));	// insert test keys
 	    	bookObject.insert(bookTable.get(6).getKey(),bookTable.get(6));
 	    	bookObject.insert(bookTable.get(2).getKey(),bookTable.get(2));
     		bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3));
 	    	bookObject.insert(bookTable.get(9).getKey(),bookTable.get(9));
 	    	bookObject.insert(bookTable.get(10).getKey(),bookTable.get(10));
 	    	
 	    	bookObject.remove(bookTable.get(0).getKey());	// remove a key
 	    	bookObject.get(bookTable.get(0).getKey());	// see if it is in the hash table
 	    	fail("Found a removed key");	// if it is, fail
 	    	// if the key was removed, pass
    	 } catch(KeyNotFoundException e) {
  	    	// pass
  	    }
    	 try {
  	    	bookObject.remove(bookTable.get(9).getKey());	// remove a key
 	    	bookObject.get(bookTable.get(9).getKey());// see if it is in the hash table
 	    	fail("Found a removed key");	// if it is, fail
 	    	// if the key was removed, pass
    	 } catch(KeyNotFoundException e) {
    		 // pass
    	 }
 	    
    }
    
    @Test
    /**
     * Tests to see if a DuplicateKeyException is thrown if
     * you add a duplicate key 
     * 
     * @throws IllegalNullKeyException	when there's a null key
     * @throws DuplicateKeyException	when there's a duplicate key
     */
    public void test008_AddDuplicateKeyAndSeeIfExceptionIsThrown() throws IllegalNullKeyException, DuplicateKeyException {
    	try {
    		bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));	// insert test keys
 	    	bookObject.insert(bookTable.get(6).getKey(),bookTable.get(6));
 	    	bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3));
     		bookObject.insert(bookTable.get(3).getKey(),bookTable.get(3)); 	// insert duplicate key
     		// if the exception isn't thrown, test fails
     		fail("duplicate key is added, but no exception was thrown");
    	} catch(DuplicateKeyException e) {
    		// pass
    	}
    }
    
    @Test
    /**
     * Checks to see if the load factor updates as keys are added and
     * the table is resized
     * 
     * @throws IllegalNullKeyException	when the key is null
     * @throws DuplicateKeyException	when there is a duplicated key
     */
    public void test009_CheckLoadFactorAfterResizing() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));	// insert test keys
 	   	bookObject.insert(bookTable.get(6).getKey(),bookTable.get(6));
 	   	// tests if the correct load factor is allocated
 	   	assertEquals(0.4, bookObject.getLoadFactor());
    }
    @Test
    /**
     * Tests to see if it works if we insert many and remove five keys
     * 
     * @throws IllegalNullKeyException	when a key is null
     * @throws DuplicateKeyException	when there is a duplicate key
     */
    public void test010_InsertManyRemoveFive() throws IllegalNullKeyException, DuplicateKeyException {
    	
    		insertMany(bookTable);	// call insert many to insert table
    		bookObject.remove(bookTable.get(0).getKey()); // remove five values
    		bookObject.remove(bookTable.get(2).getKey());
    		bookObject.remove(bookTable.get(9).getKey());
    		bookObject.remove(bookTable.get(3).getKey());
    		bookObject.remove(bookTable.get(6).getKey());
    	try {
			bookObject.get(bookTable.get(0).getKey());
 	    	fail("Found a removed key");	// if key is found, fail
		} catch (KeyNotFoundException e) {
			//pass
		}
    	try {
			bookObject.get(bookTable.get(2).getKey());
 	    	fail("Found a removed key");	// if key is found, fail
		} catch (KeyNotFoundException e) {
			//pass
		}
    	try {
			bookObject.get(bookTable.get(9).getKey());
 	    	fail("Found a removed key");	// if key is found, fail
		} catch (KeyNotFoundException e) {
			//pass
		}
    	try {
			bookObject.get(bookTable.get(3).getKey());
 	    	fail("Found a removed key");	// if key is found, fail
		} catch (KeyNotFoundException e) {
			//pass
		}
    	try {
			bookObject.get(bookTable.get(6).getKey());
 	    	fail("Found a removed key");	// if key is found, fail
		} catch (KeyNotFoundException e) {
			//pass
		}
    	try {
			bookObject.get(bookTable.get(0).getKey());
 	    	fail("Found a removed key");	// if key is found, fail
		} catch (KeyNotFoundException e) {
			//pass
		}
    }

    
}

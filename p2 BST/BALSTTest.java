//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           BALSTTest
// Course:          CS 400
//
// Author:          Andrew Fix
// Email:           afix@wisc.edu
// Lecture Number: 	001
// Description: Creates JUnit tests for the BALST class
// 
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;

//@SuppressWarnings("rawtypes")
public class BALSTTest {

    BALST<String,String> balst1;	
    BALST<Integer,String> balst2;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        balst1 = createInstance();
	balst2 = createInstance2();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        balst1 = null;
	balst2 = null;
    }

    protected BALST<String, String> createInstance() {
        return new BALST<String,String>();
    }

    protected BALST<Integer,String> createInstance2() {
        return new BALST<Integer,String>();
    }

    /** 
     * Insert three values in sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred.
     */
    @Test
    void testBALST_001_insert_sorted_order_simple() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) 
                fail("avl insert at root does not work");
            
            balst2.insert(20, "20");
            if (!balst2.getKeyOfRightChildOf(10).equals(20)) 
                fail("avl insert to right child of root does not work");
            
            balst2.insert(30, "30");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) 
                fail("avl rotate does not work");
            
            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));

            //balst2.print();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail( "Unexpected exception AVL 001: "+e.getMessage() );
        }
    }

    /** 
     * Insert three values in reverse sorted order and then check 
     * the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_002_insert_reversed_sorted_order_simple() {
		try {
            balst2.insert(30, "30");	// insert test nodes
            balst2.insert(20, "20");
            balst2.insert(10, "10");
            
            // test if tree is balanced
            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception AVL 002: " + e.getMessage());
		}
        
    }

    /** 
     * Insert three values so that a right-left rotation is
     * needed to fix the balance.
     * 
     * Example: 10-30-20
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_003_insert_smallest_largest_middle_order_simple() {
        try {
			balst2.insert(30, "30"); // insert test nodes
	        balst2.insert(20, "20");
	        balst2.insert(10, "10");
	        
	        // test if tree is balanced
	        Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
	        Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
	        Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /** 
     * Insert three values so that a left-right rotation is
     * needed to fix the balance.
     * 
     * Example: 30-10-20
     * 
     * Then check the root, left, and right keys to see if rebalancing 
     * occurred in the other direction.
     */
    @Test
    void testBALST_004_insert_largest_smallest_middle_order_simple() {
        
        try {
    			balst2.insert(30, "30"); // insert test nodes
    	        balst2.insert(10, "10");
    	        balst2.insert(20, "20");
    	        
    	        // test if tree is balanced
    	        Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
    	        Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
    	        Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }
    @Test
    /**
     * Test if you insert 20, remove 20, and see if it doesn't contain 20
     * 
     */
    void test06_insert20_remove20_contains20() {
    	try {
			balst2.insert(20, "20");// insert test nodes
			
			balst2.remove(20);	// remove test nodes
			
			if(balst2.contains(20))
				fail("test06 fails");// check if it contains

			//Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
			//Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    /**
     * Tests the left rotate/rebalance to see if the tree balances when you add
     * four nodes
     * 
     */
    void test07_insert4_checkleftrotate() {
    	try {
			balst2.insert(10, "10"); // insert test nodes
			balst2.insert(20, "20");
			balst2.insert(40, "40");
			balst2.insert(30, "30");
			
			// check if key is balanced
			Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
			Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
			Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(40));
			Assert.assertEquals(balst2.getKeyOfLeftChildOf(40), new Integer(30));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    /**
     * Tests to see if the height matches the height of a test
     * AVL tree
     */
    void test08_insert_multiple_nodes_check_height() {
    	try {
			balst2.insert(10, "10"); // insert test nodes
			balst2.insert(20, "20");
			balst2.insert(40, "40");
			balst2.insert(30, "30");
			
			// if height doesn't equal 3, test fails
			if(balst2.getHeight() != 3)
				fail("test08 fails");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Test
    /**
     * Tests to see if the get method returns the correct value
     */
    void test09_get_when_inserting_four_nodes() {
    	try {
			balst2.insert(10, "10"); // insert test nodes
			balst2.insert(20, "20");
			balst2.insert(40, "40");
			balst2.insert(30, "30");
			
			// if get method doesn't get the right key, fails
			if(!balst2.get(20).equals("20"))
				fail("test09 fails");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Test
    /**
     * Test the contains method
     */
    void test10_contains_method() {
    	try {
			balst2.insert(10, "10"); // insert test nodes
			balst2.insert(5, "5");
			balst2.insert(20, "20");
			balst2.insert(30, "30");
			
			// checks if contains returns the proper output
			if(!balst2.contains(30))
				fail("test10 fails");
			if(!balst2.contains(5))
				fail("test10 fails");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    /**
     * Tests remove method after adding nodes to the 
     * AVL tree
     */
    void test11_remove_node_after_inserting_nodes() {
    	try {
			balst2.insert(10, "10"); // insert test nodes
			balst2.insert(5, "5");
			balst2.insert(20, "20");
			balst2.insert(30, "30");
			
			// checks if remove returns is correct
			if(!balst2.remove(30))
				fail("test10 fails");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

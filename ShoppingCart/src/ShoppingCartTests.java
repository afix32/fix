//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Shopping Cart
// Files:           ShoppingCart.java, ShoppingCartTests.java
// Course:          CS 300 Spring semester freshman year
//
// Author:          Andrew Fix
// Email:           afix@wisc.edu
// Lecturer's Name: Gary Dahl
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         (identify each person and describe their help in detail)
// Online Sources:  (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

// JavaDoc class Header comes here
public class ShoppingCartTests {

	/**
	 * Checks whether the total number of items within the cart is incremented after
	 * adding one item
	 * 
	 * @return true if the test passes without problems, false otherwise
	 */
	public static boolean testCountIncrementedAfterAddingOnlyOneItem() {
		boolean testPassed = true; // boolean local variable evaluated to true if this test passed,
									// false otherwise
		String[] cart = new String[20]; // shopping cart
		int count = 0; // number of items present in the cart (initially the cart is empty)

		// Add an item to the cart
		count = ShoppingCart.add(3, cart, count); // add an item of index 3 to the cart
		// Check that count was incremented
		if (count != 1) {
			System.out.println("Problem detected: After adding only one item to the cart, "
					+ "the cart count should be incremented. But, it was not the case.");
			testPassed = false;
		}
		return testPassed;
	}

	/**
	 * Checks whether add and OccurrencesOf return the correct output when only one
	 * item is added to the cart
	 * 
	 * @return true if test passed without problems, false otherwise
	 */
	public static boolean testAddAndOccurrencesOfForOnlyOneItem() {
		boolean testPassed = true; // evaluated to true if test passed without problems, false otherwise
		// define the shopping cart as an oversize array of elements of type String
		// we can set an arbitrary capacity for the cart - for instance 10
		String[] cart = new String[10]; // shopping cart
		int count = 0; // number of items present in the cart (initially the cart is empty)

		// check that OccurrencesOf returns 0 when called with an empty cart
		if (ShoppingCart.occurrencesOf(10, cart, count) != 0) {
			System.out.println("Problem detected: Tried calling OccurrencesOf() method when the cart is "
					+ "empty. The result should be 0. But, it was not.");
			testPassed = false;
		}

		// add one item to the cart
		count = ShoppingCart.add(0, cart, count); // add an item of index 0 to the cart

		// check that OccurrencesOf("Apples", cart, count) returns 1 after adding the
		// item with key 0
		if (ShoppingCart.occurrencesOf(0, cart, count) != 1) {
			System.out.println("Problem detected: After adding only one item with key 0 to the cart, "
					+ "OccurrencesOf to count how many of that item the cart contains should return 1. "
					+ "But, it was not the case.");
			testPassed = false;
		}

		return testPassed;
	}
	
	
	/**
	 * 
	 * testing to see if the remove function works
	 * 
	 * @return true if it passed the test, false if it doesn't
	 * 
	 */
	public static boolean testRemove() {
		boolean testPassed = true;
		String[] cart = new String[10]; // test cart
		int count = 0; // test count
		count = ShoppingCart.add(0, cart, count);	// adds an item to the cart
		count = ShoppingCart.add(6, cart, count);	// adds another item to the cart
		count = ShoppingCart.remove("Apple", cart, count); // removes the apple from the cart
		for(int i = 0; i < count; i++) {		// iterated through the cart to check each element
			if(cart[i].equals("Apple")) {		// sees if 'Apple' was still in the cart
				testPassed = false;				// if it is in the cart, the method failed
			}
		}
		return testPassed;
	}
	
	
	/**
	 * testing to see if the sub total price method works
	 * when we add 3 items to it and check out
	 * 
	 * @return true if it passes the test and false if it doesn't
	 */
	public static boolean testGetSubTotalPrice() {
		boolean check = true; // used to check if the test works or not
		String[] cart = new String[10]; // test cart
		int count = 0; // test count
		count = ShoppingCart.add(0, cart, count); // adding an apple
		count = ShoppingCart.add(1, cart, count); // adding an avacado
		count = ShoppingCart.add(2, cart, count); // adding a banana
		double testTotal = ShoppingCart.getSubTotalPrice(cart, count);
		if(testTotal == 2.8) {
			check = true;
		}
		return check;
	}
	
	/*
	 * tests the displayCartContent method to see if it displays the 
	 * right items.
	 */
	public static void testDisplayCartContent() {
		String[] cart = new String[10]; // test cart
		int count = 0; // test count
		count = ShoppingCart.add(0, cart, count); // adding an apple
		count = ShoppingCart.add(1, cart, count); // adding an avacado
		ShoppingCart.displayCartContent(cart, count);
		System.out.println("Must be the same as 'Cart Content: Apple, Avacado, '"); // compares the output to the expected output
	}
	
	/**
	 * test if the occurrancesOf() method works for adding multiple items
	 *
	 * @return true if it passes, false if it doesn't
	 */
	public static boolean testOccurrancesForMultipleItems() {
		boolean test = true;
		String[] cart = new String[10]; // test cart
		int count = 0; // test count
		count = ShoppingCart.add(0, cart, count); // adding an apple
		count = ShoppingCart.add(0, cart, count); // adding another apple
		int occurAmount = ShoppingCart.occurrencesOf(0, cart, count);
		if(occurAmount != 2) {
			test = false;
		}
		return test;
	}
	
	/**
	 * tests to see what happens when you remove an item that isn't in the cart
	 */
	public static void testRemoveObjectNotThere() {
		String[] cart = new String[10]; // test cart
		int count = 0; // test count
		count = ShoppingCart.add(9, cart, count); // adding an item
		count = ShoppingCart.add(3, cart, count); // adding another item
		count = ShoppingCart.remove("Apple", cart, count); // removing an item thats not in the cart
		System.out.println("Should print a warning statement saying there is no Apple in the cart");
	}

	/**
	 * main method used to call the unit tests
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(
				"testCountIncrementedAfterAddingOnlyOneItem(): " + testCountIncrementedAfterAddingOnlyOneItem() + "\n");
		System.out.println("testAddAndOccurrencesOfForOnlyOneItem(): " + testAddAndOccurrencesOfForOnlyOneItem() + "\n");
		System.out.println("Test remove: " + testRemove() + "\n");
		System.out.println("Test get sub total price: " + testGetSubTotalPrice() + "\n");
		testDisplayCartContent();
		System.out.println("");
		System.out.println("Test occurranceOf() for multiple items: " + testOccurrancesForMultipleItems() + "\n");
		testRemoveObjectNotThere();
	}
}


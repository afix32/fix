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

import java.util.Scanner;	// imports a scanner
public class ShoppingCart {
	// Define final parameters
	private static final int CART_CAPACITY = 20; // shopping cart max capacity
	private static final double TAX_RATE = 0.05; // sales tax

	// a perfect-size two-dimensional array that stores the available items in the
	// market
	// MARKET_ITEMS[i][0] refers to a String that represents the description of the
	// item
	// identified by index i
	// MARKET_ITEMS[i][1] refers to a String that represents the unit price of the
	// item
	// identified by index i in dollars.
	public static final String[][] MARKET_ITEMS = new String[][] { { "Apple", "$1.59" }, { "Avocado", "$0.59" },
			{ "Banana", "$0.49" }, { "Beef", "$3.79" }, { "Blueberry", "$6.89" }, { "Broccoli", "$1.79" },
			{ "Butter", "$4.59" }, { "Carrot", "$1.19" }, { "Cereal", "$3.69" }, { "Cheese", "$3.49" },
			{ "Chicken", "$5.09" }, { "Chocolate", "$3.19" }, { "Cookie", "$9.5" }, { "Cucumber", "$0.79" },
			{ "Eggs", "$3.09" }, { "Grape", "$2.29" }, { "Ice Cream", "$5.39" }, { "Milk", "$2.09" },
			{ "Mushroom", "$1.79" }, { "Onion", "$0.79" }, { "Pepper", "$1.99" }, { "Pizza", "$11.5" },
			{ "Potato", "$0.69" }, { "Spinach", "$3.09" }, { "Tomato", "$1.79" } };

	/**
	 * Adds the item to the cart that corresponds with the given
	 * index
	 * 
	 * @param index: index of chosen food item
	 * @param cart: the users cart
	 * @param count: the amount of items in the users cart
	 * 
	 * @return the new count value
	 */
	public static int add(int index, String[] cart, int count) {
		if (count >= CART_CAPACITY) {		// if the count is greater than the cart capacity it prints a warning statement
			System.out.println("WARNING: The cart is full. You cannot add any new item.");
		} else {
		
		String itemAdd = getItemDescription(index);		// itemAdd gets the item name for the index that the user selected
		cart[count] = itemAdd;		// puts that value into the 'count' position in the cart
		count++;		
		}
		return count;
	}

	private static String getItemDescription(int index) {
		return MARKET_ITEMS[index][0];	// used to get the name of the item if at a certain index
	}
	
	/**
	 * Prints out how many items there are at the given index
	 * 
	 * @param itemIndex: the index typed in by the user
	 * @param cart: the users cart
	 * @param count: amount of items in the cart
	 * 
	 * @return the amount of times the specified item was found in the cart
	 * 
	 */
	public static int occurrencesOf(int itemIndex, String[] cart, int count) {
		String itemAdd = getItemDescription(itemIndex);		// assigns itemAdd with the item at the given index
		int itemNum = 0;		// sets itemNum to 0, used to keep track of how many times
		for (int i = 0; i < count; i++) {	// iterates through the cart
			if (itemAdd.equals(cart[i])) {	// if the item added is equal to an item in the cart
				itemNum++;
			}
		}
		System.out.println("The number of occurrences of " + MARKET_ITEMS[itemIndex][0]		// prints out how many occurrances there are
				+ " (id #" + itemIndex + ") is: " + itemNum);
		return itemNum;
	}
	
	/**
	 * removes a specified item from the cart
	 * 
	 * @param itemToRemove:  name of the item to be removed
	 * @param cart:  users cart
	 * @param count:  amount of items in the cart
	 * 
	 * @return the new count level
	 */
	public static int remove(String itemToRemove, String[] cart, int count) {
		String temp = "";		// creates variable temp
		int numIndex = indexOf(itemToRemove, cart, count);	// numIndex is set to the index of the item that needs to be removed
		if(numIndex != -1) {		// if numIndex isn't -1
			temp = cart[count - 1];		// assign temp to the item of the cart at the count index(1 less)
			cart[numIndex] = temp;		// assigns it to the actual cart
			cart[count - 1] = null;
			count--;
		} else {
			System.out.println("WARNING: " + itemToRemove + " not found in the shopping cart."); // if else, print warning statement
		}
		return count;
		
	}

	/**
	 * gets the index of the item name
	 * 
	 * @param item: name of the item
	 * @param cart: users shopping cart
	 * @param count:  amount of items in the users shopping cart
	 * 
	 * @return the index of that item
	 */
	private static int indexOf(String item, String[] cart, int count) {
		int index = -1; // initial index value is assigned -1
		for (int i = 0; i < count; i++) {	// iterates through the cart
			if (cart[i].equals(item)) {		// if the specified item of the cart equals the item passed thru,
				index = i;					// the index is assigned to that index and then returned
				break; // once the item is found, the for loop can stop iteratiing
			}
		}
		return index;
	}

	/**
	 * gets the sub total price of the items in the checkout
	 * 
	 * @param cart: users cart
	 * @param count: amount of items in the cart
	 * 
	 * @return the total cost of the items in the cart
	 */
	public static double getSubTotalPrice(String[] cart, int count) {
		double totCost = 0;		// creates a total cost variable
		double itemPrice = 0;	// creates a variable that holds each items prices
		for(int i = 0; i < count; i++) {		// iterate thru the cart
			for(int j = 0; j < MARKET_ITEMS.length; j++) {	// iterates thru MARKET_ITEMS
				if(cart[i].equals(MARKET_ITEMS[j][0])) {	// if the item in the cart matches an item in the market
					String item = MARKET_ITEMS[j][1];		// then assign the price to item
					item = item.replaceAll("$", "");		// it then has to have a $ placed at the start of it
					itemPrice = Double.parseDouble(item.substring(1));	// and assigns the number to item price as a double
					totCost += itemPrice;								// so that it can be added as a number
				}
			}
		}
		double tax = TAX_RATE * totCost;	// determines the tax
		double total = tax + totCost;		// determines the total after the tax
		System.out.println("#items: " + count + " Subtotal: $" + String.format("%.2f", totCost) + " Tax: $" + 
				String.format("%.2f", tax) + " TOTAL: $" + String.format("%.2f", total));
		return totCost;
	}

	/**
	 * prints the market menu with each index, item, and price
	 */
	public static void printMarketCatalog() {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Item id 	Description 	 Price");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		for (int i = 0; i < MARKET_ITEMS.length; i++) {			// iterates thru the market items
			System.out.println(i + "\t\t" + MARKET_ITEMS[i][0] + "    \t" + MARKET_ITEMS[i][1]); // prints each items index, its name, and its price
		}
		System.out.println("\n\n");
	}

	/**
	 * displays the contents that are currently in the cart
	 * 
	 * @param cart: users cart
	 * @param count: amount of items in the cart
	 * 
	 */
	public static void displayCartContent(String[] cart, int count) {
		System.out.print("Cart Content: ");
		for (int i = 0; i < count - 1; i++) {	// iterates through the cart
			if(cart[i] == null) {	// if it reaches a null element in the cart it breaks the loop
				break;
			}
			else {
				System.out.print(cart[i] + ", "); // otherwise it prints each value separated by a comma
			}
		}
		System.out.println(cart[count - 1] + ", ");
	}
	
	public static void main(String[] args) {
		System.out.println("=============   Welcome to the Shopping Cart App   =============\n");
		Scanner scnr = new Scanner(System.in);	// scans user input
		boolean check = true;	// sets check to true (used in while loop)
		int count = 0;	// sets count to 0 (no items in the cart at beginning
		String[] cart = new String[CART_CAPACITY];	// creates a cart
		
		while(check) { // continues to iterate while check is true
			System.out.println("\nCOMMAND MENU:");
			System.out.println(" [P] print the market catalog");
			System.out.println(" [A <index>] add one occurrence of an item to the cart given its identifier");
			System.out.println(" [C] checkout");
			System.out.println(" [D] display the cart content");
			System.out.println(" [O <index>] number of occurrences of an item in the cart given its identifier");
			System.out.println(" [R <index>] remove one occurrence of an item from the cart given its identifier");
			System.out.println(" [Q]uit the application\n");
			System.out.print("ENTER COMMAND: ");
			char inputChar = scnr.next().trim().charAt(0);	// scans the users character input
			if(inputChar == 'P' || inputChar == 'p') {	// if they type in P it prints the menu
				printMarketCatalog();
			}
			if(inputChar == 'A' || inputChar == 'a') {	// if they type A it gets the next number(index) 
				int index = scnr.nextInt();				// and adds that to the cart
				count = add(index, cart, count);
				continue;
			}
			if(inputChar == 'D' || inputChar == 'd') {	// if they type in D it displays what items are in the cart
				displayCartContent(cart, count);
			}
			if(inputChar == 'C' || inputChar == 'c') {	// if they type in C it checks out their items and 
				getSubTotalPrice(cart, count);			// gives them a sub total and a total after tax
			}
			if(inputChar == 'R' || inputChar == 'r') {	// if they type in R it gets the next index and removes
				int index = scnr.nextInt();				// the corresponding item from the cart
				String itemToRemove = getItemDescription(index);
				count = remove(itemToRemove, cart, count);
			}
			if(inputChar == 'O' || inputChar == 'o') {	// if they type in O it gets the next index and
				int index = scnr.nextInt();				// tells the user how many of that specific item they 
				occurrencesOf(index, cart, count);		// have in the cart
				continue;
			}
			if(inputChar == 'Q' || inputChar == 'q') {	// if they type in Q it shuts down the system
				check = false;
				System.out.println("=============  Thank you for using this App!!!!!  =============");
			}
		}
		
	}
}
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Exceptional Library Tests
// Files:           Book.java, Subscriber.java, Librarian.java, ExceptionalLibrary.java
// Course:          CS 300 Spring 2019
//
// Author:          Andrew Fix
// Email:           afix@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Gus Kalivas
// Partner Email:   wkalivas@wisc.edu
// Partner Lecturer's Name: Gary Dahl
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         (identify each person and describe their help in detail)
// Online Sources:  (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.text.ParseException;

public class ExceptionalBookLibraryTests {

    /**
     * Main method calls all the test methods in ExceptionBookLibrary tests
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(testLibraryParseCardBarCode());
        System.out.println(testLibraryParseRunLibrarianCheckoutBookCommand());
        System.out.println(testLibraryParseRunSubscriberReturnBookCommand());
        System.out.println(testparseBookId());
        System.out.println(testparseRunSubscriberCheckoutBookCommand());
    }

    /**
     * Checks whether Library Parse CardBarCode correctly throws a parse exception with an invalid
     * cardBarCode
     * 
     * @return - true or false with error message if ParseException was caught and the test passed
     */
    public static boolean testLibraryParseCardBarCode() {
        boolean test = false; // boolean test to return true or false
        try {
            // creates a new ExceptionalLibrary
            ExceptionalLibrary lib = new ExceptionalLibrary("Madison", "Gus", "abc");
            String code = "76543289333"; // invalid cardBarCode to check if a parseException will be
                                         // thrown
            int errorOffset = 0; // error offset value to pass as a parameter
            lib.parseCardBarCode(code, errorOffset);

        } catch (ParseException e) { // catches parseException if thrown by parseCardBarCode
            test = true; // test passes if exception caught
            System.out.println("Caught parse Exception: test passed");
        }
        return test;
    }

    /**
     * Checks if Library Parse Run Librarian checkout book correctly throws a parse exception if
     * invalid bookId or CardBarCode
     * 
     * @return - true or false with an error message if ParseExcpetion was caught and the test
     *         passed
     */
    public static boolean testLibraryParseRunLibrarianCheckoutBookCommand() {
        boolean test = false; // boolean test set to false because must throw a ParseException to be
                              // true
        try {
            // creates a new Exceptional Library
            ExceptionalLibrary lib = new ExceptionalLibrary("Madison", "Gus", "abc");
            String[] commands = new String[3]; // commands to pass throughout the parameters
            commands[1] = "201900000987761"; // invalid cardBarCode
            commands[2] = "1"; // bookId to correctly check if it parses the string
            lib.parseRunLibrarianCheckoutBookCommand(commands);
        } catch (ParseException e) {
            test = true; // if ParseException caught test is true and passed
            System.out.println("Caught parse exception: test passed");
        }
        return test;
    }

    /**
     * Checks if Library Parse run Subscriber return book correctly throws a parse exception if an
     * invalid bookId or catches an InstantiationException if a new subscriber cannot be created.
     * 
     * @return - true or false if with an error message if ParseException was caught and test was
     *         passed
     */
    public static boolean testLibraryParseRunSubscriberReturnBookCommand() {
        boolean test = false;// boolean test set to false because must throw a ParseException to be
                             // true
        try {
            // creates an new ExceptionalLibrary and a new Subscriber
            ExceptionalLibrary lib = new ExceptionalLibrary("Madison", "Gus", "abc");
            Subscriber scrib = new Subscriber("Gus", 1111, "Madison", "765432890");
            lib.addBook("Python", "Gary"); // adds a new book for bookID to be one
            String[] commands = new String[3]; // commands to be passed into Library parse run
                                               // Subscriber methods
            commands[1] = "a"; // invalid string bookId to be parsed by parseBookId
            lib.parseRunSubscriberCheckoutBookCommand(commands, scrib); // adds a book to check out
                                                                        // book so it can be
                                                                        // returned
            lib.parseRunSubscriberReturnBookCommand(commands, scrib); // attempts to remove a book
        } catch (ParseException e) {
            test = true; // test passes if catches a parseException
            System.out.println("Caught parse exception: Test passed");
        } catch (InstantiationException e) {
            test = false; // if subscriber can't be created catches InstantiationException
            System.out.println("Error: couldnt create new Subscriber");
        }
        return test;
    }

    /**
     * Checks if Parse bookId correctly throws a parse exception if an invalid bookId
     * 
     * @return - true or false if catches parseException with a print out statwment
     */
    public static boolean testparseBookId() {
        boolean test = false; // test set to false because must throw a ParseException to be
                              // true
        try {
            // creates a new ExceptionLibrary to check if parseException thrown from parse bookId
            ExceptionalLibrary lib = new ExceptionalLibrary("Madison", "Gus", "abc");
            String bookId = "1s"; // invalid bookId that should throw parseException
            int errorOffset = 1;
            lib.parseBookId(bookId, errorOffset);
        } catch (ParseException e) {
            test = true; // test passes if catches a parse Exception from invalid bookId
            System.out.println("Caught parse exception: test passed");
        }
        return test;
    }

    /**
     * Checks if Library Parse run Subscriber checkout book correctly throws a parse exception if an
     * invalid bookId or catches an InstantiationException if a new subscriber cannot be created.
     * 
     * @return - true or false if with an error message if ParseException was caught and test was
     *         passed
     * @return
     */
    public static boolean testparseRunSubscriberCheckoutBookCommand() {
        boolean test = false; // test set to false because must throw a ParseException to be
                              // true
        try {
            // creates a new ExceptionalLibary and subscriber to be able to test method
            ExceptionalLibrary lib = new ExceptionalLibrary("Madison", "gus", "abc");
            Subscriber scrib = new Subscriber("Gus", 1111, "Madison", "765432890");
            String[] commands = new String[2]; // checks number of elements of the commands
            commands[1] = "wordsnotBookid"; // invalid bookID
            lib.parseRunSubscriberCheckoutBookCommand(commands, scrib); // checks if throws a
                                                                        // parseException with
                                                                        // invalid bookID
        } catch (ParseException e) {
            test = true; // test is true if catches a parseException from invalid bookID
            System.out.println("Caught parseException: test passed");
        } catch (InstantiationException e) {
            test = false; // test fails if caught InstantiationException because it couldn't create
                          // a new subscriber
            System.out.println("Caught InstantiationExceptioon, couldnt create new subscriber");
        }
        return test;
    }
}

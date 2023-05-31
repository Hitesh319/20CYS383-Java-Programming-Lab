package com.ramaguru.amrita.cys.jpl.datastructures;

import java.util.HashMap;

/**
 * The HashMapExample class demonstrates the usage of HashMap to store and retrieve roll numbers and names.
 * It creates a HashMap to associate roll numbers with corresponding names.
 * This class provides a basic example of working with HashMap in Java.
 *
 * Usage:
 * - The program creates a HashMap called rollName to store roll numbers and names.
 * - It adds key-value pairs (roll numbers and names) to the HashMap using the put() method.
 * - It retrieves the value (name) associated with a specific key (roll number) using the get() method.
 * - It prints the retrieved name.
 *
 * Note: This example uses a HashMap of strings to represent roll numbers and names.
 *
 * Dependencies:
 * - None
 *
 * @author Ramaguru Radhakrishnan
 * @version 0.5
 */
public class HashMapExample {
    /**
     * The main method is the entry point of the program.
     * It demonstrates the usage of HashMap to store and retrieve roll numbers and names.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Create a HashMap object called rollName
        HashMap<Integer, Integer> Add = new HashMap<Integer, Integer>();

        Add.put(1, 10);
        Add.put(2, 20);
        Add.put(3, 30);
        Add.put(4, 40);
        Add.put(5, 50);

        // Print the HashMap
        System.out.println("HashMap: " + Add);

        // Add a new key-value pair
        Add.put(6, 60);
        System.out.println("Updated HashMap: " + Add);
    }
}

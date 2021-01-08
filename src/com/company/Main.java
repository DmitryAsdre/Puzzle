package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board b = new Board(array, 3);
        Board b2 = new Board(array, 3);
        System.out.println(b.toString());
        System.out.println(b.neighbors().toString().replaceAll("],", "]\n"));
        System.out.println(b.equals(b2));
    }
}

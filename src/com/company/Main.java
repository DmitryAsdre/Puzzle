package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int [][] array = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board b = new Board(array, 3);
        Board b2 = new Board(array, 3);
        System.out.println(b.toString());
        System.out.println(b.neighbors().toString().replaceAll("],", "]\n"));
        for(Board i : b.neighbors()){
            System.out.println(i.toString());
            System.out.println("Manhattan distance : " + i.manhattanDistance());
            System.out.println("Hamming distance : " + i.hammingDistance());
        }
    }
}

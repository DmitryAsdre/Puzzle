package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int [][] tiles = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board b = new Board(tiles, 3);
        Solver solver = new Solver(b);
        if(solver.isSolvable()){
            System.out.println("Solvable!");
        }else{
            System.out.println("Not solvable!");
        }
        Vector<Board> positions = solver.solve();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        for(Board p : positions)
            System.out.println(p.toString());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}

package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void createReportCSV(Board initial_position, String output_file, double ratio_from, double ratio_to, double ratio_step)
        throws IOException {
            Solver solver = new Solver(initial_position);
            if (!solver.isSolvable())
                return;
            BufferedWriter writer_tiles = new BufferedWriter(new FileWriter(output_file + "_tiles.txt"));
            writer_tiles.write(initial_position.toString());
            writer_tiles.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(output_file + ".csv"));
            writer.write("Ratio,#ofOperations\n");
            for (double ratio = ratio_from; ratio < ratio_to; ratio += ratio_step) {
                solver.solve(ratio, 1.0);
                writer.write(ratio + "," + solver.getOperations() + "\n");
                System.out.println(ratio + " " + solver.getOperations());
            }
            writer.close();
        }
     public static void createAllReports()
        throws IOException{
         int [][] tiles = {{1, 2, 3, 4}, {7, 5, 6, 8}, {9, 10, 11, 12}, {14, 15, 13, 0}};
         Board b1 = new Board(tiles, 4);


        }

    public static Pair<int[], Integer> countInvMerge(int[] tiles, int l, int r){
        int inversions = 0;
        int[] sorted = new int[r - l];
        if(r - l < 2){
            sorted[0] = tiles[l];
        }else{
            int mid = (r + l)/2;
            Pair<int[], Integer> left = countInvMerge(tiles, l, mid);
            Pair<int[], Integer> right = countInvMerge(tiles, mid, r);
            inversions += left.b;
            inversions += right.b;
            int counter_left = 0;
            int counter_right = 0;
            if(l == 1 && r == 3){
                System.out.println(left.b + " " + right.b);
                System.out.println(Arrays.toString(left.a)+ " " + Arrays.toString(right.a) + " ");
            }
            for(int i = 0; i < r - l; i++) {
                if(counter_left == mid - l || (counter_right != r - mid && left.a[counter_left] > right.a[counter_right])) {
                    if(l == 1 && r == 3)
                        System.out.println("I am here" + counter_left + " " + (mid- l));
                    sorted[i] = right.a[counter_right];
                    counter_right++;
                    if(counter_left != mid - l)
                        inversions += mid - l - counter_left;
                }else {
                    sorted[i] = left.a[counter_left];
                    counter_left++;
                }
            }
        }
        System.out.println("l " + l + " r "+ r + " Inversions : " + inversions);
        return new Pair<int[], Integer>(sorted, inversions);
    }
    public static int countInvDummy(int [][] tiles, int N){
        int inversions = 0;
        for(int i = 0; i < N*N; i++){
            for(int j = 0; j < N*N; j++) {
                int x1 = i % N;
                int y1 = i / N;
                int x2 = j % N;
                int y2 = j / N;
                if(tiles[y1][x1] == 0 || tiles[y2][x2] == 0)
                    continue;
                if(tiles[y1][x1] > tiles[y2][x2] && i < j)
                    inversions++;
            }
        }
        return inversions;
    }
    public static int countInv(int [][] tiles, int N){
        int [] tiles_flat = new int[N*N - 1];
        int zero = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(tiles[i][j] != 0)
                    tiles_flat[i*N + j - zero] = tiles[i][j];
                else
                    zero = 1;
            }
        }
        System.out.println(Arrays.toString(tiles_flat));
        Pair<int[], Integer> output = countInvMerge(tiles_flat, 0, N*N - 1);
        System.out.println(Arrays.toString(output.a));
        return output.b;
    }
    public static void main(String[] args){
        // write your code here
        //int [][] tiles = {{3, 9, 1, 15}, {14, 11, 4, 6}, {13, 0, 10, 12}, {2, 7, 8, 5}}; // 56
        //int [][] tiles = {{13, 2, 10, 3}, {1, 12, 8, 4}, {5, 0, 9, 6}, {15, 14, 11, 7}}; // 41
        int [][] tiles = {{3, 4, 1}, {0, 5, 2}, {6, 8, 7}};
        System.out.println(countInv(tiles, 3));
        System.out.println(countInvDummy(tiles, 3));
        /*
        solver.solve(1.0, 1.0);
        System.out.println(solver.getOperations());



         */
        /*if(solver.isSolvable()){
            System.out.println("Solvable!");
            Vector<Board> positions = solver.solve(1.4, 1.0);
            System.out.println("Operations " + solver.getOperations());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for(Board p : positions)
                System.out.println(p.toString());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }else{
            System.out.println("Not solvable!");
        }*/
    }
}

package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.*;

public class Main {
    public static void createReportCSV(Board initial_position, String output_file, double ratio_from, double ratio_to, double ratio_step)
        throws IOException {
            Solver solver = new Solver(initial_position);
            if (!solver.isSolvable())
                return;
            BufferedWriter writer_tiles = new BufferedWriter(new FileWriter("reports/" + output_file + "_tiles.txt"));
            writer_tiles.write(initial_position.toString());
            writer_tiles.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("reports/" + output_file + ".csv"));
            writer.write("Ratio,#ofOperations,ReachedMaxOperations\n");
            for (double ratio = ratio_from; ratio < ratio_to; ratio += ratio_step) {
                try {
                    solver.solve(ratio, 1.0, (int)4e6);
                    writer.write(ratio + "," + solver.getOperations() + ",0" + "\n");
                    System.out.println(ratio + " " + solver.getOperations());
                }catch (ReachedMaxOperationsException e){
                    writer.write(ratio + "," + 4e6 + ",1" + "\n");
                    System.out.println(ratio + " " + e.getMessage());
                }
            }
            writer.close();
        }
     public static void createAllReports()
        throws IOException{
         int [][] tiles = {{1, 2, 3, 4}, {7, 5, 6, 8}, {9, 10, 11, 12}, {14, 15, 13, 0}};  //test_1
         Board b1 = new Board(tiles, 4);
         createReportCSV(b1, "test_1", 0.2, 100, 0.1);
         tiles = new int[][]{{7, 6, 13, 10}, {8, 9, 0, 11}, {15, 2, 12, 5}, {3, 1, 14, 4}}; //test_2
         b1 = new Board(tiles, 4);
         createReportCSV(b1, "test_2", 0.2, 50, 0.1);
         tiles = new int[][]{{1, 8, 2}, {0, 4, 3}, {7, 6, 5}}; // test_3
         b1 = new Board(tiles, 3);
         createReportCSV(b1, "test_3", 0.2, 100, 0.1);
         tiles = new int[][]{{2, 10, 13, 3}, {1, 12, 8, 4}, {5, 0, 9, 6},{15, 14, 11, 7}}; //test_4
         b1 = new Board(tiles, 4);
         createReportCSV(b1, "test_4", 0.2, 100, 0.1);
        }
     public static int[][] genRandomTile(int N){
        Integer[] tile = new Integer[N*N];
        for(int i = 0; i < N*N; i++)
            tile[i] = i;
        List<Integer> int_list = Arrays.asList(tile);
        Collections.shuffle(int_list);
        int_list.toArray(tile);
        int [][] tiles = new int[N][N];
        for(int i = 0; i < N*N; i++){
            int x = i % N;
            int y = i / N;
            tiles[y][x] = tile[i];
        }
        return tiles;
     }

    public static void main(String[] args){
        //Графики завистимости колич. операций от ratio=a/b можно посмотреть в README.md. Лучше на гитхабе, а то в idea плохо рендерится.
        /*try {
            createAllReports();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }*/
        int N = 4;
        int [][] tile = genRandomTile(N);
        Board b = new Board(tile, N);
        Solver solver = new Solver(b);
        try{
            solver.solve(7.0, 1.0, (int)3e6);
            System.out.println(b.toString());
            System.out.println("Operations : " + solver.getOperations());
        }catch(RuntimeException e){
            System.out.println(b.toString());
            System.out.println(e.getMessage());
        }
    }
}


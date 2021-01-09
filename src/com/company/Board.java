package com.company;

import java.util.Arrays;
import java.util.Vector;

public class Board {
    private int [][] tiles;
    private int N;
    private Board previous;
    private int hamming_distance;
    private int manhattan_distance;
    private int moves;

    public Board(final int[][] t, int n){
        N = n;
        tiles = new int[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) {
                tiles[i][j] = t[i][j];
            }

        }
        hamming_distance = hammingDistance();
        manhattan_distance = manhattanDistance();
    }
    public int hammingDistance(){
        int hammingDist = 0;
        for(int i = 0; i < N*N - 1; i++){
            int y = i / N;
            int x = i % N;
            if(tiles[y][x] != i + 1)
                hammingDist += 1;
        }
        return hammingDist;
    }
    public int manhattanDistance(){
        int manhattanDist = 0;
        for(int i = 0; i < N*N; i++){
            int y = i / N;
            int x = i % N;
            if(tiles[y][x] != 0){
                int x_goal = (tiles[y][x] - 1) % N;
                int y_goal = (tiles[y][x] - 1) / N;
                manhattanDist += Math.abs(x - x_goal) + Math.abs(y - y_goal);
            }
        }
        return manhattanDist;
    }
    public boolean equals(final Board b){
        if(b.N != N)
            return false;
        return Arrays.deepEquals(tiles, b.tiles);
    }
    public Vector<Board> neighbors(){
        int x = 0, y = 0;
        Vector<Board> output = new Vector<Board>();
        for(int i = 0; i < N*N; i++){
            x = i % N;
            y = i / N;
            if(tiles[y][x] == 0)
                break;
        }
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(Math.abs(i) == Math.abs(j))
                    continue;
                int x_cur = x + i, y_cur = y + j;
                if(x_cur < 0 || x_cur >= N)
                    continue;
                if(y_cur < 0 || y_cur >= N)
                    continue;
                int [][] tmp = new int[N][N];
                for(int k = 0; k < N; k++) {
                    for (int s = 0; s < N; s++)
                        tmp[k][s] = tiles[k][s];
                }
                int buffer = tmp[y][x]; tmp[y][x] = tmp[y_cur][x_cur]; tmp[y_cur][x_cur] = buffer;
                output.add(new Board(tmp, N));
            }
        }
        return output;
    }
    public String toString(){
        String output = "N : " + N;
        output += "\n" + Arrays.deepToString(tiles).replace("], ", "]\n").replace("[[", "[").replace("]]", "]");

        return output;
    }

    public final int[][] getTitles(){return tiles;}
    public int getN(){return N;}
    public Board getPrevious(){return previous;}
    public void setPrevious(Board p){previous = p;}
    public void setMoves(int m){moves = m;}
    public int getMoves(){return moves;};
    public int getManhattanDistance(){return manhattan_distance;}
    public int getHammingDistance(){return hamming_distance;}
}

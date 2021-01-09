package com.company;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Collections;

public class Solver {
    private Board initial_position;
    private int operations = 0;
    public Solver(Board initial){initial_position = new Board(initial.getTitles(), initial.getN());}
    public final Board getInitialPosition(){return initial_position;}
    public int getOperations(){return operations;}
    private int countInv(int [][] tiles, int N){
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
    public boolean isSolvable(){
        int inversions = countInv(initial_position.getTitles(), initial_position.getN());
        System.out.println(inversions + " " + (initial_position.getN() -  initial_position.getY_void()));
        if(initial_position.getN() % 2 == 1){
            return inversions%2 == 0;
        }else{
            //System.out.println(initial_position.getX_void() - initial_position.getN() + 1 + " " + inversions);
            if((initial_position.getN() - initial_position.getY_void()) % 2 == 0)
                return inversions%2 == 1;
            else
                return inversions%2 == 0;
        }
    }
    public Vector<Board> solve(double a, double b){
        if(!isSolvable())
            throw new RuntimeException("not solvable");
        Vector<Board> positions = new Vector<Board>();
        PriorityQueue<Board> priority_queue = new PriorityQueue<Board>(new BoardComparator(a, b));
        priority_queue.add(initial_position);
        Board tmp;
        operations = 0;
        do{
            operations += 1;
            tmp = priority_queue.poll();
            //System.out.println(tmp.getHammingDistance());
            for(Board neighbor : tmp.neighbors()){
                neighbor.setMoves(tmp.getMoves() + 1);
                neighbor.setPrevious(tmp);
                priority_queue.add(neighbor);
            }
        }while(tmp.getHammingDistance() != 0);
        while(tmp != null){
            positions.add(tmp);
            tmp = tmp.getPrevious();
        }
        Collections.reverse(positions);
        return positions;
    }
}

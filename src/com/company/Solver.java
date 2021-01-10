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
    private static int countInvDummy(int [][] tiles, int N){
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
    private static int countInv(int [][] tiles, int N){
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
        Pair<int[], Integer> output = countInvMerge(tiles_flat, 0, N*N - 1);
        return output.b;
    }
    private static Pair<int[], Integer> countInvMerge(int[] tiles, int l, int r){
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
            for(int i = 0; i < r - l; i++) {
                if(counter_left == mid - l || (counter_right != r - mid && left.a[counter_left] > right.a[counter_right])) {
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
        return new Pair<int[], Integer>(sorted, inversions);
    }
    public boolean isSolvable(){
        int inversions = countInv(initial_position.getTitles(), initial_position.getN());
        if(initial_position.getN() % 2 == 1){
            return inversions%2 == 0;
        }else{
            if((initial_position.getN() - initial_position.getY_void()) % 2 == 0)
                return inversions%2 == 1;
            else
                return inversions%2 == 0;
        }
    }
    public Vector<Board> solve(double a, double b, int max_operations){
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
            for(Board neighbor : tmp.neighbors()){
                neighbor.setMoves(tmp.getMoves() + 1);
                neighbor.setPrevious(tmp);
                priority_queue.add(neighbor);
            }
        }while(tmp.getHammingDistance() != 0 && max_operations > operations);
        if(tmp.getHammingDistance() != 0){
            throw new ReachedMaxOperationsException("reached max operations " + max_operations);
        }
        while(tmp != null){
            positions.add(tmp);
            tmp = tmp.getPrevious();
        }
        Collections.reverse(positions);
        return positions;
    }
}

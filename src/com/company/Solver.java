package com.company;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Collections;

public class Solver {
    private Board initial_position;
    public Solver(Board initial){initial_position = new Board(initial.getTitles(), initial.getN());}
    public final Board getInitialPosition(){return initial_position;}
    public boolean isSolvable(){
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        int zero = 0;
        for(int i = 0; i < initial_position.getN(); i++){
            for(int j = 0; j < initial_position.getN(); j++) {
                if(initial_position.getTitles()[i][j] == 0) {
                    zero = 1;
                    continue;
                }
                map.put(initial_position.getTitles()[i][j] - 1, i * initial_position.getN() + j - zero);
            }
        }
        int counter = 0;
        int dist = 0;
        for(Map.Entry<Integer, Integer> m : map.entrySet()){
            System.out.println(m.getValue() + " " + counter);
            dist += Math.abs(m.getValue() - counter);
            counter++;
        }
        //System.out.println(dist);
        return initial_position.getN() % 2 != (dist / 2) % 2;
    }
    public Vector<Board> solve(){
        Vector<Board> positions = new Vector<Board>();
        PriorityQueue<Board> priority_queue = new PriorityQueue<Board>(new BoardComparator(1.0, 1.0));
        initial_position.setPrevious(null);
        initial_position.setMoves(0);
        priority_queue.add(initial_position);
        Board tmp;
        do{
            tmp = priority_queue.poll();
            System.out.println(tmp.getHammingDistance());
            for(Board neighbor : tmp.neighbors()){
                neighbor.setMoves(tmp.getMoves() + 1);
                neighbor.setPrevious(tmp);
                priority_queue.add(neighbor);
            }
        }while(tmp.getHammingDistance() != 0);
        System.out.println(tmp.getHammingDistance());
        System.out.println(tmp.toString());
        while(tmp != null){
            positions.add(tmp);
            tmp = tmp.getPrevious();
        }
        Collections.reverse(positions);
        return positions;
    }
}

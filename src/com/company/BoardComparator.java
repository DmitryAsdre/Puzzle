package com.company;
import java.util.Comparator;

public class BoardComparator implements Comparator<Board>{
    private double a, b;
    public BoardComparator(double a, double b){this.a = a; this.b = b;}
    public void setA(int a) {this.a = a;}
    public void setB(int b) {this.b = b;}

    @Override
    public int compare(final Board b1, final Board b2){
        double priority1 = b1.getManhattanDistance()*a + b1.getMoves()*b;
        double priority2 = b2.getManhattanDistance()*a + b2.getMoves()*b;
        return priority1 < priority2 ? -1 : priority1 > priority2 ? +1 : 0;
    }
}

package com.sine_labs.cs.linalg;

public class CSMain {
    public static void main(String[] args) {
        double[][] m = {
                { 1, 2, 3},
                { 2, 3, 2}
        };
        LinSolver ls = new LinSolver(m);
        ls.solve();
        ls.printArray();
    }
}

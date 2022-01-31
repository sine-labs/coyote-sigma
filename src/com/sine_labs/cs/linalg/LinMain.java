package com.sine_labs.cs.linalg;

import java.util.Random;

public class LinMain {
    public static void main(String[] args) {
        Random rng = new Random();
        double[][] m = new double[5][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                m[i][j] = rng.nextInt(50);
            }
        }
        String[] v = {"a", "b", "c", "d", "e"};
        LinSolver ls = new LinSolver(m, v);
        ls.solve();
        System.out.println("result: ");
        ls.printArray();
        ls.verify();
    }
}

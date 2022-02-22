package com.sine_labs.cs.poly;

import java.util.Arrays;

public class PolyMain {
    public static void main(String[] args) {
        int[][] polys = new int[][] {
                {1, 2, 1},  // (x + 1)^2
                {8, -32, 34, -10}  // (2x - 5)(4x - 2)(x - 1)
        };
        for (int[] p : polys) {
            PolySolver solver = new PolySolver(p);
            System.out.println(Arrays.toString(solver.sols()));
        }
    }
}

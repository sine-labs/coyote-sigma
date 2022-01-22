package com.sine_labs.cs;

import com.sine_labs.cs.coyote_sigma.LinSolver;

public class CSMain {
    public static void main(String[] args) {
        double[][] m = { { 1, 2, 3},
                { 2, 3, 2}};
        LinSolver ls = new LinSolver(m);
        ls.solve();
        ls.printArray();
    }
}

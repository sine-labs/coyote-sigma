package com.sine_labs.cs.poly;

import java.util.ArrayList;
import java.util.Arrays;

public class PolySolver {
    private static final double EPSILON = 0.0001;

    private final int[] coeffs;
    private final ArrayList<Double> roots = new ArrayList<>();
    private final int deg;
    
    public PolySolver(int[] coeffs) {
        this.coeffs = coeffs;
        deg = coeffs.length - 1;
        solve();
    }

    // only called once in the constructor
    private void solve() {
        // base solutions for deg 1 & deg 2 solutions
        ArrayList<Integer> leading = factors(coeffs[0]);
        ArrayList<Integer> constant = factors(coeffs[deg]);
        for (int p : constant) {
            for (int q : leading) {
                double poss = (double) p / q;
                if (Math.abs(eval(poss)) <= EPSILON && !roots.contains(poss)) {
                    roots.add(poss);
                }
                if (Math.abs(eval(-poss)) <= EPSILON && !roots.contains(-poss)) {
                    roots.add(-poss);
                }
            }
        }
    }

    private static ArrayList<Integer> factors(int n) {
        ArrayList<Integer> factors = new ArrayList<>();
        n = Math.abs(n);
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                factors.add(i);
                if (i * i != n) {
                    factors.add(n / i);
                }
            }
        }
        return factors;
    }

    // ---------------------- PUBLIC METHODS ----------------------
    public double[] sols() {
        double[] sols = new double[roots.size()];
        for (int i = 0; i < roots.size(); i++) {
            sols[i] = roots.get(i);
        }
        return sols;
    }

    public double eval(double x) {
        double total = 0;
        for (int i = 0; i < coeffs.length; i++) {
            int pow = deg - i;
            total += coeffs[i] * Math.pow(x, pow);
        }
        return total;
    }
}

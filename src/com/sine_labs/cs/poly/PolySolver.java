package com.sine_labs.cs.poly;

import java.util.ArrayList;
import java.util.Arrays;

public class PolySolver {
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
        if (deg == 1) {
            roots.add((double) -coeffs[1] / coeffs[0]);
            return;
        } else if (deg == 2) {
            long a = coeffs[0];
            long b = coeffs[1];
            long c = coeffs[2];
            long discr = b * b - 4 * a * c;
            if (discr >= 0) {
                roots.add((-b + Math.sqrt(discr)) / (2 * a));
                if (discr != 0) {
                    roots.add((-b - Math.sqrt(discr)) / (2 * a));
                }
            }
            return;
        }

        
    }

    private static ArrayList<Integer> factors(int n) {
        ArrayList<Integer> factors = new ArrayList<>();
        for (int i = 2; i * i <= n; i++) {
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

    public long eval(long x) {
        long total = 0;
        for (int i = 0; i < coeffs.length; i++) {
            int pow = deg - i;
            total += coeffs[i] * Math.pow(x, pow);
        }
        return total;
    }
}

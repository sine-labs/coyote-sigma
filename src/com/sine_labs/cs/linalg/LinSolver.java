package com.sine_labs.cs.linalg;

public class LinSolver {
    // the matrix (in is original mat for verification purposes)
    private final double[][] in, m;
    private final boolean[] free;
    private final int[] num, lead;  // equation number, which column has the leading 1
    private final String[] var;  // "name" of each variable

    // TODO: constructor
    public LinSolver(double[][] m, String[] var) {
        this.m = m;
        in = m.clone();
        this.var = var;
        free = new boolean[m.length];
        num = new int[m.length];
        lead = new int[m.length];
        for (int i = 0; i < m.length; i++) {
            num[i] = i + 1;
            lead[i] = m.length;
        }
    }

    public boolean solve() {
        printArray();
        int nextNum = num.length + 1;
        int v = 0;  // the variable we're trying to create a pivot for
        for (int i = 0; i < m.length; i++) {
            // j: scan for a row with non-zero v coef
            int j = i;
            while (j < m.length && round(m[j][v]) == 0) j++;
            if (j == m.length) {  // if all zeros, v is free; increment v (at end of if else), same row
                free[v] = true;
                i--;
            } else {  // found nonzero v coef!
                switchRow(i, j);  // switch the rows to maintain echelon form
                lead[i] = v;  // variable v is the pivot for this row
                System.out.print(round(1 / m[i][v]) + " * " + num[i] + " --> ");
                mult(i, 1 / m[i][v]);  // make sure the leading entry is 1
                num[i] = nextNum++;
                print(i);
                for (j = 0; j < m.length; j++) {
                    if (m[j][v] != 0 && i != j) {
                        System.out.print(num[j] + " + " + round(-m[j][v]) + " * " + num[i] + " --> ");
                        add(j, i, -m[j][v]);  // remove v from all other equations (make coef 0)
                        num[j] = nextNum++;
                        boolean consistent = print(j);
                        if (!consistent) return false;
                    }
                }
                // printArray();  // for debugging
            }
            v++;
        }
        return true;  // has proper solution!
    }

    public boolean print(int r) {
        System.out.print(num[r] + ": ");
        boolean allZero = true;
        for (int i = 0; i < m.length; i++) {
            if (round(m[r][i]) != 0) {
                if (!allZero) System.out.print(" + ");
                allZero = false;
                System.out.print(round(m[r][i]) + var[i]);
            }
        }
        if (allZero) {
            System.out.println("0 = " + round(m[r][m.length]));
            System.out.println(" This system of equations cannot be solved");
            return false;
        }
        System.out.println(" = " + round(m[r][m.length]));
        return true;
    }

    public void printArray() {
        for (int i = 0; i < m.length; i++) {
            print(i);
        }
    }

    public double[] getCol(int c) {
        double[] col = new double[m.length];
        for (int i = 0; i < m.length; i++) col[i] = m[i][c];
        return col;
    }

    public double[] getCol() {
        return getCol(m[0].length - 1);
    }

    public double round(double d) {
        double r = (int)(d * 100 + 0.5);
        return r / 100;
    }

    public void switchRow(int a, int b) {
        if (a == b) return;
        double[] temp = m[a];
        m[a] = m[b];
        m[b] = temp;
    }

    public void mult(int r, double factor) {
        for (int i = 0; i < m[r].length; i++) m[r][i] = m[r][i] * factor;
    }

    // row a += factor * row b
    public void add(int a, int b, double factor) {
        for (int i = 0; i < m[a].length; i++)
            m[a][i] += m[b][i] * factor;
    }

    public void verify() {
        double[] val = new double[var.length + 1];
        for (int i = 0; i < m.length; i++) {
            if (free[i]) val[i] = 10;  // assign all free vars to random value
        }
        // find values for all other variables
        for (int r = m.length - 1; r >= 0; r--) {
            for (int c = lead[r] + 1; c < m.length; c++) {
                val[lead[r]] -= m[r][c] * val[c];
            }
            val[lead[r]] += m[r][m.length];
        }
        // plug and chug into original equations
        for (int r = 0; r < in.length; r++) {
            double sum = 0;
            for (int c = 0; c < m.length; c++) {
                sum += val[c] * in[r][c];
            }
            if (round(sum - in[r][m.length]) != 0) {
                System.out.println("I'm sorry, something went wrong with equation " + (r + 1) + ".\n" +
                        "check " + sum + " vs " + in[r][m.length]);
            }
        }
    }
}

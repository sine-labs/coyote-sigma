package com.sine_labs.cs.linalg;

import java.util.Scanner;

public class LinSolver {

    private double[][] m;  // the matrix
    private boolean[] free;
    private int[] num;  // equation number?
    private String[] var;  // "name" of each variable

    // TODO: constructor
    public LinSolver(double[][] m) {
        this.m = m;
        free = new boolean[m.length];
        num = new int[m.length];
        for (int i = 0; i < m.length; i++) {
            num[i] = i + 1;
        }

    }

    // TODO: input
    public void input() {
        Scanner sc = new Scanner(System.in);

        sc.close();
    }

    public boolean solve() {
        int nextNum = num.length + 1;
        int v = 0;
        for (int i = 0; i < m.length; i++) {
            int j = i;
            while (j < m.length && m[j][v] == 0) j++;
            if (j == m.length) {
                free[v] = true;
                i--;
            }
            else {
                switchRow(i, j);
                System.out.println("switch " + i + " " + j);
                printArray();
                mult(i, 1 / m[i][v]);
                for (j = 0; j < m.length; j++) {
                    if (m[j][v] != 0 && i != j) {
                        add(j, i, -m[j][v]);
                        System.out.print(num[j] + " + " + (-m[j][v]) + " * " + num[i] + " --> " + nextNum + ": ");
                        print(j);
                        num[j] = nextNum++;
                    }
                }
            }
            v++;
        }
        return true;
    }

    // TODO: print out an equation
    public void print(int r) {
        System.out.println();
    }
    public void printArray() {
        for (double[] r : m) {
            for (double i : r) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
    // TODO: rounding - to make things prettier
    public double round() {
        return 0.0;
    }

    public void switchRow(int a, int b) {
        if (a == b) return;
        double[] temp = m[a];
        m[a] = m[b];
        m[b] = temp;
    }

    // TODO: row operators
    public void mult(int r, double factor) {

    }
    // row a += factor * row b
    public void add(int a, int b, double factor) {

    }
}
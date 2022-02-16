package com.sine_labs.cs.calculus;

import java.util.Scanner;

public class CalcMain {

    public static void main(String[] args) {
        System.out.println("Please input your equation.");
        Scanner sc = new Scanner(System.in);
//        String in = sc.nextLine();
        Input in = new Input(sc.nextLine());

        Expression e = new Expression(in);
//        Expression e = new Term(in);
        System.out.println(e);
        System.out.println(e.getVal(1));

        System.out.println("Integral lower bound:");
        double a = sc.nextDouble(); sc.nextLine();

        System.out.println("Integral upper bound:");
        double b = sc.nextDouble(); sc.nextLine();

        System.out.println(integral(e, a, b));
        sc.close();
        System.exit(0);
    }

    public static double integral(Expression e, double a, double b) {
        // left riemann sum
        double sum = 0;
        for (double i = a; i < b; i += 0.1) sum += e.getVal(i) / 10;
        return sum;
    }
}

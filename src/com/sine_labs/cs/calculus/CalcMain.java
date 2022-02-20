package com.sine_labs.cs.calculus;

import java.util.Scanner;

public class CalcMain {

    public static void main(String[] args) {
        System.out.println("Please input your equation.");
        Scanner sc = new Scanner(System.in);
//        String in = sc.nextLine();

        // testing equations
        String line = sc.nextLine();
        line = line.replaceAll("--.+", "");
        Input in = new Input(line);
        Expression e = new Expression(in);
        String s = e.toString().replace(" ", "");
        line = line.replace(" ", "");
        line = "(" + line + ")";
        System.out.print(s);
        if (!s.equals(line)) System.out.println(" vs " + line + " -- bug");
        else System.out.println(" -- ok");

//        System.out.println("Integral lower bound:");
//        double a = sc.nextDouble(); sc.nextLine();
//
//        System.out.println("Integral upper bound:");
//        double b = sc.nextDouble(); sc.nextLine();
//
//        System.out.println(integral(e, a, b));
        sc.close();
        System.exit(0);
    }

    public static double integral(Expression e, double a, double b) {
        // left riemann sum
        double sum = 0;
        double d = (b - a) / 100;
        for (double i = a; i < b; i += d) sum += e.getVal(i) * d;
        return sum;
    }
}

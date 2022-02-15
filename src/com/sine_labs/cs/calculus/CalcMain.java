package com.sine_labs.cs.calculus;

import java.util.Scanner;

public class CalcMain {

    public static void main(String[] args) {
        System.out.println("Please input your equation.");
        Scanner sc = new Scanner(System.in);
//        String in = sc.nextLine();
        Input in = new Input(sc.nextLine());
        sc.close();

        Expression e = new Expression(in);
//        Expression e = new Term(in);
        System.out.println(e);
        System.out.println(e.getVal(1));
    }
}

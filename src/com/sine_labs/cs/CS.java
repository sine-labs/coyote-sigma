package com.sine_labs.cs;

import com.sine_labs.cs.ascii_img.AsciiArt;
import com.sine_labs.cs.ascii_img.Image;
import com.sine_labs.cs.util.Colors;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CS {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println(Colors.conv("COYOTE SIGMA", Colors.CYAN));

        String[] options = new String[] {"lin", "ascii", "poly", "exit"};
        while (true) {
            System.out.println("What would you like to do?");
            String res = getInput(options);
            if (res.equals("lin")) {
                double[][] mat = readEq();
            } else if (res.equals("ascii")) {
                System.out.print("Image file:");
                String file = sc.next();
                System.out.print("Scale: ");
                int scale = sc.nextInt();
                Image img = new Image(new File(file));
                AsciiArt art = new AsciiArt(img, scale);
                System.out.println(art);
            } else if (res.equals("poly")) {
                int[] coeff = readPoly();
            } else if (res.equals("exit")) {
                System.out.println(Colors.conv("Goodbye.", Colors.YELLOW));
                break;
            }
        }
    }

    private static double[][] readEq() {
        System.out.print("Number of equations (and variables): ");
        int eqNum = sc.nextInt();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        double[][] mat = new double[eqNum][eqNum + 1];
        System.out.println("Equation:");
        for (int e = 0; e < eqNum - 1; e++) {
            System.out.print("c_" + e + " * " + alphabet.charAt(e) + " + ");
        }
        System.out.print("c_" + (eqNum - 1) + " * " + alphabet.charAt(eqNum - 1));
        System.out.println(" = c_" + eqNum);

        System.out.println("Input your coefficients:");
        for (int e = 0; e < eqNum; e++) {
            for (int c = 0; c <= eqNum; c++) {
                mat[e][c] = sc.nextDouble();
            }
        }
        return mat;
    }

    private static int[] readPoly() {
        System.out.print("Degree of polynomial: ");
        int deg = sc.nextInt();
        System.out.println("Polynomial: ");
        for (int p = deg; p > 0; p--) {
            System.out.print("c_" + p + " * x^" + p + " + ");
        }
        System.out.println("c_0");

        System.out.println("Input the coefficients (must be integers):");
        int[] coeff = new int[deg + 1];
        for (int i = 0; i <= deg; i++) {
            coeff[i] = sc.nextInt();
        }
        return coeff;
    }

    private static String getInput(String[] valid) {
        String inp = sc.next();
        String err = "input needs to be one of these: " + Arrays.toString(valid);
        while (!contains(valid, inp)) {
            System.out.println(Colors.conv(err, Colors.RED));
            inp = sc.next();
        }
        return inp;
    }

    private static boolean contains(String[] arr, String s) {
        for (String str : arr) {
            if (str.equals(s)) {
                return true;
            }
        }
        return false;
    }
}

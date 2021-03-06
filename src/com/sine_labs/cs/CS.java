package com.sine_labs.cs;

import com.sine_labs.cs.ascii_img.AsciiArt;
import com.sine_labs.cs.ascii_img.Image;
import com.sine_labs.cs.calculus.Expression;
import com.sine_labs.cs.calculus.Input;
import com.sine_labs.cs.chem.Element;
import com.sine_labs.cs.linalg.LinSolver;
import com.sine_labs.cs.poly.PolySolver;
import com.sine_labs.cs.util.Colors;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CS {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println(Colors.conv("COYOTE SIGMA", Colors.CYAN));
        Element.loadClass();

        String[] options = new String[] {
                "lin", "ascii", "poly", "chem", "integ", "exit"
        };
        while (true) {
            System.out.println("What would you like to do?");
            String option = getInput(options);
            if (option.equals("lin")) {
                double[][] mat = readEq();
                String[] vars = new String[mat.length];
                for (int i = 0; i < vars.length; i++) {
                    vars[i] = "x" + i;
                }
                LinSolver ls = new LinSolver(mat, vars);
                boolean res = ls.solve();
                if (!res) {
                    System.out.println("No valid solution found.");
                } else {
                    double[] ans = ls.getCol(vars.length);
                    for (int i = 0; i < ans.length; i++) {
                        System.out.println(vars[i] + " = " + ans[i]);
                    }
                }
            } else if (option.equals("ascii")) {
                System.out.print("Image file: ");
                String file = sc.next();
                System.out.print("Scale: ");
                int scale = sc.nextInt();
                Image img = new Image(new File(file));
                AsciiArt art = new AsciiArt(img, scale);
                System.out.println(art);
            } else if (option.equals("poly")) {
                int[] coeffs = readPoly();
                double[] sols = new PolySolver(coeffs).sols();
                if (sols.length == 0) {
                    System.out.println("No Solutions :(");
                } else {
                    System.out.println("Solutions");
                    for (double s : sols) {
                        System.out.println(s);
                    }
                }
            } else if (option.equals("chem")) {
                System.out.print("Atomic number: ");
                int atomNum = sc.nextInt();
                Element e = Element.getElement(atomNum);
                System.out.println("-- ELEMENT INFO --");
                System.out.println(e.getName() + " - " + e.getAbbreviation());
                System.out.println("Atomic number: " + e.getAtomicNumber());
                System.out.println("Mass: " + e.getAtomicMassUnits());
                System.out.println("Electron configuration: " + e.getSubshellString());
            } else if (option.equals("integ")) {
                System.out.print("Please input expression: ");
                String exp = sc.next();
                Expression expr = new Expression(new Input(exp));
                System.out.print("Please input the bounds of integration: ");
                double from = sc.nextDouble();
                double to = sc.nextDouble();
                System.out.print("Input the number of divisions you want: ");
                int sliceNum = sc.nextInt();
                while (sliceNum < 1) {
                    System.out.println("Invalid division number.");
                    sliceNum = sc.nextInt();
                }

                double total = 0;
                double size = (to - from) / sliceNum;
                for (double i = from; i < to; i += size) {
                    total += expr.getVal(i) * size;
                }
                System.out.println("Approximate value: " + total);
            } else if (option.equals("exit")) {
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
        while (deg < 1) {
            System.out.print("Invalid degree. Please try again: ");
            deg = sc.nextInt();
        }

        System.out.println("Polynomial: ");
        for (int p = deg; p > 0; p--) {
            System.out.print("c_" + p + " * x^" + p + " + ");
        }
        System.out.println("c_0");

        System.out.println("Input the numbers from the leading coefficient to the constant term:");
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

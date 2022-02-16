package com.sine_labs.cs.calculus;

import java.util.ArrayList;

public class Expression {
    private Expression e1, e2;
    private int type;
    /* supported operations:
    0: +    1: -    2: *    3: /
    4: sin  5: cos  6: tan  7: log
    8: e^x
     */

    public Expression() {}


    public Expression(Input in) {
        if (checkEmpty(in)) return;
        char first = in.getFirst();
        type = -1;
        System.out.println("dbug in " + in);
        // checking for unary operators
        if (first == 's') type = 4;
        else if (first == 'c') type = 5;
        else if (first == 't') type = 6;
        else if (first == 'l') type = 7;
        else if (first == 'e') type = 8;
        if (type == 8) in.pop(2);  // remove "e^"
        else if (type != -1) in.pop(3);  // remove 'sin'
        else {
            if (first != '(' && first != 'x' && !((first - '0' < 10) && (first - '0' > 0))) {
                System.out.println("dbug 2 " + in);
                System.out.println("I'm sorry, this function is not supported by the free version. \n" +
                        "Please consider purchasing the premium version for full access to functions.");
                return;
            }
        }
        System.out.println("dbug 1 " + type + " :" + in);

        // scan in first expression
        first = in.getFirst();
        if (first == 'x' || (first - '0' < 10 && first - '0' > 0)) {
            System.out.println("T1");
            e1 = new Term(in);
        }
        else {
            System.out.println("E1");
            if (in.getFirst() == '(') in.pop();
            e1 = new Expression(in);
        }

        // scan in second expression
        if (type == -1) {  // must be a binary operator - note, three-term operators not supported
            if (checkEmpty(in)) return;

            char op = in.getFirst(); in.pop();
            if (op == '+') type = 0;
            else if (op == '-') type = 1;
            else if (op == '*') type = 2;
            else if (op == '/') type = 3;
            System.out.println("dbug 2 " + type + " :" + in);

            if (checkEmpty(in)) return;
            char second = in.getFirst();
            if (second == 'x' || (second - '0' < 10 && second - '0' > 0)) {
                System.out.println("T2");
                e2 = new Term(in);
            }
            else {
                System.out.println("E2");
                if (in.getFirst() == '(') in.pop();
                e2 = new Expression(in);
            }
        }
        if (!in.isEmpty() && in.getFirst() == ')') in.pop();
    }

    public double toDouble(Input in) {
        double d = 0;
        int dec = 0;
        if (checkEmpty(in)) return -1;
        int diff = in.getFirst() - '0';
        if ((diff < 0 || diff >= 10) && diff != -2) return 1;
        while (!in.isEmpty() && ((diff >= 0 && diff < 10) || diff == -2)) {
            in.pop();
            dec *= 10;
            if (diff == -2) dec = 10;
            else {
                d = (d * 10) + diff;
            }
            if (!in.isEmpty()) diff = in.getFirst() - '0';
        }
        if (dec != 0) d /= dec;
        return d;
    }

    public String toString() {
        String op = "";
        if (type == 0) op = " + ";
        else if (type == 1) op = " - ";
        else if (type == 2) op = " * ";
        else if (type == 3) op = " / ";
        else if (type == 4) op = "sin";
        else if (type == 5) op = "cos";
        else if (type == 6) op = "tan";
        else if (type == 7) op = "log";
        else if (type == 8) op = "e^";
        if (type < 4) return "( " + e1.toString() + op + e2.toString() + " )";
        else return op + e1.toString();
    }

    public double getVal(double x) {
        if (type == 0) { return e1.getVal(x) + e2.getVal(x); }
        else if (type == 1) { return e1.getVal(x) - e2.getVal(x); }
        else if (type == 2) { return e1.getVal(x) * e2.getVal(x); }
        else if (type == 3) { return e1.getVal(x) / e2.getVal(x); }
        else if (type == 4) { return Math.sin(e1.getVal(x)); }
        else if (type == 5) { return Math.cos(e1.getVal(x)); }
        else if (type == 6) { return Math.tan(e1.getVal(x)); }
        else if (type == 7) { return Math.log(e1.getVal(x)); }
        else if (type == 8) { return Math.exp(e1.getVal(x)); }
        else {
            System.out.println("DEAD_BEEF");
            return -1;
        }
    }

    public boolean checkEmpty(Input in) {
        if (in.isEmpty()) {
            System.out.println("DEAD_BEEF");
            return true;
        }
        return false;
    }

}

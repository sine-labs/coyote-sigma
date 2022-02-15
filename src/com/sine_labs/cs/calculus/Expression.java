package com.sine_labs.cs.calculus;

import java.util.ArrayList;

public class Expression {

    /* supported operations:
    0: +    1: -    2: *    3: /
    4: sin  5: cos  6: tan  7: log
    8: e^x
     */
    private Expression e1, e2;
    private int type;

    public Expression(Input in) {
        if (checkEmpty(in)) return;
        char first = in.getFirst();
        type = -1;
        if (first == '(') {
            System.out.println("A1");
            in.pop();
            e1 = new Expression(in);
        }
        else if (first == 'x' || (first - '0' < 10 && first - '0' > 0)) {
            System.out.println("B1");
            e1 = new Term(in);
        }
        else {
            // TODO: unary functions
            System.out.println("C1");
        }

        if (type == -1) {
            if (checkEmpty(in)) return;
            if (in.getFirst() == ' ') in.pop();  // remove whitespace

            if (checkEmpty(in)) return;
            char op = in.getFirst(); in.pop(2);
            if (op == '+') type = 0;
            else if (op == '-') type = 1;
            else if (op == '*') type = 2;
            else if (op == '/') type = 3;

            if (checkEmpty(in)) return;
            char second = in.getFirst();
            if (second == '(') {
                in.pop();
                e2 = new Expression(in);
            }
            else if (second == 'x' || (second - '0' < 10 && second - '0' > 0)) {
                e2 = new Term(in);
            }
        }
        if (!in.isEmpty() && in.getFirst() == ' ') in.pop();
        if (!in.isEmpty() && in.getFirst() == ')') in.pop();
    }

    public Expression() {}

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
        return "( " + e1.toString() + op + e2.toString() + " )";
    }

    public double getVal(double x) {
        if (type == 0) { return e1.getVal(x) + e2.getVal(x); }
        else if (type == 1) { return e1.getVal(x) - e2.getVal(x); }
        else if (type == 2) { return e1.getVal(x) * e2.getVal(x); }
        else if (type == 3) { return e1.getVal(x) / e2.getVal(x); }
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

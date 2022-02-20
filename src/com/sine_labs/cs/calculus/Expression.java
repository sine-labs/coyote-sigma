package com.sine_labs.cs.calculus;

import java.util.ArrayList;

public class Expression {
    private Expression e1, e2;
    private int type;
    private double coef;
    /* supported operations:
    0: sin  1: cos  2: tan  3: log  4: e^x
    5: +    6: -    7: *    8: /    9: ^
    -1: x   -2: constant    -3: single term in parentheses (x)
     */
    private char[] one = { 's', 'c', 't', 'l', 'e' };
    private char[] two = { '+', '-', '*', '/', '^' };
    private String[] oneString = { "sin", "cos", "tan", "log", "e^" };
    private String[] twoString = { " + ", " - ", " * ", " / " , " ^ "};

    public Expression() {}

    public Expression(Input in) {
        if (in.checkEmpty()) return;
        coef = in.toDouble();
        char first = in.getFirst();
        type = -10;
        dbug("in " + in);
        // checking for unary operators
        for (int i = 0; i < one.length; i++) {
            if (first == one[i]) type = i;
        }
        if (type == one.length - 1) in.pop(2);  // remove "e^"
        else if (type != -10) in.pop(3);  // remove 'sin'
        else {
            // TODO: check for illegal operations
//            if (first != '(' && first != 'x' && !((first - '0' < 10) && (first - '0' > 0))) {
//                System.out.println("dbug 2 " + in);
//                System.out.println("I'm sorry, this function is not supported by the free version. \n" +
//                        "Please consider purchasing the premium version for full access to functions.");
//                return;
//            }
        }
        dbug("1 " + type + " :" + in);

        // scan in first expression
        if (type >= 0) {  // if already determined as unary expression, find inner expression
            e1 = new Expression(in);
            return;
        }
        first = in.getFirst();
        if (first == 'x') {
            dbug("x1");
            type = -1;
            in.pop();
            return;
        }
        else if (first == '('){
            dbug("E1");
            in.pop();  // remove (
            e1 = new Expression(in);
        }
        else {
            dbug("const1");
            type = -2;
            return;
        }

        // scan in second expression
        if (in.checkEmpty()) return;
        if (type == -10 && in.getFirst() == ')') type = -3;
        else if (type == -10) {  // must be a binary operator - note, three-term operators not supported
            char op = in.getFirst(); in.pop();
            for (int i = 0; i < two.length; i++) {
                if (op == two[i]) type = one.length + i;
            }
            dbug("2 " + type + " :" + in);
            e2 = new Expression(in);
        }
        if (!in.isEmpty() && in.getFirst() == ')') in.pop();
    }

    public String toString() {
//        dbug(type + "");
        String s = (coef == 1 && type != -2)? "": ((int)coef + "");  // TODO: change coef back to double
        if (type >= one.length) s += "( " + e1.toString() + twoString[type - one.length]
                + e2.toString() + " )";
        else if (type >= 0) s += oneString[type] + e1.toString();
        else if (type == -1) s += "x";
        else if (type == -3) s = "( " + s + e1.toString() + " )";
        return s;
    }

    public double getVal(double x) {
        double ans = 0;
        if (type == 0) { ans = Math.sin(e1.getVal(x)); }
        else if (type == 1) { ans = Math.cos(e1.getVal(x)); }
        else if (type == 2) { ans = Math.tan(e1.getVal(x)); }
        else if (type == 3) { ans = Math.log(e1.getVal(x)); }
        else if (type == 4) { ans = Math.exp(e1.getVal(x)); }
        else if (type == 5) { ans = e1.getVal(x) + e2.getVal(x); }
        else if (type == 6) { ans = e1.getVal(x) - e2.getVal(x); }
        else if (type == 7) { ans = e1.getVal(x) * e2.getVal(x); }
        else if (type == 8) { ans = e1.getVal(x) / e2.getVal(x); }
        else if (type == 9) { ans = Math.pow(e1.getVal(x), e2.getVal(x)); }
        else if (type == -1) { ans = x; }
        else if (type == -2) { ans = 1; }
        else if (type == -3) { ans = e1.getVal(x); }
        else {
            System.out.println("DEAD_BEEF getVal");
            return -1;
        }
        return coef * ans;
    }

    public void dbug(String s) {  /*
        System.out.println(s);  // */
    }

}

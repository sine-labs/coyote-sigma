package com.sine_labs.cs.calculus;

import java.util.List;

public class Term extends Expression {
    private double coef, pow;

    public Term(Input in) {
        coef = in.toDouble();
        pow = 0;
        if (!in.isEmpty() && in.getFirst() == 'x') {
            in.pop();  // remove x
            if (!in.isEmpty() && in.getFirst() == '^') {
                in.pop();  // remove ^
                pow = in.toDouble();
            }
            else pow = 1;
        }
    }

    public String toString() {
        String s = "";
        if (coef != 1 || pow == 0) s += coef;
        if (pow != 0) s += "x";
        if (pow != 0 && pow != 1) s += "^" + pow;
        return s;
    }

    public double getVal(double x) {
        return coef * Math.pow(x, pow);
    }
}

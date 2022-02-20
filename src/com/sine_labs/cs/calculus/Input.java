package com.sine_labs.cs.calculus;

public class Input {  // container function for strings to make them mutable :\

    private String s;

    public Input(String s) {
        this.s = "(" + s + ")";
        removeSpaces();
    }

    public void removeSpaces() {
        s = s.replace(" ", "");
    }
    public void pop(int i) {
        if (s.length() < i) System.out.println("DEAD_BEEF");
        else s = s.substring(i);
    }
    public void pop() { pop(1); }

    public char getFirst() {
//        if (s.isEmpty()) return '#';
        return s.charAt(0);
    }
    public boolean isEmpty() { return s.isEmpty(); }
    public String toString() { return s; }

    public double toDouble() {
        double d = 0;
        int dec = 0;
        if (checkEmpty()) return -1;
        int diff = getFirst() - '0';
        if ((diff < 0 || diff >= 10) && diff != -2) return 1;
        while (!s.isEmpty() && ((diff >= 0 && diff < 10) || diff == -2)) {
            pop();
            dec *= 10;
            if (diff == -2) dec = 10;
            else {
                d = (d * 10) + diff;
            }
            if (!s.isEmpty()) diff = getFirst() - '0';
        }
        if (dec != 0) d /= dec;
        return d;
    }


    public boolean checkEmpty() {
        if (s.isEmpty()) {
            System.out.println("DEAD_BEEF checkEmpty");
            return true;
        }
        return false;
    }

}

package com.sine_labs.cs.calculus;

public class Input {  // container function for strings to make them mutable :\

    private String s;

    public Input(String s) {
        this.s = s;
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
}

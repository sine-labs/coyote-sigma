package com.sine_labs.cs.electron_orbitals;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

public class Element {
    // -- Setup for an Element array --
    private static Scanner fileReader;

    // -- Not apt to change much as elements get harder to find --
    private static final int elementCount = 118;

    // -- Loaded names --
    private static Element[] elements;

    public static void loadClass () throws FileNotFoundException {
        // -- Subshells needs to be loaded before this class --
        Subshell.loadClass();
        
        fileReader = new Scanner(new File("Elements.csv"));

        elements = new Element[elementCount];

        for (int i = 0; i < elementCount; ++i) {
            String name, abbreviation;

            String[] line = fileReader.nextLine().split(",");
            name = line[0];
            abbreviation = line[1];
            float atomicMassUnits = Float.parseFloat(line[2]);

            elements[i] = new Element(name, abbreviation, atomicMassUnits, i);
        }

        fileReader.close();
    }

    private String name; // -- Qualified name --
    private String abbreviation; // -- On the periodic table --
    private int atomicNumber; // -- How many protons in nucleus? --
    private float atomicMassUnits; // -- Relative mass of nucleus? --
    private String[] subshells; // -- Final result --

    // -- Constructor --
    public Element(String name, String abbreviation, float atomicMassUnits, int atomicNumber) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.atomicMassUnits = atomicMassUnits;
        this.atomicNumber = atomicNumber + 1;
        subshells = Subshell.getSubshellsString(this.atomicNumber);
    }

    // -- Getters --
    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public float getAtomicMassUnits () {
        return atomicMassUnits;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public String getSubshellString() {
        String result = "";

        for (String subshell : subshells) {
            result += subshell + ",";
        }

        // -- Cut out the last (extraneous) comma --
        return result.substring(0, result.length() - 1);
    }
}

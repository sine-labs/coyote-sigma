package com.sine_labs.cs.electron_orbitals;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.event.SwingPropertyChangeSupport;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 1s
 * 2s, 2p
 * 3s, 3p, 3d
 * 4s, 4p, 4d, 4f
 * 5s, 5p, 5d, 5f
 * 6s, 6p, 6d
 * 7s, 7p
 *
 * 1s 2
 *
 * 2s 4
 *
 * 2p 10
 * 3s 12
 *
 * 3p 18
 * 4s 20
 *
 * 3d 30
 * 4p 36
 * 5s 38
 *
 * 4d 48
 * 5p 54
 * 6s 56
 *
 * 4f 70
 * 5d 80
 * 6p 86
 * 7s 88
 *
 * 5f 102
 * 6d 112
 * 7p 118
 **/

public class Subshell {
    private static Scanner fileReader;

    private static char[] subshellTypeNames;
    private static final ArrayList<String[]> aufbauTable = new ArrayList<String[]>();
    private static final ArrayList<String> orderedSubshells = new ArrayList<String>();
    private static final ArrayList<Integer> subshellCapacities = new ArrayList<Integer>();
    private static final ArrayList<Integer> prefixSums = new ArrayList<Integer>();

    private static int row = 0,
            x = 0,
            y = 0;
    private static boolean isLastOfRow = true;

    private static final int deltaElectrons = 4;
    private static final int sElectrons = 2;

    static {
        try {
            // -- Load subshellTypeNames --
            fileReader = new Scanner(new File("Aufbaus.csv"));

            String[] subshellTypeNames = fileReader.nextLine().split(",");
            int subshellTypeNamesLength = subshellTypeNames.length;

            Subshell.subshellTypeNames = new char[subshellTypeNamesLength];

            for (int i = 0, len = subshellTypeNamesLength; i < len; ++i) {
                Subshell.subshellTypeNames[i] = subshellTypeNames[i].charAt(0);
            }

            fileReader.close();

            generateAufbauTable(7);
            updateOrderedSubshells();
            updatePrefixSumsAndSubshellCapacities();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateAufbauTable(int rows) {
        int aufbauTableSize = aufbauTable.size();

        while (aufbauTableSize < rows) {
            int rowSize = aufbauTableSize + 1;
            String[] row = new String[rowSize];

            for (int i = 0; i < rowSize; ++i) {
                row[i] = rowSize + "" + subshellTypeNames[i];
            }

            aufbauTable.add(row);

            ++aufbauTableSize;
        }
    }

    private static void updateOrderedSubshells() {
        int aufbauTableSize = aufbauTable.size();

        boolean reachedUngenerated = false;

        while (!reachedUngenerated) {
            orderedSubshells.add(aufbauTable.get(y++)[x--]);

            if (x < 0) {
                if (isLastOfRow) {
                    x = aufbauTable.get(y = ++row).length - 2;
                } else {
                    x = aufbauTable.get(y = row).length - 1;
                }

                isLastOfRow = !isLastOfRow;
            }

            if (y >= aufbauTableSize) {
                reachedUngenerated = true;
            }
        }
    }

    /**
     * Update both prefixSums AND subshellCapacities
     **/
    private static void updatePrefixSumsAndSubshellCapacities() {
        int prefixSumsSize = prefixSums.size(),
                orderedSubshellsSize = orderedSubshells.size();

        if (prefixSumsSize == 0) {
            subshellCapacities.add(sElectrons);

            prefixSums.add(0);
            prefixSums.add(sElectrons);

            prefixSumsSize += 2;
        }

        while (prefixSumsSize <= orderedSubshellsSize) {
            int previousSum = prefixSums.get(prefixSumsSize - 1),
                    subshellCapacity = getShellCapacity(orderedSubshells.get(prefixSumsSize - 1));

            prefixSums.add(previousSum + subshellCapacity);
            subshellCapacities.add(subshellCapacity);

            ++prefixSumsSize;
        }
    }

    private static int getShellCapacity(String subshell) {
        char subshellTypeName = subshell.charAt(subshell.length() - 1);
        int subshellTypeNameIndex = -1;
        while (subshellTypeNames[++subshellTypeNameIndex] != subshellTypeName)
            ;

        return (subshellTypeNameIndex * deltaElectrons) + sElectrons;
    }

    /**
     * Load more state
     **/
    private static void memoizeState() {
        generateAufbauTable(aufbauTable.size() + 1);
        updateOrderedSubshells();
        updatePrefixSumsAndSubshellCapacities();
    }

    /**
     * Return a (without exceptions) array of the
     * subshell filled capacities
     *
     * @param atomicNumber atomic number of the
     *                     element
     *
     * @return array of the subshell fill states
     *         for the element
     **/
    public static String[] getSubshellsString(int atomicNumber) {
        // -- Load more state if needed --
        int prefixSumsSize = prefixSums.size(),
                largestPrefixSums = prefixSums.get(prefixSumsSize - 1);

        while (largestPrefixSums < atomicNumber) {
            memoizeState();
            prefixSumsSize = prefixSums.size();
            largestPrefixSums = prefixSums.get(prefixSumsSize - 1);
        }

        // -- Binary search through prefixSums for last full subshell --
        int low = 0,
                high = prefixSumsSize,
                mid = (low + high) / 2;

        boolean keepSearching = true;

        while (keepSearching) {
            // -- Found answer, stop searching --
            if (low == mid && mid == high) {
                keepSearching = false;
            }

            int previousSum = prefixSums.get(mid),
                    nextSum = 0;

            // -- Found answer, stop --
            if (previousSum == atomicNumber) {
                keepSearching = false;
            }
            // -- nextSum becomes a useful thing to look at --
            else /* previousSum != atomicNumber */
            {
                nextSum = prefixSums.get(mid + 1);
            }

            // -- Divide search area in half --
            if (previousSum > atomicNumber) {
                high = mid;
            } else if (nextSum <= atomicNumber) {
                low = mid;
            }

            // -- Found answer, stop searching --
            else /* previousSum < atomicNumber && nextSum > atomicNumber */
            {
                keepSearching = false;
            }

            // -- Don't update mid if we've determined we don't need to --
            if (keepSearching) {
                mid = (low + high) / 2;
            }
        }

        int lastFilledPrefixSum = prefixSums.get(mid),
                resultLength = mid + 1;

        if (atomicNumber == lastFilledPrefixSum) {
            --resultLength;
        }

        String[] result = new String[resultLength];
        for (int i = 0; i < resultLength; ++i) {
            result[i] = orderedSubshells.get(i) + subshellCapacities.get(i);
        }

        int lastIndex = resultLength - 1;
        result[lastIndex] = orderedSubshells.get(lastIndex) + (atomicNumber - prefixSums.get(lastIndex));

        return result;
    }
}

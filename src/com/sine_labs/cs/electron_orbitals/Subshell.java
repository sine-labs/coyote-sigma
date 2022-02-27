package com.sine_labs.cs.electron_orbitals;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Subshell {
    private static Scanner fileReader;

    private static char[] subshellTypeNames; // -- s, p, d, f, etc --
    private static final ArrayList<String[]> aufbauTable = new ArrayList<String[]>(); // -- 2D triangular array --
    private static final ArrayList<String> orderedSubshells = new ArrayList<String>(); // -- Arranged aufbauTable based on aufbau's principle --
    private static final ArrayList<Integer> subshellCapacities = new ArrayList<Integer>(); // -- s → 2, p → 6, d → 10, etc --
    private static final ArrayList<Integer> prefixSums = new ArrayList<Integer>(); // -- Atomic number of largest element in subshell --

    // -- Look into aufbauTable to create orderedSubshells --
    private static int row = 0,
            x = 0,
            y = 0;
    private static boolean isLastOfRow = true;

    // -- Numeric constants --
    private static final int DELTA_ELECTRONS = 4;
    private static final int S_ELECTRONS = 2;

    public static void loadClass () throws FileNotFoundException {
        // -- Load subshellTypeNames --
        fileReader = new Scanner(new File("aufbaus.csv"));

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
    }

    /**
     * Generate rows up to the indicated amount
     * Does not regenerate rows (duh)
     *
     * @param rows final size of aufbauTable
     */
    private static void generateAufbauTable(int rows) {
        int aufbauTableSize = aufbauTable.size();

        // -- More rows need to be generated --
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

    /**
     * Using state (x, y, row, isLastOfRow),
     * order the subshells in aufbauTable in
     * sequence
     */
    private static void updateOrderedSubshells() {
        int aufbauTableSize = aufbauTable.size();

        // -- y has gone off bottom of aufbauTable without x going off left side --
        boolean reachedUngenerated = false;

        while (!reachedUngenerated) {
            // -- Update pointers such that next is down and to the right --
            orderedSubshells.add(aufbauTable.get(y++)[x--]);

            // -- Check if next is valid position --
            if (x < 0) {
                if (isLastOfRow) {
                    // -- Increase row as well --
                    x = aufbauTable.get(y = ++row).length - 2;
                } else {
                    x = aufbauTable.get(y = row).length - 1;
                }

                // -- Toggle value --
                isLastOfRow = !isLastOfRow;
            }

            // -- Gone off bottom without going off left edge --
            if (y >= aufbauTableSize) {
                reachedUngenerated = true;
            }
        }
    }

    /**
     * Update both prefixSums AND subshellCapacities
     * based on the values now in orderedSubshells
     **/
    private static void updatePrefixSumsAndSubshellCapacities() {
        int prefixSumsSize = prefixSums.size(),
            orderedSubshellsSize = orderedSubshells.size();

        // -- Initial populization of both when empty --
        if (prefixSumsSize == 0) {
            subshellCapacities.add(S_ELECTRONS);

            // -- PrefixSums.size() == subshellCapacities.size() + 1 --
            prefixSums.add(0);
            prefixSums.add(S_ELECTRONS);

            prefixSumsSize += 2;
        }

        while (prefixSumsSize <= orderedSubshellsSize) {
            int previousSum = prefixSums.get(prefixSumsSize - 1),
                subshellCapacity = getShellCapacity(orderedSubshells.get(prefixSumsSize - 1));

                subshellCapacities.add(subshellCapacity);
                prefixSums.add(previousSum + subshellCapacity);

            ++prefixSumsSize;
        }
    }

    /**
     * Given the subshell, return the number of elements
     * in this subshell (how many protons)
     *
     * @param subshell the subshell (1s, 4f, etc)
     * @return quantity of elements in subshell (2, 14, etc)
     */
    private static int getShellCapacity(String subshell) {
        char subshellTypeName = subshell.charAt(subshell.length() - 1);

        // -- Linear search (find the index of the given char) --
        int subshellTypeNameIndex = -1;
        while (subshellTypeNames[++subshellTypeNameIndex] != subshellTypeName);

        return (subshellTypeNameIndex * DELTA_ELECTRONS) + S_ELECTRONS;
    }

    // -- Load one more row of aufbauTable and corresponding others --
    private static void memoizeState() {
        generateAufbauTable(aufbauTable.size() + 1);
        updateOrderedSubshells();
        updatePrefixSumsAndSubshellCapacities();
    }

    /**
     * Return a (without exceptions) array
     * of the subshell filled capacities
     *
     * @param atomicNumber atomic number of the
     *                     element
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
            else /* previousSum != atomicNumber */ {
                nextSum = prefixSums.get(mid + 1);
            }

            // -- Divide search area in half --
            if (previousSum > atomicNumber) {
                high = mid;
            } else if (nextSum <= atomicNumber) {
                low = mid;
            }

            // -- Found answer, stop searching --
            else /* previousSum < atomicNumber && nextSum > atomicNumber */ {
                keepSearching = false;
            }

            // -- Don't update mid if we've determined we don't need to --
            if (keepSearching) {
                mid = (low + high) / 2;
            }
        }

        int lastFilledPrefixSum = prefixSums.get(mid),
            resultLength = mid + 1;

        // -- There's no need for a "potentially not full last subshell" space --
        if (atomicNumber == lastFilledPrefixSum) {
            --resultLength;
        }

        String[] result = new String[resultLength];

        // -- Fill the values for the completely filled subshells --
        for (int i = 0; i < resultLength; ++i) {
            result[i] = orderedSubshells.get(i) + subshellCapacities.get(i);
        }

        // -- Modify last index. For "potentially not full last --
        // -- subshell" scenarios (which is most of the time) --
        int lastIndex = resultLength - 1;
        result[lastIndex] = orderedSubshells.get(lastIndex) + (atomicNumber - prefixSums.get(lastIndex));

        return result;
    }
}

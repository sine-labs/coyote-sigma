package com.sine_labs.cs.electron_orbitals;

public class Orbital
{
    public static enum SubShell
    {
        s(2),
        p(6),
        d(10),
        f(14),
        g(18),
        h(22),
        i(26),
        j(30);

        private int electronCount;

        private SubShell (int electronCount)
        { this.electronCount = electronCount; }

        public int electronCount ()
        { return electronCount; }
    }

    public static final int principalLevels = 8;

    private static Orbital[][] orbitalLevels;

    static
    {
        orbitalLevels = new Orbital[principalLevels][];
        SubShell[] values = SubShell.values();

        for (int i = 0; i < principalLevels; ++i)
        {
            Orbital[] principalLevel = orbitalLevels[i] = new Orbital[i + 1];

            for (int j = 0; j < i + 1; ++j)
            {
                principalLevel[j] = new Orbital(i + 1, values[j]);
            }
        }
    }

    public static Orbital[][] getOrbitalLevels ()
    { return orbitalLevels; }

    private int principalLevel;
    private SubShell subshell;

    public Orbital (int principalLevel, SubShell subshell)
    {
        this.principalLevel = principalLevel;
        this.subshell = subshell;
    }

    public int getPrincipalLevel ()
    { return principalLevel; }

    public SubShell getSubShell ()
    { return subshell; }
}
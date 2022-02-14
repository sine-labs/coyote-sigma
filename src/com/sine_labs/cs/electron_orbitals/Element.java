package com.sine_labs.cs.electron_orbitals;

public class Element
{
    // -- Elements are perfectly encapsulated as an enum --
    public static enum Elements
    {
        // -- Elements, abbreviations, and atomic masses --
        Hydrogen("H", 1.008f),
        Helium("He", 4.003f),
        Lithium("Li", 6.941f),
        Beryllium("Be", 9.012f),
        Boron("B", 10.811f),
        Carbon("C", 12.011f),
        Nitrogen("N", 14.007f),
        Oxygen("O", 15.999f),
        Fluorine("F", 18.998f),
        Neon("Ne", 20.180f),
        Sodium("Na", 22.990f),
        Magnesium("Mg", 24.305f),
        Aluminum("Al", 26.982f),
        Silicon("Si", 28.086f),
        Phosphorus("P", 30.974f),
        Sulfur("S", 32.066f),
        Chlorine("Cl", 35.453f),
        Argon("Ar", 39.948f),
        Potassium("K", 39.098f),
        Calcium("Ca", 40.078f),
        Scandium("Sc", 44.956f),
        Titanium("Ti", 47.88f),
        Vanadium("V", 50.942f),
        Chromium("Cr", 51.996f),
        Manganese("Mn", 54.938f),
        Iron("Fe", 55.845f),
        Cobalt("Co", 58.933f),
        Nickel("Ni", 58.693f),
        Copper("Cu", 63.546f),
        Zinc("Zn", 65.38f),
        Gallium("Ga", 69.723f),
        Germanium("Ge", 72.631f),
        Arsenic("As", 74.922f),
        Selenium("Se", 78.971f),
        Bromine("Br", 79.904f),
        Krypton("Kr", 84.798f),
        Rubidium("Rb", 85.468f),
        Strontium("Sr", 87.62f),
        Yttrium("Y", 88.906f),
        Zirconium("Zr", 91.224f),
        Niobium("Nb", 92.906f),
        Molybdenum("Mo", 95.95f),
        Technetium("Tc", 98.907f),
        Ruthenium("Ru", 101.07f),
        Rhodium("Rh", 102.906f),
        Palladium("Pd", 106.42f),
        Silver("Ag", 107.868f),
        Cadmium("Cd", 112.414f),
        Indium("In", 114.818f),
        Tin("Sn", 118.711f),
        Antimony("Sb", 121.760f),
        Tellurium("Te", 127.6f),
        Iodine("I", 126.904f),
        Xenon("Xe", 131.294f),
        Cesium("Cs", 132.905f),
        Barium("Ba", 137.328f),
        Lanthanum("La", 138.905f),
        Cerium("Ce", 140.116f),
        Praseodymium("Pr", 140.908f),
        Neodymium("Nd", 144.243f),
        Promethium("Pm", 144.913f),
        Samarium("Sm", 150.36f),
        Europium("Eu", 151.964f),
        Gadolinium("Gd", 157.25f),
        Terbium("Tb", 158.925f),
        Dysprosium("Dy", 162.500f),
        Holmium("Ho", 164.930f),
        Erbium("Er", 167.259f),
        Thulium("Tm", 168.934f),
        Ytterbium("Yb", 173.055f),
        Lutetium("Lu", 174.967f),
        Hafnium("Hf", 178.49f),
        Tantalum("Ta", 180.948f),
        Tungsten("W", 183.85f),
        Rhenium("Re", 186.207f),
        Osmium("Os", 190.23f),
        Iridium("Ir", 192.22f),
        Platinum("Pt", 195.08f),
        Gold("Au", 196.967f),
        Mercury("Hg", 200.59f),
        Thallium("Tl", 204.383f),
        Lead("Pb", 207.2f),
        Bismuth("Bi", 208.980f),
        Polonium("Po", 208.982f),
        Astatine("At", 209.987f),
        Radon("Rn", 222.018f),
        Francium("Fr", 223.020f),
        Radium("Ra", 226.025f),
        Actinium("Ac", 227.028f),
        Thorium("Th", 232.038f),
        Protactinium("Pa", 231.036f),
        Uranium("U", 238.029f),
        Neptunium("Np", 237.048f),
        Plutonium("Pu", 244.064f),
        Americium("Am", 243.061f),
        Curium("Cm", 247.070f),
        Berkelium("Bk", 247.070f),
        Californium("Cf", 251.080f),
        Einsteinium("Es", 254f),
        Fermium("Fm", 257.095f),
        Mendelevium("Md", 258.1f),
        Nobelium("No", 259.101f),
        Lawrencium("Lr", 262f),
        Rutherfordium("Rf", 261f),
        Dubnium("Db", 262f),
        Seaborgium("Sg", 266f),
        Bohrium("Bh", 264f),
        Hassium("Hs", 269f),
        Meitnerium("Mt", 278f),
        Darmstadtium("Ds", 281f),
        Roentgenium("Rg", 280f),
        Copernicium("Cn", 285f),
        Nihonium("Nh", 286f),
        Flerovium("Fl", 289f),
        Moscovium("Mc", 289f),
        Livermorium("Lv", 293f),
        Tennessine("Ts", 294f),
        Oganesson("Og", 294f);

        // -- Constructor and data setup for enumeration --
        private String abbreviation;
        private float atomicMass;

        private Elements (String abbreviation, float atomicMass)
        {
            this.abbreviation = abbreviation;
            this.atomicMass = atomicMass;
        }

        // -- Getters --
        public String getAbbreviation ()
        { return abbreviation; }

        public float getAtomicMass ()
        { return atomicMass; }

        // -- Store Array of Enumeration and how many elements there are  --
        private static Elements[] values = Elements.values();
        private static int elementCount = values.length;

        // -- Static getters --
        public static int elementCount ()
        { return elementCount; }

        public static Elements atomicElement (int atomicNumber)
        {
            return values[atomicNumber - 1];
        }
    }

    /**
     * Number of elements covered up until each s level:
     * 1s                             2
     * 2s, 2p                         4
     * 3s, 3p, 3d                     12
     * 4s, 4p, 4d, 4f                 20
     * 5s, 5p, 5d, 5f, 5g             38
     * 6s, 6p, 6d, 6f, 6g, 6h         56
     * 7s, 7p, 7d, 7f, 7g, 7h, 7i     88
     * 8s, 8p, 8d, 8f, 8g, 8h, 8i, 8j 120
     **/
    private static Orbital[] AufbausOrbitals = new Orbital[] {
        new Orbital(1, Orbital.SubShell.s),
        new Orbital(2, Orbital.SubShell.s),
        new Orbital(2, Orbital.SubShell.p),
        new Orbital(3, Orbital.SubShell.s),
        new Orbital(3, Orbital.SubShell.p),
        new Orbital(4, Orbital.SubShell.s),
        new Orbital(3, Orbital.SubShell.d),
        new Orbital(4, Orbital.SubShell.p),
        new Orbital(5, Orbital.SubShell.s),
        new Orbital(4, Orbital.SubShell.d),
        new Orbital(5, Orbital.SubShell.p),
        new Orbital(6, Orbital.SubShell.s),
        new Orbital(4, Orbital.SubShell.f),
        new Orbital(5, Orbital.SubShell.d),
        new Orbital(6, Orbital.SubShell.p),
        new Orbital(7, Orbital.SubShell.s),
        new Orbital(5, Orbital.SubShell.f),
        new Orbital(6, Orbital.SubShell.d),
        new Orbital(7, Orbital.SubShell.p),
        new Orbital(8, Orbital.SubShell.s)
    };

    private Elements element;

    // -- According to Aufbau's Principle --
    private int orbitalIndex;

    // -- Constructors --
    public Element (int atomicNumber)
    {
        this.element = Elements.atomicElement(atomicNumber);
        
    }

    public Element (Elements element)
    {
        this.element = element;
    }

    public Elements getElement ()
    { return element; }
}
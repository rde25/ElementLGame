import java.io.*;
import java.util.ArrayList;

/** Class storing information about an element on the periodic table. Element objects are immutable.
 * @author Rohit De*/
class Element {
    /** Default lowercase version of element name. */
    private String name;
    private String abbreviation;
    private int atomicNumber;
    private double atomicMass;
    private int protons;
    private int neutrons;
    private int electrons;
    /** Column the element is in on the periodic table.
     * 8B columns are separated out into 8B, 9B, and 0B for clarity.
     * Most lanthanides and actinides have no columns on the periodic table, so column names created for them are "LA" (e.g. 1LA, 2LA, ... 14LA).
     * */
    private String column;

    /** Enumeration of the column to use for electron configuration.
     * The modern IUPAC method of naming groups, apart from the lanthanides and actinides.
     * Columns 1-18 have corresponding double values.
     * Most lanthanides and actinides have a double value between 2 and 3, ranging from 2.01 to 2.14 based on their "LA column". */
    private double columnEnumeration;
    private int period;
    /** Array of orbitals occupied by electrons, from lowest to highest energy. Accessible outside class as a String. */
    private ArrayList<String> electronConfig;
    /** Short form electron configuration of the element.
     * Uses noble gas notation, shortening the length of the configuration. */
    private String shortElectronConfig;
    /** The highest occupied orbital of the element, with the number of elements in said orbital. */
    private String highestOccupiedOrbital;
    /** The family of elements this element belongs to. Families include: alkali metals, alkaline earth metals,
     * transition metals, lanthanides, actinides, post-transition metals, metalloids, nonmetals, halogens, and noble gases. */
    private String elementFamily;
    // SOLID OR LIQUID OR GAS
    public static final String ALKALI_METAL = "ALKALI METAL";
    public static final String ALKALINE_EARTH_METAL = "ALKALINE EARTH METAL";
    public static final String TRANSITION_METAL = "TRANSITION METAL";
    public static final String LANTHANIDE = "LANTHANIDE";
    public static final String ACTINIDE = "ACTINIDE";
    public static final String POST_TRANSITION_METAL = "POST TRANSITION METAL";
    public static final String METALLOID = "METALLOID";
    public static final String REACTIVE_NONMETAL = "REACTIVE NONMETAL";
    public static final String HALOGEN = "HALOGEN";
    public static final String NOBLE_GAS = "NOBLE GAS";

    public static final String[] ELEMENT_FAMILIES = {ALKALI_METAL, ALKALINE_EARTH_METAL,
            TRANSITION_METAL, LANTHANIDE, ACTINIDE, POST_TRANSITION_METAL, METALLOID, REACTIVE_NONMETAL, HALOGEN,
            NOBLE_GAS};

    public static final String[] COLUMNS = {"1A", "2A", "1LA", "2LA", "3LA", "4LA", "5LA", "6LA", "7LA", "8LA", "9LA",
            "10LA", "11LA", "12LA", "13LA", "14LA", "3B", "4B", "5B", "6B", "7B", "8B", "9B", "0B", "1B", "2B", "3A",
            "4A", "5A", "6A", "7A", "8A"};

    private boolean isAlkaliMetal;
    private boolean isAlkalineEarthMetal;
    private boolean isTransitionMetal;
    private boolean isLanthanide;
    private boolean isActinide;
    private boolean isPostTransitionMetal;
    private boolean isMetalloid;
    private boolean isReactiveNonmetal;
    private boolean isHalogen;
    private boolean isNobleGas;

    private boolean isInnerTransitionMetal;


    private boolean isMetal;
    private boolean isNonmetal;

    /** Array with information read from file. Vital to set element properties. Inaccessible outside of class. */
    private final String[] informationArray;
    /** The atomic number of the element that was last in the previous row.
     * Useful in calculating columns and electron configuration.
     * For period 1 elements, this value is 0. */
    private int lastInPreviousRow;
    /** The number of elements on the periodic table.*/
    public static final int TOTAL_ELEMENT_COUNT = 118;




    /** Constructor using element's atomic number.
     * @param atomicNumber Atomic number of the element.
     * @exception IllegalArgumentException thrown if invalid atomic number provided. */
    public Element(int atomicNumber) throws IllegalArgumentException {
        if (atomicNumber > TOTAL_ELEMENT_COUNT || atomicNumber < 1) {
            throw new IllegalArgumentException("Invalid atomic number entered.");
        }
        this.atomicNumber = atomicNumber;
        informationArray = findInformationArray(Integer.toString(atomicNumber), 0);
        setValues();
    }

    /** Constructor using element name or abbreviation (is able to differentiate).
     * @param element Either the element's name or abbreviation. The constructor can differentiate between the two via length.
     * @exception IllegalArgumentException thrown if invalid abbreviation or name provided.*/
    public Element(String element) throws IllegalArgumentException {
        try {
            if (element.length() > 2) { // full element name search
                this.name = element.toLowerCase();
                informationArray = findInformationArray(name, 2); // can throw NullPointerException
            } else { // abbreviation search
                StringBuilder builder = new StringBuilder();
                builder.append(element.toUpperCase().charAt(0));
                if (element.length() > 1) builder.append(element.toLowerCase().charAt(1));
                this.abbreviation = builder.toString();
                informationArray = findInformationArray(element, 1); // can throw NullPointerException
            }
            setValues();
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Invalid element name or abbreviation entered.");
        }
    }

    /** Finds the information array for the element given the name, abbreviation, or atomic number.
     * Not accessible outside of class.
     * @param knownInput String of the element's name, abbreviation, or atomic number
     * @param index Position of the knownInput in the informationArray. 0 for atomic number, 1 for abbreviation, 2 for name.
     * @throws NullPointerException thrown if input (name or abbreviation) is invalid/not found.
     * @return array with information for the provided element. Provided element is determined from knownInput. */
    private String[] findInformationArray(String knownInput, int index) throws NullPointerException {
        String[] returnable = {};
        try (BufferedReader reader = new BufferedReader(new FileReader("src/elementlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(",");
                if (array[index].equalsIgnoreCase(knownInput)) {
                    returnable = array;
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.getMessage());
        }
        if (returnable.length == 0) {
            throw new NullPointerException("Element name or abbreviation not found.");
        } else return returnable;
    }

    /** Calculates and sets all the relevant values for elements. */
    private void setValues() {
        atomicNumber = Integer.parseInt(informationArray[0]);
        // abbreviation and name are set in case provided values are lowercase
        abbreviation = informationArray[1];
        name = informationArray[2].toLowerCase();
        if (atomicMass == 0) atomicMass = Double.parseDouble(informationArray[3]);
        protons = this.atomicNumber;
        electrons = this.protons;
        neutrons = (int) Math.round(this.atomicMass) - this.protons;
        // period will always be 1-7 for valid element. If the element is invalid an exception is thrown in the constructor
        period = findRow();
        lastInPreviousRow = findLastInRow(this.period-1);
        // column uses switch statement on this.period: since period is always 1-7, column is never null
        column = findColumn();
        // the null warning does not matter because this.column will never be null, as explained in comments above
        // assertion exists only to placate IntelliJ
        assert this.column != null;
        columnEnumeration = columnToDouble(this.column);
        electronConfig = findElectronConfiguration();
        highestOccupiedOrbital = this.electronConfig.get(electronConfig.size()-1);
        shortElectronConfig = findShortElectronConfiguration();
        elementFamily = findElementFamily();
        isAlkaliMetal = isFromFamily(ALKALI_METAL);
        isAlkalineEarthMetal = isFromFamily(ALKALINE_EARTH_METAL);
        isTransitionMetal = isFromFamily(TRANSITION_METAL);
        isLanthanide = isFromFamily(LANTHANIDE);
        isActinide = isFromFamily(ACTINIDE);
        isPostTransitionMetal = isFromFamily(POST_TRANSITION_METAL);
        isMetalloid = isFromFamily(METALLOID);
        isReactiveNonmetal = isFromFamily(REACTIVE_NONMETAL);
        isHalogen = isFromFamily(HALOGEN);
        isNobleGas = isFromFamily(NOBLE_GAS);

        isInnerTransitionMetal = isLanthanide || isActinide;

        isMetal = isAlkaliMetal || isAlkalineEarthMetal || isTransitionMetal || isInnerTransitionMetal
                || isPostTransitionMetal;
        isNonmetal = isReactiveNonmetal || isHalogen || isNobleGas;
    }

    /** @return period element is found in. Will always return 1-7 because it is called only for valid elements. */
    private int findRow() {
        if (this.atomicNumber <= 2) return 1;
        else if (this.atomicNumber <= 10) return 2;
        else if (this.atomicNumber <= 18) return 3;
        else if (this.atomicNumber <= 36) return 4;
        else if (this.atomicNumber <= 54) return 5;
        else if (this.atomicNumber <= 86) return 6;
        else if (this.atomicNumber <= 118) return 7;
        // never returns 0 because the numbers provided in Element will always be valid
        else return 0;
    }

    /** @param rowNumber The row whose last element is searched for.
     * @return atomic number of the last element in the row, or 0 (if it does not exist: H & He). */
    private int findLastInRow(int rowNumber) {
        return switch (rowNumber) {
            case 1 -> 2;
            case 2 -> 10;
            case 3 -> 18;
            case 4 -> 36;
            case 5 -> 54;
            case 6 -> 86;
            case 7 -> 118;
            // hydrogen & helium
            default -> 0;
        };
    }

    /** Finds the column number for a B column element. Helper function for findColumn().
     * 8B column is split into 8B, 9B, and 0B.
     * @return number for the B column (3B-9B, 0B, 1B, 2B) */
    private int findBColumnNumber() {
        int columnNumber = this.atomicNumber-lastInPreviousRow;
        if (this.period == 6 || this.period == 7) columnNumber -= 14;
        if (columnNumber >= 10)
            columnNumber -= 10;
        return columnNumber;
    }

    /** Finds the column of an element. 8B is separated into 8B, 9B, and 0B.
     * Most lanthanides and actinides get custom columns, ranging from 1LA to 14 LA.
     * @return column of the element. Will never return null because method uses the period, which will always be 1-7 for a valid element. */
    private String findColumn() {
        // efficiency?
        switch (this.period) {
            case 1:
                if (this.atomicNumber == 1) return "1A";
                else return "8A";
            case 2:
            case 3:
                return String.format("%dA",(this.atomicNumber-lastInPreviousRow));
            case 4:
            case 5:
                // first A columns
                if (this.atomicNumber <= (lastInPreviousRow+2)) {
                    return String.format("%dA", this.atomicNumber - lastInPreviousRow);
                    // B columns
                } else if (this.atomicNumber <= (lastInPreviousRow+2+10)) {
                    int columnNumber = findBColumnNumber();
                    return String.format("%dB", (columnNumber));
                    // second A columns
                } else {
                    return String.format("%dA", (this.atomicNumber-(lastInPreviousRow+10)));
                }
            case 6:
            case 7:
                // first A columns
                if (this.atomicNumber <= (lastInPreviousRow+2)) {
                    return String.format("%dA", this.atomicNumber - lastInPreviousRow);
                    // LA columns
                } else if (this.atomicNumber <= (lastInPreviousRow+2+14)) {
                    return String.format("%dLA", this.atomicNumber - (lastInPreviousRow+2));
                    // B columns
                } else if (this.atomicNumber <= (lastInPreviousRow+2+14+10)) {
                    int columnNumber = findBColumnNumber();
                    return String.format("%dB", (columnNumber));
                    // second A columns
                } else {
                    return String.format("%dA", (this.atomicNumber-(lastInPreviousRow+10+14)));
                }
        }
        // will never return null because this.period will always be 1-7
        return null;
    }

    /** Enumerates column for use in findElectronConfiguration().
     * Values between 2 and 3 are used for most lanthanides and actinides.
     * @param column the column the element is in
     * @return number the column the element is as a double. Lanthanide/actinide values are between 2.01 and 2.14, inclusive. */
    private double columnToDouble(String column) {
        if (column.equals("1A")) return 1;
        else if (column.equals("2A")) return 2;
        // Lanthanides and actinides get decimal values because they don't fit into the traditional periodic table column
        else if (column.contains("LA")) {
            StringBuilder fColumnNumber = new StringBuilder();
            for (int i = 0; i < column.length(); i++) {
                if (Character.isDigit(column.charAt(i))) {
                    fColumnNumber.append(column.charAt(i));
                }
            }
            double d = Double.parseDouble(fColumnNumber.toString());
            return 2+d/100;
        }
        else if (column.contains("B")) {
            int bColumnNumber = Integer.parseInt(column.substring(0,1));
            // 0B, 1B, 2B -> 10, 11, 12
            if (bColumnNumber <= 2) bColumnNumber += 10;
            return bColumnNumber;
            // A columns 3A-8A
        } else if (column.contains("A")) {
            int aColumnNumber = Integer.parseInt(column.substring(0,1));
            return aColumnNumber+10;
        }
        return -1;
    }

    /** Generates the electron configuration as a list of filled orbitals.
     * @return array containing occupied electron orbitals, ranging from lowest to highest energy level. */
    private ArrayList<String> findElectronConfiguration() {
        ArrayList<String> list = new ArrayList<>();
        for (int row = 1; row <= this.period; row++) {
            // S logic
            if (columnEnumeration >= 2){
                list.add(String.format("%ds2", row));
            } else {
                list.add(String.format("%ds1", row));
            }
            // F logic
            if (row >= 6) {
                if (columnEnumeration >= 3 || this.period > row) {
                    list.add(String.format("%df14", row - 2));
                } else if (columnEnumeration > 2) {
                    // double round-off error requires rounding
                    int columnNumberLA = (int) Math.round((columnEnumeration - 2) * 100);
                    list.add(String.format("%df%d", row - 2, columnNumberLA));
                }
            }
            // D logic
            if (row >= 4) {
                if (columnEnumeration > 12 || this.period > row) {
                    list.add(String.format("%dd10", row-1));
                } else if (columnEnumeration >= 3){
                    list.add(String.format("%dd%d", row - 1, (int) columnEnumeration-2));
                }
            }
            // P logic
            if (row >= 2) {
                if (this.period > row) {
                    list.add(String.format("%dp6", row));
                } else if (columnEnumeration >= 13) {
                    list.add(String.format("%dp%d", row, (int) columnEnumeration - 12));
                }
            }
        }
        return list;
    }
    /** Generates a shorter electron configuration using noble gas notation.
     * @return String containing the noble gas placeholder and highest energy level. */
    private String findShortElectronConfiguration() {
        StringBuilder builder = new StringBuilder();
        int electronsSoFar = 0;
        builder.append(highestOccupiedOrbital);
        electronsSoFar += Integer.parseInt(highestOccupiedOrbital.substring(2));
        for (int i = electronConfig.size() - 2; i >= 0; i--) {
            String thisOrbital = electronConfig.get(i);
            builder.insert(0, String.format("%s ", thisOrbital));
            electronsSoFar += Integer.parseInt(thisOrbital.substring(2));
            if (electronsSoFar == atomicNumber-lastInPreviousRow) break;
        }
        if (lastInPreviousRow > 0) {
            Element lastElInPreviousRow = new Element(this.lastInPreviousRow);
            builder.insert(0,String.format("[%s] ", lastElInPreviousRow.getAbbreviation()));
        }
        return builder.toString();
    }

    /** @return  the family this element belongs to. */
    private String findElementFamily() {
        if (columnEnumeration == 18) return NOBLE_GAS;
        else if (columnEnumeration == 17) return HALOGEN;
        else if (columnEnumeration == 1 && atomicNumber != 1) return ALKALI_METAL;
        else if (columnEnumeration == 2) return ALKALINE_EARTH_METAL;
        // middle 10 columns, except for lanthanide Lutetium & actinide Lawrencium
        else if (columnEnumeration >= 3 && columnEnumeration <= 12 && !(columnEnumeration == 3 && period >= 6)) {
            return TRANSITION_METAL;
        } else if (columnEnumeration > 2 && columnEnumeration <= 3) {
            if (period == 6) return LANTHANIDE;
            else if (period == 7) return ACTINIDE;
        // figuring out what is under the metalloid staircase
        } else if ((columnEnumeration == 13 && period >= 3) || (columnEnumeration == 14 && period >= 5) ||
                (columnEnumeration == 15 && period >= 6) || (columnEnumeration == 16 && period >= 6)) {
            return POST_TRANSITION_METAL;
        // hydrogen + figuring out what is above the metalloid staircase
        } else if ((atomicNumber == 1) || (columnEnumeration == 14 && period == 2) ||
                (columnEnumeration == 15 && period <= 3) || (columnEnumeration == 16 && period <= 4)) {
            return REACTIVE_NONMETAL;
        // figuring out the metalloid staircase
        }  else if ((columnEnumeration == 13 && period == 2) || (columnEnumeration == 14 && (period == 3 || period == 4))
                || (columnEnumeration == 15 && (period == 4 || period == 5))
                || (columnEnumeration == 16 && period == 5)) {
            return METALLOID;
        }
        return null;
    }

    /** Checks whether an element is part of a certain family.
     * @param familyName the family being checked for
     * @return true if element is part of the family, false if not*/
    private boolean isFromFamily(String familyName) {
        return (this.elementFamily.equals(familyName));
    }

    /**@return name of the element */
    public String getName() {
        return this.name;
    }

    /** @return abbreviation of the element*/
    public String getAbbreviation() {
        return this.abbreviation;
    }
    /** @return atomic number of the element*/
    public int getAtomicNumber() {
        return this.atomicNumber;
    }
    /** @return atomic mass of the element*/
    public double getAtomicMass() {
        return this.atomicMass;
    }
    /** @return number of protons in the element*/
    public int getProtons() {
        return protons;
    }
    /** @return number of neutrons in the element*/
    public int getNeutrons() {
        return neutrons;
    }
    /** @return number of electrons in a stable element*/
    public int getElectrons() {
        return electrons;
    }

    /** @return the row/period of the element on the periodic table*/
    public int getPeriod() {
        return this.period;
    }
    /** @return the column of the element on the periodic table (old notation, 9B, 0B, custom LA columns) */
    public String getColumn() {
        return this.column;
    }
    /** @return the enumeration of the column as a double. The current column system, apart from the lanthanides and actinides*/
    public double getColumnEnumeration() {
        return this.columnEnumeration;
    }
    /** @return the highest occupied orbital of the element */
    public String getHighestOccupiedOrbital() {
        return highestOccupiedOrbital;
    }
    /** @return the short form of the electron configuration, using noble gas notation */
    public String getShortElectronConfig() {
        return shortElectronConfig;
    }

    /** @return the electron configuration as a String, with orbitals of the same orbital linked by dashes. */
    public String getElectronConfig() {
        StringBuilder builder = new StringBuilder();
        for (String str: this.electronConfig) {
            builder.append(String.format("%s%s", str, (str.contains("p") || str.equals("1s2")) ? " " : "-"));
        }
        if (builder.charAt(builder.length()-1) == '-') {
            builder.delete(builder.length()-1, builder.length());
        }
        return builder.toString().trim();
    }
    /** @return the family the element belongs to. */
    public String getElementFamily() {
        return elementFamily;
    }

    /** Compares whether two elements are the same.
     * @param otherElement element being compared to
     * @return true if the atomic numbers are the same */
    public boolean equals(Element otherElement) {
        return this.atomicNumber == otherElement.atomicNumber;
    }

    // method could be useful, but was not implemented
    /** Compares elements in regards to periodic table
     * @param otherElement element being compared to
     * @return positive integer if more protons than other element, negative integer if less, 0 if the same number*/
    public int compareTo(Element otherElement) {
        return this.atomicNumber-otherElement.atomicNumber;
    }

    /** @return element with first letter capitalized */ @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            if (i == 0) builder.append(Character.toUpperCase(name.charAt(i)));
            else builder.append(name.charAt(i));
        }
        return builder.toString();
    }

    // made these accessible if needed outside of class, but those methods are never called
    public boolean isAlkaliMetal() {
        return isAlkaliMetal;
    }
    public boolean isAlkalineEarthMetal() {
        return isAlkalineEarthMetal;
    }
    public boolean isTransitionMetal() {
        return isTransitionMetal;
    }
    public boolean isLanthanide() {
        return isLanthanide;
    }
    public boolean isActinide() {
        return isActinide;
    }
    public boolean isPostTransitionMetal() {
        return isPostTransitionMetal;
    }
    public boolean isReactiveNonmetal() {
        return isReactiveNonmetal;
    }
    public boolean isHalogen() {
        return isHalogen;
    }
    public boolean isNobleGas() {
        return isNobleGas;
    }
    public boolean isInnerTransitionMetal() {
        return isInnerTransitionMetal;
    }
    public boolean isMetalloid() {
        return isMetalloid;
    }
    public boolean isMetal() {
        return isMetal;
    }
    public boolean isNonmetal() {
        return isNonmetal;
    }
}
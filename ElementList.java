/** Class with constant elements from the periodic table and lists of different periodic table groups.
 * @author Rohit De*/
public class ElementList {
    // I did not type out all of these: printing and copy-paste were my best friends. The numbers were easy to change.
    public static final Element HYDROGEN = new Element(1);
    public static final Element HELIUM = new Element(2);
    public static final Element LITHIUM = new Element(3);
    public static final Element BERYLLIUM = new Element(4);
    public static final Element BORON = new Element(5);
    public static final Element CARBON = new Element(6);
    public static final Element NITROGEN = new Element(7);
    public static final Element OXYGEN = new Element(8);
    public static final Element FLUORINE = new Element(9);
    public static final Element NEON = new Element(10);
    public static final Element SODIUM = new Element(11);
    public static final Element MAGNESIUM = new Element(12);
    public static final Element ALUMINUM = new Element(13);
    public static final Element SILICON = new Element(14);
    public static final Element PHOSPHORUS = new Element(15);
    public static final Element SULFUR = new Element(16);
    public static final Element CHLORINE = new Element(17);
    public static final Element ARGON = new Element(18);
    public static final Element POTASSIUM = new Element(19);
    public static final Element CALCIUM = new Element(20);
    public static final Element SCANDIUM = new Element(21);
    public static final Element TITANIUM = new Element(22);
    public static final Element VANADIUM = new Element(23);
    public static final Element CHROMIUM = new Element(24);
    public static final Element MANGANESE = new Element(25);
    public static final Element IRON = new Element(26);
    public static final Element COBALT = new Element(27);
    public static final Element NICKEL = new Element(28);
    public static final Element COPPER = new Element(29);
    public static final Element ZINC = new Element(30);
    public static final Element GALLIUM = new Element(31);
    public static final Element GERMANIUM = new Element(32);
    public static final Element ARSENIC = new Element(33);
    public static final Element SELENIUM = new Element(34);
    public static final Element BROMINE = new Element(35);
    public static final Element KRYPTON = new Element(36);
    public static final Element RUBIDIUM = new Element(37);
    public static final Element STRONTIUM = new Element(38);
    public static final Element YTTRIUM = new Element(39);
    public static final Element ZIRCONIUM = new Element(40);
    public static final Element NIOBIUM = new Element(41);
    public static final Element MOLYBDENUM = new Element(42);
    public static final Element TECHNETIUM = new Element(43);
    public static final Element RUTHENIUM = new Element(44);
    public static final Element RHODIUM = new Element(45);
    public static final Element PALLADIUM = new Element(46);
    public static final Element SILVER = new Element(47);
    public static final Element CADMIUM = new Element(48);
    public static final Element INDIUM = new Element(49);
    public static final Element TIN = new Element(50);
    public static final Element ANTIMONY = new Element(51);
    public static final Element TELLURIUM = new Element(52);
    public static final Element IODINE = new Element(53);
    public static final Element XENON = new Element(54);
    public static final Element CESIUM = new Element(55);
    public static final Element BARIUM = new Element(56);
    public static final Element LANTHANUM = new Element(57);
    public static final Element CERIUM = new Element(58);
    public static final Element PRASEODYMIUM = new Element(59);
    public static final Element NEODYMIUM = new Element(60);
    public static final Element PROMETHIUM = new Element(61);
    public static final Element SAMARIUM = new Element(62);
    public static final Element EUROPIUM = new Element(63);
    public static final Element GADOLINIUM = new Element(64);
    public static final Element TERBIUM = new Element(65);
    public static final Element DYSPROSIUM = new Element(66);
    public static final Element HOLMIUM = new Element(67);
    public static final Element ERBIUM = new Element(68);
    public static final Element THULIUM = new Element(69);
    public static final Element YTTERBIUM = new Element(70);
    public static final Element LUTETIUM = new Element(71);
    public static final Element HAFNIUM = new Element(72);
    public static final Element TANTALUM = new Element(73);
    public static final Element TUNGSTEN = new Element(74);
    public static final Element RHENIUM = new Element(75);
    public static final Element OSMIUM = new Element(76);
    public static final Element IRIDIUM = new Element(77);
    public static final Element PLATINUM = new Element(78);
    public static final Element GOLD = new Element(79);
    public static final Element MERCURY = new Element(80);
    public static final Element THALLIUM = new Element(81);
    public static final Element LEAD = new Element(82);
    public static final Element BISMUTH = new Element(83);
    public static final Element POLONIUM = new Element(84);
    public static final Element ASTATINE = new Element(85);
    public static final Element RADON = new Element(86);
    public static final Element FRANCIUM = new Element(87);
    public static final Element RADIUM = new Element(88);
    public static final Element ACTINIUM = new Element(89);
    public static final Element THORIUM = new Element(90);
    public static final Element PROTACTINIUM = new Element(91);
    public static final Element URANIUM = new Element(92);
    public static final Element NEPTUNIUM = new Element(93);
    public static final Element PLUTONIUM = new Element(94);
    public static final Element AMERICIUM = new Element(95);
    public static final Element CURIUM = new Element(96);
    public static final Element BERKELIUM = new Element(97);
    public static final Element CALIFORNIUM = new Element(98);
    public static final Element EINSTEINIUM = new Element(99);
    public static final Element FERMIUM = new Element(100);
    public static final Element MENDELEVIUM = new Element(101);
    public static final Element NOBELIUM = new Element(102);
    public static final Element LAWRENCIUM = new Element(103);
    public static final Element RUTHERFORDIUM = new Element(104);
    public static final Element DUBNIUM = new Element(105);
    public static final Element SEABORGIUM = new Element(106);
    public static final Element BOHRIUM = new Element(107);
    public static final Element HASSIUM = new Element(108);
    public static final Element MEITNERIUM = new Element(109);
    public static final Element DARMSTADTIUM = new Element(110);
    public static final Element ROENTGENIUM = new Element(111);
    public static final Element COPERNICIUM = new Element(112);
    public static final Element NIHONIUM = new Element(113);
    public static final Element FLEROVIUM = new Element(114);
    public static final Element MOSCOVIUM = new Element(115);
    public static final Element LIVERMORIUM = new Element(116);
    public static final Element TENNESSINE = new Element(117);
    public static final Element OGANESSON = new Element(118);

    // System.out.print(Element e + ",") really helped
    // as did boolean is[family_name]() instance functions
    public static final int TOTAL_ELEMENT_COUNT = Element.TOTAL_ELEMENT_COUNT;
    public static final Element[] ALL_ELEMENTS = {HYDROGEN, HELIUM, LITHIUM, BERYLLIUM, BORON, CARBON, NITROGEN, OXYGEN,
            FLUORINE, NEON, SODIUM, MAGNESIUM, ALUMINUM, SILICON, PHOSPHORUS, SULFUR, CHLORINE, ARGON, POTASSIUM,
            CALCIUM, SCANDIUM, TITANIUM, VANADIUM, CHROMIUM, MANGANESE, IRON, COBALT, NICKEL, COPPER, ZINC, GALLIUM,
            GERMANIUM, ARSENIC, SELENIUM, BROMINE, KRYPTON, RUBIDIUM, STRONTIUM, YTTRIUM, ZIRCONIUM, NIOBIUM,
            MOLYBDENUM, TECHNETIUM, RUTHENIUM, RHODIUM, PALLADIUM, SILVER, CADMIUM, INDIUM, TIN, ANTIMONY, TELLURIUM,
            IODINE, XENON, CESIUM, BARIUM, LANTHANUM, CERIUM, PRASEODYMIUM, NEODYMIUM, PROMETHIUM, SAMARIUM, EUROPIUM,
            GADOLINIUM, TERBIUM, DYSPROSIUM, HOLMIUM, ERBIUM, THULIUM, YTTERBIUM, LUTETIUM, HAFNIUM, TANTALUM, TUNGSTEN,
            RHENIUM, OSMIUM, IRIDIUM, PLATINUM, GOLD, MERCURY, THALLIUM, LEAD, BISMUTH, POLONIUM, ASTATINE, RADON,
            FRANCIUM, RADIUM, ACTINIUM, THORIUM, PROTACTINIUM, URANIUM, NEPTUNIUM, PLUTONIUM, AMERICIUM, CURIUM,
            BERKELIUM, CALIFORNIUM, EINSTEINIUM, FERMIUM, MENDELEVIUM, NOBELIUM, LAWRENCIUM, RUTHERFORDIUM, DUBNIUM,
            SEABORGIUM, BOHRIUM, HASSIUM, MEITNERIUM, DARMSTADTIUM, ROENTGENIUM, COPERNICIUM, NIHONIUM, FLEROVIUM,
            MOSCOVIUM, LIVERMORIUM, TENNESSINE, OGANESSON};
    public static final Element[] ALKALI_METALS = {LITHIUM, SODIUM, POTASSIUM, RUBIDIUM, CESIUM, FRANCIUM};
    public static final Element[] ALKALINE_EARTH_METALS = {BERYLLIUM, MAGNESIUM, CALCIUM, STRONTIUM, BARIUM, RADIUM};
    public static final Element[] TRANSITION_METALS = {SCANDIUM, TITANIUM, VANADIUM, CHROMIUM, MANGANESE, IRON, COBALT,
            NICKEL, COPPER, ZINC, YTTRIUM, ZIRCONIUM, NIOBIUM, MOLYBDENUM, TECHNETIUM, RUTHENIUM, RHODIUM, PALLADIUM,
            SILVER, CADMIUM, HAFNIUM, TANTALUM, TUNGSTEN, RHENIUM, OSMIUM, IRIDIUM, PLATINUM, GOLD, MERCURY,
            RUTHERFORDIUM, DUBNIUM, SEABORGIUM, BOHRIUM, HASSIUM, MEITNERIUM, DARMSTADTIUM, ROENTGENIUM, COPERNICIUM};
    public static final Element[] LANTHANIDES = {LANTHANUM, CERIUM, PRASEODYMIUM, NEODYMIUM, PROMETHIUM, SAMARIUM,
            EUROPIUM, GADOLINIUM, TERBIUM, DYSPROSIUM, HOLMIUM, ERBIUM, THULIUM, YTTERBIUM};
    public static final Element[] ACTINIDES = {ACTINIUM, THORIUM, PROTACTINIUM, URANIUM, NEPTUNIUM, PLUTONIUM,
            AMERICIUM, CURIUM, BERKELIUM, CALIFORNIUM, EINSTEINIUM, FERMIUM, MENDELEVIUM, NOBELIUM, LAWRENCIUM};
    public static final Element[] POST_TRANSITION_METALS = {ALUMINUM, GALLIUM, INDIUM, TIN, THALLIUM, LEAD, BISMUTH,
            POLONIUM, NIHONIUM, FLEROVIUM, MOSCOVIUM, LIVERMORIUM};
    public static final Element[] METALLOIDS = {BORON, SILICON, GERMANIUM, ARSENIC, ANTIMONY, TELLURIUM};
    public static final Element[] REACTIVE_NONMETALS = {HYDROGEN, CARBON, NITROGEN, OXYGEN, PHOSPHORUS, SULFUR,
            SELENIUM};
    public static final Element[] HALOGENS = {FLUORINE, CHLORINE, BROMINE, IODINE, ASTATINE, TENNESSINE};
    public static final Element[] NOBLE_GASSES = {HELIUM, NEON, ARGON, KRYPTON, XENON, RADON, OGANESSON};

    public static final Element[] INNER_TRANSITION_METALS = {LANTHANUM, CERIUM, PRASEODYMIUM, NEODYMIUM, PROMETHIUM,
            SAMARIUM, EUROPIUM, GADOLINIUM, TERBIUM, DYSPROSIUM, HOLMIUM, ERBIUM, THULIUM, YTTERBIUM, LUTETIUM,
            ACTINIUM, THORIUM, PROTACTINIUM, URANIUM, NEPTUNIUM, PLUTONIUM, AMERICIUM, CURIUM, BERKELIUM, CALIFORNIUM,
            EINSTEINIUM, FERMIUM, MENDELEVIUM, NOBELIUM, LAWRENCIUM};

    public static final Element[] METALS = {LITHIUM, BERYLLIUM, SODIUM, MAGNESIUM, ALUMINUM, POTASSIUM, CALCIUM,
            SCANDIUM, TITANIUM, VANADIUM, CHROMIUM, MANGANESE, IRON, COBALT, NICKEL, COPPER, ZINC, GALLIUM, RUBIDIUM,
            STRONTIUM, YTTRIUM, ZIRCONIUM, NIOBIUM, MOLYBDENUM, TECHNETIUM, RUTHENIUM, RHODIUM, PALLADIUM, SILVER,
            CADMIUM, INDIUM, TIN, CESIUM, BARIUM, LANTHANUM, CERIUM, PRASEODYMIUM, NEODYMIUM, PROMETHIUM, SAMARIUM,
            EUROPIUM, GADOLINIUM, TERBIUM, DYSPROSIUM, HOLMIUM, ERBIUM, THULIUM, YTTERBIUM, LUTETIUM, HAFNIUM, TANTALUM,
            TUNGSTEN, RHENIUM, OSMIUM, IRIDIUM, PLATINUM, GOLD, MERCURY, THALLIUM, LEAD, BISMUTH, POLONIUM, FRANCIUM,
            RADIUM, ACTINIUM, THORIUM, PROTACTINIUM, URANIUM, NEPTUNIUM, PLUTONIUM, AMERICIUM, CURIUM, BERKELIUM,
            CALIFORNIUM, EINSTEINIUM, FERMIUM, MENDELEVIUM, NOBELIUM, LAWRENCIUM, RUTHERFORDIUM, DUBNIUM, SEABORGIUM,
            BOHRIUM, HASSIUM, MEITNERIUM, DARMSTADTIUM, ROENTGENIUM, COPERNICIUM, NIHONIUM, FLEROVIUM, MOSCOVIUM,
            LIVERMORIUM};
    public static final Element[] NONMETALS = {HYDROGEN, HELIUM, CARBON, NITROGEN, OXYGEN, FLUORINE, NEON, PHOSPHORUS,
            SULFUR, CHLORINE, ARGON, SELENIUM, BROMINE, KRYPTON, IODINE, XENON, ASTATINE, RADON, TENNESSINE, OGANESSON};

    public static final Element[] NO_LANTHANIDES_OR_ACTINIDES = {HYDROGEN, HELIUM, LITHIUM, BERYLLIUM, BORON, CARBON,
            NITROGEN, OXYGEN, FLUORINE, NEON, SODIUM, MAGNESIUM, ALUMINUM, SILICON, PHOSPHORUS, SULFUR, CHLORINE, ARGON,
            POTASSIUM, CALCIUM, SCANDIUM, TITANIUM, VANADIUM, CHROMIUM, MANGANESE, IRON, COBALT, NICKEL, COPPER, ZINC,
            GALLIUM, GERMANIUM, ARSENIC, SELENIUM, BROMINE, KRYPTON, RUBIDIUM, STRONTIUM, YTTRIUM, ZIRCONIUM, NIOBIUM,
            MOLYBDENUM, TECHNETIUM, RUTHENIUM, RHODIUM, PALLADIUM, SILVER, CADMIUM, INDIUM, TIN, ANTIMONY, TELLURIUM,
            IODINE, XENON, CESIUM, BARIUM, HAFNIUM, TANTALUM, TUNGSTEN, RHENIUM, OSMIUM, IRIDIUM, PLATINUM, GOLD,
            MERCURY, THALLIUM, LEAD, BISMUTH, POLONIUM, ASTATINE, RADON, FRANCIUM, RADIUM, RUTHERFORDIUM, DUBNIUM,
            SEABORGIUM, BOHRIUM, HASSIUM, MEITNERIUM, DARMSTADTIUM, ROENTGENIUM, COPERNICIUM, NIHONIUM, FLEROVIUM,
            MOSCOVIUM, LIVERMORIUM, TENNESSINE, OGANESSON};
}

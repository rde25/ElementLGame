import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** @author Rohit De*/
class Main {
    /** The one Scanner meant for use in this program (to avoid any Scanner errors). */
    public static Scanner mainScanner = new Scanner(System.in);

    public static void main(String[] args) {
        // determines action
        actionLoop: while (true) {
          System.out.println("""
                  What would you like to do? Type in the corresponding integer.
                  [1] Play Element-L game
                  [2] Get information about elements of the periodic table
                  [-1] Quit""");
          String input = mainScanner.nextLine();
          try {
            int intInput = Integer.parseInt(input);
            if (intInput == 1) runElementLGame(mainScanner);
            else if (intInput == 2) runElementInformationGetter(mainScanner);
            else if (intInput == -1) break actionLoop;
            else continue;
          } catch (NumberFormatException ignored) {
              continue actionLoop;
          }
          System.out.println("\n\n");
        }
        mainScanner.close();
    }

    /** Runs the program that lets the user play the ElementL game.
     * @param scanner the one Scanner used by the program (to avoid Scanner errors) */
    public static void runElementLGame(Scanner scanner) {
        ElementLGame.displayOpeningMessage(scanner);
        int difficulty = ElementLGame.getDifficulty(scanner);
        boolean playingWithLanthanidesActinides = ElementLGame.playWithLanthanidesActinides(scanner);
        boolean playingAgain = true;
        ElementLGame game = null;
        ArrayList<Boolean> winStatuses = new ArrayList<>();
        while (playingAgain) {
            game = new ElementLGame(scanner, playingWithLanthanidesActinides, difficulty);
            winStatuses.add(game.getVictoryStatus());
            playingAgain = game.isPlayAgain();
        }
        int wins = 0;
        int losses = 0;
        if (winStatuses.size() > 0) {
            for (Boolean isWin: winStatuses) {
                if (isWin) wins++;
                else losses++;
            }
            System.out.printf("Difficulty Level: %s%nPlayer Record: %d-%d%n", game.getDifficultyLevel(), wins, losses);
        }

    }

    /** Runs the program allowing the user to get information about periodic table elements.
     * @param scanner the one Scanner used by the program (to avoid Scanner errors) */
    public static void runElementInformationGetter(Scanner scanner) {
        outer: while (true) {
            System.out.println("\nType in an element's name, abbreviation, or atomic number. " +
                    "Otherwise, enter \"[q]uit\" to quit.");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) break;
            Element el;
            try {
                try {
                    int x = Integer.parseInt(input);
                    el = new Element(x);
                } catch (NumberFormatException ex) {
                    el = new Element(input);
                }
                System.out.printf("%s (%s):%nAtomic Number: %d%n", el.toString(), el.getAbbreviation(), el.getAtomicNumber());
                inner: while (true) {
                    displayPrompt(el);
                    String response = scanner.nextLine().trim();
                    int x = displayInfo(response, el);
                    if (x == 1) break inner;
                    else if (x == -1) break outer;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("An invalid input was provided: please try again.\n");
            }
        }
    }

    /** Displays prompt asking user what information is requested.
     * @param el The element information is being found for.*/
    public static void displayPrompt(Element el) {
        System.out.printf("%nWhat information would you like about %s (#%d)?%n", el.getName(),
                el.getAtomicNumber());
        System.out.println("Atomic Mass[AM]\t[P]rotons\t[N]eutrons\t[E]lectrons\t[Pe]riod/Row" +
                "\tColumn/[G]roup\tElectron Configuration[EC]\nShort Electron Configuration[SEC]" +
                "\tHighest Occupied Orbital[HOO]\t[F]amily\t[D]ifferent Element\t[A]ll Information\t[Q]uit");
    }

    /** Displays information for single element based on user input.
     * @param prompt Input provided by user.
     * @param el The element information is being found for.
     * @return 1 to go to different element, 0 to do nothing more, -1 to quit program entirely */
    public static int displayInfo(String prompt, Element el) {
        if (prompt.equalsIgnoreCase("AM") || prompt.equalsIgnoreCase("mass")) {
            System.out.println("Atomic Mass: " + el.getAtomicMass());
        } else if (prompt.equalsIgnoreCase("P") || prompt.equalsIgnoreCase("Protons")) {
            System.out.printf("Proton Count: %d%n", el.getProtons());
        } else if (prompt.equalsIgnoreCase("N") || prompt.equalsIgnoreCase("neutrons")) {
            System.out.printf("Neutron Count: %d%n", el.getNeutrons());
        } else if (prompt.equalsIgnoreCase("E") || prompt.equalsIgnoreCase("electrons")) {
            System.out.printf("Electron Count: %d%n", el.getElectrons());
        } else if (prompt.equalsIgnoreCase("PE") || prompt.equalsIgnoreCase("row") ||
                prompt.equalsIgnoreCase("period")) {
            System.out.printf("Period: %d%n", el.getPeriod());
        } else if (prompt.equalsIgnoreCase("G") || prompt.equalsIgnoreCase("column") ||
                prompt.equalsIgnoreCase("group")) {
            if (el.getColumn().contains("LA")) {
                System.out.printf("Column/Group: %s%n", el.getColumn());
            } else {
                System.out.printf("Column/Group: %s (%d)%n", el.getColumn(), (int) el.getColumnEnumeration());
            }
        } else if (prompt.equalsIgnoreCase("configuration") ||
                prompt.equalsIgnoreCase("ec")) {
            System.out.printf("Electron Configuration: %s%n", el.getElectronConfig());
        } else if (prompt.equalsIgnoreCase("sec")) {
            System.out.printf("Short Electron Configuration: %s%n", el.getShortElectronConfig());
        } else if (prompt.equalsIgnoreCase("hoo")) {
            System.out.printf("Highest Occupied Orbital: %s%n", el.getHighestOccupiedOrbital());
        } else if (prompt.equalsIgnoreCase("family") || prompt.equalsIgnoreCase("f")) {
            System.out.printf("Family: %s%n", el.getElementFamily());
        } else if (prompt.equalsIgnoreCase("D") || prompt.equalsIgnoreCase("different")) {
            return 1;
        } else if (prompt.equalsIgnoreCase("A") || prompt.equalsIgnoreCase("ALL")) {
            displayAllInfo(el);
        }
        else if (prompt.equalsIgnoreCase("q") || prompt.equalsIgnoreCase("quit")) {
            return -1;
        } else {
            System.out.println("Command not recognized; only one-word commands are accepted. Please try again.");
        }
        //System.out.println();
        return 0;
    }

    /** Displays all the information for a certain element by calling displayInfo(). Called from static displayInfo()
     * @param el the element whose information is being displayed */
    public static void displayAllInfo(Element el) {
        String[] inputs = {"AM", "P", "N", "E", "PE", "G", "EC", "SEC", "HOO", "F"};
        for (String input : inputs) {
            displayInfo(input, el);
        }
    }


    /** Searches for an object in an array. Objects must be exact match for equals() to work.
     * @param array the array being searched
     * @param searchedFor the element being searched for
     * @return the index of the first instance of the searched element, or -1 if not found */
    public static <T> int searchFor(T[] array, T searchedFor) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(searchedFor)) return i;
        }
        return -1;
    }
}
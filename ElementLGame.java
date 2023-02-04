import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

/** A game where the user tries to guess the mystery element L by using information about the element, especially in
 * regard to the periodic table. The class is designed for re-playability, with the difficulty level, element set, and
 * Scanner provided by the user.
 * @author Rohit De*/
public class ElementLGame {
    private int infoFetches;
    private final String difficultyLevel;
    private boolean outOfFetches;
    private final boolean playWithLanthanidesActinides;
    /** Inputted Scanner provided by the player to ensure that there are no Scanner errors. */
    private final Scanner scanner;
    /** The Element that needs to be guessed. Randomly set by setElementL(). */
    private Element elementL;
    private final Element[] playableElementList;
    private boolean gameIsOver;
    private boolean elementHasBeenGuessed;
    private boolean rowGuessed, columnGuessed, familyGuessed, metallicStateGuessed, modulusHintGotten;
    /** Is true only if the player has won. isVictory & isDefeat can simultaneously be false. */
    private boolean isVictory;
    /** Is true only if the player has won. isVictory & isDefeat can simultaneously be false. */
    private boolean isDefeat;
    /** Set by playAgainPrompt(). Allows for re-playability by creating a new instance of this class in the main method. */
    private final boolean playAgain;
    /** Information the player has guessed about element L. Displayed to the player by displayInformation(). */
    private final ArrayList<String> information = new ArrayList<>();

    /** The base number of fetches corresponding with the hydrogen level. */
    public static final int HYDROGEN_LEVEL = 13;
    /** The base number of fetches corresponding with the iron level. */
    public static final int IRON_LEVEL = 10;
    /** The base number of fetches corresponding with the gold level. */
    public static final int GOLD_LEVEL = 7;
    /** The base number of fetches corresponding with the uranium level. */
    public static final int URANIUM_LEVEL = 4;

    /** Empty constructor sets difficulty to HYDROGEN (13), excludes lanthanides & actinides, and provides a Scanner. */
    public ElementLGame() {
        this(new Scanner(System.in), false, ElementLGame.HYDROGEN_LEVEL);
    }

    /**
     * Sets difficulty level, playable element set, number of info fetches, and the used Scanner.
     * Plays the game, and then stores playAgain information that can be used outside the class.
     *
     * @param scanner a valid Scanner provided by the user. Ensures that no Scanner-related errors occur.
     *                Scanner must not be closed or null.
     * @param playWithLanthanidesActinides whether the player is playing with lanthanides & actinides or not
     * @param difficulty the difficulty level returned by static method getDifficulty(scanner). H=13, I=10, G=7, U=4
     */
    public ElementLGame(Scanner scanner, boolean playWithLanthanidesActinides, int difficulty) {
        this.scanner = scanner;
        this.playWithLanthanidesActinides = playWithLanthanidesActinides;
        this.infoFetches = difficulty;
        if (this.playWithLanthanidesActinides) {
            playableElementList = ElementList.ALL_ELEMENTS;
            this.infoFetches += 2;
        } else {
            playableElementList = ElementList.NO_LANTHANIDES_OR_ACTINIDES;
        }
        this.difficultyLevel = difficultyToString(difficulty);
        this.setElementL();
        this.playGame();
        this.playAgain = this.playAgainPrompt();
    }

    /** The first method meant to be called for this class, before any constructors are called.
     * Displays a welcome message and rules for the game (via displayInstructions()) if the player wishes to see them.
     * Static so it can be called and information can be displayed only at the very start.
     * @param scanner a Scanner provided for use by the player */
    public static void displayOpeningMessage(Scanner scanner) {
        boolean viewInstructions;
        System.out.println("Welcome to Element-Ls! Try to guess the mystery element L!");
        System.out.println("Would you like to learn more about the game? Type [y]es or [n]o");
        while (true) {
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
                viewInstructions = true;
                break;
            } else if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
                viewInstructions = false;
                break;
            }
        }
        if (viewInstructions) displayInstructions();
    }

    /** Displays instructions for the game if the user requests it from displayOpeningMessage().*/
    public static void displayInstructions() {
        System.out.println("""
              
              
              Element-Ls is a game where the player tries to guess the mystery element L.
              The player obtains information about the element in order to guess it.
              A player wins by guessing an element, and they lose by running out of fetches.
              IMPORTANT: Element-Ls is to be played with a periodic table on hand.""");
        System.out.println("""
              
              A player uses information "fetches" to get hints about the element.
              The player can guess the element's row, column, family, or metallic state.
              If the player is incorrect, they lose a fetch.""");
        System.out.println("""
              Incorrectly guessing an element costs 2 fetches instead of 1.
              Obtaining the result of (atomic number % 3) also costs 1 fetch.""");
        System.out.println("""
              
              Periodic rows range from 1 to 7. Lanthanides and actinides get special columns, and column 8B is split up.
              The periodic columns are listed below:""");
        System.out.println(Arrays.toString(Element.COLUMNS));
        System.out.println("\nThere are 10 families used in this game, as listed below:");
        System.out.println(Arrays.toString(Element.ELEMENT_FAMILIES));
        System.out.println("""
              
              A few notes:
              Reactive nonmetals are any nonmetals that are not halogens or noble gasses.
              There are only 6 elements considered metalloids: boron (5), silicon (14), germanium (32), arsenic (33), antimony (51), and tellurium (52).
              Polonium (84), nihonium (113), flerovium (114), moscovium (115), and livermorium (116) are considered post-transition metals.
              Tennessine (117) is considered a halogen, while oganesson (118) is considered a noble gas.""");
        System.out.println("\nThe three metallic states are metal, nonmetal, and metalloid.");
        System.out.println("\nThe modulus 3 hint does cost 1 fetch, but it can help narrow down elements of a single row or column.");
        System.out.println("""
              
              There are 4 different difficulties to this game: hydrogen (easy), iron (medium), gold (hard), and uranium (very hard).
              Hydrogen allots a user 13 guesses, iron allots 10, gold allots 7, and uranium allots only 4.
              The hydrogen level is almost always guessable, while the uranium level will almost always require some luck.""");
        System.out.println("""
              
              You can choose your difficulty level, as well as whether you would like to play with the lanthanide and actinide series or not.
              Good luck playing Element-Ls!""");
    }

    /** Uses user input to determine the element set (including lanthanides/actinides or not) used for the game.
     * Static method so element set can be used for multiple consecutive games without extra input.
     * @param scanner a Scanner provided for use by the player
     * @return true if the user is playing with lanthanides & actinides, false if they are not */
    public static boolean playWithLanthanidesActinides(Scanner scanner) {
        while (true) {
            System.out.println("Would you like to play with lanthanides and actinides? Type [y]es or [n]o");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
                return true;
            } else if (response.equalsIgnoreCase("N") || response.equalsIgnoreCase("no")) {
                return false;
            }
        }
    }

    /** Uses user input to determine the difficulty (HYDROGEN/IRON/GOLD/URANIUM) for the game.
     * Static method so difficulty can be used for multiple consecutive games without extra input.
     * THe result should be passed as a parameter to the constructor for this class.
     * @param scanner a Scanner provided for use by the player
     * @return the number of info fetches allotted to the user based on the difficulty (H=13, I=10, G=7, U=4) */
    public static int getDifficulty(Scanner scanner) {
        while (true) {
            System.out.println("\nWhich difficulty would you like to play?\n[H]ydrogen\t[I]ron\t[G]old\t[U]ranium");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("H") || response.equalsIgnoreCase("hydrogen")) {
                return HYDROGEN_LEVEL;
            } else if (response.equalsIgnoreCase("i") || response.equalsIgnoreCase("iron")) {
                return IRON_LEVEL;
            }
            else if (response.equalsIgnoreCase("g") || response.equalsIgnoreCase("gold")) {
                return GOLD_LEVEL;
            } else if (response.equalsIgnoreCase("u") || response.equalsIgnoreCase("uranium")) {
                return URANIUM_LEVEL;
            }
        }
    }
    /** Converts int difficulty level into String. Used so difficulty for a singular game can be found outside of class
     * if difficulty is not provided as a constructor parameter. Called in a constructor.
     * @param difficulty the maximum number of fetches the user is allowed  (13 or 10 or 7 or 4)
     * @return the String representation of the difficulty (HYDROGEN or IRON or GOLD or URANIUM)*/
    private String difficultyToString(int difficulty) {
        return switch (difficulty) {
            case HYDROGEN_LEVEL -> "HYDROGEN";
            case IRON_LEVEL -> "IRON";
            case GOLD_LEVEL -> "GOLD";
            case URANIUM_LEVEL -> "URANIUM";
            /* default: shouldn't reach this: static method getDifficulty() returns only 13, 10, 7, or 4,
        which is the input for this method */
            default -> "UNKNOWN";
        };
    }

    /** Chooses a random element from playableElementList to set as ElementL, the element to be guessed.*/
    private void setElementL() {
        // Generates a random index to select from list
        int index = (int) (Math.random() * (double) playableElementList.length);
        this.elementL = playableElementList[index];
    }

    /** The method that operates the game steps. Displays element information, finds the next action to take,
     * performs said action, checks if the game is over, and displays a victory or defeat message.
     * Methods called include: displayInformation(), findAction(), performAction(), isOutOfFetches(), isVictory(), isDefeat(), isGameOver()*/
    private void playGame() {
        while (!this.gameIsOver) {
            displayInformation();
            int action = findAction();
            performAction(action);
            this.outOfFetches = isOutOfFetches();
            this.isVictory = isVictory();
            this.isDefeat = isDefeat();
            this.gameIsOver = isGameOver();
        }
        if (this.isVictory) {
            System.out.println("Congratulations! You won!\nYou successfully guessed element L!");
            System.out.printf("Element L was %s.%n", this.elementL.toString().toUpperCase());
        } else if (this.isDefeat) {
            System.out.println("Sorry, you did not win.");
            System.out.printf("The mystery element L was %s.%n", elementL.toString().toUpperCase());
        }


    }

    /** Displays any information the user has obtained from their fetches.
     * If no information has been fetched, nothing is displayed.*/
    private void displayInformation() {
        System.out.printf("%n%nFetches remaining: %d%n", this.infoFetches);
        if (!information.isEmpty()) {
            System.out.println("Available Information:");
            for (String s: information) {
                System.out.println(s);
            }
            System.out.println();
        }
    }

    /** Determines what action to take based on user input.
     * @return an int value corresponding with the action to take: this int is used in performAction(). */
    private int findAction() {
        while (true) {
            System.out.println("What action would you like to perform? Type in the NUMBER corresponding to the action.");
            System.out.println("[1] guessElement\t[2] guessRow\t[3] guessColumn\t[4] guessFamily" +
                    "\t[5] guessMetallicState\t[6] getModulus3Hint\t[-1] quitGame");
            String input = scanner.nextLine();
            try {
                int act = Integer.parseInt(input);
                if (act >= -1 && act <= 6) return act;
                else System.out.println("Invalid input: please try again.");
            } catch (Exception ex) {
                System.out.println("Something went wrong; please try again.");
            }
        }
    }

    /** Allows the user to perform an action based on their input. Actions include: quitting the game;
     * guessing the row, column, family, or metallic state; and getting the modulus 3 hint.
     * @param actionToPerform int corresponding with the action to perform; obtained from findAction() */
    private void performAction(int actionToPerform) {
        switch (actionToPerform) {
            case -1 -> quitGame();
            case 1 -> this.elementHasBeenGuessed = guessElement();
            case 2 -> this.rowGuessed = guessRow();
            case 3 -> this.columnGuessed = guessColumn();
            case 4 -> this.familyGuessed = guessFamily();
            case 5 -> this.metallicStateGuessed = guessMetallicState();
            case 6 -> this.modulusHintGotten = getModulus3Hint();
        }
    }

    /** Quits the current game and sets it as a loss. */
    private void quitGame() {
        this.isDefeat = true;
        this.isVictory = false;
        this.gameIsOver = true;
    }


    /** Reads user input to either guess the element or go back and perform another action.
     * Incorrect element guesses cost 2 information fetches, as opposed to 1.
     * If the guess is incorrect, that information is added to the information list to be displayed.
     * @return true if the element has been guessed correctly, false if the guess is incorrect or if the user backs out. */
    private boolean guessElement() {
        Element guessedElement;
        while (true) {
            System.out.println("Type in the name or abbreviation of the element you think element L is. Type -1 to go back:");
            String guess = scanner.nextLine();
            if (guess.equals("-1")) return false;
            try {
              guessedElement = new Element(guess);
              break;
            } catch (IllegalArgumentException ex) {
              System.out.println("Invalid input: please try again");
            }
        }
        if (guessedElement.equals(this.elementL)) {
            return true;
        } else {
            System.out.print("What were you thinking? ");
            boolean hasBeenGuessed = addToInformation(guessedElement.toString().toUpperCase(), false, "");
            checkHasBeenGuessed(hasBeenGuessed, false, true);
            ///this.infoFetches -= 2;
            return false;
        }
    }

    /** Reads user input to either guess the metallic state of the element or go back and perform another action.
     * If the state has already been guessed, the method completes early to save the user hassle.
     * The information guessed about the metallic state is added to information, too.
     * Incorrect state guesses cost 1 information fetch.
     * @return true if the metallic state has been guessed correctly, false if the guess is incorrect or if the user backs out*/
    private boolean guessMetallicState() {
        if (this.metallicStateGuessed) {
            System.out.println("You already know the metallic state!");
            return this.metallicStateGuessed;
        }
        int guessedInt;
        // IntelliJ says to initialize these, even though my code sets these when they are needed.
        //  (My code proceeds to use these only if the input is 1, 2, or 3, and it sets these values with these inputs specifically.)
        String guessedMetallicState = "";
        boolean guessedCorrectState = false;
        while (true) {
          System.out.println("Guessable metallic states: [1] METAL\t[2] NONMETAL\t[3] METALLOID");
          System.out.println("Type in the integer corresponding to the metallic state you think element L has. Type -1 to go back.");
          String input = scanner.nextLine();
          try {
              guessedInt = Integer.parseInt(input);
              // -1 quits before proceeding further
              if (guessedInt==-1) return false;
              // proceed further if correct input
              if (guessedInt >= 1 && guessedInt <= 3) break;
          } catch (Exception ignored) {}
          // if not correct input, go back
          System.out.println("Invalid input; please try again");
        }
        switch (guessedInt) {
            case 1 -> {
                guessedMetallicState = "METAL";
                guessedCorrectState = this.elementL.isMetal();
            }
            case 2 -> {
                guessedMetallicState = "NONMETAL";
                guessedCorrectState = this.elementL.isNonmetal();
            }
            case 3 -> {
                guessedMetallicState = "METALLOID";
                guessedCorrectState = this.elementL.isMetalloid();
            }
        }
        if (guessedCorrectState) {
            System.out.print("Correct! ");
        } else {
            System.out.print("Sorry, ");
        }
        boolean hasBeenGuessed = addToInformation(guessedMetallicState, guessedCorrectState, "a ");
        checkHasBeenGuessed(hasBeenGuessed, guessedCorrectState, false);
        return guessedCorrectState;
    }

    /** Takes information about the element from guess functions, prints it out, and adds it to information
     * if it was not there already. If the information was there, it does not add anything.
     * @param guessedInformation the specific information group the user guessed Element L belonged to
     * @param isCorrect whether the player's guess was correct or not
     * @param otherString any additional words meant to form a complete phrase
     * @return true if the information has already been fetches (is in this.information), false otherwise. */
    private boolean addToInformation(String guessedInformation, boolean isCorrect, String otherString) {
        String familyInfo = String.format("Element L is %s%s%s", (isCorrect) ? "" : "not ", otherString,
                guessedInformation);
        System.out.printf("%s.%n", familyInfo);
        // if not found in information
        if (Main.searchFor(information.toArray(), familyInfo) == -1) {
            information.add(familyInfo);
            return false;
        }
        // if already found in information
        else return true;
    }

    /** Subtracts a number of fetches for an incorrect fetch (2 for element, 1 for anything else); if the fetch was
     * guessed beforehand, nothing is subtracted
     * @param hasBeenGuessed whether the information has already been guessed before
     * @param guessIsCorrect whether the guess is correct
     * @param isElementGuess whether the element itself is being guessed or not */
    private void checkHasBeenGuessed(boolean hasBeenGuessed, boolean guessIsCorrect, boolean isElementGuess) {
        if (!hasBeenGuessed && !guessIsCorrect) {
            if (isElementGuess) this.infoFetches -=2;
            else this.infoFetches--;
        }
        else if (hasBeenGuessed) System.out.println("You already guessed that, though!");
    }

    /** Reads user input to either guess the family of the element or go back and perform another action.
     * If the family has already been guessed, the method completes early to save the user hassle.
     * The information guessed about the family is added to information, too.
     * Incorrect state guesses cost 1 information fetch.
     * @return true if the family has been guessed correctly, false if the guess is incorrect or if the user backs out*/
    private boolean guessFamily() {
        String guessedFamily;
        if (this.familyGuessed) {
          System.out.println("You already know the family!");
          return this.familyGuessed;
        }
        while (true) {
            System.out.print("Guessable families: ");
            for (int i = 0; i < Element.ELEMENT_FAMILIES.length; i++) {
                String thisFamily = Element.ELEMENT_FAMILIES[i];
                if (!playWithLanthanidesActinides && (thisFamily.equals(Element.LANTHANIDE)
                        || thisFamily.equals(Element.ACTINIDE))) {
                    continue;
                } else System.out.printf("[%d] %s; ", i+1, thisFamily);
                if ((playWithLanthanidesActinides && i==4) || (!playWithLanthanidesActinides && i==2))
                    System.out.println();
            }
            System.out.println("\nType in the integer corresponding to the family you think element L belongs to. Type -1 to go back.");
            // columns are written uppercase
            String input = scanner.nextLine();
            try {
                int familyIndex = Integer.parseInt(input)-1;
                // if -1 entered, familyIndex=-2
                // quits step before proceeding further
                if (familyIndex==-2) return false;
                guessedFamily = Element.ELEMENT_FAMILIES[familyIndex];
                if (!playWithLanthanidesActinides && (guessedFamily.equals(Element.ACTINIDE) ||
                        guessedFamily.equals(Element.LANTHANIDE))) {
                    System.out.println("You are not playing with lanthanides or actinides; please guess again.");
                    continue;
                } else break;
            } catch (Exception ex) {
                System.out.println("Invalid input; please try again");
            }
        }
        boolean isCorrectFamily = (guessedFamily.equals(this.elementL.getElementFamily()));
        if (isCorrectFamily) {
            System.out.print("Correct! ");
        } else {
            System.out.print("Sorry, ");
        }
        // add "a" or "an" depending on whether the family starts with "a" or not
        String aOrAn = (guessedFamily.equals(Element.ALKALI_METAL) || guessedFamily.equals(Element.ALKALINE_EARTH_METAL)
                || guessedFamily.equals(Element.ACTINIDE)) ? "an " : "a ";
        boolean hasBeenGuessed = addToInformation(guessedFamily, isCorrectFamily, aOrAn);
        checkHasBeenGuessed(hasBeenGuessed, isCorrectFamily, false);
        return isCorrectFamily;
    }

    /** Reads user input to either guess the column of the element or go back and perform another action.
     * If the column has already been guessed, the method completes early to save the user hassle.
     * The information guessed about the column is added to information, too.
     * Incorrect state guesses cost 1 information fetch.
     * @return true if the column has been guessed correctly, false if the guess is incorrect or if the user backs out*/
    private boolean guessColumn() {
        String guessedColumn;
        if (this.columnGuessed) {
          System.out.println("You already know the column!");
          return this.columnGuessed;
        }
        while (true) {
            System.out.print("Guessable columns: ");
            for (String str: Element.COLUMNS) {
                if (!(!playWithLanthanidesActinides && str.contains("LA"))) {
                   System.out.printf("%s; ", str);
                }
            }
            System.out.println("\nType in the column you think element L is in. Type -1 to go back.");
            // columns are written uppercase
            String input = scanner.nextLine().toUpperCase();
            System.out.println(input);
            // quits step before going further
            if (input.equals("-1")) return false;
            if (Main.searchFor(Element.COLUMNS, input) >= 0) {
                guessedColumn = input;
                break;
            } else {
                System.out.println("Invalid input; please try again");
            }
        }
        boolean inCorrectColumn = (guessedColumn.equals(this.elementL.getColumn()));
        if (inCorrectColumn) {
            System.out.print("Correct! ");
        } else {
            System.out.print("Sorry, ");
        }
        boolean hasBeenGuessed = addToInformation(guessedColumn, inCorrectColumn, "in column ");
        checkHasBeenGuessed(hasBeenGuessed, inCorrectColumn, false);
        return inCorrectColumn;
    }

    /** Reads user input to either guess the row of the element or go back and perform another action.
     * If the row has already been guessed, the method completes early to save the user hassle.
     * The information guessed about the row is added to information, too.
     * Incorrect state guesses cost 1 information fetch.
     * @return true if the row has been guessed correctly, false if the guess is incorrect or if the user backs out*/
    private boolean guessRow() {
        int guessedRow;
        if (this.rowGuessed) {
          System.out.println("You already know the row!");
          return this.rowGuessed;
        }
        while (true) {
            System.out.println("Type in the row/period you think element L is in:");
            String input = scanner.nextLine();
            try {
                guessedRow = Integer.parseInt(input);
                // quits
                if (guessedRow == -1) return false;
                if (guessedRow >= 1 && guessedRow <= 7) break;
                else System.out.println("Invalid input: please try again");
            } catch (Exception ex) {
                System.out.println("Invalid input: please try again");
            }
        }
        boolean inCorrectRow = (guessedRow == this.elementL.getPeriod());
        if (inCorrectRow) {
            System.out.print("Correct! ");
        } else {
            System.out.print("Sorry, ");
        }
        boolean hasBeenGuessed = addToInformation(Integer.toString(guessedRow), inCorrectRow, "in row ");
        checkHasBeenGuessed(hasBeenGuessed, inCorrectRow, false);
        return inCorrectRow;
    }

    /** Provides the user with the modulus 3 hint or goes back to perform another action.
     * @return true because the modulus hint has been fetched */
    private boolean getModulus3Hint() {
        if (this.modulusHintGotten) {
          System.out.println("You already have the modulus hint!");
          return this.modulusHintGotten;
        }
        String modInfo = String.format("Atomic Number %% 3 = %d", this.elementL.getAtomicNumber() % 3);
        System.out.printf("%s.%n", modInfo);
        if (Main.searchFor(information.toArray(), modInfo) == -1) information.add(modInfo);
        this.infoFetches--;
        return true;
    }

    /** @return true if the game is over, false if otherwise */
    private boolean isGameOver() {
        return (this.isVictory || this.isDefeat || this.gameIsOver || this.elementHasBeenGuessed || this.outOfFetches);
    }

    /** @return true if the user is out of fetches, false if the user still has fetches*/
    private boolean isOutOfFetches() {
        return (this.infoFetches <= 0);
    }

    /** @return true if the user has won, false otherwise*/
    private boolean isVictory() {
        return this.elementHasBeenGuessed && !this.isDefeat;
    }

    /** @return true if the user has lost, false otherwise*/
    private boolean isDefeat() {
        return (this.gameIsOver || this.outOfFetches) && !this.isVictory;
    }

    /** Asks whether the user wants to play again: information is used outside of class via getter to start a new game
     * @return true if the user wants to play again, false if not */
    private boolean playAgainPrompt() {
        String response;
        while (true) {
            System.out.println("\nWould you like to play again? Type [y]es or [n]o");
            response = scanner.nextLine();
            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
                System.out.println("\n");
                return true;
            } else if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
                System.out.println("\n");
                return false;
            } else {
                System.out.println("Invalid input; please try again\n");
            }
        }
    }

    /** @return true if the user has won the game, false if the user has lost the game
     * @throws IllegalStateException thrown if the game has not yet finished */
    public boolean getVictoryStatus() throws IllegalStateException {
        if (gameIsOver) return this.isVictory;
        else throw new IllegalStateException("Game is not over");
    }

    /** @return whether the user wants to play again (true) or not (false) */
    public boolean isPlayAgain() {
        return playAgain;
    }

    /** @return the difficulty level of the game (HYDROGEN/IRON/GOLD/URANIUM) as a String */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
}

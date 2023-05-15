package ClientUIHandling;

import delta.dkt.logic.structure.DiceRange;

public class Config {

    private Config() {
        // No instantiation of class
    }

    public static final int MAX_CLIENTS = 6;
    public static final int END_TIME = 3000000; // end_time in milliseconds
    public static final int INITIAL_CASH = 1000; // end_time in milliseconds

    public static boolean Skip = true; //? Used for quick development, skips activities until false.
    public static final boolean DEBUG = true;
    public static final int ENDROUNDS = 10; //Number of rounds until the game ends

    public static final DiceRange diceRange = new DiceRange(1, 6);
    public static final DiceRange CheatRange = new DiceRange(7, 9);
}

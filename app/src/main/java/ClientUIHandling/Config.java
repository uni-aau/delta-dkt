package ClientUIHandling;

import delta.dkt.logic.structure.Dice;

public class Config {

    private Config() {
        // No instantiation of class
    }

    public static final int MAX_CLIENTS = 6;
    public static final int END_TIME = 3000000; // end_time in milliseconds
    public static final int INITIAL_CASH = 1000; // end_time in milliseconds

    public static boolean Skip = true; //? Used for quick development, skips activities until false.
    public static final boolean DEBUG = true;

    public static final int START_CASH = 1000; //Number of rounds until the game ends
    public static final int ENDROUNDS = 100; //Number of rounds until the game ends


    public static final Dice diceRange = new Dice(1, 6);
    public static final Dice CheatRange = new Dice(7, 9);
}

package ClientUIHandling;

import delta.dkt.logic.structure.Dice;

public class Config {

    private Config() {
        // No instantiation of class
    }

    public static final int MAX_CLIENTS = 6;
    public static final int END_TIME = 3000000; // end_time in milliseconds
    public static final int INITIAL_CASH = 1000; // end_time in milliseconds

    public static boolean Skip = false; //? Used for quick development, skips activities until false.
    public static final boolean DEBUG = true;

    public static final int START_CASH = 1000; //Number of rounds until the game ends
    public static final int ENDROUNDS = 100; //Number of rounds until the game ends
    public static float TAX_PERCENTAGE = 0.1F; // Percentage of money that gets removed from player cash
    public static int STATIC_TAX_AMOUNT = 100; // Specify the static tax amount that would get removed from player cash
    // -1 for disabling MAX_TAX_AMOUNT check
    public static int MAX_TAX_AMOUNT = 1500; // Specify the max tax amount that would get removed from player cash

    public static final Dice diceRange = new Dice(1, 6);
    public static final Dice CheatRange = new Dice(7, 9);

    public static final int punishmentForCheating = 500;
    public static final int punishmentForWrongReport = 200;
}

package ClientUIHandling;

import delta.dkt.logic.structure.Dice;

public class Config {

    private Config() {
        // No instantiation of class
    }

    public static int MAX_CLIENTS = 6; // value gets set by user (needs to be <= MAX_CLIENTS_AMOUNT)
    public static final int MAX_CLIENTS_AMOUNT = 6; // Maximum clients that are possible in game (should be only changed here)
    public static final int MIN_CLIENTS = 1;
    public static int END_TIME = 3000000; // end_time in milliseconds
    public static int ENDROUNDS = 100; //Number of rounds until the game ends
    public static final float TAX_PERCENTAGE = 0.1F; // Percentage of money that gets removed from player cash
    public static final int STATIC_TAX_AMOUNT = 100; // Specify the static tax amount that would get removed from player cash
    // -1 for disabling MAX_TAX_AMOUNT check
    public static final int MAX_TAX_AMOUNT = 1500; // Specify the max tax amount that would get removed from player cash
    public static final int INITIAL_CASH = 1000; // end_time in milliseconds

    public static boolean Skip = false; //? Used for quick development, skips activities until false.
    public static final boolean DEBUG = false;

    public static final int START_CASH = 1000;
    public static final boolean ONLY_SHOW_PROPERTY_WITH_OWNER = false;
    public static final int MAX_HOUSES = 4; // also used in displaying house amount (currently max. 4)
    public static final int PUNISHMENT_FOR_CHEATING = 500; // cash for successful report
    public static final int PUNISHMENT_FOR_WRONG_REPORT = 200; // cash deduction for unsuccessful report

    public static final Dice DICE_RANGE = new Dice(1, 6);
    public static final Dice CHEAT_RANGE = new Dice(7, 9);
    public static final int TIMEOUT = 20000; // timeout threshold (in milliseconds)

    public static final int TIMEOUT_WARNING_THRESHOLD = 5000; // opens snackbar as warning that time is nearly over (in milliseconds)

    public static final int PING_TIMEOUT = 2000;
}

package ClientUIHandling;

public class Constants {
    private Constants() {
        // No instantiation of class
    }

    public static final String PREFIX_ACTIONCARD_PRISON = "action_prison";

    public static final String PREFIX_ACTIONCARD_TRAVEL = "action_travel";

    public static final String PREFIX_ACTIONCARD_MONEY = "action_money";

    public static final String PREFIX_ACTIONCARD_OUT_OF_PRISON = "action_outofprison";

    public static final String MAIN_ACTIVITY_TYPE = "main";
    public static final String GAMEVIEW_ACTIVITY_TYPE = "game";
    public static final String MAINMENU_ACTIVITY_TYPE = "menu";
    public static final String PROPERTYLIST_ACTIVITY_TYPE = "list";
    public static final String LOBBYVIEW_ACTIVITY_TYPE = "lobby";
    public static final String PREFIX_PLAYER_MOVE = "move";
    public static final String FIND_HOST_VIEW_ACTIVITY_TYPE = "findHost";

    public static final String PREFIX_PLAYER_RENTPAID = "RENTPAID";

    public static final String PREFIX_PLAYER_BUYPROPERTY = "PROPERTY_BUY";
    public static final String PREFIX_PLAYER_PROPERTYBOUGHT = "PROPERTY_BOUGHT";

    public static final String PREFIX_PLAYER_PAYRENT = "PAYRENT";

    public static final String PREFIX_HOST_NEW_GAME = "host";


    public static final String PREFIX_ROLL_DICE_REQUEST = "ROLL_DICE_REQUEST";
    public static final String PREFIX_GET_SERVER_TIME = "SERVER_TIME";
    public static final String PREFIX_GAME_START = "GAMESTART";
    public static final String PREFIX_REGISTER = "REGISTERUSER";
    public static final String PREFIX_INIT_PLAYERS = "INIT_PLAYERS";
    public static final String PREFIX_GAME_START_STATS = "START_STATS";

    public static final String PREFIX_PLAYER_LOST = "LOST";
    public static final String PREFIX_ADD_USER_TO_LIST = "Add_User_To_List";
    public static final String PREFIX_REMOVE_USER_FROM_LIST = "Remove_User_from_List";

    public static final String PREFIX_UPDATE_USER_LIST = "Update_UserList";

    public static final String PREFIX_CLOSE_GAME = "Close_Game";

    public static final String PREFIX_END_GAME = "ENDGAME";

    public static final String PREFIX_SERVER = "SERVER";

    public static final String PREFIX_GET_IP = "IP";

    public static final String PREFIX_ROLL_DICE_RECEIVE = "ROLLRECEIVE";
    public static final String PREFIX_START_CASH_VALUE = "START_CASH";
    public static final String PREFIX_SET_MONEY = "SET_MONEY";
    public static final String PREFIX_ACTIVITY_BROADCAST = "ACTIVITY_BROADCAST";
    public static final String PREFIX_PAY_TAX = "PAY_TAX";
    public static final String PREFIX_PLAYER_CHEATED = "CHEATED";
    public static final String PREFIX_PLAYER_REPORTED_WRONGLY = "REPORTED_WRONGLY";

    public static final String PREFIX_SERVER_FULL = "SERVER_FULL";
    public static final String PREFIX_PROPLIST_UPDATE = "PROPLIST_UPDATE";
  
    public static final String PREFIX_PLAYER_CHEAT_MENU = "CHEAT_MENU";
    public static final String PREFIX_PLAYER_REPORT_CHEATER = "REPORT_CHEATER";
    public static final String PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT = "REQUEST_SERVER_ACTION";

    public static final String LOG_MAIN = "DKT-MAIN";
    public static final String LOG_CHEAT = String.format("%s-Cheat", LOG_MAIN);
    public static final String LOG_LIGHT_SENSOR = String.format("%s-LightSensor", LOG_MAIN);
}

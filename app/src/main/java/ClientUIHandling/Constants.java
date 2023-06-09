package ClientUIHandling;

public class Constants {
    private Constants() {
        // No instantiation of class
    }

    public static final String MAIN_ACTIVITY_TYPE = "main";
    public static final String GAMEVIEW_ACTIVITY_TYPE = "game";
    public static final String MAINMENU_ACTIVITY_TYPE = "menu";
    public static final String PROPERTYLIST_ACTIVITY_TYPE = "list";
    public static final String LOBBYVIEW_ACTIVITY_TYPE = "lobby";
    public static final String PREFIX_PLAYER_MOVE = "move";
    public static final String FIND_HOST_VIEW_ACTIVITY_TYPE = "findHost";
    public static final String PREFIX_BUY_PROPERTY_TOASTS = "BUY_PROPERTY_TOASTS";
    public static final String PREFIX_PLAYER_RENTPAID = "RENTPAID";

    public static final String PREFIX_PLAYER_BUYPROPERTY = "PROPERTY_BUY";
    public static final String PREFIX_PLAYER_PROPERTYBOUGHT = "PROPERTY_BOUGHT";

    public static final String PREFIX_PLAYER_PAYRENT = "PAYRENT";

    public static final String PREFIX_HOST_NEW_GAME = "host";


    public static final String PREFIX_ROLL_DICE_REQUEST = "ROLL_DICE_REQUEST";
    public static final String PREFIX_GET_SERVER_TIME = "SERVER_TIME";
    public static final String PREFIX_GAME_START = "GAMESTART";
    public static final String PREFIX_INIT_PLAYERS = "INIT_PLAYERS";
    public static final String PREFIX_PLAYER_LEAVE = "PLAYER_LEAVE";
    public static final String PREFIX_PLAYER_SPECTATOR_LEAVE = "PLAYER_SPECTATOR_LEAVE";
    public static final String PREFIX_CLIENT_LEAVE_EVENT = "PLAYER_CLIENT_LEAVE";
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
    public static final String PREFIX_ASK_BUY_PROPERTY = "ASK_BUY_PROPERTY";
    public static final String PREFIX_CLIENT_BUY_PROPERTY = "CLIENT_BUY_PROPERTY";
    public static final String PREFIX_PLAYER_CHEATED = "CHEATED";
    public static final String PREFIX_PLAYER_REPORTED_WRONGLY = "REPORTED_WRONGLY";
    public static final String PREFIX_GO_TO_PRISON_FIELD = "Go_To_Prison";
    public static final String PREFIX_PRISON = "You_Are_In_Prison";
    public static final String PREFIX_HAS_PRISONCARD = "Player_Has_A_YouGetOutOfPrison_Card";

    public static final String PREFIX_PRISONCARD_AWARDED = "Player_RECEIVED_YOUGETOUTOFPRISONCARD";
    public static final String PREFIX_PRISONCARD_AWARDED_NOTIFICATION = "Player_RECEIVED_YOUGETOUTOFPRISONCARD_NOTIFICATION";
    public static final String PREFIX_NOTIFICATION = "Player_Moves_To_Prison_Field";

    public static final String PREFIX_SERVER_FULL = "SERVER_FULL";
    public static final String PREFIX_PROPLIST_UPDATE = "PROPLIST_UPDATE";

    public static final String PREFIX_SUSPENSION_COUNT = "SUSPENSION_COUNT";

  
    public static final String PREFIX_PLAYER_CHEAT_MENU = "CHEAT_MENU";
    public static final String PREFIX_PLAYER_REPORT_CHEATER = "REPORT_CHEATER";
    public static final String PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT = "REQUEST_SERVER_ACTION";

    public static final String LOG_MAIN = "DKT-MAIN";
    public static final String LOG_CHEAT = String.format("%s-Cheat", LOG_MAIN);
    public static final String LOG_LIGHT_SENSOR = String.format("%s-LightSensor", LOG_MAIN);
    public static final String LOG_NETWORK = String.format("%s-Network", LOG_MAIN);
    public static final String LOG_PROPERTY_BUY = String.format("%s-Property-Buy", LOG_MAIN);
    public static final String LOG_ERROR = String.format("%s-Error", LOG_MAIN);

    public static final String PREFIX_PLAYER_TIMEOUT_WARNING = "TIMEOUT_WARNING";
    public static final String PREFIX_CASH_TASK = "CASH_TASK";
    public static final String PREFIX_JAIL_TASK = "JAIL_TASK";
    public static final String PING = "PING";

    public static final String PREFIX_CLOSE_LOBBY = "CLOSE_LOBBY";



}

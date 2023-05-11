package delta.dkt.logic.structure;

import java.util.HashMap;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;

public class Game {
    private Game() {
    }

    private static HashMap<Integer, Player> players;

    static {
        map = new GameMap();
        players = new HashMap<>();
    }

    private static GameMap map;

    public static int roundStartID = -1;

    public static int rounds = 0;
    public static void setMap(GameMap map) {
        Game.map = map;
    }

    public static GameMap getMap() {
        return map;
    }

    public static HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public static void incrementRounds(int playerID){
        if(Game.roundStartID ==-1){
            Game.roundStartID = playerID;
        }else{
            if(Game.roundStartID == playerID){
                Game.rounds++;
                if(Game.rounds == Config.ENDROUNDS){
                    ServerActionHandler.triggerAction(Constants.PREFIX_END_GAME, "ROUNDS RAN OUT");
                }
            }
        }
    }

    public static void reset(){
        map = new GameMap();
        players = new HashMap<>();
        roundStartID = -1;
        rounds = 0;
    }
}

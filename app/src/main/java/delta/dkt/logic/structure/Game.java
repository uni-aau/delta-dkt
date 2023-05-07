package delta.dkt.logic.structure;

import java.util.HashMap;

public class Game {
    private Game() {
    }

    private static HashMap<Integer, Player> players;

    static {
        map = new GameMap();
        players = new HashMap<>();
    }

    private static GameMap map;
    
    public static void setMap(GameMap map) {
        Game.map = map;
    }

    public static GameMap getMap() {
        return map;
    }

    public static HashMap<Integer, Player> getPlayers() {
        return players;
    }
}

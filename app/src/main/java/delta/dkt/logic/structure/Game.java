package delta.dkt.logic.structure;

import java.util.HashMap;

public class Game {
    private Game() {
    }

    private static HashMap<Integer, Player> players;

    static {
        map = new GameMap();
        players = new HashMap<>();
//        players.put(0, new Player("testPlayer"));
//        players.put(1, new Player("TestPlayer2"));

    }

    private static GameMap map;
    private static int playerCount;


    public static void setMap(GameMap map) {
        Game.map = map;
    }

    public static GameMap getMap() {
        return map;
    }

    public static int getPlayerCount() {
        return playerCount;
    }

    public static void setPlayerCount(int playerCount) {
        Game.playerCount = playerCount;
    }

    public static HashMap<Integer, Player> getPlayers() {
        return players;
    }
}

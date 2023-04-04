package delta.dkt.logic.structure;

public class Game {
    private Game () {
    }

    private static GameMap map = new GameMap();
    private static int playerCount;

    public static void setMap (GameMap map) {
        Game.map = map;
    }

    public static GameMap getMap () {
        return map;
    }

    public static int getPlayerCount () {
        return playerCount;
    }

    public static void setPlayerCount (int playerCount) {
        Game.playerCount = playerCount;
    }
}

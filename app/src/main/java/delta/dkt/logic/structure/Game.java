package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    static final ArrayList<Player> winnerRankingList = new ArrayList<>();

    private static GameMap map;

    private static int roundStartID = -1;
    private static int rounds = 0;
    public static void setMap(GameMap map) {
        Game.map = map;
    }

    public static GameMap getMap() {
        return map;
    }

    public static HashMap<Integer, Player> getPlayers() {
        return players;
    }


    public static Player findPlayerByClientId(int clientId){
        return players.get(clientId);
    }
    public static ArrayList<Player> getPlayerList(){
       return new ArrayList<>(players.values());
    }
    public static Integer getClientIdByPlayerObject(Player player){
        return getClientIdByPlayerId(player.getId());
    }

    public static Player getPlayerById(int playerId){
        Optional<Player> player = players.values().stream().filter(p -> p.getId() == playerId).findFirst();
        return player.orElse(null);
    }

    public static Integer getClientIdByPlayerId(int playerId){
        Set<Map.Entry<Integer, Player>> map =  Game.getPlayers().entrySet();
        Integer clientId = -1;
        for(Map.Entry<Integer,Player> e : map){
            if(e.getValue().getId() == playerId) {
                clientId = e.getKey();
                return clientId;
            }
        }
        return clientId; //if we reach this statement we haven´t found any entry for the given playerId
    }

    public static int getRoundStartID() {
        return roundStartID;
    }

    public static void setRoundStartID(int roundStartID) {
        Game.roundStartID = roundStartID;
    }

    public static int getRounds() {
        return rounds;
    }


    public static ArrayList<Player> getWinnerList(){
        winnerRankingList.clear();
        winnerRankingList.addAll(players.values());

        // Sort the playerList by Wealth
        Collections.sort(winnerRankingList, Comparator.comparingInt(Player::getWealth).reversed());
        return winnerRankingList;
    }


    public static void setRounds(int rounds) {
        Game.rounds = rounds;
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

package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_CHEATED;
import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;
import static ClientUIHandling.Constants.PREFIX_PLAYER_REPORTED_WRONGLY;

import ClientUIHandling.Config;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class ActionPunish implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        if(!(parameters instanceof Object[])){
            System.err.println("WRONG PARAMETERS FOR ActionPunish, expected an array!");
            return;
        }
        Object[] parameterArray = (Object[]) parameters;
        if(!(parameterArray[0] instanceof Boolean)){
            System.err.println("WRONG PARAMETERS FOR ActionPunish, expected boolean as first element!");
            return;
        }
        if(!(parameterArray[1] instanceof Integer)){
            System.err.println("WRONG PARAMETERS FOR ActionPunish, expected integer as second element!");
            return;
        }

        boolean isCheater = (boolean) parameterArray[0];
        int id = (int) parameterArray[1];
        Player player = Game.getPlayers().get(id);
        if(isCheater){
            player.setCash(player.getCash()- Config.punishmentForCheating);
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE +":"+PREFIX_PLAYER_CHEATED+" 1 "+player.getNickname()+" "+player.getId()+" "+player.getCash());

            //TODO SET PLAYER FIELD "hasCheated" to true
        }else{
            player.setCash(player.getCash()- Config.punishmentForWrongReport);
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE +":"+PREFIX_PLAYER_CHEATED+" 0 "+player.getNickname()+" "+player.getId()+" "+player.getCash());
        }

        if(player.getCash() < 0){
            ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, player.getId());
        }
    }
}

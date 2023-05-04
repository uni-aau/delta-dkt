package ServerLogic;

import static ClientUIHandling.Constants.*;


import android.util.Log;

import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import network2.ServerNetworkClient;

public class ActionPayRent implements ServerActionInterface{
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        {
            Log.i("INFO", "TRIGGERED ACTION");
            int id = (int)parameters;
            if (! Game.getPlayers().containsKey(id)) {
                Log.e("ERROR","Wrong player id: " + id);
                return;
            }

            Player player = Game.getPlayers().get(id);


            int fieldLocation = player.getPosition().getLocation();
            System.out.println("LOCATION: "+fieldLocation);
            if(Game.getMap().getField(fieldLocation) instanceof Property){
                Property property = (Property) Game.getMap().getField(fieldLocation);

                //? moved here - from the GameMap.
                property.setOwner(Player.testInstance); 

                Player owner = property.getOwner();
                if(owner != null && !property.getOwner().equals(player)){
                    int rent = property.calculateRent();
                    if(player.getCash() < rent){
                        ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, player.getId());

                    }else{
                        player.payRentTo(owner, rent);



                        server.broadcast(GAMEVIEW_ACTIVITY_TYPE +":"+PREFIX_PLAYER_RENTPAID+" "+player.getNickname()+"(id= "+player.getId()+" ) "+player.getCash()+" "+owner.getNickname()+"(id= "+owner.getId()+" ) "+owner.getCash());
                    }

                }
            }

        }
    }
}

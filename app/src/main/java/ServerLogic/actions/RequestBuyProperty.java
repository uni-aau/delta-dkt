package ServerLogic.actions;

import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Field;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import network2.ServerNetworkClient;

import static ClientUIHandling.Constants.*;

public class RequestBuyProperty implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
            Log.d("INFO", "BuyPropertyAction");

            if (! Game.getPlayers().containsKey((int)parameters)) {
                Log.e("ERROR","The playerId provided to the BuyPropertyAction class does not exist. Id:  " + parameters.toString());
                return;
            }

            //fetch player object
            Player player = Game.getPlayers().get((int)parameters);

            int fieldLocation = player.getPosition().getLocation();

            Log.d(LOG_PROPERTY_BUY, "BuyProperty location: "+fieldLocation);

            Field field = Game.getMap().getField(fieldLocation);

            if(field instanceof Property &&  ((Property) field).getOwner() == null && ((Property) field).getPrice() <= player.getCash()){
                Property property = (Property) field;
                //player.buyProperty again checks if the values provided are valid (field isnt owned by anybody, player has enough cash etc.)
                boolean success = player.buyProperty(property.getLocation());
                if(success) {
                    ServerActionHandler.triggerAction(PREFIX_PROPLIST_UPDATE, fieldLocation); // initializes propertylist
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE +":"+PREFIX_PLAYER_PROPERTYBOUGHT+" "+player.getNickname()+"(id= "+player.getId()+" ) "+player.getCash()+" "+property.getName()+"(Pos= "+property.getLocation()+" )");
                }
                else{
                    throw new RuntimeException("BuyPropertyAction::execute():Failed to buy a property");
                }
            }
            else{
//                throw new IllegalArgumentException("Invalid arguments provided. Property on Players position cannot be bought.");
            }

        }
    }

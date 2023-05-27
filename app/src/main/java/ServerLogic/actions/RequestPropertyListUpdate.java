package ServerLogic.actions;

import java.util.ArrayList;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Field;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Property;
import network2.ServerNetworkClient;

public class RequestPropertyListUpdate implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        ArrayList<String> args = new ArrayList<>();
        ArrayList<Field> fields = Game.getMap().getFields();
        String owner;


        for (int i = 1; i < fields.size(); i++) {
            if (Game.getMap().getField(i) instanceof Property) {
                Property property = (Property) Game.getMap().getField(i);
                if (property.getOwner() == null) {
                    owner = "-/-";
                } else {
                    owner = property.getOwner().getNickname();
                }
                System.out.println("Prop: " + Game.getMap().getField(i).getName() + " / " + i);
                args.add(String.format("%d#%d#%d#%s#%d", i, property.getPrice(), property.getBaseRent(), owner, property.getHouses()));
            }
        }

        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_PROPLIST_UPDATE, args.toArray(new String[args.size()]));

    }
}

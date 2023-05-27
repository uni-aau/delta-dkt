package ServerLogic.actions;

import java.util.ArrayList;
import java.util.Locale;

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

        for (int i = 1; i < fields.size(); i++) {
            Field field = Game.getMap().getField(i);
            if (field instanceof Property) {
                Property property = (Property) field;
                String owner = (property.getOwner() == null) ? "-/-" : property.getOwner().getNickname();

                // Hint: Only a maximum of 1460-2000 letters can be sent via socket at once
                String arg = String.format(Locale.getDefault(), "%d#%d#%d#%s#%d", i, property.getPrice(), property.getBaseRent(), owner, property.getHouses());
                args.add(arg);
            }
        }
        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_PROPLIST_UPDATE, args.toArray(new String[args.size()]));
    }
}

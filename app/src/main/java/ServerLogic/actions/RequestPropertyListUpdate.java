package ServerLogic.actions;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

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
                args.add(String.format("%d#%s#%d", i, owner, property.getPrice()));
            }
        }

        Log.d("SERVER", "Test " + Arrays.toString(args.toArray(new String[args.size()])) + " Size:");

        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_PROPLIST_UPDATE, args.toArray(new String[args.size()]));

    }
}

package ClientUIHandling.actions;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import delta.dkt.logic.structure.Field;
import delta.dkt.logic.structure.GameMap;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;

class ActionBuyPropertyTest {

    @Test
    void execute() {
            Player p1 = new Player();

            p1.setCash(1000);

            GameMap g1 = new GameMap();
            Optional<Field> field1 = g1.getFields().stream().filter(x -> x instanceof Property).findFirst();
            Property prop = (Property) field1.get();

            //PropertyBoughtAction pba = new PropertyBoughtAction();
           // pba.execute(new AppCompatActivity(), GAMEVIEW_ACTIVITY_TYPE +":"+PREFIX_PLAYER_PROPERTYBOUGHT+" Mustermann(id= "+p1.getId()+" ) "+p1.getCash()+" "+prop.getName()+"(Pos= "+prop.getLocation()+" )");

    }
}
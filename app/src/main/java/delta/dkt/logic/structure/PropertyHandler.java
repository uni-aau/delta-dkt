package delta.dkt.logic.structure;

import static delta.dkt.logic.structure.PropertyLevel.CHEAP;
import static delta.dkt.logic.structure.PropertyLevel.NORMAL;
import static delta.dkt.logic.structure.PropertyLevel.PREMIUM;

/**
 * This class represents a PropertyHandler to handle all the properties.
 */
public class PropertyHandler {

    /**
     * Gets the property of a given position
     * @param position The position of the property.
     */
    public static Field getProperties(int position){

        Property bregenz1;
        Property bregenz2;
        Property bregenz3;
        Property graz1;
        Property graz2;
        Property graz3;
        Property eisenstadt1;
        Property eisenstadt2;
        Property wien1;
        Property wien2;
        Property wien3;
        Property linz1;
        Property linz2;
        Property linz3;
        Property salzburg1;
        Property salzburg2;
        Property salzburg3;
        Property klagenfurt1;
        Property klagenfurt2;
        Property klagenfurt3;
        Property innsbruck1;
        Property innsbruck2;
        Property innsbruck3;

        switch (position){
            case 2:{
                bregenz1 = new Property(2,220, 80, CHEAP, 160);
                bregenz1.setName("Amtsplatz");
                return bregenz1;
            }
            case 5: {
                graz1 = new Property(5, 300, 120, NORMAL, 200);
                graz1.setName("Murplatz");
                return graz1;
            }
            case 6: {
                graz2 = new Property(6, 250, 96, NORMAL, 150);
                graz2.setName("Annenstraße");
                return graz2;
            }
            case 7:{
                graz3 = new Property(7,220, 80, NORMAL, 130);
                graz3.setName("Joaneumring");
                return graz3;
            }
            case 10: {
                eisenstadt1 = new Property(10, 100, 24, CHEAP, 50);
                eisenstadt1.setName("Joseph-Haydn-Gasse");
                return eisenstadt1;
            }
            case 12: {
                eisenstadt2 = new Property(12, 220, 80, CHEAP, 160);
                eisenstadt2.setName("Schloßgrund");
                return eisenstadt2;
            }
            case 15:{
                wien1 = new Property(15,380, 200, PREMIUM, 220);
                wien1.setName("Kärntnerstraße");
                return wien1;
            }
            case 16:{
                wien2 = new Property(16,350, 160, PREMIUM, 220);
                wien2.setName("MariahilferStraße");
                return wien2;
            }
            case 17:{
                wien3 = new Property(17,250, 96, NORMAL, 150);
                wien3.setName("Kobenzlstraße");
                return wien3;
            }
            case 19:{
                linz1 = new Property(19,300, 120, NORMAL, 200);
                linz1.setName("Landstraße");
                return linz1;
            }
            case 20:{
                linz2 = new Property(20,180, 56, PREMIUM, 100);
                linz2.setName("Stifterstraße");
                return linz2;
            }
            case 22:{
                linz3 = new Property(22,220, 80, CHEAP, 160);
                linz3.setName("Museumstraße");
                return linz3;
            }
            case 25:{
                salzburg1 = new Property(25,250, 96, NORMAL, 150);
                salzburg1.setName("Mirabellplatz");
                return salzburg1;
            }
            case 26:{
                salzburg2 = new Property(26,240, 88, NORMAL, 140);
                salzburg2.setName("Westbahnstraße");
                return salzburg2;
            }
            case 27:{
                salzburg3 = new Property(27,250, 96, NORMAL, 150);
                salzburg3.setName("Universitätsplatz");
                return salzburg3;
            }
            case 29:{
                klagenfurt1 = new Property(29,140, 40, NORMAL, 100);
                klagenfurt1.setName("Burggasse");
                return klagenfurt1;
            }
            case 30:{
                klagenfurt2 = new Property(30,200, 64, NORMAL, 110);
                klagenfurt2.setName("Villacherstraße");
                return klagenfurt2;
            }
            case 32:{
                klagenfurt3 = new Property(32,210, 72, NORMAL, 120);
                klagenfurt3.setName("Alter Platz");
                return klagenfurt3;
            }
            case 35:{
                innsbruck1 = new Property(35,300, 120, NORMAL, 200);
                innsbruck1.setName("Maria-Theresien-Straße");
                return innsbruck1;
            }
            case 36:{
                innsbruck2 = new Property(36,250, 96, NORMAL, 150);
                innsbruck2.setName("Andreas-Hofer-Straße");
                return innsbruck2;
            }
            case 37:{
                innsbruck3 = new Property(37,300, 120, NORMAL, 200);
                innsbruck3.setName("Boznerplatz");
                return innsbruck3;
            }
            case 39:{
                bregenz2 = new Property(39,120, 32, NORMAL, 50);
                bregenz2.setName("Arlbergstraße");
                return bregenz2;
            }
            case 40:{
                bregenz3 = new Property(40,180, 56, PREMIUM, 100);
                bregenz3.setName("Rathausstraße");
                return bregenz3;
            }
            default: return null;
        }
    }

}

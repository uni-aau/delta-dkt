package delta.dkt.logic.structure;

import java.util.ArrayList;

public class Player {
    static int startCash = 500;
    private static int _id = 1;

    //? May be used to sync player data across clients
    private int id = Player._id++;
    private String nickname;

    private Field location = null; //todo -> set this to be the start field
    private int cash = Player.startCash;
    private ArrayList<Property> properties = new ArrayList<>();

    //? May be used to check whether a player is timeoutet, e.g. prison, or not.
    private int suspention = 0;

    public Player (String nickname) {
        this.nickname = nickname;
    }
}

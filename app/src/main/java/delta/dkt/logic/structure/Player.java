package delta.dkt.logic.structure;

import java.util.ArrayList;

public class Player {
    static int startCash = 500;
    private static int _id = 1;

    //? May be used to sync player data across clients
    private int id = Player._id++;
    private String nickname;

    private Field position = null; //todo -> set this to be the start field
    private int cash = Player.startCash;
    private ArrayList<Property> properties = new ArrayList<>();

    //? May be used to check whether a player is timeoutet, e.g. prison, or not.
    private int suspention = 0;

    public Player (String nickname) {
        this.nickname = nickname;
    }

    /**
     * Will time out this player from moving for a given amount of rounds.
     */
    public void setTimeout (int rounds) {
        this.suspention = rounds;
    }

    /**
     * @return Returns whether the player is prohibited from moving, thus has a suspension / timeout.
     */
    public boolean isTimeoutet () {
        return this.suspention > 0;
    }

    /**
     * This function may be called when a player is supposed to move, but is prohibited from doing so, thus the remaining amount of suspended rounds, can be decreased by 1.
     */
    public void reduceTimeout () {
        if (this.suspention > 0) this.suspention--;
    }

    /**
     * This function may be called when the player uses his 'jail-free-card' or receives any other action, that removes his suspension / timeout.
     */
    public void resetTimeout () {
        this.suspention = 0;
    }

}

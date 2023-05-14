package delta.dkt.logic.structure;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ClientUIHandling.Config;

class GameTest {




    void setUpReset() {
        Game.getPlayers().put(0, new Player());
        Game.setRounds(5);
        Game.setRoundStartID(3);
    }

    @Test
    void incrementRounds() {

        for (int playerId = 1; playerId < 6; playerId++) {
            for(int rounds = 0; rounds < Config.ENDROUNDS;rounds++){
                Game.incrementRounds(playerId);
            }
        }
        Game.incrementRounds(1);
        assertEquals(10, Game.getRounds());
    }

    @Test
    void reset() {
        setUpReset();
        Game.reset();
        assertTrue(Game.getPlayers().size()==0);
        assertTrue(Game.getRoundStartID() == -1);
        assertTrue(Game.getRounds() == 0);
    }
}
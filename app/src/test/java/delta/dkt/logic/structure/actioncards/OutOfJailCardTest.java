package delta.dkt.logic.structure.actioncards;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import delta.dkt.logic.structure.Player;

class OutOfJailCardTest {


    OutOfJailCard card;
    int cardId;
    String name;

    String description;

    @BeforeEach
    void setup() {
        cardId = 3;
        name = "Get-out-of-Jail Card";
        description = "Du kommst aus dem Gefängnis frei";
        card = new OutOfJailCard(cardId,name, description);
    }

    @Test
    void testOutOfJailCardConstructor(){
        assertEquals(cardId, card.getId());
        assertEquals(name, card.getName());
        assertEquals(description, card.getDescriptionString());
    }


    @Test
    void testExecutePlayerSideEffectsNoPreviousCard(){
        Player player = new Player();
        //initial situation is that player has no card
        player.setYouGetOutOfPrisonCard(false);
        card.execute(player);
        assertTrue(player.getYouGetOutOfPrisonCard());
    }


    /**
    @Test
    void testExecutePlayerSideEffectsPreviousCard(){
        Player player = new Player();
        //initial situation is that player has no card
        player.setYouGetOutOfPrisonCard(true);
        card.execute(player);
        assertEquals(player.getYouGetOutOfPrisonCard(), true);
    }

    @Test
    void testExecuteServerActionTrigger() {
        ;
        Player p = mock(Player.class);
        when(p.getId()).thenReturn(1);

        try (MockedStatic<ServerActionHandler> handler = Mockito.mockStatic(ServerActionHandler.class)) {
            handler.when(() -> ServerActionHandler.triggerAction(anyString(), anyInt()))
                    .thenReturn(null);
            card.execute(p);
            handler.verify(() -> ServerActionHandler.triggerAction(anyString(), anyInt()), times(1));
        }
    }
    */

}
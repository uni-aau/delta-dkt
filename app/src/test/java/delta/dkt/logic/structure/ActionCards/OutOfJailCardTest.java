package delta.dkt.logic.structure.ActionCards;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import ServerLogic.ServerActionHandler;
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
        assertEquals(card.getId(), cardId);
        assertEquals(card.getName(), name);
        assertEquals(card.getDescriptionString(), description);
    }


    @Test
    void testExecutePlayerSideEffectsNoPreviousCard(){
        Player player = new Player();
        //initial situation is that player has no card
        player.setYouGetOutOfPrisonCard(false);
        card.execute(player);
        assertEquals(player.getYouGetOutOfPrisonCard(), true);
    }


    @Test
    void testExecutePlayerSideEffectsPreviousCard(){
        Player player = new Player();
        //initial situation is that player has no card
        player.setYouGetOutOfPrisonCard(true);
        card.execute(player);
        assertEquals(player.getYouGetOutOfPrisonCard(), true);
    }

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

}
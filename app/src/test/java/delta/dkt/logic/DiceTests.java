package delta.dkt.logic;

import delta.dkt.logic.structure.Dice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DiceTests {

    Dice dice = null;

    @BeforeEach
    void setup(){
        dice = new Dice(1, 6);
    }

    /**
     * Checks whether a random value is calculated and if it is within the bound of the dice.
     */
    @Test
    void randomValueCheck(){
        //* Check multiple times
        for(int i = 0; i < 50; i++){
            int random = dice.getRandomValue();
            Assertions.assertTrue(random >= dice.getMin());
            Assertions.assertTrue(random <= dice.getMax());
        }
    }


}

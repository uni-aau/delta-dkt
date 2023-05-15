package delta.dkt.logic;

import delta.dkt.logic.structure.Dice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceTests {

    Dice dice = null;

    @BeforeEach
    void setup() {
        dice = new Dice(1, 6);
    }

    /**
     * Checks whether a random value is calculated and if it is within the bound of the dice.
     */
    @Test
    void randomValueCheck() {
        //* Check multiple times
        for (int i = 0; i < 50; i++) {
            int random = dice.getRandomValue();
            Assertions.assertTrue(random >= dice.getMin());
            Assertions.assertTrue(random <= dice.getMax());
        }
    }

    /**
     * Checks whether the random value of a modified dice is still withing its range.
     */
    @Test
    void randomValueCheck_cheatDice() {
        dice = new Dice(7, 10);
        //* Check multiple times
        for (int i = 0; i < 50; i++) {
            int random = dice.getRandomValue();
            Assertions.assertTrue(random >= dice.getMin());
            Assertions.assertTrue(random <= dice.getMax());
        }
    }

}

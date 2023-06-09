package delta.dkt.logic.structure;

public class Dice {
    private final int min;
    private final int max;

    public Dice(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    /**
     * Throws the dice and returns a random number.
     * @return Returns a random value that is within the range of the dice.
     */
    public int getRandomValue() {
        //START-NOSCAN
        return (int) (Math.random() * (this.getMax() - this.getMin() + 1) + this.getMin());
        //END-NOSCAN
    }

    public int getMax() {
        return max;
    }
}

package delta.dkt.logic.structure;

public class DiceRange {
    private int min, max;

    public DiceRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

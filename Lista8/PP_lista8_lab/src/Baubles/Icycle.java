package Baubles;

import Details.Color;
import Details.Pattern;

public class Icycle extends Bauble {
    protected static double DEFAULT_ICYCLE_LENGTH = 10.0;
    protected double icycleLength;

    public Icycle(Color color, Pattern pattern, double icycleLength) {
        super(color, pattern);
        if (icycleLength > 0.0) this.icycleLength = icycleLength;
        else throw new IllegalArgumentException();
    }

    public Icycle(Color color, Pattern pattern) {
        this(color, pattern, DEFAULT_ICYCLE_LENGTH);
    }

    public Icycle(Color color) {
        this(color, DEFAULT_BAUBLE_PATTERN);
    }

    public Icycle() {
        this(DEFAULT_BAUBLE_COLOR);
    }


    @Override
    public String toString() {
        return super.toString() + " in shape of Icycle";
    }
}

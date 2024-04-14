package Baubles;

import Details.Color;
import Details.Pattern;

public class BigBall extends Ball{
    protected boolean isGilded;

    public BigBall(Color color, Pattern pattern, double baubleRadius, boolean isGilded) {
        super(color, pattern, baubleRadius);
        this.isGilded = isGilded;
    }

    public BigBall(Color color, Pattern pattern, double baubleRadius) {
        this(color, pattern, baubleRadius, false);
    }


    public BigBall(Color color, Pattern pattern) {
        this(color, pattern, 2* DEFAULT_BALL_RADIUS);
    }

    public BigBall(Color color) {
        this(color, DEFAULT_BAUBLE_PATTERN);
    }

    public BigBall() {
        this(DEFAULT_BAUBLE_COLOR);
    }

    @Override
    public String toString() {
        return super.toString() + " and is BIIIIIIG (at least according to the manufacturer)";
    }
}

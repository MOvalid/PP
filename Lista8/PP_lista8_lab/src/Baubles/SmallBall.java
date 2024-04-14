package Baubles;

import Details.Color;
import Details.Pattern;

public class SmallBall extends Ball{
    protected boolean isFittedToHand;

    public SmallBall(Color color, Pattern pattern, double baubleRadius, boolean isFittedToHand) {
        super(color, pattern, baubleRadius);
        this.isFittedToHand = isFittedToHand;
    }

    public SmallBall(Color color, Pattern pattern, double baubleRadius) {
        this(color, pattern, baubleRadius, false);
    }


    public SmallBall(Color color, Pattern pattern) {
        this(color, pattern, DEFAULT_BALL_RADIUS);
    }

    public SmallBall(Color color) {
        this(color, DEFAULT_BAUBLE_PATTERN);
    }

    public SmallBall() {
        this(DEFAULT_BAUBLE_COLOR);
    }

    @Override
    public String toString() {
        return super.toString() + " and is small (at first glance)";
    }
}

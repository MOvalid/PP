package Baubles;

import Details.Color;
import Details.Pattern;

public abstract class Ball extends Bauble {

    protected static double DEFAULT_BALL_RADIUS = 3.0;
    protected final double baubleRadius;

    protected Ball(Color color, Pattern pattern, double baubleRadius) {
        super(color, pattern);
        if (baubleRadius > 0.0) this.baubleRadius = baubleRadius;
        else throw new IllegalArgumentException("Radius <= 0.0");
    }


    public Ball(Color color, Pattern pattern) {
        this(color, pattern, DEFAULT_BALL_RADIUS);
    }

    public Ball(Color color) {
        this(color, DEFAULT_BAUBLE_PATTERN);
    }

    public Ball() {
        this(DEFAULT_BAUBLE_COLOR);
    }


    @Override
    public String toString() {
        return super.toString() + " in shape of Ball (with radius = " + baubleRadius + " cm) ";
    }
}

package Baubles;

import Details.Color;
import Details.Pattern;

public abstract class Bauble {
    protected static final Color DEFAULT_BAUBLE_COLOR = Color.WHITE;
    protected static final Pattern DEFAULT_BAUBLE_PATTERN = Pattern.PLAIN;
    protected Color color;
    protected Pattern pattern;

    public Bauble(Color color, Pattern pattern) {
        this.color = color;
        this.pattern = pattern;
    }


    public Bauble(Color color) {
        this(color, DEFAULT_BAUBLE_PATTERN);
    }

    public Bauble() {
        this(DEFAULT_BAUBLE_COLOR);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "The " +  color + " bauble (pattern: " + pattern + ")";
    }
}

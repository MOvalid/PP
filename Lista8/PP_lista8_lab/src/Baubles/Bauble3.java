package Baubles;

import Details.BaubleType;
import Details.Color;
import Details.Pattern;

public class Bauble3 {
    protected static final Color DEFAULT_BAUBLE_COLOR = Color.WHITE;
    protected static final Pattern DEFAULT_BAUBLE_PATTERN = Pattern.PLAIN;
    protected static double DEFAULT_ICYCLE_LENGTH = 10.0;

    protected static double DEFAULT_BALL_RADIUS = 3.0;

    private final BaubleType baubleType;

    private Color color;

    private Pattern pattern;

    private double number;
    private boolean bool;

    public Bauble3(BaubleType baubleType, Color color, Pattern pattern, double number, boolean bool) {
        if (baubleType == BaubleType.MUSHROOM){
            throw new IllegalArgumentException("MUSHROOM is invalid BaubleType for this constructor!");
        }
        if(number <= 0.0){
            throw new IllegalArgumentException();
        }
        this.number = number;
        this.bool = bool;
        this.baubleType = baubleType;
        this.color = color;
        this.pattern = pattern;
    }

    public Bauble3(BaubleType baubleType, Color color, Pattern pattern, double number) {
        this(baubleType, color, pattern, number, false);
    }

    public Bauble3(BaubleType baubleType, Color color, Pattern pattern, boolean bool) {
        switch(baubleType) {
            case SMALL_BALL -> this.number = DEFAULT_BALL_RADIUS;
            case BIG_BALL -> this.number = 2 * DEFAULT_BALL_RADIUS;
            case ICYCLE -> this.number = DEFAULT_ICYCLE_LENGTH;
        }
        this.bool = bool;
        this.baubleType = baubleType;
        this.color = color;
        this.pattern = pattern;
    }

    public Bauble3(BaubleType baubleType, Color color, Pattern pattern) {
        this(baubleType, color, pattern, false);
    }

    public Bauble3(BaubleType baubleType, Color color) {
        this(baubleType, color, DEFAULT_BAUBLE_PATTERN);
    }

    public Bauble3(BaubleType baubleType) {
        this(baubleType, DEFAULT_BAUBLE_COLOR);
    }

    public BaubleType getBaubleType() {
        return baubleType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public boolean isGilded() {
        if(this.baubleType == BaubleType.BIG_BALL) return bool;
        else throw new IllegalStateException();
    }

    public void setGilded(boolean isGilded) {
        if(this.baubleType == BaubleType.BIG_BALL) bool = isGilded;
        else throw new IllegalStateException();
    }

    public boolean isFittedToHand() {
        if(this.baubleType == BaubleType.SMALL_BALL) return bool;
        else throw new IllegalStateException();
    }

    public void setFittedToHand(boolean isFittedToHand) {
        if(this.baubleType == BaubleType.SMALL_BALL) bool = isFittedToHand;
        else throw new IllegalStateException();
    }

    public boolean isMuchomorek() {
        if(this.baubleType == BaubleType.MUSHROOM) return bool;
        else throw new IllegalStateException();
    }

    public void setMuchomorek(boolean isMuchomorek) {
        if(this.baubleType == BaubleType.MUSHROOM) bool = isMuchomorek;
        else throw new IllegalStateException();
    }

    public double getBaubleRadius() {
        if(this.baubleType == BaubleType.BIG_BALL || this.baubleType == BaubleType.SMALL_BALL) return this.number;
        else throw new IllegalStateException();
    }

//    public void setBaubleRadius(double newRadius) {
//        if (newRadius <= 0.0) throw new IllegalArgumentException();
//        if(this.baubleType == BaubleType.BIG_BALL || this.baubleType == BaubleType.SMALL_BALL) this.number = newRadius;
//        else throw new IllegalStateException();
//    }

    public double getIcycleLength() {
        if(this.baubleType == BaubleType.ICYCLE) return this.number;
        else throw new IllegalStateException();
    }

    public void setIcycleLength(double newIcycleLength) {
        if (newIcycleLength <= 0.0) throw new IllegalArgumentException();
        if(this.baubleType == BaubleType.ICYCLE) this.number = newIcycleLength;
        else throw new IllegalStateException();
    }

    @Override
    public String toString() {
        String begin = "The " + color + " bauble (pattern: " + pattern + ")";
        return switch (this.baubleType){
            case BIG_BALL -> begin + ", which is a Big Ball with radius = " + number + " cm and is gilded: " + bool + "";
            case SMALL_BALL -> begin + ", which is a Small Ball with radius = " + number + " cm and is fitted to hand: " + bool + "";
            case ICYCLE -> begin + ", which is an Icycle with length = " + number + " cm";
            case MUSHROOM -> begin + ", which is a mushroom, that is Muchomorek: " + bool;
        };
    }
}

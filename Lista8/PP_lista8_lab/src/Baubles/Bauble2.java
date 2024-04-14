package Baubles;

import Details.*;

import java.util.ArrayList;
import java.util.List;

public class Bauble2 {

    protected static final Color DEFAULT_BAUBLE_COLOR = Color.WHITE;
    protected static final Pattern DEFAULT_BAUBLE_PATTERN = Pattern.PLAIN;

    protected static double DEFAULT_BALL_RADIUS = 3.0;

    protected static double DEFAULT_ICYCLE_LENGTH = 10.0;
    private final BaubleType baubleType;

    private Color color;

    private Pattern pattern;

    private List<Object> arg;


    public Bauble2(BaubleType baubleType, Color color, Pattern pattern, List<Object> arg){
        this.arg = new ArrayList<>();
        switch(arg.size()){
            case 0 -> initialArguments(baubleType);
            case 1 -> checkAndFill(arg, baubleType);
            case 2 -> checkAndSet(arg, baubleType);
            default -> throw new IllegalArgumentException();
        }
        this.baubleType = baubleType;
        this.color = color;
        this.pattern = pattern;
    }

    private void initialArguments(BaubleType baubleType){
        switch (baubleType){
            case BIG_BALL -> {
               this.arg.add(2 * DEFAULT_BALL_RADIUS);
               this.arg.add(true);
            }
            case SMALL_BALL -> {
                this.arg.add(DEFAULT_BALL_RADIUS);
                this.arg.add(true);
            }
            case MUSHROOM -> this.arg.add(true);
            case ICYCLE -> this.arg.add(DEFAULT_ICYCLE_LENGTH);
            default -> throw new IllegalStateException();
        }
    }

    private void checkAndFill(List<Object> arg, BaubleType baubleType){
        switch (baubleType){
            case BIG_BALL, SMALL_BALL-> {
                if (!(arg.get(0) instanceof Double)) throw new IllegalArgumentException();
                this.arg.add(arg.get(0));
                this.arg.add(true);
            }
            case ICYCLE -> {
                if (!(arg.get(0) instanceof Double)) throw new IllegalArgumentException();
                this.arg.add(arg.get(0));
            }
            case MUSHROOM -> {
                if (!(arg.get(0) instanceof Boolean)) throw new IllegalArgumentException();
                this.arg.add(arg.get(0));
            }
            default -> throw new IllegalStateException();
        }
    }
    private void checkAndSet(List<Object> arg, BaubleType baubleType){
        switch (baubleType){
            case BIG_BALL, SMALL_BALL-> {
                if (!(arg.get(0) instanceof Double && arg.get(1) instanceof Boolean)) throw new IllegalArgumentException();
                this.arg.add(arg.get(0));
                this.arg.add(arg.get(1));
            }
            default -> throw new IllegalStateException();
        }
    }
    public Bauble2(BaubleType baubleType, Color color, Pattern pattern){
        this(baubleType, color, pattern, new ArrayList<Object>());
    }

    public Bauble2(BaubleType baubleType, Color color){
        this(baubleType, color, DEFAULT_BAUBLE_PATTERN);
    }

    public Bauble2(BaubleType baubleType){
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
        if(this.baubleType == BaubleType.BIG_BALL) return (Boolean) this.arg.get(1);
        else throw new IllegalStateException();
    }

    public void setGilded(boolean isGilded) {
        if(this.baubleType == BaubleType.BIG_BALL) this.arg.set(1, isGilded);
        else throw new IllegalStateException();
    }

    public boolean isFittedToHand() {
        if(this.baubleType == BaubleType.SMALL_BALL) return (Boolean) this.arg.get(1);
        else throw new IllegalStateException();
    }

    public void setFittedToHand(boolean isFittedToHand) {
        if(this.baubleType == BaubleType.SMALL_BALL) this.arg.set(0, isFittedToHand);
        else throw new IllegalStateException();
    }

    public boolean isMuchomorek() {
        if(this.baubleType == BaubleType.MUSHROOM) return (Boolean) this.arg.get(0);
        else throw new IllegalStateException();
    }

    public void setMuchomorek(boolean isMuchomorek) {
        if(this.baubleType == BaubleType.MUSHROOM) this.arg.set(0, isMuchomorek);
        else throw new IllegalStateException();
    }

    public double getBaubleRadius() {
        if(this.baubleType == BaubleType.SMALL_BALL || this.baubleType == BaubleType.BIG_BALL) return (Double) this.arg.get(0);
        else throw new IllegalStateException();
    }

//    public void setBaubleRadius(double newRadius) {
//        if (newRadius <= 0.0) throw new IllegalArgumentException();
//        switch (this.baubleType){
//            case SMALL_BALL, BIG_BALL -> {
//                this.arg.set(0, newRadius);
//            }
//            default -> throw new IllegalStateException();
//        }
//    }

    public double getIcycleLength() {
        if(this.baubleType == BaubleType.ICYCLE) return (Double) this.arg.get(0);
        else throw new IllegalStateException();
    }

    public void setIcycleLength(double newIcycleLength) {
        if (newIcycleLength <= 0.0) throw new IllegalArgumentException();

        if(this.baubleType == BaubleType.MUSHROOM) this.arg.set(0, newIcycleLength);
        else throw new IllegalStateException();
    }

    @Override
    public String toString() {
        String begin = "The " + color + " bauble (pattern: " + pattern + ")";
        return switch (this.baubleType){
            case BIG_BALL -> begin + ", which is a Big Ball with radius = " + arg.get(0) + " cm and is gilded: " + arg.get(1);
            case SMALL_BALL -> begin + ", which is a Small Ball with radius = " + arg.get(0) + " cm and is fitted to hand: " + arg.get(1);
            case ICYCLE -> begin + ", which is an Icycle with length = " + arg.get(0) + " cm";
            case MUSHROOM -> begin + ", which is a mushroom, that is Muchomorek: " + arg.get(0);
        };
    }
}

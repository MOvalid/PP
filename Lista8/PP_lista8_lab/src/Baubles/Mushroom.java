package Baubles;

import Details.Color;
import Details.Pattern;

public class Mushroom extends Bauble{
    protected boolean isMuchomorek;

    public Mushroom(Color color, Pattern pattern, boolean isMuchomorek) {
        super(color, pattern);
        this.isMuchomorek = isMuchomorek;
    }

    public Mushroom(Color color, Pattern pattern) {
        this(color, pattern, true);
    }

    public Mushroom(Color color) {
        this(color, DEFAULT_BAUBLE_PATTERN);
    }

    public Mushroom() {
        this(DEFAULT_BAUBLE_COLOR);
    }


    @Override
    public String toString() {
        if(isMuchomorek) return super.toString() + " in shape of Muchomorek";

        return super.toString() + " in shape of Mushroom.";
    }
}

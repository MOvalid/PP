package Details;

public enum Pattern {
    PLAIN, METALLIC, FOLK, STARS, SNOWFLAKES, CHRISTMAS_TREES;

    @Override
    public String toString(){
        return switch (this) {
            case CHRISTMAS_TREES -> super.toString().toLowerCase().replaceFirst("_", " ");
            default -> super.toString().toLowerCase();
        };
    }
}

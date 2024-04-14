package Details;

public enum BaubleType {
    ICYCLE, SMALL_BALL, BIG_BALL, MUSHROOM;

    @Override
    public String toString(){
        return switch (this) {
            case SMALL_BALL, BIG_BALL -> super.toString().toLowerCase().replaceFirst("_", " ");
            default -> super.toString().toLowerCase();
        };
    }
}

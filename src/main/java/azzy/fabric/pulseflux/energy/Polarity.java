package azzy.fabric.pulseflux.energy;

public enum Polarity {
    POSITIVE,
    NEGATIVE,
    NEUTRAL,
    NONE;

    public static Polarity getOpposite(Polarity polarity) {
        switch (polarity) {
            case POSITIVE: return NEGATIVE;
            case NEGATIVE: return POSITIVE;
            case NONE: return NONE;
            default: return NEUTRAL;
        }
    }
}

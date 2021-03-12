package azzy.fabric.pulseflux.energy;

public enum Polarity {
    POSITIVE,
    NEGATIVE,
    NEUTRAL;

    public static Polarity getOpposite(Polarity polarity) {
        switch (polarity) {
            case POSITIVE: return NEGATIVE;
            case NEGATIVE: return POSITIVE;
            default: return NEUTRAL;
        }
    }
}

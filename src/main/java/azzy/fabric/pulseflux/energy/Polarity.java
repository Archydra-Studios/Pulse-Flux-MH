package azzy.fabric.pulseflux.energy;

import net.minecraft.client.util.math.Vector3f;

public enum Polarity {
    POSITIVE(93, 255, 160),
    NEGATIVE(255, 195, 69),
    NEUTRAL(255, 149, 240),
    NONE(0, 0, 0);

    public final Vector3f color;

    Polarity(int r, int g, int b) {
        this.color = new Vector3f(r / 255F, g / 255F, b / 255F);
    }

    public static Polarity getOpposite(Polarity polarity) {
        switch (polarity) {
            case POSITIVE: return NEGATIVE;
            case NEGATIVE: return POSITIVE;
            case NONE: return NONE;
            default: return NEUTRAL;
        }
    }

    public boolean matches(Polarity other) {
        return this == NONE || other == NONE || this == other;
    }
}

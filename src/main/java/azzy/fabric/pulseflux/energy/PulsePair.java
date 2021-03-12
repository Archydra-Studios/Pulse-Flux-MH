package azzy.fabric.pulseflux.energy;

import org.jetbrains.annotations.NotNull;

public class PulsePair {

    public static final PulsePair EMPTY = new PulsePair(0, 0);

    public final double inductance, frequency;

    public PulsePair(double inductance, double frequency) {
        this.inductance = inductance;
        this.frequency = frequency;
    }

    public double getMotive() {
        return frequency * inductance;
    }

    public static PulsePair of(@NotNull PulseCarrier carrier) {
        return new PulsePair(carrier.getInductance(), carrier.getFrequency());
    }

    public static boolean isEmpty(PulsePair pulsePair) {
        return pulsePair == null || pulsePair == EMPTY;
    }
}

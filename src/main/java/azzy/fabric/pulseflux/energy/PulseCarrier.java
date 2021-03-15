package azzy.fabric.pulseflux.energy;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Data class intended to carry information about Pulses.
 * Meant for use with {@link PulseIo}
 */
public final class PulseCarrier {

    public static final PulseCarrier EMPTY = new PulseCarrier();

    private double inductance;
    private double frequency;

    @NotNull
    public final Polarity polarity;

    @NotNull
    private final LongSet traveledBlocks = new LongOpenHashSet();

    /**
     * General use constructor for Pulse carriers.
     * @param inductance The Flux Inductance (Fi) carried by the Pulse. Fi = CFm / Hz
     * @param frequency The Hertz (Hz) of a pulse, not technically Hertz since it is per tick rather than per second. Hz = CFm / Fi
     * @param polarity The {@link Polarity} of the pulse, if this is null I will steal your kneecaps
     */
    public PulseCarrier(double inductance, double frequency, @NotNull Polarity polarity) {
        this.inductance = inductance;
        this.frequency = frequency;
        this.polarity = polarity;
    }

    /**
     * Special constructor for nbt serialization
     * @param inductance The Flux Inductance (Fi) carried by the Pulse. Fi = CFm / Hz
     * @param frequency The Hertz (Hz) of a pulse, not technically Hertz since it is per tick rather than per second. Hz = CFm / Fi
     * @param polarity A string to be parsed as a polarity
     * @param traversed A list of blocks already traveled in long form
     */
    public PulseCarrier(double inductance, double frequency, @NotNull String polarity, long[] traversed) {
        this(inductance, frequency, Polarity.valueOf(polarity));
        if(traversed != null)
            Arrays.stream(traversed).forEach(traveledBlocks::add);
    }

    /**
     * Constructs an empty Pulse carrier
     */
    public PulseCarrier() {
        this(0, 0, Polarity.NONE);
    }

    /**
     * Sums together two pulse carriers, the receiver is mutated
     * @param carrier The Pulse carrier to be added to this
     * @return Whether or not the operation could be performed
     */
    public boolean add(@NotNull PulseCarrier carrier) {
        if(isCompatible(carrier)) {
            this.inductance += carrier.inductance;
            this.frequency += carrier.frequency;
            this.traveledBlocks.addAll(carrier.traveledBlocks);
            return true;
        }
        return false;
    }

    /**
     * Subtracts two Pulse carriers, the receiver is mutated
     * @param carrier The Pulse carrier to subtract from this with
     * @return Whether or not the operation could be performed
     */
    public boolean sub(@NotNull PulseCarrier carrier) {
        if(isCompatible(carrier)) {
            this.inductance -= carrier.inductance;
            this.frequency -= carrier.frequency;
            return true;
        }
        return false;
    }

    /**
     * Returns the difference in inductance and frequency of two Pulse carriers, does not mutate
     * nor does this checks polarity
     * @param carrier The Pulse carrier to compare this with
     * @return A {@link PulsePair} containing the difference between the pulses' inductance and frequency
     */
    public PulsePair dif(@NotNull PulseCarrier carrier) {
        return new PulsePair(
                this.inductance - carrier.inductance,
                this.frequency - carrier.frequency
        );
    }

    /**
     * Checks whether two Pulse carrier's polarity are equal
     * @param carrier The Pulse carrier who's polarity is to be checked
     */
    public boolean isCompatible(@NotNull PulseCarrier carrier) {
        return this.polarity == carrier.polarity;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        if(frequency < 0) {
            throw new IllegalArgumentException("Frequency must not be negative!");
        }
        this.frequency = frequency;
    }

    public double getInductance() {
        return inductance;
    }

    public void setInductance(double inductance) {
        if(inductance < 0) {
            throw new IllegalArgumentException("Inductance must not be negative!");
        }
        this.inductance = inductance;
    }

    public double getMotive() {
        return frequency * inductance;
    }

    public boolean hasPassed(BlockPos pos) {
        return traveledBlocks.contains(pos.asLong());
    }

    public void markPassed(BlockPos pos) {
        traveledBlocks.add(pos.asLong());
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("Fi", inductance);
        tag.putDouble("Hz", frequency);
        tag.putString("polarity", polarity.name());
        tag.putLongArray("traveled", traveledBlocks.toArray(new long[0]));
        return tag;
    }

    public static boolean isEmpty(@NotNull PulseCarrier carrier) {
        return carrier == EMPTY || (carrier.frequency <= 0 && carrier.inductance <= 0);
    }

    public static PulseCarrier fromTag(@NotNull CompoundTag tag) {
        return new PulseCarrier(
                tag.getDouble("Fi"),
                tag.getDouble("Hz"),
                tag.getString("polarity"),
                tag.getLongArray("traveled")
        );
    }

}

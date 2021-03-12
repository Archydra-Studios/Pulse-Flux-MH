package azzy.fabric.pulseflux.energy;

import dev.technici4n.fasttransferlib.api.Simulation;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * An object that can hold and optionally take and provide mechanical energy.
 */
public interface PulseIo {
    
    /**
     * Get the stored pulse energy.
     */
    @NotNull PulseCarrier getPulse();

    /**
     * Get the maximum amount of mechanical energy that can be stored, or 0 if unsupported or unknown.
     */

    default @NotNull PulsePair getPulseCapacity() {
        return new PulsePair(0, 0);
    }

    /**
     * Return false if this object does not support insertion at all, meaning that insertion will always return the passed amount,
     * and insert-only cables should not connect.
     */
    default boolean supportsInsertion() {
        return false;
    }

    /**
     * Return false if this object does not support extraction at all, meaning that extraction will always return 0,
     * and extract-only cables should not connect.
     */
    default boolean supportsExtraction() {
        return false;
    }

    /**
     * Insert motive into this inventory, and return the amount of leftover motive.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param amount     The amount of mechanical energy to insert
     * @param simulation If {@link Simulation#SIMULATE}, do not mutate this object
     * @param direction The direction the insertion is being attempted from, this is in relation to the receiver
     * @throws IllegalArgumentException If the polarity of the pulses does not match
     * @return the amount of mechanical energy that could not be inserted
     */
    default PulsePair insert(@NotNull PulseCarrier amount, Direction direction, @NotNull Simulation simulation) {
        return PulsePair.of(amount);
    }

    /**
     * Extract motive from this inventory, and return the amount of leftover motive.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param maxAmount     The maximum amount of mechanical energy to extract
     * @param simulation 	If {@link Simulation#SIMULATE}, do not mutate this object
     * @param direction The direction the extraction is being attempted from, this is in relation to the provider
     * @throws IllegalArgumentException If the polarity of the pulses does not match
     * @return the amount of mechanical energy that was extracted
     */
    default PulsePair extract(@NotNull PulseCarrier maxAmount, Direction direction, @NotNull Simulation simulation) {
        return PulsePair.EMPTY;
    }
}

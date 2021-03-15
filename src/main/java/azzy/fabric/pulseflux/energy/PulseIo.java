package azzy.fabric.pulseflux.energy;

import dev.technici4n.fasttransferlib.api.Simulation;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * An object that can hold and optionally take and provide motive.
 */
public interface PulseIo {

    /**
     * Get the stored pulse energy.
     */
    @NotNull PulseCarrier getStoredPulse();

    /**
     * Get the maximum amount of motive that can be stored, or empty if unsupported or unknown.
     */
    default @NotNull PulsePair getPulseCapacity() {
        return PulsePair.EMPTY;
    }

    /**
     * Returns false if the querying object should not connect to this object. Used primarily for rendering.
     * @param direction The direction from which the query is being performed in relation to the receiver
     */
    default boolean shouldConnect(PulseCarrier pulse, @NotNull Direction direction, @NotNull MutationType type) {return false;}

    ///**
    // * Return false if this object does not support insertion at all, meaning that insertion will always return the passed amount,
    // * and insert-only cables should not connect.
    // */
    //default boolean supportsInsertion() {
    //    return false;
    //}
//
    ///**
    // * Return false if this object does not support extraction at all, meaning that extraction will always return empty,
    // * and extract-only cables should not connect.
    // */
    //default boolean supportsExtraction() {
    //    return false;
    //}

    /**
     * Insert motive into this inventory, and return the amount of leftover motive.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param amount     The amount of mechanical energy to insert
     * @param direction The direction the insertion is being attempted from, this is in relation to the receiver
     * @throws IllegalArgumentException If the polarity of the pulses does not match
     * @return Whether the insertion was successful
     */
    default boolean set(@NotNull PulseCarrier amount, Direction direction) {
        return false;
    }

    ///**
    // * Extract motive from this inventory, and return the amount of leftover motive.
    // *
    // * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
    // *
    // * @param maxAmount     The maximum amount of mechanical energy to extract
    // * @param simulation 	If {@link Simulation#SIMULATE}, do not mutate this object
    // * @param direction The direction the extraction is being attempted from, this is in relation to the provider
    // * @throws IllegalArgumentException If the polarity of the pulses does not match
    // * @return the amount of motive that was extracted
    // */
    //default PulsePair extract(@NotNull PulseCarrier maxAmount, Direction direction, @NotNull Simulation simulation) {
    //    return PulsePair.EMPTY;
    //}

    default Set<Direction> getInputs() {
        return Collections.emptySet();
    }

    default Set<Direction> getOutputs() {
        return Collections.emptySet();
    }

    default List<ActiveIO<?>> getActiveOutputs() {
        return Collections.emptyList();
    }

    default void setPrimaryInput(Direction output) {}

    default void setPrimaryOutput(Direction input) {}
}

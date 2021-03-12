package azzy.fabric.pulseflux.energy;

import dev.technici4n.fasttransferlib.api.Simulation;

/**
 * An object that can hold and optionally take and provide heat energy.
 */
public interface HeatIo {

    /**
     * Get the amount of heat stored.
     */
    double getTemperature();

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
     * Insert heat into this inventory, and return the amount of leftover heat.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param amount     The amount of heat to insert
     * @param simulation If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of heat that could not be inserted
     */
    default double heat(double amount, Simulation simulation) {
        return amount;
    }

    /**
     * Extract heat from this inventory, and return the amount of leftover heat.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param maxAmount     The maximum amount of heat to extract
     * @param simulation 	If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of heat that was extracted
     */
    default double cool(double maxAmount, Simulation simulation) {
        return 0;
    }
}

package azzy.fabric.pulseflux.energy;

import dev.technici4n.fasttransferlib.api.Simulation;

/**
 * An object that can hold and optionally take and provide mechanical energy.
 */
public interface MechanicalIo {
    
    /**
     * Get the mechanical energy stored.
     */
    double geMechanicalEnergy();

    /**
     * Get the maximum amount of mechanical energy that can be stored, or 0 if unsupported or unknown.
     */

    default double getMechanicalCapacity() {
        return 0;
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
     * Insert mechanical energy into this inventory, and return the amount of leftover mechanical energy.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param amount     The amount of mechanical energy to insert
     * @param simulation If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of mechanical energy that could not be inserted
     */
    default double insert(double amount, Simulation simulation) {
        return amount;
    }

    /**
     * Extract mechanical energy from this inventory, and return the amount of leftover mechanical energy.
     *
     * <p>If simulation is {@link Simulation#SIMULATE}, the result of the operation must be returned, but the underlying state of the object must not change.
     *
     * @param maxAmount     The maximum amount of mechanical energy to extract
     * @param simulation 	If {@link Simulation#SIMULATE}, do not mutate this object
     * @return the amount of mechanical energy that was extracted
     */
    default double extract(double maxAmount, Simulation simulation) {
        return 0;
    }
}

package net.azzy.pulseflux.blockentity.logistic.modulators;

import net.azzy.pulseflux.blockentity.logistic.ModulatorEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.MODULATOR_2_ENTITY;
import static net.azzy.pulseflux.registry.BlockEntityRegistry.MODULATOR_8_ENTITY;

public class Modulator8Entity extends ModulatorEntity {

    public Modulator8Entity(long maxInductance, double maxFrequency) {
        super(MODULATOR_8_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, 8, maxInductance, maxFrequency);
    }
}

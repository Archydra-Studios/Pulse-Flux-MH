package net.azzy.pulseflux.blockentity.logistic.modulators;

import net.azzy.pulseflux.blockentity.logistic.ModulatorEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.MODULATOR_2_ENTITY;
import static net.azzy.pulseflux.registry.BlockEntityRegistry.MODULATOR_4_ENTITY;

public class Modulator4Entity extends ModulatorEntity {

    public Modulator4Entity(long maxInductance, double maxFrequency) {
        super(MODULATOR_4_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, 4, maxInductance, maxFrequency);
    }
}

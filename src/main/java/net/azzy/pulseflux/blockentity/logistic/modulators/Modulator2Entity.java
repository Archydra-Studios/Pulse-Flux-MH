package net.azzy.pulseflux.blockentity.logistic.modulators;

import net.azzy.pulseflux.blockentity.logistic.ModulatorEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.entity.BlockEntityType;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.MODULATOR_2_ENTITY;

public class Modulator2Entity extends ModulatorEntity {

    public Modulator2Entity(long maxInductance, double maxFrequency) {
        super(MODULATOR_2_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, 2, maxInductance, maxFrequency);
    }
}

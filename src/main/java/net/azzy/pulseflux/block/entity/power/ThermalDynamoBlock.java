package net.azzy.pulseflux.block.entity.power;

import net.azzy.pulseflux.block.entity.PulseCarryingDirectionalBlock;
import net.azzy.pulseflux.blockentity.power.ThermalDynamoEntity;

import java.util.function.Supplier;

public class ThermalDynamoBlock extends PulseCarryingDirectionalBlock<ThermalDynamoEntity> {

    public ThermalDynamoBlock(Settings settings, Supplier<ThermalDynamoEntity> blockEntitySupplier) {
        super(settings, blockEntitySupplier, true);
    }
}

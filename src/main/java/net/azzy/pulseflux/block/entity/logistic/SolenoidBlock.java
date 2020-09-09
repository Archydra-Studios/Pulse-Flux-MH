package net.azzy.pulseflux.block.entity.logistic;

import net.azzy.pulseflux.block.entity.PulseCarryingBlock;
import net.azzy.pulseflux.blockentity.logistic.FailingPulseCarryingEntity;

import java.util.function.Supplier;

public class SolenoidBlock <T extends FailingPulseCarryingEntity> extends PulseCarryingBlock<T>
{
    public SolenoidBlock(Settings settings, Supplier<T> blockEntitySupplier) {
        super(settings, blockEntitySupplier, true);
    }
}


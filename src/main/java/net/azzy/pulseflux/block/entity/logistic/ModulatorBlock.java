package net.azzy.pulseflux.block.entity.logistic;

import net.azzy.pulseflux.block.entity.PulseCarryingDirectionalBlock;
import net.minecraft.block.entity.BlockEntity;

import java.util.function.Supplier;

public class ModulatorBlock<T extends BlockEntity> extends PulseCarryingDirectionalBlock<T> {

    public ModulatorBlock(Settings settings, Supplier<T> blockEntitySupplier) {
        super(settings, blockEntitySupplier, true);
    }
}

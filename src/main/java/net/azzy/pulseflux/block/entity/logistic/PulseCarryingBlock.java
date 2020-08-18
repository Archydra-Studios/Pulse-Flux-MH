package net.azzy.pulseflux.block.entity.logistic;

import net.azzy.pulseflux.block.MultiFacingBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class PulseCarryingBlock <T extends BlockEntity> extends MultiFacingBlock implements BlockEntityProvider {

    protected final Supplier<T> blockEntitySupplier;

    public PulseCarryingBlock(Settings settings, Supplier<T> blockEntitySupplier) {
        super(settings);
        this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return blockEntitySupplier.get();
    }
}

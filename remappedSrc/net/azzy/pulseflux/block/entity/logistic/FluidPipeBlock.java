package net.azzy.pulseflux.block.entity.logistic;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.function.Supplier;

import static net.azzy.pulseflux.registry.BlockRegistry.BASIC_LIQUID_PIPE;

public class FluidPipeBlock extends PipeBlock implements BlockEntityProvider{

    private final Supplier<BlockEntity> blockEntitySupplier;

    public FluidPipeBlock(Settings settings, Supplier<BlockEntity> blockEntitySupplier) {
        super(settings);
        this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return blockEntitySupplier.get();
    }
}

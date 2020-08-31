package net.azzy.pulseflux.block.entity.logistic;

import net.azzy.pulseflux.block.MultiFacingBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.LinkedList;
import java.util.List;
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

    @Override
    public List<Direction> getIO(BlockState state) {
        List<Direction> io = new LinkedList<>();
        for(Direction direction : Direction.values())
            if(state.get(FACING.get(direction)))
                io.add(direction);
            return io;
    }

    @Override
    public List<Direction> getInputs(BlockState state) {
        return getIO(state);
    }

    @Override
    public List<Direction> getOutputs(BlockState state) {
        return getIO(state);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}

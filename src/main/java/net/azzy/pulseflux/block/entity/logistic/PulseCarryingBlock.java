package net.azzy.pulseflux.block.entity.logistic;

import net.azzy.pulseflux.block.MultiFacingBlock;
import net.azzy.pulseflux.blockentity.PulseRenderingEntityImpl;
import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity;
import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.azzy.pulseflux.util.interaction.MultiFacingRotatableBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class PulseCarryingBlock <T extends BlockEntity> extends MultiFacingBlock implements BlockEntityProvider, MultiFacingRotatableBlock {

    protected final Supplier<T> blockEntitySupplier;
    private final boolean horizontal;

    public PulseCarryingBlock(Settings settings, Supplier<T> blockEntitySupplier, boolean horizontal) {
        super(settings);
        this.blockEntitySupplier = blockEntitySupplier;
        this.horizontal = horizontal;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING.get(horizontal ? ctx.getPlayerFacing().getOpposite() : ctx.getPlayerLookDirection().getOpposite()), true);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity entity = world.getBlockEntity(pos);
        for(Direction direction : Direction.values()){
            if(state.get(FACING.get(direction))) {
                if(entity instanceof PulseRenderingEntityImpl)
                    ((PulseRenderingEntityImpl) entity).recalcIO(direction, state, true);
                else if(entity instanceof CreativePulseSourceEntity)
                    ((CreativePulseSourceEntity) entity).recalcIO(direction);
            }
        }
    }

    @Override
    public void setInput(World world, BlockPos pos, ItemUsageContext ctx) {
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof PulseRenderingEntityImpl) {
            ((PulseRenderingEntityImpl) entity).setIO(horizontal ? ctx.getPlayerFacing().getOpposite() : ctx.getSide(), world.getBlockState(pos), pos, true);
            ((PulseRenderingEntityImpl) entity).requestDisplay();
        }
    }

    @Override
    public void setOutput(World world, BlockPos pos, ItemUsageContext ctx) {
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof PulseRenderingEntityImpl) {
            ((PulseRenderingEntityImpl) entity).setIO(horizontal ? ctx.getPlayerFacing().getOpposite() : ctx.getSide(), world.getBlockState(pos), pos, false);
            ((PulseRenderingEntityImpl) entity).requestDisplay();
        }
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
    public BlockEntity createBlockEntity(BlockView world) {
        return blockEntitySupplier.get();
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

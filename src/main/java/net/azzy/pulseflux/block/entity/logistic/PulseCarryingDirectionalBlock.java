package net.azzy.pulseflux.block.entity.logistic;

import net.azzy.pulseflux.blockentity.PulseRenderingEntityImpl;
import net.azzy.pulseflux.util.energy.BlockNode;
import net.azzy.pulseflux.util.interaction.RotatableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class PulseCarryingDirectionalBlock <T extends BlockEntity> extends FacingBlock implements BlockEntityProvider, BlockNode, RotatableBlock {

    protected final Supplier<T> blockEntitySupplier;
    private final boolean horizontal;

    protected PulseCarryingDirectionalBlock(Settings settings, Supplier<T> blockEntitySupplier, boolean horizontal) {
        super(settings);
        this.blockEntitySupplier = blockEntitySupplier;
        this.horizontal = horizontal;
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING, (horizontal ? ctx.getPlayerFacing().getOpposite() : ctx.getPlayerLookDirection().getOpposite()));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof PulseRenderingEntityImpl)
            ((PulseRenderingEntityImpl) entity).recalcIO(true, state.get(FACING), state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void rotate(World world, BlockPos pos, PlayerEntity playerEntity) {
        BlockState state = world.getBlockState(pos);
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof PulseRenderingEntityImpl) {
            ((PulseRenderingEntityImpl) entity).recalcIO(true, horizontal ? playerEntity.getHorizontalFacing().getOpposite() : Direction.getEntityFacingOrder(playerEntity)[0].getOpposite(), state);
            ((PulseRenderingEntityImpl) entity).requestDisplay();
        }
    }

    @Override
    public List<Direction> getIO(BlockState state) {
        List<Direction> io = new LinkedList<>();
        Direction facing = state.get(FACING);
        io.add(facing);
        io.add(facing.getOpposite());
        return io;
    }

    @Override
    public List<Direction> getInputs(BlockState state) {
        return Collections.singletonList(state.get(FACING).getOpposite());
    }

    @Override
    public List<Direction> getOutputs(BlockState state) {
        return Collections.singletonList(state.get(FACING));
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

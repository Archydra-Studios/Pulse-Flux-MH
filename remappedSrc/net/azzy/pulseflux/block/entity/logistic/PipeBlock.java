package net.azzy.pulseflux.block.entity.logistic;


import net.azzy.pulseflux.block.MultiFacingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.*;

import static net.azzy.pulseflux.registry.BlockRegistry.BASIC_LIQUID_PIPE;

public abstract class PipeBlock extends MultiFacingBlock {

    public static final BooleanProperty CENTER = BooleanProperty.of("center");
    public static final DirectionProperty DIRECTION = Properties.FACING;

    public PipeBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public List<Direction> getIO(BlockState state) {
        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CENTER, DIRECTION);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = Block.createCuboidShape(0, 0, 0, 0, 0, 0);
        if(state.get(CENTER))
            shape = VoxelShapes.union(shape, Block.createCuboidShape(5, 5, 5, 11, 11, 11));
        if(state.get(FACING.get(Direction.NORTH)))
            shape = VoxelShapes.union(shape, Block.createCuboidShape(6, 6, 0, 10, 10, 5));
        if(state.get(FACING.get(Direction.EAST)))
            shape = VoxelShapes.union(shape, Block.createCuboidShape(11, 6, 6, 16, 10, 10));
        if(state.get(FACING.get(Direction.SOUTH)))
            shape = VoxelShapes.union(shape, Block.createCuboidShape(6, 6, 11, 10, 10, 16));
        if(state.get(FACING.get(Direction.WEST)))
            shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 6, 6, 5, 10, 10));
        if(state.get(FACING.get(Direction.UP)))
            shape = VoxelShapes.union(shape, Block.createCuboidShape(6, 11, 6, 10, 16, 10));
        if(state.get(FACING.get(Direction.DOWN)))
            shape = VoxelShapes.union(shape, Block.createCuboidShape(6, 0, 6, 10, 5, 10));
        Direction middleDir = state.get(DIRECTION);
        if(middleDir == Direction.NORTH || middleDir == Direction.SOUTH)
            shape = VoxelShapes.union(shape, Block.createCuboidShape(6, 6, 5, 10, 10, 11));
        else if(middleDir == Direction.EAST || middleDir == Direction.WEST)
            shape = VoxelShapes.union(shape, Block.createCuboidShape(5, 6, 6, 11, 10, 10));
        else if(middleDir == Direction.UP || middleDir == Direction.DOWN)
            shape = VoxelShapes.union(shape, Block.createCuboidShape(6, 5, 6, 10, 11, 10));
        return shape;
    }
}

package net.azzy.pulseflux.block;

import net.azzy.pulseflux.block.BaseMachine;
import net.azzy.pulseflux.util.energy.BlockNode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class MultiFacingBlock extends Block implements BlockNode {

    protected static final HashMap<Direction, BooleanProperty> FACING = new HashMap<>();

    public MultiFacingBlock(Settings settings) {
        super(settings);
        for(Direction direction : Direction.values())
            this.setDefaultState(getDefaultState().with(FACING.get(direction), false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        for(Direction direction : Direction.values())
            builder.add(FACING.get(direction));
    }

    public static HashMap<Direction, BooleanProperty> getFACING() {
        return FACING;
    }

    static {
        for(Direction direction : Direction.values()){
            FACING.put(direction, BooleanProperty.of(direction.getName()));
        }
    }

    @Override
    public List<Direction> getInputs(BlockState state) {
        return new LinkedList<>();
    }

    @Override
    public List<Direction> getOutputs(BlockState state) {
        return new LinkedList<>();
    }
}

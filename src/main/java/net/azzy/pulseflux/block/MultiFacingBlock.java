package net.azzy.pulseflux.block;

import net.azzy.pulseflux.block.BaseMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.HashMap;

public abstract class MultiFacingBlock extends Block {

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
}

package azzy.fabric.pulseflux.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import org.jetbrains.annotations.Nullable;


public class PulseFluxFacingBlock extends PulseFluxBlock {

    public PulseFluxFacingBlock(Settings settings, boolean loggable) {
        super(settings, loggable);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState baseState = super.getPlacementState(ctx);
        if(baseState == null)
            baseState = getDefaultState();
        return baseState.with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.FACING);
    }
}

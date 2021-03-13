package azzy.fabric.pulseflux.block.logistics;

import azzy.fabric.pulseflux.block.PulseFluxBlock;
import azzy.fabric.pulseflux.blockentity.logistics.DiodeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DiodeBlock extends PulseFluxBlock implements BlockEntityProvider {

    public static final DirectionProperty INPUT = DirectionProperty.of("input", Direction.values());
    public static final DirectionProperty OUTPUT = DirectionProperty.of("output", Direction.values());

    @NotNull
    private final Supplier<DiodeBlockEntity> entitySupplier;

    public DiodeBlock(Settings settings, @NotNull Supplier<DiodeBlockEntity> entitySupplier) {
        super(settings, true);
        this.entitySupplier = entitySupplier;
        this.setDefaultState(getDefaultState().with(INPUT, Direction.NORTH).with(OUTPUT, Direction.SOUTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(INPUT, OUTPUT);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return entitySupplier.get();
    }
}

package azzy.fabric.pulseflux.block.logistics;

import azzy.fabric.pulseflux.block.PulseFluxBlock;
import azzy.fabric.pulseflux.blockentity.logistics.FluidPipeBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FluidPipeBlock extends PulseFluxBlock implements BlockEntityProvider {

    private final Supplier<FluidPipeBlockEntity> pipeBlockEntitySupplier;

    public FluidPipeBlock(Settings settings, Supplier<FluidPipeBlockEntity> pipeProvider) {
        super(settings, true);
        this.pipeBlockEntitySupplier = pipeProvider;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return pipeBlockEntitySupplier.get();
    }
}

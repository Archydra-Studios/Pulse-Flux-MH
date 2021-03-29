package azzy.fabric.pulseflux.block.logistics;

import azzy.fabric.pulseflux.block.PulseFluxBlock;
import azzy.fabric.pulseflux.blockentity.logistics.CreativeFluidSourceBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class CreativeFluidSourceBlock extends PulseFluxBlock implements BlockEntityProvider {

    public CreativeFluidSourceBlock(Settings settings) {
        super(settings, true);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new CreativeFluidSourceBlockEntity();
    }
}

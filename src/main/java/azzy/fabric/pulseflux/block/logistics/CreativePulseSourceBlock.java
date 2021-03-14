package azzy.fabric.pulseflux.block.logistics;

import azzy.fabric.pulseflux.block.PulseFluxFacingBlock;
import azzy.fabric.pulseflux.blockentity.logistics.CreativePulseSourceBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class CreativePulseSourceBlock extends PulseFluxFacingBlock implements BlockEntityProvider {

    public CreativePulseSourceBlock(Settings settings) {
        super(settings, true);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new CreativePulseSourceBlockEntity();
    }
}

package azzy.fabric.pulseflux.blockentity.logistics;

import azzy.fabric.pulseflux.energy.PulseCarrier;
import azzy.fabric.pulseflux.energy.PulseIo;
import azzy.fabric.pulseflux.util.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Tickable;
import org.jetbrains.annotations.NotNull;

public class DiodeBlockEntity extends BlockEntity implements Tickable, PulseIo {

    private final Material material;

    @NotNull
    private PulseCarrier pulse = PulseCarrier.EMPTY;

    public DiodeBlockEntity(BlockEntityType<?> type, Material material) {
        super(type);
        this.material = material;
    }

    @Override
    public void tick() {

    }

    @Override
    public @NotNull PulseCarrier getStoredPulse() {
        return pulse;
    }

    @Override
    public boolean supportsInsertion() {
        return true;
    }

    @Override
    public boolean supportsExtraction() {
        return true;
    }
}

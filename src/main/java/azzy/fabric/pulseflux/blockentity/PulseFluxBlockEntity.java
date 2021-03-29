package azzy.fabric.pulseflux.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public abstract class PulseFluxBlockEntity extends BlockEntity implements Tickable {

    protected final Direction[] DIRECTIONS = Direction.values();
    private boolean initialized;

    public PulseFluxBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    protected abstract void tickInner();

    protected abstract void initialize();

    @Override
    public void tick() {
        tickInner();
        if(world != null && !world.isClient() && !initialized) {
            initialized = true;
            initialize();
            markDirty();
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putBoolean("initalized", initialized);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        initialized = tag.getBoolean("initalized");
        super.fromTag(state, tag);
    }
}

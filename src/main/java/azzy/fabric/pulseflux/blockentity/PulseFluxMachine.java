package azzy.fabric.pulseflux.blockentity;

import azzy.fabric.pulseflux.PulseFluxCommon;
import azzy.fabric.pulseflux.util.UpdateTimeProvider;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.world.World;

public class PulseFluxMachine extends BlockEntity implements BlockEntityClientSerializable, UpdateTimeProvider {

    protected long lastUpdateTime;
    protected final int tickOffset = PulseFluxCommon.PFRandom.nextInt(0, 20);

    public PulseFluxMachine(BlockEntityType<?> type) {
        super(type);
    }

    @Override
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public void update(World world) {
        lastUpdateTime = world.getTime();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putLong("lastUpdate", lastUpdateTime);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        lastUpdateTime = tag.getLong("lastUpdate");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putLong("lastUpdate", lastUpdateTime);
        return tag;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        lastUpdateTime = tag.getLong("lastUpdate");
    }
}

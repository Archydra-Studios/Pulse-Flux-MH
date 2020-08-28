package net.azzy.pulseflux.blockentity;

import net.azzy.pulseflux.client.util.IORenderingEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Supplier;

public abstract class IORenderingEntityImpl extends PulseEntity implements IORenderingEntity {

    protected int renderTickTime;
    protected boolean renderInit = true;

    public IORenderingEntityImpl(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, Supplier<DefaultedList<ItemStack>> invSupplier) {
        super(type, material, invSupplier);
    }

    @Override
    public void tick() {
        super.tick();
        renderIO();
    }

    protected void renderIO(){
        if(renderInit){
            renderTickTime += 5;
        }
        else if(renderTickTime > 0) {
            renderTickTime -= 2;
        }

        if(renderTickTime >= 120){
            renderInit = false;
        }
        else if(renderTickTime < 0){
            renderTickTime = 0;
        }
    }

    @Override
    public void requestDisplay() {
        renderInit = true;
    }

    @Override
    public int getRenderTickTime() {
        return renderTickTime;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putBoolean("rendertick", renderInit);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        renderInit = tag.getBoolean("rendertick");
        super.fromTag(state, tag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        renderInit = compoundTag.getBoolean("rendertick");
        super.fromClientTag(compoundTag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putBoolean("rendertick", renderInit);
        return super.toClientTag(compoundTag);
    }
}

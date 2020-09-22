package net.azzy.pulseflux.blockentity;

import net.azzy.pulseflux.client.util.PulseOffsetEntity;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public abstract class PulseGeneratingEntity extends IORenderingEntityImpl implements PulseNode, PulseOffsetEntity {

    protected Direction output;

    public PulseGeneratingEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, Supplier<DefaultedList<ItemStack>> invSupplier) {
        super(type, material, invSupplier);
    }

    public void setOutput(Direction output){
        this.output = output;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(output != null)
            tag.putString("out", output.getName());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        output = Direction.byName(tag.getString("out"));
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        if(output != null)
            compoundTag.putString("out", output.getName());
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        output = Direction.byName(compoundTag.getString("out"));
        super.fromClientTag(compoundTag);
    }

    @Override
    public int getPixelOffset() {
        return 0;
    }

    @Override
    public Collection<Direction> getOutputs() {
        return Collections.singleton(output);
    }

    @Override
    public Collection<Direction> getRenderOutputs() {
        return getOutputs();
    }

    @Override
    public void accept(Direction direction, BlockPos senders) {
    }

    @Override
    public long getInductance() {
        return inductance;
    }

    @Override
    public long getMaxInductance() {
        return -1;
    }

    @Override
    public Polarity getPolarity() {
        return polarity;
    }

    @Override
    public double getFrequency() {
        return frequency;
    }

    @Override
    public double getMaxFrequency() {
        return -1;
    }

    @Override
    public boolean canFail() {
        return false;
    }

    @Override
    public boolean isOverloaded() {
        return false;
    }
}

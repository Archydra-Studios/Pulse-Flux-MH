package net.azzy.pulseflux.blockentity;

import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.PulseNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Supplier;

public abstract class PulseEntity extends MachineEntity{

    protected long inductance;
    protected PulseNode.Polarity polarity = PulseNode.Polarity.NEUTRAL;
    protected double maxDistance;
    protected double frequency;

    public PulseEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, Supplier<DefaultedList<ItemStack>> invSupplier) {
        super(type, material, invSupplier);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putLong("amplitude", inductance);
        tag.putString("polarity", polarity.name());
        tag.putDouble("maxdistance", maxDistance);
        tag.putDouble("frequency", frequency);

        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        inductance = tag.getLong("amplitude");
        polarity = PulseNode.Polarity.valueOf(tag.getString("polarity"));
        maxDistance = tag.getDouble("maxdistance");
        frequency = tag.getDouble("frequency");
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        inductance = compoundTag.getLong("amplitude");
        polarity = PulseNode.Polarity.valueOf(compoundTag.getString("polarity"));
        maxDistance = compoundTag.getDouble("maxdistance");
        frequency = compoundTag.getDouble("frequency");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putLong("amplitude", inductance);
        compoundTag.putString("polarity", polarity.name());
        compoundTag.putDouble("maxdistance", maxDistance);
        compoundTag.putDouble("frequency", frequency);
        return compoundTag;
    }

}

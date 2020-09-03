package net.azzy.pulseflux.blockentity;

import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class PulseEntity extends MachineEntity{

    protected long inductance;
    protected PulseNode.Polarity polarity = PulseNode.Polarity.NEUTRAL;
    protected double maxDistance;
    protected double frequency;
    protected boolean receivedPower;
    protected final List<Direction> inputs, outputs;

    public PulseEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, Supplier<DefaultedList<ItemStack>> invSupplier) {
        super(type, material, invSupplier);
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
    }


    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putLong("amplitude", inductance);
        tag.putString("polarity", polarity.name());
        tag.putDouble("maxdistance", maxDistance);
        tag.putDouble("frequency", frequency);

        int a = inputs.size();
        int b = outputs.size();

        tag.putInt("directionsin", a);
        tag.putInt("directionsout", b);

        for(Direction direction : inputs){
            tag.putString("in"+a, direction.getName());
            a--;
        }
        for(Direction direction :outputs){
            tag.putString("in"+b, direction.getName());
            b--;
        }

        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        inductance = tag.getLong("amplitude");
        polarity = PulseNode.Polarity.valueOf(tag.getString("polarity"));
        maxDistance = tag.getDouble("maxdistance");
        frequency = tag.getDouble("frequency");

        int a = tag.getInt("directionsin");
        int b = tag.getInt("directionsout");
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

    protected void clearPower(){
        if(world.isClient())
            return;
        inductance = 0;
        frequency = 0;
    }

}

package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.LinkedList;
import java.util.List;

public abstract class DiodeEntity extends FailingPulseCarryingEntity {

    protected final List<Direction> io = new LinkedList<>();
    protected Direction lastInput;

    public DiodeEntity(BlockEntityType<?> type, long maxAmplitude, double maxFrequency) {
        super(type, HeatTransferHelper.HeatMaterial.STEEL, () -> DefaultedList.ofSize(0, ItemStack.EMPTY), maxAmplitude, maxFrequency);
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return null;
    }

    @Override
    public double getArea() {
        return 1;
    }

    @Override
    public void accept(long amplitude, Direction direction, Polarity polarity, BlockPos sender) {
        if(io.contains(direction.getOpposite())){
            this.amplitude = amplitude;
            this.polarity = polarity;
            this.lastInput = direction.getOpposite();
        }
    }

    @Override
    public long getAmplitude() {
        return amplitude;
    }

    @Override
    public Polarity getPolarity() {
        return polarity;
    }

    @Override
    public double getFrequency() {
        return frequency;
    }

    public Direction getLastInput(){
        return lastInput;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(lastInput != null)
            tag.putString("lastin", lastInput.getName());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        lastInput = Direction.byName(tag.getString("lastin"));
        super.fromTag(state, tag);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}

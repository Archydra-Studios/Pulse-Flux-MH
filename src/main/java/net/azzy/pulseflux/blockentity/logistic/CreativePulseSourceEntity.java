package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.block.entity.logistic.LinearDiodeBlock;
import net.azzy.pulseflux.blockentity.PulseEntity;
import net.azzy.pulseflux.client.util.IORenderingEntity;
import net.azzy.pulseflux.util.gui.ExtendedPropertyDelegate;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.azzy.pulseflux.util.networking.Syncable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collection;
import java.util.Collections;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.CREATIVE_PULSE_SOURCE;

public class CreativePulseSourceEntity extends PulseEntity implements PulseNode, Syncable, IORenderingEntity {

    private Direction output;
    private int renderTickTime;
    private boolean renderInit = true;

    public CreativePulseSourceEntity() {
        super(CREATIVE_PULSE_SOURCE, HeatTransferHelper.HeatMaterial.AIR, () -> DefaultedList.ofSize(0, ItemStack.EMPTY));
        inductance = 1000;
        frequency = 2000;
        polarity = Polarity.NEUTRAL;
    }

    @Override
    public void tick() {
        super.tick();
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
        if(output == null){
            for(Direction direction : Direction.values())
                if(getCachedState().get(LinearDiodeBlock.getFACING().get(direction)))
                    output = direction;
        }
    }

    public void cyclePolarity(){
        int ordinal = polarity.ordinal() + 1;
        if(ordinal > 2)
            ordinal = 0;
        polarity = Polarity.values()[ordinal];
    }

    public void recalcIO(Direction direction){
        output = direction;
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return delegate;
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public void accept(Direction direction, BlockPos sender) {
    }

    @Override
    public long getInductance() {
        return inductance;
    }

    @Override
    public long getMaxInductance() {
        return 0;
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
        return 0;
    }

    @Override
    public boolean canFail() {
        return false;
    }

    @Override
    public boolean isOverloaded() {
        return false;
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

    PropertyDelegate delegate = new ExtendedPropertyDelegate() {

        @Override
        public long getLong(int index) {
            return inductance;
        }

        @Override
        public void setLong(int index, long value) {
            if(self.getWorld().isClient()) {
                inductance = value;
            }
        }

        @Override
        public Double getDouble(int index) {
            return frequency;
        }

        @Override
        public void setDouble(int index, double value) {
            if(self.getWorld().isClient()) {
                frequency = value;
            }
        }

        @Override
        public BlockPos getpos() {
            return pos;
        }
    };

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("output", output.name());
        tag.putBoolean("rendertick", renderInit);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        output = Direction.byName(tag.getString("name"));
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

    @Override
    public void syncrhonize(SyncPacket packet) {
        this.inductance = (long) packet.unpack();
        this.frequency = (double) packet.unpack();
    }

    @Override
    public Collection<Direction> getInputs() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<Direction> getOutputs() {
        return Collections.singleton(output);
    }

    @Override
    public int getRenderTickTime() {
        return renderTickTime;
    }

    @Override
    public void requestDisplay() {
        renderInit = true;
    }

    @Override
    public Direction getOutput() {
        return output;
    }
}

package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.block.entity.logistic.LinearDiodeBlock;
import net.azzy.pulseflux.blockentity.PulseEntity;
import net.azzy.pulseflux.util.gui.ExtendedPropertyDelegate;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.PulseNode;
import net.azzy.pulseflux.util.networking.Syncable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.CREATIVE_PULSE_SOURCE;

public class CreativePulseSourceEntity extends PulseEntity implements PulseNode, Syncable {

    private BlockPos cachedOutput;
    private Direction facing;

    public CreativePulseSourceEntity() {
        super(CREATIVE_PULSE_SOURCE, HeatTransferHelper.HeatMaterial.AIR, () -> DefaultedList.ofSize(1, ItemStack.EMPTY));
        inductance = 1000;
        frequency = 2000;
        polarity = Polarity.NEUTRAL;
    }

    @Override
    public void tick() {
        super.tick();
        if(facing == null)
            fetchFacing();
        if(cachedOutput == null){
            BlockPos scanPos = pos;
            for(int i = 1; i <= 16; i++){
                scanPos = scanPos.offset(facing);
                BlockEntity entity = world.getBlockEntity(scanPos);
                if(entity instanceof PulseNode){
                    cachedOutput = scanPos;
                }
            }
        }
        else {
            BlockEntity entity = world.getBlockEntity(cachedOutput);
            if(entity instanceof PulseNode)
                offer(world, this, inductance, frequency, polarity, inventory.get(0), facing, cachedOutput, -1);
        }
    }

    public void fetchFacing(){
        facing = LinearDiodeBlock.getIOFacing(getCachedState()).get(0);
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
    public void accept(long amplitude, double frequency, Polarity polarity, ItemStack medium, Direction direction, BlockPos sender) {
    }

    @Override
    public long getAmplitude() {
        return inductance;
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
    public Item getMedium() {
        return inventory.get(0).getItem();
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
    public void syncrhonize(SyncPacket packet) {
        this.inductance = (long) packet.unpack();
        this.frequency = (double) packet.unpack();
    }
}

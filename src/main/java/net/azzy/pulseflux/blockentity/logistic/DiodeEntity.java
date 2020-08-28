package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.block.entity.logistic.LinearDiodeBlock;
import net.azzy.pulseflux.client.util.PulseRenderingEntity;
import net.azzy.pulseflux.util.energy.BlockNode;
import net.azzy.pulseflux.util.energy.IOScans;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collection;
import java.util.Collections;

import static net.azzy.pulseflux.PulseFlux.PFLog;
import static net.azzy.pulseflux.PulseFlux.PFRandom;

public abstract class DiodeEntity extends FailingPulseCarryingEntity implements PulseRenderingEntity {

    protected BlockPos cachedInput;
    protected BlockState cachedInputState;
    protected int pulseTickTime;
    protected int delay = PFRandom.nextInt(19);

    public DiodeEntity(BlockEntityType<?> type, long maxAmplitude, double maxFrequency) {
        super(type, HeatTransferHelper.HeatMaterial.STEEL, () -> DefaultedList.ofSize(1, ItemStack.EMPTY), maxAmplitude, maxFrequency);
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
    public void tick() {
        super.tick();
        if(pulseTickTime > 0){
            pulseTickTime -= 2;
        }
        else if(pulseTickTime < 0){
            pulseTickTime = 0;
        }
        if(!world.isClient() && (world.getTime() + delay) % 20 == 0) {
            cachedInput = null;
        }
        if(input != null && cachedInput == null) {
            cachedInput = IOScans.seekInputNode(pos, input, world);
            if(cachedInput != null) {
                if(checkIO(input, cachedInput))
                    cachedInputState = world.getBlockState(cachedInput);
                else{
                    cachedInput = null;
                    clearPower();
                }
            }
        }
        if(cachedInput != null && world.getBlockState(cachedInput) != cachedInputState)
            cachedInput = null;
        if(cachedInput != null) {
            if (cachedInputState == null) {
                cachedInputState = world.getBlockState(cachedInput);
            }
            else if(!world.isClient() &&  !cachedInputState.isOf(world.getBlockState(cachedInput).getBlock())){
                cachedInput = null;
                clearPower();
            }
        }
        if (!world.isClient() && cachedInput != null && simulate(world, world.getBlockEntity(cachedInput), getMedium(), false, this, 16) && checkIO(input, cachedInput)) {
            if(world.getTime() % 10 == 0)
                accept(input, cachedInput);
            if(inductance > 0 && frequency > 0)
                playTransferSound();
        }
        else if(cachedInput == null && world.getTime() % 10 == 0){
            clearPower();
        }
    }

    @Override
    public void accept(Direction direction, BlockPos sender) {
        PulseNode node = (PulseNode) world.getBlockEntity(sender);
        if(checkIO(direction, sender)){
            long flux = node.getInductance();
            inductance = flux > maxAmplitude ? world.getRandom().nextInt((int) Math.max((flux - maxAmplitude) / 100, 10)) == 0 ? flux : maxAmplitude : flux;
            frequency = Math.min(node.getFrequency(), maxFrequency);
            polarity = node.getPolarity();
            inventory.set(0, new ItemStack(node.getMedium()));
            if(inductance != 0 && frequency != 0)
                pulseTickTime = 100;
        }
        else
            clearPower();
    }

    public void playTransferSound(){
        if(world.getTime() % 20 == 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 0.125f, 0.25f);

        }
        else if(world.getTime() % 10 == 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 0.065f, 0.55f);
        }
        if(world.getTime() % 5 == 0)
            world.playSound(null, pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 0.2f, 2f);
    }

    public boolean checkIO(Direction direction, BlockPos sender){
        BlockState state = world.getBlockState(sender);
        return state.getBlock() instanceof BlockNode && ((BlockNode) state.getBlock()).getIO(state).contains(direction.getOpposite());
    }

    public void recalcIO(Direction direction, BlockState state, boolean io){
        if(io){
            output = direction;
            input = IOScans.seekInputDir(pos, world, output, 16);
            if(input == null)
                input = direction.getOpposite();
            world.setBlockState(pos, state.with(LinearDiodeBlock.getFACING().get(direction.getOpposite()), true));
        }
        else if(input == null && output != null){
            for(Direction facing : Direction.values())
                if(output != facing && state.get(LinearDiodeBlock.getFACING().get(facing))){
                    input = facing;
                    break;
                }
        }
        else if(output == null && input != null){
            for(Direction facing : Direction.values())
                if(input != facing && state.get(LinearDiodeBlock.getFACING().get(facing))){
                    output = facing;
                    break;
                }
        }
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putInt("alpha", pulseTickTime);
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        pulseTickTime = compoundTag.getInt("alpha");
        super.fromClientTag(compoundTag);
    }

    @Override
    public double getPulseDistance() {
        if(cachedInput != null)
            return pos.getManhattanDistance(cachedInput);
        else
            return 0;
    }

    @Override
    public Direction getPulseDirection() {
        return input;
    }

    @Override
    public Polarity getPulsePolarity() {
        return polarity;
    }

    @Override
    public float getPulseAlpha() {
        return pulseTickTime / 100f;
    }

    @Override
    public Collection<Direction> getInputs() {
        return Collections.singleton(input);
    }

    @Override
    public Collection<Direction> getOutputs() {
        return Collections.singleton(output);
    }

    @Override
    protected void clearPower() {
        if(!world.isClient()) {
            super.clearPower();
            inventory.set(0, ItemStack.EMPTY);
        }
    }

    @Override
    public Item getMedium() {
        return inventory.get(0).getItem();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(cachedInput != null)
            tag.putLong("cachedin", cachedInput.asLong());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        cachedInput = BlockPos.fromLong(tag.getLong("cachedin"));
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

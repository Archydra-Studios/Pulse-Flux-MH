package net.azzy.pulseflux.blockentity;

import net.azzy.pulseflux.block.entity.logistic.LinearDiodeBlock;
import net.azzy.pulseflux.client.util.PulseRenderingEntity;
import net.azzy.pulseflux.util.energy.BlockNode;
import net.azzy.pulseflux.util.energy.IOScans;
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
import java.util.Set;
import java.util.function.Supplier;

import static net.azzy.pulseflux.PulseFlux.PFRandom;

public abstract class PulseRenderingEntityImpl extends IORenderingEntityImpl implements PulseRenderingEntity, PulseNode {

    protected Direction input, output;
    final short range;
    protected int pulseTickTime;
    protected int delay = PFRandom.nextInt(19);
    protected BlockPos cachedInput;
    protected BlockState cachedInputState;

    public PulseRenderingEntityImpl(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, short range, Supplier<DefaultedList<ItemStack>> invSupplier) {
        super(type, material, invSupplier);
        this.range = range;
    }

    @Override
    public void tick() {
        super.tick();
        if(pulseTickTime > 0){
            pulseTickTime -= 4;
        }
        else if(pulseTickTime < 0){
            pulseTickTime = 0;
        }
        performIOPermutations();
    }

    protected void performIOPermutations(){
        if((world.getTime() + delay) % 20 == 0) {
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
        if (!world.isClient() && cachedInput != null && simulate(world, world.getBlockEntity(cachedInput), false, this, 16) && checkIO(input, cachedInput)) {
            if(world.getTime() % 10 == 0)
                accept(input, cachedInput);
            if(inductance > 0 && frequency > 0)
                playTransferSound();
        }
        else if(cachedInput == null && world.getTime() % 10 == 0){
            clearPower();
        }
    }

    protected abstract void playTransferSound();

    public boolean checkIO(Direction direction, BlockPos sender){
        BlockState state = world.getBlockState(sender);
        return state.getBlock() instanceof BlockNode && ((BlockNode) state.getBlock()).getIO(state).contains(direction.getOpposite());
    }

    public void recalcIO(Direction direction, BlockState state, boolean io){
        if(io){
            output = direction;
            input = IOScans.seekInputDir(pos, world, output, range);
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
    public Set<Direction> getPulseDirections() {
        return Collections.singleton(input);
    }

    @Override
    public Polarity getPulsePolarity(Direction direction) {
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
}

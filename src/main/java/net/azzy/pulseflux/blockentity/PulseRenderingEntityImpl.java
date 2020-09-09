package net.azzy.pulseflux.blockentity;

import net.azzy.pulseflux.block.MultiFacingBlock;
import net.azzy.pulseflux.block.entity.logistic.DiodeBlock;
import net.azzy.pulseflux.block.entity.PulseCarryingBlock;
import net.azzy.pulseflux.block.entity.PulseCarryingDirectionalBlock;
import net.azzy.pulseflux.client.util.PulseOffsetEntity;
import net.azzy.pulseflux.client.util.PulseRenderingEntity;
import net.azzy.pulseflux.util.energy.IOScans;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static net.azzy.pulseflux.PulseFlux.PFRandom;

public abstract class PulseRenderingEntityImpl extends IORenderingEntityImpl implements PulseRenderingEntity, PulseNode, PulseOffsetEntity {

    protected Direction input, output;
    protected final short range;
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
        if (!world.isClient() && cachedInput != null) {
            if(world.getTime() % 10 == 0)
                accept(input, cachedInput);
            if(inductance > 0 && frequency > 0)
                playTransferSound();
        }
        else if(cachedInput == null && world.getTime() % 10 == 0){
            clearPower();
        }
    }

    protected void playTransferSound(){
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
        BlockEntity node = world.getBlockEntity(sender);
        return node instanceof PulseNode && ((PulseNode) node).getOutputs().contains(direction.getOpposite());
    }

    public void recalcIO(Direction direction, BlockState state, boolean io){
        if(io){
            output = direction;
            input = IOScans.seekInputDir(pos, world, output, range);
            if(input == null)
                input = direction.getOpposite();
            if(state.getBlock() instanceof PulseCarryingBlock)
                world.setBlockState(pos, state.with(PulseCarryingBlock.getFACING().get(input), true).with(PulseCarryingBlock.getFACING().get(output), true));
            else if(state.getBlock() instanceof FacingBlock)
                world.setBlockState(pos, state.with(PulseCarryingDirectionalBlock.FACING, direction.getOpposite()), 3);
        }
    }

    public void recalcIO(boolean straight, Direction direction, BlockState state){
        output = direction;
        input = direction.getOpposite();
        if(state.getBlock() instanceof MultiFacingBlock)
            world.setBlockState(pos, state.with(DiodeBlock.getFACING().get(input), true));
        else if(state.getBlock() instanceof FacingBlock)
            world.setBlockState(pos, state.with(PulseCarryingDirectionalBlock.FACING, direction.getOpposite()), 3);
    }

    public void setIO(Direction direction, BlockState state, BlockPos pos, boolean in){
        if(in){
            if(direction == input || direction == output){
                Direction temp = input;
                input = output;
                output = temp;
            }
            else {
                input = direction;
                MultiFacingBlock.clearFacing(world, pos);
                world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(input), true).with(MultiFacingBlock.getFACING().get(output), true));
            }
        }
        else {
            if(direction == input || direction == output){
                Direction temp = input;
                input = output;
                output = temp;
            }
            else {
                output = direction;
                MultiFacingBlock.clearFacing(world, pos);
                world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(input), true).with(MultiFacingBlock.getFACING().get(output), true));
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
    public PulseNode getSender(Direction direction) {
        return cachedInput != null ? (PulseNode) world.getBlockEntity(cachedInput) : null;
    }

    @Override
    public int getPixelOffset() {
        return 0;
    }

    @Override
    public double getPulseDistance(Direction direction) {
        if(cachedInput != null)
            return pos.getManhattanDistance(cachedInput);
        else
            return 0;
    }

    @Override
    public Set<Direction> getPulseDirections() {
        Set<Direction> directions = new HashSet<>();
        directions.addAll(getInputs());
        return directions;
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

    @Override
    public Collection<Direction> getRenderInputs() {
        return getInputs();
    }

    @Override
    public Collection<Direction> getRenderOutputs() {
        return getOutputs();
    }
}

package net.azzy.pulseflux.blockentity.logistic.misc;

import net.azzy.pulseflux.block.MultiFacingBlock;
import net.azzy.pulseflux.block.entity.PulseCarryingBlock;
import net.azzy.pulseflux.blockentity.logistic.FailingPulseCarryingEntity;
import net.azzy.pulseflux.util.energy.IOScans;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.ScrewableEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.SOLENOID_MERGING_ENTITY;
import static net.azzy.pulseflux.registry.BlockRegistry.SOLENOID_SPLIT;

public class SolenoidMergingEntity extends FailingPulseCarryingEntity implements ScrewableEntity {

    private Direction input2;
    private BlockPos cachedInput2;
    private BlockState cachedInputState2;

    public SolenoidMergingEntity() {
        super(SOLENOID_MERGING_ENTITY, HeatTransferHelper.HeatMaterial.CERAMIC, (short) 9, () -> DefaultedList.ofSize(0, ItemStack.EMPTY), -1, -1, true);
    }

    @Override
    public void recalcIO(Direction direction, BlockState state, boolean io) {
        if(io){
            output = direction;
            input = IOScans.seekInputDir(pos, world, output, range);
            if(input == null)
                input = direction.getOpposite();
            input2 = input.rotateYClockwise();
            if(state.getBlock() instanceof PulseCarryingBlock)
                world.setBlockState(pos, state.with(PulseCarryingBlock.getFACING().get(input), true).with(PulseCarryingBlock.getFACING().get(output), true).with(PulseCarryingBlock.getFACING().get(input2), true));
        }
    }

    @Override
    protected void performIOPermutations() {
        if((world.getTime() + delay) % 20 == 0) {
            cachedInput = null;
            cachedInput2 = null;
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
        if(input2 != null && cachedInput2 == null) {
            cachedInput2 = IOScans.seekInputNode(pos, input2, world);
            if(cachedInput2 != null) {
                if(checkIO(input2, cachedInput2))
                    cachedInputState2 = world.getBlockState(cachedInput2);
                else{
                    cachedInput2 = null;
                    clearPower();
                }
            }
        }
        if(cachedInput != null && world.getBlockState(cachedInput) != cachedInputState)
            cachedInput = null;
        if(cachedInput2 != null && world.getBlockState(cachedInput2) != cachedInputState2)
            cachedInput2 = null;
        if(cachedInput != null) {
            if (cachedInputState == null) {
                cachedInputState = world.getBlockState(cachedInput);
            }
            else if(!world.isClient() &&  !cachedInputState.isOf(world.getBlockState(cachedInput).getBlock())){
                cachedInput = null;
                clearPower();
            }
        }
        if(cachedInput != null) {
            if (cachedInputState == null) {
                cachedInputState = world.getBlockState(cachedInput);
            }
            else if(!world.isClient() &&  !cachedInputState.isOf(world.getBlockState(cachedInput).getBlock())){
                cachedInput = null;
                clearPower();
            }
        }
        if(cachedInput2 != null) {
            if (cachedInputState2 == null) {
                cachedInputState2 = world.getBlockState(cachedInput2);
            }
            else if(!world.isClient() && !cachedInputState2.isOf(world.getBlockState(cachedInput2).getBlock())){
                cachedInput2 = null;
                clearPower();
            }
        }
        if (!world.isClient() && (cachedInput != null || cachedInput2 != null)) {
            if(world.getTime() % 10 == 0)
                internalAccept();
            if(inductance > 0 && frequency > 0)
                playTransferSound();
        }
        else if(cachedInput == null && cachedInput2 == null && world.getTime() % 10 == 0){
            clearPower();
        }
    }

    private void internalAccept(){
        if((input != null && cachedInput != null) && (input2 != null && cachedInput2 != null)){
            PulseNode node = (PulseNode) world.getBlockEntity(cachedInput);
            PulseNode node2 = (PulseNode) world.getBlockEntity(cachedInput2);
            inductance = (long) (node.getInductance()  * node.getPulseMultiplier());
            frequency = node.getFrequency();
            polarity = node.getPolarity();
            if(frequency == node2.getFrequency() && polarity == node2.getPolarity()){
                inductance += (node2.getInductance() * node2.getPulseMultiplier());
                if(inductance != 0 && frequency != 0)
                    pulseTickTime = 100;
            }
            else {
                clearPower();
            }
        }
        else{
            Direction in = input == null ? input2 : input;
            BlockPos pos = input == null ? cachedInput2 : cachedInput;
            if(in != null && pos != null && world.getBlockEntity(pos) instanceof PulseNode){
                PulseNode singletNode = (PulseNode) world.getBlockEntity(pos);
                inductance = (long) (singletNode.getInductance() * singletNode.getPulseMultiplier());
                frequency = singletNode.getFrequency();
                polarity = singletNode.getPolarity();
                if(inductance != 0 && frequency != 0)
                    pulseTickTime = 100;
            }
            else
                clearPower();
        }
    }

    @Override
    public void setIO(Direction direction, BlockState state, BlockPos pos, boolean in) {
        if(in){
            if(direction == input || direction == output){
                Direction temp = input;
                input = output;
                input2 = output.rotateYClockwise();
                output = temp;
            }
            else {
                input = direction;
                input2 = direction.rotateYClockwise();
                MultiFacingBlock.clearFacing(world, pos);
                world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(input), true).with(MultiFacingBlock.getFACING().get(output), true).with(PulseCarryingBlock.getFACING().get(input2), true));
            }
        }
        else {
            if(direction == input || direction == output){
                Direction temp = input;
                input = output;
                input2 = output.rotateYClockwise();
                output = temp;
            }
            else {
                output = direction;
                MultiFacingBlock.clearFacing(world, pos);
                world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(input), true).with(MultiFacingBlock.getFACING().get(output), true).with(PulseCarryingBlock.getFACING().get(input2), true));
            }
        }
    }

    @Override
    public double getPulseDistance(Direction direction) {
        if(direction == input && cachedInput != null){
            return pos.getManhattanDistance(cachedInput);
        }
        else if(direction == input2 && cachedInput2 != null)
            return pos.getManhattanDistance(cachedInput2);
        return 0;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(input2 != null)
            tag.putString("input2", input2.getName());
        if(cachedInput2 != null)
            tag.putLong("cachedin2", cachedInput2.asLong());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        input2 = Direction.byName(tag.getString("input2"));
        cachedInput2 = BlockPos.fromLong(tag.getLong("cachedin2"));
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        if(input2 != null)
            compoundTag.putString("input2", input2.getName());
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        input2 = Direction.byName(compoundTag.getString("input2"));
        super.fromClientTag(compoundTag);
    }

    @Override
    public PulseNode getSender(Direction direction) {
        if(direction == input && cachedInput != null){
            return (PulseNode) world.getBlockEntity(cachedInput);
        }
        else if(direction == input2 && cachedInput2 != null){
            return (PulseNode) world.getBlockEntity(cachedInput2);
        }
        return null;
    }

    @Override
    public Collection<Direction> getInputs() {
        return Arrays.asList(input, input2);
    }

    @Override
    public Set<Direction> getPulseDirections() {
        return super.getPulseDirections();
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return null;
    }

    @Override
    public double getArea() {
        return 0.5;
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

    @Override
    public void onScrewed(PlayerEntity entity) {
        world.setBlockState(pos, SOLENOID_SPLIT.getDefaultState());
        ((SolenoidSplittingEntity) world.getBlockEntity(pos)).forceSetDirs(input, input2, output);
        entity.sendMessage(new TranslatableText("block.pulseflux.solenoid.mode_change.split"), true);
    }

    public void forceSetDirs(Direction in, Direction in2, Direction out){
        input = in;
        input2 = in2;
        output = out;
        world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(input), true).with(MultiFacingBlock.getFACING().get(output), true).with(PulseCarryingBlock.getFACING().get(input2), true));
    }
}

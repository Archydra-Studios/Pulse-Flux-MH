package net.azzy.pulseflux.blockentity.logistic.misc;

import net.azzy.pulseflux.block.MultiFacingBlock;
import net.azzy.pulseflux.block.entity.PulseCarryingBlock;
import net.azzy.pulseflux.blockentity.logistic.FailingPulseCarryingEntity;
import net.azzy.pulseflux.util.energy.IOScans;
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

import java.util.*;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.SOLENOID_SPLITTING_ENTITY;
import static net.azzy.pulseflux.registry.BlockRegistry.SOLENOID_MERGE;

public class SolenoidSplittingEntity extends FailingPulseCarryingEntity implements ScrewableEntity {

    private Direction output2;

    public SolenoidSplittingEntity() {
        super(SOLENOID_SPLITTING_ENTITY, HeatTransferHelper.HeatMaterial.CERAMIC, (short) 9, () -> DefaultedList.ofSize(0, ItemStack.EMPTY), -1, -1, true);
    }

    @Override
    public void recalcIO(Direction direction, BlockState state, boolean io) {
        if(io){
            output = direction;
            output2 = direction.rotateYClockwise();
            Set<Direction> outputs = new HashSet<>();
            outputs.add(output);
            outputs.add(output2);
            input = IOScans.seekInputDir(pos, world, outputs, range);
            if(input == null)
                input = direction.getOpposite();
            if(state.getBlock() instanceof PulseCarryingBlock)
                world.setBlockState(pos, state.with(PulseCarryingBlock.getFACING().get(input), true).with(PulseCarryingBlock.getFACING().get(output), true).with(PulseCarryingBlock.getFACING().get(output2), true));
        }
    }

    @Override
    public void setIO(Direction direction, BlockState state, BlockPos pos, boolean in) {
        if(in){
            if(direction == input || direction == output){
                Direction temp = input;
                input = output;
                output = temp;
                output2 = temp.rotateYClockwise();
            }
            else {
                input = direction;
                MultiFacingBlock.clearFacing(world, pos);
                world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(input), true).with(MultiFacingBlock.getFACING().get(output), true).with(PulseCarryingBlock.getFACING().get(output2), true));
            }
        }
        else {
            if(direction == input || direction == output){
                Direction temp = input;
                input = output;
                output = temp;
                output2 = temp.rotateYClockwise();
            }
            else {
                output = direction;
                output2 = direction.rotateYClockwise();
                MultiFacingBlock.clearFacing(world, pos);
                world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(input), true).with(MultiFacingBlock.getFACING().get(output), true).with(PulseCarryingBlock.getFACING().get(output2), true));
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(output2 != null)
            tag.putString("output2", output2.getName());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        output2 = Direction.byName(tag.getString("output2"));
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        if(output2 != null)
            compoundTag.putString("output2", output2.getName());
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        output2 = Direction.byName(compoundTag.getString("output2"));
        super.fromClientTag(compoundTag);
    }

    @Override
    public double getPulseMultiplier() {
        return 0.5;
    }

    @Override
    public Collection<Direction> getOutputs() {
        return Arrays.asList(output, output2);
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
        world.setBlockState(pos, SOLENOID_MERGE.getDefaultState());
        ((SolenoidMergingEntity) world.getBlockEntity(pos)).forceSetDirs(output, output2, input);
        entity.sendMessage(new TranslatableText("block.pulseflux.solenoid.mode_change.merge"), true);
    }

    public void forceSetDirs(Direction out, Direction out2, Direction in){
        output = out;
        output2 = out2;
        input = in;
        world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(input), true).with(MultiFacingBlock.getFACING().get(output), true).with(PulseCarryingBlock.getFACING().get(output2), true));
    }
}

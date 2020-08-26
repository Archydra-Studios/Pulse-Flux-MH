package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.block.MultiFacingBlock;
import net.azzy.pulseflux.block.entity.logistic.LinearDiodeBlock;
import net.azzy.pulseflux.client.util.IORenderingEntity;
import net.azzy.pulseflux.util.energy.BlockNode;
import net.azzy.pulseflux.util.energy.IOScans;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static net.azzy.pulseflux.PulseFlux.PFRandom;

public abstract class DiodeEntity extends FailingPulseCarryingEntity implements IORenderingEntity {

    protected Direction input, output;
    protected BlockPos cachedInput;
    protected BlockState cachedInputState;
    protected int delay = PFRandom.nextInt(19), renderTickTime;
    private boolean renderInit = true;

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
        if(output != null && cachedInput == null) {
            cachedInput = IOScans.seekInputNode(pos, input, world);
            if(cachedInput != null)
                cachedInputState = world.getBlockState(cachedInput);
        }
        if(cachedInput != null) {
            if (cachedInputState == null) {
                cachedInputState = world.getBlockState(cachedInput);
            }
            else if(!world.isClient() &&  !cachedInputState.isOf(world.getBlockState(cachedInput).getBlock())){
                cachedInput = null;
                clearPower();
            }
            if(!world.isClient() && (world.getTime() + delay) % 20 == 0) {
                cachedInput = null;
                clearPower();
            }
            if (!world.isClient() && cachedInput != null && simulate(world, world.getBlockEntity(cachedInput), getMedium(), false, this, 16) && checkIO(input, cachedInput)) {
                if(world.getTime() % 20 == 0) {
                    world.playSound(null, pos, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 0.125f, 0.25f);
                    accept(input, cachedInput);
                }
                else if(world.getTime() % 10 == 0) {
                    world.playSound(null, pos, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 0.065f, 0.55f);
                    accept(input, cachedInput);
                }
                if(world.getTime() % 5 == 0)
                    world.playSound(null, pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 0.2f, 2f);
            }
        }
    }

    @Override
    public void accept(Direction direction, BlockPos sender) {
        PulseNode node = (PulseNode) world.getBlockEntity(sender);
        if(checkIO(direction, sender)){
            inductance = node.getInductance();
            frequency = node.getFrequency();
            polarity = node.getPolarity();
            inventory.set(0, new ItemStack(node.getMedium()));
        }
        else
            clearPower();
    }

    public boolean checkIO(Direction direction, BlockPos sender){
        BlockState state = world.getBlockState(sender);
        return state.getBlock() instanceof BlockNode && ((BlockNode) state.getBlock()).getIO(state).contains(direction.getOpposite());
    }

    public void recalcIO(Direction direction, BlockState state, boolean io){
        if(io){
            output = direction;
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
    public Collection<Direction> getInputs() {
        return Collections.singleton(input);
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
        tag.putString("input", input.getName());
        if(cachedInput != null)
            tag.putLong("cachedin", cachedInput.asLong());
        tag.putBoolean("rendertick", renderInit);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        input = Direction.byName(tag.getString("input"));
        cachedInput = BlockPos.fromLong(tag.getLong("cachedin"));
        renderInit = tag.getBoolean("rendertick");
        recalcIO(null, state, false);
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

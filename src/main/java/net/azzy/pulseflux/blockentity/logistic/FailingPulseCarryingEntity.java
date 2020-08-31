package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.blockentity.IORenderingEntityImpl;
import net.azzy.pulseflux.blockentity.PulseEntity;
import net.azzy.pulseflux.blockentity.PulseRenderingEntityImpl;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Supplier;

public abstract class FailingPulseCarryingEntity extends PulseRenderingEntityImpl {

    protected final long maxAmplitude;
    protected final double maxFrequency;

    public FailingPulseCarryingEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, short range, Supplier<DefaultedList<ItemStack>> invSupplier, long maxAmplitude, double maxFrequency) {
        super(type, material, range, invSupplier);
        this.maxAmplitude = maxAmplitude;
        this.maxFrequency = maxFrequency;
    }

    @Override
    public void tick() {
        if((inductance > maxAmplitude && maxAmplitude >= 0))
            fail(FailureType.AMPLITUDE);
        else if((frequency >= maxFrequency && maxFrequency >= 0))
            fail(FailureType.FREQUENCY);
        super.tick();
    }

    public void fail(FailureType failureType){
        //world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHAIN_BREAK, SoundCategory.BLOCKS, 4f, 0.75f, true);
        if(failureType == FailureType.AMPLITUDE){
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 2.5f, 0.5f, true);
            if(!world.isClient()) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.ENCHANTED_HIT, pos.getX(), pos.getY(), pos.getZ(), 15, 1f, 1f, 1f, 0);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        else if(failureType == FailureType.FREQUENCY && world.getTime() % 5 == 0){
            if(!world.isClient()) {
                if(world.getTime() % 10 == 0)
                    world.playSound(null, pos, SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 0.125f, 0.5f);
                ((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0, 0, 0, 0.062);
            }
        }
    }

    @Override
    public void accept(Direction direction, BlockPos sender) {
        PulseNode node = (PulseNode) world.getBlockEntity(sender);
        if(checkIO(direction, sender)){
            long flux = node.getInductance();
            inductance = flux > maxAmplitude ? world.getRandom().nextInt((int) Math.max((flux - maxAmplitude) / 100, 10)) == 0 ? flux : maxAmplitude : flux;
            frequency = Math.min(node.getFrequency(), maxFrequency);
            polarity = node.getPolarity();;
            if(inductance != 0 && frequency != 0)
                pulseTickTime = 100;
        }
        else
            clearPower();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("input", input.getName());
        tag.putString("output", output.getName());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        input = Direction.byName(tag.getString("input"));
        output = Direction.byName(tag.getString("output"));
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putString("input", input.getName());
        compoundTag.putString("output", output.getName());
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        input = Direction.byName(compoundTag.getString("input"));
        output = Direction.byName(compoundTag.getString("output"));
        super.fromClientTag(compoundTag);
    }

    @Override
    public long getInductance() {
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
    public boolean canFail() {
        return true;
    }

    public long getMaxInductance() {
        return maxAmplitude;
    }

    public double getMaxFrequency() {
        return maxFrequency;
    }

    @Override
    public Direction getInput() {
        return input;
    }

    @Override
    public Direction getOutput() {
        return output;
    }

    enum FailureType{
        AMPLITUDE,
        FREQUENCY,
        GENERIC
    }
}

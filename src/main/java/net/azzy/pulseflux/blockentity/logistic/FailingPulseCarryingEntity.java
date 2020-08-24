package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.blockentity.PulseEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Supplier;

public abstract class FailingPulseCarryingEntity extends PulseEntity implements PulseNode {

    protected final long maxAmplitude;
    protected final double maxFrequency;

    public FailingPulseCarryingEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, Supplier<DefaultedList<ItemStack>> invSupplier, long maxAmplitude, double maxFrequency) {
        super(type, material, invSupplier);
        this.maxAmplitude = maxAmplitude;
        this.maxFrequency = maxFrequency;
    }

    @Override
    public void tick() {
        if((inductance > maxAmplitude && maxAmplitude >= 0))
            fail(FailureType.AMPLITUDE);
        else if((frequency > maxFrequency && maxFrequency >= 0))
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
                    world.playSound(null, pos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.BLOCKS, 0.9f, 0.5f);
                ((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 19, 0, 0, 0, 0.062);
            }
        }
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

    @Override
    public boolean canUseMedium(Item item) {
        return true;
    }

    public long getMaxInductance() {
        return maxAmplitude;
    }

    public double getMaxFrequency() {
        return maxFrequency;
    }

    enum FailureType{
        AMPLITUDE,
        FREQUENCY,
        GENERIC
    }
}

package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.blockentity.MachineEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.PulseNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Supplier;

public abstract class FailingPulseCarryingEntity extends MachineEntity implements PulseNode {

    protected final long maxAmplitude;
    protected final double maxFrequency;

    public FailingPulseCarryingEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, Supplier<DefaultedList<ItemStack>> invSupplier, long maxAmplitude, double maxFrequency) {
        super(type, material, invSupplier);
        this.maxAmplitude = maxAmplitude;
        this.maxFrequency = maxFrequency;
    }

    @Override
    public void tick() {
        if((amplitude > maxAmplitude && maxAmplitude >= 0))
            fail(FailureType.AMPLITUDE);
        else if((frequency > maxFrequency && maxFrequency >= 0))
            fail(FailureType.FREQUENCY);
        super.tick();
    }

    public void fail(FailureType failureType){
        if(!world.isClient()){
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHAIN_BREAK, SoundCategory.BLOCKS, 4f, 0.75f, true);
            if(failureType == FailureType.AMPLITUDE){
                ((ServerWorld) world).spawnParticles(ParticleTypes.ENCHANTED_HIT, pos.getX(), pos.getY(), pos.getZ(), 15, 1f, 1f, 1f, 0);
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 2.5f, 0.5f, true);
            }
            else if(failureType == FailureType.FREQUENCY){
                ((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, pos.getX(), pos.getY(), pos.getZ(), 15, 1f, 1f, 1f, 0);
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 3f, 1.2f, true);
            }
        }
        world.breakBlock(pos, false);
    }

    enum FailureType{
        AMPLITUDE,
        FREQUENCY,
        GENERIC
    }
}

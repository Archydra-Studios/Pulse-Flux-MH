package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.block.entity.logistic.LinearDiodeBlock;
import net.azzy.pulseflux.client.util.PulseRenderingEntity;
import net.azzy.pulseflux.util.energy.BlockNode;
import net.azzy.pulseflux.util.energy.IOScans;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.azzy.pulseflux.util.interaction.MultiFacingRotatableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;

public abstract class DiodeEntity extends FailingPulseCarryingEntity {

    public DiodeEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, long maxAmplitude, double maxFrequency) {
        super(type, material, (short) 9, () -> DefaultedList.ofSize(0, ItemStack.EMPTY), maxAmplitude, maxFrequency, false);
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
}

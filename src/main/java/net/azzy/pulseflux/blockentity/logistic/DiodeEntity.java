package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public abstract class DiodeEntity extends FailingPulseCarryingEntity {

    public DiodeEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, long maxAmplitude, double maxFrequency) {
        super(type, material, (short) 9, () -> DefaultedList.ofSize(0, ItemStack.EMPTY), maxAmplitude, maxFrequency, false);
    }

    @Override
    public int getPixelOffset() {
        return 0;
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

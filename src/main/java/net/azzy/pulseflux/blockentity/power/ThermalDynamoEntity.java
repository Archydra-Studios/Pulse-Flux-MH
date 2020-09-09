package net.azzy.pulseflux.blockentity.power;

import net.azzy.pulseflux.blockentity.PulseGeneratingEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

import java.util.function.Supplier;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.THERMAL_DYNAMO_ENTITY;

public class ThermalDynamoEntity extends PulseGeneratingEntity {

    public ThermalDynamoEntity() {
        super(THERMAL_DYNAMO_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, () -> DefaultedList.ofSize(0, ItemStack.EMPTY));
    }

    @Override
    public void calcHeat() {
        for(int i = 0; i < 4; i++){
            HeatTransferHelper.simulateAmbientHeat(this, this.world.getBiome(pos));
            simulateSurroundingHeat(pos, this, Direction.DOWN);
        }
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

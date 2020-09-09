package net.azzy.pulseflux.blockentity.power;

import net.azzy.pulseflux.blockentity.PulseGeneratingEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.misc.DimensionAccessor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.world.dimension.DimensionType;

import java.util.function.Supplier;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.SOLAR_PANEL_ENTITY;

public class SolarPanelEntity extends PulseGeneratingEntity {

    public SolarPanelEntity() {
        super(SOLAR_PANEL_ENTITY, HeatTransferHelper.HeatMaterial.GRANITE, () -> DefaultedList.ofSize(0, ItemStack.EMPTY));
    }

    @Override
    public void tick() {
        DimensionType dimension = world.getDimension();
        if(world.isSkyVisible(pos) && !dimension.hasCeiling() && !(dimension == DimensionAccessor.getTheEnd()) && world.isDay()){
            inductance = 1;
            frequency = 100;
        }
        else{
            inductance = 0;
            frequency = 0;
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

    @Override
    public int getPixelOffset() {
        return 2;
    }
}

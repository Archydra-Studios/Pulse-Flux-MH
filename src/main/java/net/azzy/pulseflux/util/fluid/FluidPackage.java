package net.azzy.pulseflux.util.fluid;

import net.azzy.pulseflux.util.interaction.HeatHolder;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.PressureHolder;
import net.azzy.pulseflux.util.interaction.WorldPressure;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class FluidPackage implements HeatHolder, PressureHolder {

    private final Fluid wrappedFluid;
    private double heat;
    private long pressure, amount;
    private final BlockPos source;
    private final boolean gas;

    public FluidPackage(Fluid wrappedFluid, double heat, long pressure, long amount, boolean gaseous){
        this.wrappedFluid = wrappedFluid;
        this.heat = heat;
        this.pressure = pressure;
        this.amount = amount;
        this.source = null;
        this.gas = gaseous;
    }

    public FluidPackage(Fluid wrappedFluid, double heat, long pressure, long amount, boolean gaseous, BlockPos source){
        this.wrappedFluid = wrappedFluid;
        this.heat = heat;
        this.pressure = pressure;
        this.amount = amount;
        this.source = source;
        this.gas = gaseous;
    }

    public static FluidPackage fromTag(CompoundTag tag){
        return new FluidPackage(
                Registry.FLUID.get(Identifier.tryParse(tag.getString("wrappedfluid"))),
                tag.getDouble("temp"),
                tag.getLong("pressure"),
                tag.getLong("amount"),
                tag.getBoolean("gas"),
                BlockPos.fromLong(tag.getLong("source"))
        );
    }

    public CompoundTag toTag(){
        CompoundTag fluid = new CompoundTag();
        fluid.putDouble("temp", heat);
        fluid.putLong("pressure", pressure);
        fluid.putLong("amount", amount);
        fluid.putString("wrappedfluid", Registry.FLUID.getId(wrappedFluid).toString());
        fluid.putBoolean("gas", gas);
        if(source != null)
            fluid.putLong("source", source.asLong());
        return fluid;
    }

    public Fluid getWrappedFluid() {
        return wrappedFluid;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long changeAmount(long amount){
        this.amount += amount;
        if(this.amount < 0) {
            long returnedAmount = -this.amount;
            this.amount = 0;
            return returnedAmount;
        }
        else
            return  0;
    }

    @Override
    public double getHeat() {
        return heat;
    }

    public BlockPos getSource() {
        return source;
    }

    public boolean isGas() {
        return gas;
    }

    @Override
    public void moveHeat(double change) {
        heat += change;
    }

    @Override
    public double getArea() {
        return 0.00001;
    }

    @Override
    public HeatTransferHelper.HeatMaterial getMaterial() {
        return HeatTransferHelper.HeatMaterial.NULL;
    }

    @Override
    public long getPressure() {
        return pressure;
    }

    @Override
    public void setPressure(long pressure) {
        this.pressure = pressure;
    }
}

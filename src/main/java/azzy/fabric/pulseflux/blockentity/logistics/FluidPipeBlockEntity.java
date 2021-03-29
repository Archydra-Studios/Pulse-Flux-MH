package azzy.fabric.pulseflux.blockentity.logistics;

import azzy.fabric.pulseflux.blockentity.PulseFluxBlockEntity;
import azzy.fabric.pulseflux.energy.*;
import azzy.fabric.pulseflux.util.Material;
import dev.technici4n.fasttransferlib.api.Simulation;
import dev.technici4n.fasttransferlib.api.fluid.FluidApi;
import dev.technici4n.fasttransferlib.api.fluid.FluidIo;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FluidPipeBlockEntity extends PulseFluxBlockEntity implements FluidIo, PressureIo, HeatIo, InputSensitiveBlockEntity {

    @NotNull
    protected final Material material;
    protected double pressure, temperature;
    protected final long pressurizationPoint;
    protected long fluidAmount;
    @NotNull
    protected Fluid fluid = Fluids.EMPTY;
    @Nullable
    protected Direction inputDir;

    public FluidPipeBlockEntity(BlockEntityType<?> type, @NotNull Material material, long pressurizationPoint) {
        super(type);
        this.material = material;
        this.pressurizationPoint = pressurizationPoint;
    }

    public void tickInner() {
        List<Direction> shuffledDirections = Arrays.asList(DIRECTIONS);

        //Collections.shuffle(shuffledDirections);

        for (Direction direction : shuffledDirections) {

            if(direction == inputDir) {
                continue;
            }

            FluidIo io = FluidApi.SIDED.find(world, pos.offset(direction), direction.getOpposite());

            if(io instanceof PressureIo) {
                if(((PressureIo) io).getPressure() >= getPressure())
                    continue;
            }

            if(io != null) {
                if(io.supportsFluidInsertion()) {

                    long transferRate = (long) (Math.max(0, getFluidAmount(0) - io.getFluidAmount(0)) / 1.005);

                    long leftover = io.insert(fluid, transferRate, Simulation.SIMULATE);

                    transferRate -= leftover;

                    double finalPercent = (double) transferRate / fluidAmount;

                    io.insert(fluid, transferRate, Simulation.ACT);
                    fluidAmount -= transferRate;

                    if(io instanceof InputSensitiveBlockEntity) {
                        ((InputSensitiveBlockEntity) io).notifyInput(direction.getOpposite());
                    }

                    if(io instanceof HeatIo) {
                        ((HeatIo) io).heat(temperature * finalPercent, Simulation.ACT);
                        heat(-temperature * finalPercent, Simulation.ACT);
                    }

                    if(io instanceof PressureIo) {
                        ((PressureIo) io).requestPressureRecalc();
                    }
                }
            }
            requestPressureRecalc();
        }
        inputDir = null;
    }

    @Override
    public void notifyInput(@NotNull Direction direction) {
        inputDir = direction;
    }

    @Override
    protected void initialize() {
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public double getPressure() {
        return pressure;
    }

    @Override
    public void requestPressureRecalc() {
        long overFluid = Math.max(0, fluidAmount - pressurizationPoint);
            pressure = Math.pow(overFluid / 36586.544243, 2);
    }

    @Override
    public boolean supportsFluidInsertion() {
        return true;
    }

    @Override
    public boolean supportsExtraction() {
        return true;
    }

    @Override
    public int getFluidSlotCount() {
        return 1;
    }

    @Override
    public Fluid getFluid(int slot) {
        return slot == 0 ? fluid : Fluids.EMPTY;
    }

    @Override
    public long getFluidAmount(int slot) {
        return slot == 0 ? fluidAmount : 0;
    }

    @Override
    public long extract(int slot, Fluid fluid, long maxAmount, Simulation simulation) {
        if(fluid != this.fluid && fluid != Fluids.EMPTY)
            return 0;

        if(simulation == Simulation.SIMULATE) {
            return maxAmount + Math.min(0, fluidAmount - maxAmount);
        }
        else {
            long extracted =  maxAmount + Math.min(0, fluidAmount - maxAmount);
            fluidAmount -= extracted;
            return extracted;
        }
    }

    @Override
    public long extract(Fluid fluid, long maxAmount, Simulation simulation) {
        if(fluid != this.fluid && fluid != Fluids.EMPTY)
            return 0;

        if(simulation == Simulation.SIMULATE) {
            return maxAmount + Math.min(0, fluidAmount - maxAmount);
        }
        else {
            long extracted =  maxAmount + Math.min(0, fluidAmount - maxAmount);
            fluidAmount -= extracted;
            return extracted;
        }
    }

    @Override
    public long insert(Fluid fluid, long amount, Simulation simulation) {
        if(fluid != Fluids.EMPTY && simulation == Simulation.ACT) {
            fluidAmount += amount;
            if(this.fluid == Fluids.EMPTY)
                this.fluid = fluid;
        }
        return 0;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putDouble("temperature", temperature);
        tag.putDouble("pressure", pressure);
        tag.putLong("fluidAmount", fluidAmount);
        tag.putString("fluid", Registry.FLUID.getId(fluid).toString());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        double x = tag.getDouble("velX");
        double y = tag.getDouble("velY");
        double z = tag.getDouble("velZ");

        temperature = tag.getDouble("temperature");
        pressure = tag.getDouble("pressure");
        fluidAmount = tag.getLong("fluidAmount");
        fluid = Registry.FLUID.get(Identifier.tryParse(tag.getString("fluid")));

        super.fromTag(state, tag);
    }
}

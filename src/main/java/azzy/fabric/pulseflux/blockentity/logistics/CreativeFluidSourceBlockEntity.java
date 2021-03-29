package azzy.fabric.pulseflux.blockentity.logistics;

import azzy.fabric.pulseflux.block.PulseFluxBlock;
import azzy.fabric.pulseflux.block.PulseFluxBlocks;
import azzy.fabric.pulseflux.blockentity.PulseFluxBlockEntity;
import azzy.fabric.pulseflux.energy.HeatIo;
import azzy.fabric.pulseflux.energy.KineticIO;
import azzy.fabric.pulseflux.energy.PressureIo;
import dev.technici4n.fasttransferlib.api.Simulation;
import dev.technici4n.fasttransferlib.api.fluid.FluidApi;
import dev.technici4n.fasttransferlib.api.fluid.FluidIo;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class CreativeFluidSourceBlockEntity extends PulseFluxBlockEntity implements FluidIo {

    @NotNull
    private Vec3d velocity = Vec3d.ZERO;
    private double pressure, temperature;
    private long fluidAmount;
    private Fluid fluid;

    public CreativeFluidSourceBlockEntity() {
        super(PulseFluxBlocks.CREATIVE_FLUID_SOURCE_BLOCK_ENTITY);
    }


    @Override
    protected void tickInner() {
        for (Direction direction : Direction.values()) {
            FluidIo io = FluidApi.SIDED.find(world, pos.offset(direction), direction.getOpposite());
            if(io != null && io.supportsFluidInsertion()) {
                io.insert(Fluids.WATER, 81000, Simulation.ACT);
                if(io instanceof KineticIO && ((KineticIO) io).supportsPermutation()) {
                    ((KineticIO) io).permute(direction.getVector().getX() * 10, direction.getVector().getY() * 10, direction.getVector().getZ() * 10);
                }
                if(io instanceof PressureIo) {
                    ((PressureIo) io).requestPressureRecalc();
                }
            }
        }
    }

    @Override
    protected void initialize() {

    }

    @Override
    public boolean supportsFluidInsertion() {
        return getCachedState().get(PulseFluxBlock.POWERED);
    }

    @Override
    public long extract(int slot, Fluid fluid, long maxAmount, Simulation simulation) {
        return getCachedState().get(PulseFluxBlock.POWERED) ? maxAmount : 0;
    }

    @Override
    public long insert(Fluid fluid, long amount, Simulation simulation) {
        return getCachedState().get(PulseFluxBlock.POWERED) ? 0 : amount;
    }

    @Override
    public int getFluidSlotCount() {
        return 1;
    }

    @Override
    public Fluid getFluid(int slot) {
        return Fluids.WATER;
    }

    @Override
    public long getFluidAmount(int slot) {
        return Long.MAX_VALUE;
    }

}

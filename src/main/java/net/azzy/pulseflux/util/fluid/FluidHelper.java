package net.azzy.pulseflux.util.fluid;

import net.azzy.pulseflux.util.interaction.HeatHolder;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.PressureHolder;
import net.azzy.pulseflux.util.interaction.WorldPressure;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidHelper {

    public static boolean isEmpty(FluidPackage fluid){
        if(fluid == null)
            return true;
        else if(fluid.getAmount() <= 0 || fluid.getWrappedFluid() == Fluids.EMPTY){
            fluid.setAmount(0);
            return true;
        }
        return false;
    }

    public static FluidPackage of(Fluid fluid, boolean gaseous){
        return new FluidPackage(fluid, 0, 0, 0, gaseous);
    }

    public static FluidPackage copyOf(FluidPackage fluid, long amount){
        return new FluidPackage(fluid.getWrappedFluid(), fluid.getHeat(), 0, amount, fluid.isGas(), fluid.getSource());
    }

    public static FluidPackage fromWorld(Fluid fluid, boolean gaseous, World world, BlockPos pos){
        BlockEntity entity = world.getBlockEntity(pos);
        return new FluidPackage(fluid, entity instanceof HeatHolder ? ((HeatHolder) entity).getHeat() : HeatTransferHelper.translateBiomeHeat(world.getBiome(pos)), entity instanceof PressureHolder ? ((PressureHolder) entity).getPressure() : WorldPressure.getDimPressure(world.getDimension(), pos), 0, gaseous, pos);
    }

    public static FluidPackage empty(){
        return new FluidPackage(Fluids.EMPTY, 0, 0, 0, false);
    }
}

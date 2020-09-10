package net.azzy.pulseflux.util.fluid

import net.azzy.pulseflux.util.interaction.HeatHolder
import net.azzy.pulseflux.util.interaction.HeatTransferHelper
import net.azzy.pulseflux.util.interaction.PressureHolder
import net.azzy.pulseflux.util.interaction.WorldPressure
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object FluidHelper {
    @JvmStatic
    fun isEmpty(fluid: FluidPackage?): Boolean {
        if (fluid == null) return true else if (fluid.amount <= 0 || fluid.wrappedFluid === Fluids.EMPTY) {
            fluid.amount = 0
            return true
        }
        return false
    }

    fun of(fluid: Fluid = Fluids.EMPTY, gaseous: Boolean): FluidPackage {
        return FluidPackage(fluid, 0.0, 0, 0, gaseous)
    }

    @JvmStatic
    fun copyOf(fluid: FluidPackage, amount: Long): FluidPackage {
        return FluidPackage(fluid.wrappedFluid, fluid.heat, 0, amount, fluid.source, fluid.gas)
    }

    fun fromWorld(fluid: Fluid = Fluids.EMPTY, gaseous: Boolean, world: World, pos: BlockPos?): FluidPackage {
        val entity = world.getBlockEntity(pos)
        return FluidPackage(fluid, if (entity is HeatHolder) (entity as HeatHolder).heat else HeatTransferHelper.translateBiomeHeat(world.getBiome(pos)), if (entity is PressureHolder) (entity as PressureHolder).pressure else WorldPressure.getDimPressure(world.dimension, pos), 0, pos, gaseous)
    }

    @JvmStatic
    fun empty(): FluidPackage {
        return FluidPackage(Fluids.EMPTY, 0.0, 0, 0, false)
    }
}
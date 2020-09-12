package net.azzy.pulseflux.registry

import net.azzy.pulseflux.PulseFlux
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.*

object FluidRegistry {
    @JvmField
    val FLUID_PAIRS: List<FluidPair> = ArrayList()

    @JvmField
    @Environment(EnvType.CLIENT)
    val FLUID_TRANS: List<Fluid> = ArrayList()
    @JvmStatic
    @Environment(EnvType.CLIENT)
    fun initTransparency() {
    }

    fun registerStill(name: String?, item: FlowableFluid): FlowableFluid {
        Registry.register(Registry.FLUID, Identifier(PulseFlux.MOD_ID, name), item)
        return item
    }

    fun registerFlowing(name: String?, item: FlowableFluid): FlowableFluid {
        Registry.register(Registry.FLUID, Identifier(PulseFlux.MOD_ID, name), item)
        return item
    }

    @JvmStatic
    fun init() {}
    class FluidPair private constructor(val stillState: FlowableFluid, val flowState: FlowableFluid, val color: Int)
}
package net.azzy.pulseflux.util.fluid

import net.azzy.pulseflux.util.interaction.HeatHolder
import net.azzy.pulseflux.util.interaction.HeatTransferHelper
import net.azzy.pulseflux.util.interaction.PressureHolder
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry

data class FluidPackage(val wrappedFluid: Fluid = Fluids.EMPTY, private var heat: Double, private var pressure: Long, var amount: Long, val source: BlockPos?, val gas: Boolean = false) : HeatHolder, PressureHolder{

    constructor(wrappedFluid: Fluid, heat: Double, pressure: Long, amount: Long, gas: Boolean) : this(wrappedFluid, heat, pressure, amount, null, gas)

    fun toTag(): CompoundTag {
        val tag = CompoundTag()
        with(tag){
            putString("fluid", Registry.FLUID.getId(wrappedFluid).toString())
            putDouble("heat", heat)
            putLong("pressure", pressure)
            putLong("amount", amount)
            if(source != null)
                putLong("source", source.asLong())
            putBoolean("gaseous", gas)
        }
        return tag
    }

    fun changeAmount(amount: Long): Long {
        this.amount += amount
        if(this.amount < 0){
            val returnedAmount = -amount
            this.amount = 0
            return returnedAmount
        }
        return 0
    }

    override fun getHeat(): Double {
       return heat
    }

    override fun moveHeat(change: Double) {
        heat += change
    }

    override fun getArea(): Double {
        return 0.00001
    }

    override fun getMaterial(): HeatTransferHelper.HeatMaterial {
        return HeatTransferHelper.HeatMaterial.NULL
    }

    override fun getPressure(): Long {
        return pressure
    }

    override fun setPressure(pressure: Long) {
        this.pressure = pressure
    }

    companion object {
        @JvmStatic
        fun fromTag(tag: CompoundTag): FluidPackage{
            with(tag){
                return FluidPackage(
                        Registry.FLUID.get(Identifier(getString("fluid"))),
                        getDouble("heat"),
                        getLong("pressure"),
                        getLong("amount"),
                        BlockPos.fromLong(getLong("source")),
                        getBoolean("gaseous")
                )
            }
        }
    }
}
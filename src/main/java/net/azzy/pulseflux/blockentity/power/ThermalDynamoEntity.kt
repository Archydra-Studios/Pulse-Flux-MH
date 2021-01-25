package net.azzy.pulseflux.blockentity.power

import dev.technici4n.fasttransferlib.api.fluid.FluidIo
import net.azzy.pulseflux.block.entity.PulseCarryingDirectionalBlock
import net.azzy.pulseflux.blockentity.PulseGeneratingEntity
import net.azzy.pulseflux.registry.BlockEntityRegistry
import net.azzy.pulseflux.util.fluid.FluidHelper
import net.azzy.pulseflux.util.interaction.HeatHolder
import net.azzy.pulseflux.util.interaction.HeatTransferHelper
import net.azzy.pulseflux.util.interaction.PressureHolder
import net.minecraft.block.BlockState
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.PropertyDelegate
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction
import java.util.*
import java.util.function.Supplier
import kotlin.math.pow

class ThermalDynamoEntity : PulseGeneratingEntity(BlockEntityRegistry.THERMAL_DYNAMO_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, Supplier { DefaultedList.ofSize(0, ItemStack.EMPTY) }), PressureHolder, FluidIo {

    private var pressure = 0L
    private var fluid = Fluids.EMPTY
    private var amount = 0L

    override fun tick() {
        super.tick()
        if(fluid == Fluids.WATER && amount >= 2 && getHeat() >= 100) {
            amount -= 2
            if(world!!.time % 20 == 0L)
                moveHeat(-1.0)
            frequency = 5.0
            inductance = 50L
        }
        else{
            frequency = 0.0
            inductance = 0L
            }
        recalcPressure()
    }

    override fun calcHeat() {
        for (i in 0..3) {
            HeatTransferHelper.simulateAmbientHeat(this, world!!.getBiome(pos))
            simulateSurroundingHeat(pos, this, Direction.DOWN)
        }
    }

    override fun getPropertyDelegate(): PropertyDelegate? {
        return null
    }

    override fun getArea(): Double {
        return 1.0
    }

    override fun getAvailableSlots(side: Direction): IntArray {
        return IntArray(0)
    }

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean {
        return true
    }

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean {
        return false
    }

    override fun toTag(tag: CompoundTag?): CompoundTag {
        if (tag != null) {
            FluidHelper.tankToTag(tag, fluid, amount)
            tag.putDouble("heat", heat)
        }
        return super.toTag(tag)
    }

    override fun fromTag(state: BlockState?, tag: CompoundTag?) {
        if (tag != null) {
            val tank = FluidHelper.tankFromTag(tag)
            fluid = tank.first
            amount = tank.second
        }
        super.fromTag(state, tag)
    }

    override fun toClientTag(tag: CompoundTag?): CompoundTag {
        if (tag != null) {
            FluidHelper.tankToTag(tag, fluid, amount)
            tag.putDouble("heat", heat)
        }
        return super.toClientTag(tag)
    }

    override fun fromClientTag(tag: CompoundTag?) {
        if (tag != null) {
            val tank = FluidHelper.tankFromTag(tag)
            fluid = tank.first
            amount = tank.second
        }
        super.fromClientTag(tag)
    }

    override fun getRenderInputs(): MutableCollection<Direction> {
        return Collections.singleton(cachedState.get(PulseCarryingDirectionalBlock.FACING).opposite)
    }

    override fun getRenderOutputs(): MutableCollection<Direction> {
        return Collections.singleton(cachedState.get(PulseCarryingDirectionalBlock.FACING))
    }

    private fun recalcPressure() {
        if(world!!.isClient)
            return
        val fluid = amount / 1000.0
        pressure = fluid.pow(4.0).toLong()
        markDirty()
        sync()
    }

    override fun getPressure(): Long {
        return pressure
    }

    override fun setPressure(pressure: Long) {
        this.pressure = pressure;
    }

    override fun getFluidSlotCount(): Int {
        return 1
    }

    override fun getFluid(slot: Int): Fluid {
        return fluid
    }

    override fun getFluidAmount(slot: Int): Long {
        return amount
    }

}
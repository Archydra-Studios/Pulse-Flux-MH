package net.azzy.pulseflux.blockentity.power

import net.azzy.pulseflux.PulseFlux.PFLog
import net.azzy.pulseflux.block.entity.PulseCarryingDirectionalBlock
import net.azzy.pulseflux.blockentity.PulseGeneratingEntity
import net.azzy.pulseflux.registry.BlockEntityRegistry
import net.azzy.pulseflux.util.fluid.FluidHelper
import net.azzy.pulseflux.util.fluid.FluidHolder
import net.azzy.pulseflux.util.fluid.FluidPackage
import net.azzy.pulseflux.util.interaction.HeatTransferHelper
import net.azzy.pulseflux.util.interaction.PressureHolder
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Direction
import java.util.*
import java.util.function.Supplier
import kotlin.math.pow

class ThermalDynamoEntity : PulseGeneratingEntity(BlockEntityRegistry.THERMAL_DYNAMO_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, Supplier { DefaultedList.ofSize(0, ItemStack.EMPTY) }), FluidHolder, PressureHolder {

    private var tank = FluidHelper.empty()

    override fun tick() {
        super.tick()
        if(!FluidHelper.isEmpty(tank))
            recalcPressure()
        else
            fluid = FluidHelper.empty()
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
        return false
    }

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean {
        return false
    }

    override fun getFluid(): FluidPackage {
        return tank
    }

    override fun setFluid(fluid: FluidPackage) {
        tank = fluid;
    }

    override fun getRenderInputs(): MutableCollection<Direction> {
        return Collections.singleton(cachedState.get(PulseCarryingDirectionalBlock.FACING).opposite)
    }

    override fun getRenderOutputs(): MutableCollection<Direction> {
        return Collections.singleton(cachedState.get(PulseCarryingDirectionalBlock.FACING))
    }

    override fun recalcPressure() {
        val fluid = tank.amount / 1000.0
        tank.pressure = fluid.pow(4.0).toLong()
        markDirty()
        sync()
    }

    override fun addFluid(amount: Long): Long {
        return tank.changeAmount(amount)
    }

    override fun extractFluid(amount: Long): Long {
        return tank.changeAmount(-amount)
    }

    override fun canConnect(direction: Direction?): Boolean {
        return cachedState.get(PulseCarryingDirectionalBlock.FACING) == direction
    }

    override fun gasCarrying(): Boolean {
        return false
    }

    override fun getPressure(): Long {
        return tank.pressure
    }

    override fun setPressure(pressure: Long) {
        tank.pressure = pressure;
    }
}
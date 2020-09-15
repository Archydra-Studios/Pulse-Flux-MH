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
import net.minecraft.block.BlockState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
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
        if(tank.wrappedFluid == Fluids.WATER && getHeat() >= 100) {
            tank.changeAmount(-5)
            if(world!!.time % 100 == 0L)
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
        return false
    }

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean {
        return false
    }

    override fun getFluid(): FluidPackage {
        return tank
    }

    override fun moveHeat(change: Double) {
        tank.moveHeat(change)
    }

    override fun getHeat(): Double {
        return tank.heat
    }

    override fun setFluid(fluid: FluidPackage) {
        tank = fluid;
    }

    override fun toTag(tag: CompoundTag?): CompoundTag {
        tag!!.put("tank", tank.toTag())
        return super.toTag(tag)
    }

    override fun fromTag(state: BlockState?, tag: CompoundTag?) {
        tank = FluidPackage.fromTag(tag!!.getCompound("tank"))
        super.fromTag(state, tag)
    }

    override fun toClientTag(tag: CompoundTag?): CompoundTag {
        tag!!.put("tank", tank.toTag())
        return super.toClientTag(tag)
    }

    override fun fromClientTag(tag: CompoundTag?) {
        tank = FluidPackage.fromTag(tag!!.getCompound("tank"))
        super.fromClientTag(tag)
    }

    override fun getRenderInputs(): MutableCollection<Direction> {
        return Collections.singleton(cachedState.get(PulseCarryingDirectionalBlock.FACING).opposite)
    }

    override fun getRenderOutputs(): MutableCollection<Direction> {
        return Collections.singleton(cachedState.get(PulseCarryingDirectionalBlock.FACING))
    }

    override fun recalcPressure() {
        if(world!!.isClient)
            return
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
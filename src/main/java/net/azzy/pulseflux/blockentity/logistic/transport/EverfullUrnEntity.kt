package net.azzy.pulseflux.blockentity.logistic.transport

import dev.technici4n.fasttransferlib.api.Simulation
import dev.technici4n.fasttransferlib.api.fluid.FluidIo
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.azzy.pulseflux.client.util.RenderMathHelper
import net.azzy.pulseflux.mixin.BucketFluidAccessor
import net.azzy.pulseflux.registry.BlockEntityRegistry.EVERFULL_URN_ENTITY
import net.azzy.pulseflux.util.gui.ExtendedPropertyDelegate
import net.azzy.pulseflux.util.interaction.HeatTransferHelper
import net.azzy.pulseflux.util.interaction.InventoryWrapper
import net.azzy.pulseflux.util.interaction.PressureHolder
import net.azzy.pulseflux.util.networking.Syncable
import net.azzy.pulseflux.util.networking.Syncable.SyncPacket
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.inventory.Inventories
import net.minecraft.item.BucketItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.PropertyDelegate
import net.minecraft.util.Tickable
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class EverfullUrnEntity : BlockEntity(EVERFULL_URN_ENTITY), BlockEntityClientSerializable, PressureHolder, Syncable, InventoryWrapper, PropertyDelegateHolder, Tickable, FluidIo {

    private var pressure: Long = 0
    var fluid: Fluid = Fluids.EMPTY
    private val inventory = DefaultedList.ofSize(1, ItemStack.EMPTY)

    override fun tick() {
        //for (direction in Direction.values())
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        Inventories.toTag(tag, inventory)
        tag.putLong("pressure", pressure)
        return super.toTag(tag)
    }

    override fun fromTag(state: BlockState, tag: CompoundTag) {
        Inventories.fromTag(tag, inventory)
        fluid = (inventory[0].item as BucketFluidAccessor).fluid
        pressure = tag.getLong("pressure")
        super.fromTag(state, tag)
    }

    override fun fromClientTag(compoundTag: CompoundTag) {
        pressure = compoundTag.getLong("pressure")
        Inventories.fromTag(compoundTag, inventory)
        fluid = (inventory[0].item as BucketFluidAccessor).fluid
    }

    override fun toClientTag(compoundTag: CompoundTag): CompoundTag {
        Inventories.toTag(compoundTag, inventory)
        compoundTag.putLong("pressure", pressure)
        return compoundTag
    }

    override fun synchronize(packet: SyncPacket) {
        pressure = packet.unpack() as Long
        fluid = if (!inventory.isEmpty() && inventory[0].item is BucketItem) {
            (inventory[0].item as BucketFluidAccessor).fluid
        } else Fluids.EMPTY
        markDirty()
        sync()
    }

    override fun getItems(): DefaultedList<ItemStack> {
        return inventory
    }

    private val delegate: PropertyDelegate = object : ExtendedPropertyDelegate {
        override fun getLong(index: Int): Long {
            return pressure
        }

        override fun setLong(index: Int, value: Long) {}
        override fun getpos(): BlockPos {
            return pos
        }
    }

    override fun markDirty() {
        super<BlockEntity>.markDirty()
    }

    override fun getPropertyDelegate(): PropertyDelegate {
        return delegate
    }

    override fun getFluidSlotCount(): Int {
        return 1
    }

    override fun getFluid(p0: Int): Fluid {
        return fluid
    }

    override fun getFluidAmount(p0: Int): Long {
        return Long.MAX_VALUE
    }

    override fun getPressure(): Long {
        return pressure
    }

    override fun setPressure(pressure: Long) {
        this.pressure = pressure
    }

    override fun supportsFluidExtraction(): Boolean {
        return true
    }

    override fun supportsFluidInsertion(): Boolean {
        return fluid == Fluids.EMPTY
    }

    override fun extract(fluid: Fluid?, maxAmount: Long, simulation: Simulation?): Long {
        return maxAmount
    }

    override fun insert(fluid: Fluid?, amount: Long, simulation: Simulation?): Long {
        return 0L
    }
}
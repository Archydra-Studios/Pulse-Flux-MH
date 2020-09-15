package net.azzy.pulseflux.blockentity.logistic.transport

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.azzy.pulseflux.client.util.RenderHelper
import net.azzy.pulseflux.client.util.RenderMathHelper
import net.azzy.pulseflux.mixin.BucketFluidAccessor
import net.azzy.pulseflux.registry.BlockEntityRegistry.EVERFULL_URN_ENTITY
import net.azzy.pulseflux.util.fluid.FluidHelper.empty
import net.azzy.pulseflux.util.fluid.FluidHolder
import net.azzy.pulseflux.util.fluid.FluidPackage
import net.azzy.pulseflux.util.fluid.FluidPackage.Companion.fromTag
import net.azzy.pulseflux.util.gui.ExtendedPropertyDelegate
import net.azzy.pulseflux.util.interaction.HeatTransferHelper
import net.azzy.pulseflux.util.interaction.InventoryWrapper
import net.azzy.pulseflux.util.networking.Syncable
import net.azzy.pulseflux.util.networking.Syncable.SyncPacket
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
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

class EverfullUrnEntity : BlockEntity(EVERFULL_URN_ENTITY), BlockEntityClientSerializable, Syncable, FluidHolder, InventoryWrapper, PropertyDelegateHolder, Tickable {
    private var pressure: Long = 0
    private var tank = empty()
    private val inventory = DefaultedList.ofSize(1, ItemStack.EMPTY)
    override fun tick() {
        for (direction in Direction.values()) forcePush(world, pos.offset(direction), direction, 1, 1000)
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        Inventories.toTag(tag, inventory)
        tag.putLong("pressure", pressure)
        tag.put("tank", tank.toTag())
        return super.toTag(tag)
    }

    override fun fromTag(state: BlockState, tag: CompoundTag) {
        Inventories.fromTag(tag, inventory)
        pressure = tag.getLong("pressure")
        tank = fromTag(tag.getCompound("tank"))
        super.fromTag(state, tag)
    }

    override fun fromClientTag(compoundTag: CompoundTag) {
        pressure = compoundTag.getLong("pressure")
        tank = fromTag(compoundTag.getCompound("tank"))
    }

    override fun toClientTag(compoundTag: CompoundTag): CompoundTag {
        compoundTag.putLong("pressure", pressure)
        compoundTag.put("tank", tank.toTag())
        return compoundTag
    }

    override fun synchronize(packet: SyncPacket) {
        pressure = packet.unpack() as Long
        tank = if (!inventory.isEmpty() && inventory[0].item is BucketItem) {
            val fluid = (inventory[0].item as BucketFluidAccessor).fluid
            if (fluid === Fluids.EMPTY) empty() else FluidPackage(fluid, HeatTransferHelper.translateBiomeHeat(world!!.getBiome(pos)), pressure, 8000, RenderMathHelper fromHex world!!.getBiome(pos).waterColor, false)
        } else empty()
        markDirty()
        sync()
    }

    override fun getFluid(): FluidPackage {
        return tank
    }

    override fun setFluid(fluid: FluidPackage) {}
    override fun testFluid(fluid: FluidPackage): Boolean {
        return true
    }

    override fun recalcPressure() {}
    override fun addFluid(amount: Long): Long {
        return 0
    }

    override fun extractFluid(amount: Long): Long {
        return 0
    }

    override fun gasCarrying(): Boolean {
        return false
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
}
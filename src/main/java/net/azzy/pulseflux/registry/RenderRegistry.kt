package net.azzy.pulseflux.registry

import net.azzy.pulseflux.client.rendering.base.IOMachineRenderer
import net.azzy.pulseflux.client.rendering.base.PulseMachineRenderer
import net.azzy.pulseflux.client.rendering.logistics.EverfullUrnRenderer
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.entity.EntityRenderer
import java.util.function.Function
import java.util.function.Supplier

object RenderRegistry {

    @JvmStatic
    fun init() {
        fun <T : BlockEntity> register(item: BlockEntityType<T>, rendererProvider: (BlockEntityRenderDispatcher) -> BlockEntityRenderer<T>) = BlockEntityRendererRegistry.INSTANCE.register(item, rendererProvider)
        register(BlockEntityRegistry.STEEL_DIODE_ENTITY, ::PulseMachineRenderer)
        register(BlockEntityRegistry.MODULATOR_2_ENTITY, ::PulseMachineRenderer)
        register(BlockEntityRegistry.MODULATOR_4_ENTITY, ::PulseMachineRenderer)
        register(BlockEntityRegistry.MODULATOR_8_ENTITY, ::PulseMachineRenderer)
        register(BlockEntityRegistry.SOLENOID_SPLITTING_ENTITY, ::PulseMachineRenderer)
        register(BlockEntityRegistry.SOLENOID_MERGING_ENTITY, ::PulseMachineRenderer)
        register(BlockEntityRegistry.SOLAR_PANEL_ENTITY, ::IOMachineRenderer)
        register(BlockEntityRegistry.EVERFULL_URN_ENTITY, ::EverfullUrnRenderer)
        register(BlockEntityRegistry.THERMAL_DYNAMO_ENTITY, ::IOMachineRenderer)
    }
}
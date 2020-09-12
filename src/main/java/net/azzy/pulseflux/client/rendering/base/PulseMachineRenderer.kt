package net.azzy.pulseflux.client.rendering.base

import net.azzy.pulseflux.client.util.IORenderingEntity
import net.azzy.pulseflux.client.util.PulseRenderer
import net.azzy.pulseflux.client.util.PulseRenderingEntity
import net.azzy.pulseflux.util.energy.PulseNode
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.util.math.MatrixStack

class PulseMachineRenderer<T>(dispatcher: BlockEntityRenderDispatcher?) : IOMachineRenderer<T>(dispatcher), PulseRenderer<T> where T : BlockEntity?, T : IORenderingEntity?, T : PulseRenderingEntity?, T : PulseNode? {
    override fun render(entity: T, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay)
        renderPulse(matrices, vertexConsumers, entity, tickDelta)
    }
}
package net.azzy.pulseflux.client.rendering.base

import net.azzy.pulseflux.client.util.IORenderer
import net.azzy.pulseflux.client.util.IORenderingEntity
import net.azzy.pulseflux.util.energy.PulseNode
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack

open class IOMachineRenderer<T>(dispatcher: BlockEntityRenderDispatcher?) : BlockEntityRenderer<T>(dispatcher), IORenderer<T> where T : BlockEntity?, T : IORenderingEntity?, T : PulseNode? {

    override fun rendersOutsideBoundingBox(blockEntity: T): Boolean {
        return true
    }

    override fun render(entity: T, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        renderIO(matrices, vertexConsumers, entity, tickDelta.toDouble())
    }
}
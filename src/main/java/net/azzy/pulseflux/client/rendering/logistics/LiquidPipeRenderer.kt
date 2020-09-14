package net.azzy.pulseflux.client.rendering.logistics

import net.azzy.pulseflux.blockentity.logistic.transport.FluidPipeEntity
import net.azzy.pulseflux.client.util.RenderHelper
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack

class LiquidPipeRenderer<T>(dispatcher: BlockEntityRenderDispatcher) : BlockEntityRenderer<T>(dispatcher) where T : FluidPipeEntity {

    override fun render(entity: T, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        matrices.push()
        RenderHelper.renderCuboid(matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), 100, light, 1F, 1F, 1F, null)
        matrices.pop()
    }

}

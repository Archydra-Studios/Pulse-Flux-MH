package net.azzy.pulseflux.client.rendering.logistics

import net.azzy.pulseflux.blockentity.logistic.transport.EverfullUrnEntity
import net.azzy.pulseflux.client.util.RenderHelper
import net.azzy.pulseflux.client.util.RenderMathHelper.fromHex
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f
import net.minecraft.fluid.Fluids

class EverfullUrnRenderer(dispatcher: BlockEntityRenderDispatcher?) : BlockEntityRenderer<EverfullUrnEntity>(dispatcher) {
    override fun render(entity: EverfullUrnEntity, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {
        val fluid = entity.fluid
        if (fluid != Fluids.EMPTY) {
            matrices.push()
            val color = if (fluid === Fluids.LAVA) fromHex(0xffa841) else fromHex(FluidRenderHandlerRegistry.INSTANCE[fluid].getFluidColor(entity.world, entity.pos, null))
            matrices.translate(0.5, 0.5, 0.5)
            matrices.scale(0.25f, 0.25f, 0.25f)
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(entity.world!!.time + tickDelta))
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.world!!.time + tickDelta) * 2))
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(entity.world!!.time + tickDelta))
            matrices.translate(-0.5, -0.5, -0.5)
            RenderHelper.renderOverlayCuboid(matrices, vertexConsumers, color.r, color.g, color.b, 200, 1f, 1f, 1f)
            matrices.pop()
            matrices.push()
            matrices.translate(0.5, 0.5, 0.5)
            matrices.scale(0.125f, 0.125f, 0.125f)
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(entity.world!!.time + tickDelta))
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.world!!.time + tickDelta) * 2))
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(entity.world!!.time + tickDelta))
            matrices.translate(-0.5, -0.5, -0.5)
            RenderHelper.renderOverlayCuboid(matrices, vertexConsumers, color.r, color.g, color.b, 200, 1f, 1f, 1f)
            matrices.pop()
            matrices.push()
            matrices.translate(0.5, 0.5, 0.5)
            matrices.scale(0.125f, 0.125f, 0.125f)
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(entity.world!!.time + tickDelta))
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.world!!.time + tickDelta) * 2))
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(entity.world!!.time + tickDelta))
            matrices.translate(-2.5, -0.0, -0.0)
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.world!!.time + tickDelta) * 3))
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.world!!.time + tickDelta) * 2))
            matrices.translate(-0.5, -0.5, -0.5)
            RenderHelper.renderOverlayCuboid(matrices, vertexConsumers, color.r, color.g, color.b, 200, 1f, 1f, 1f)
            matrices.pop()
        }
    }
}
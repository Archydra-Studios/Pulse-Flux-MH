package net.azzy.pulseflux.client.rendering.logistics

import net.azzy.pulseflux.block.entity.logistic.FluidPipeBlock
import net.azzy.pulseflux.blockentity.logistic.transport.FluidPipeEntity
import net.azzy.pulseflux.client.shaders.ShaderManager
import net.azzy.pulseflux.client.util.FFRenderLayers
import net.azzy.pulseflux.client.util.RenderHelper
import net.azzy.pulseflux.client.util.RenderMathHelper
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f
import net.minecraft.fluid.Fluids
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Quaternion

class LiquidPipeRenderer<T>(dispatcher: BlockEntityRenderDispatcher) : BlockEntityRenderer<T>(dispatcher) where T : FluidPipeEntity {

    override fun render(entity: T, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, overlay: Int) {

        val fluid = entity.fluid
        val amount = entity.getFluidAmount(0)
        if(fluid == null || fluid == Fluids.EMPTY)
            return

        val wrapped = entity.fluid
        val identifier = if(fluid == Fluids.LAVA) Identifier("minecraft:block/lava_still") else FluidRenderHandlerRegistry.INSTANCE[fluid].getFluidSprites(entity.world, entity.pos, null)[0].id
        val color = if(fluid == Fluids.WATER) RenderMathHelper.fromHex(0x6083d5, 140) else if(fluid == Fluids.LAVA) RenderMathHelper fromHex 0xffffff else (RenderMathHelper fromHex 0xffffff).appendAlpha(if(entity.gasCarrying) 80 else 140)
        val consumer = if(fluid == Fluids.LAVA || ((entity.heat >= 1000 && entity.gasCarrying))) vertexConsumers.getBuffer(FFRenderLayers.FLUID_BLOOM) else if (entity.heat >= 1500) vertexConsumers.getBuffer(FFRenderLayers.TRANSLUCENT_FLUID_BLOOM) else vertexConsumers.getBuffer(RenderLayer.getTranslucent())
        val scaling = (amount / 16000F).coerceAtMost(1F)

        matrices.push()
        if(entity.isStraight) {
            matrices.translate(8.02/16.0, 8.02/16.0, 8.02/16.0)
            when(entity.world?.getBlockState(entity.pos)?.get(FluidPipeBlock.DIRECTION)) {
                Direction.NORTH -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180F))
                Direction.EAST -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90F))
                Direction.WEST -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90F))
                Direction.UP -> matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90F))
                Direction.DOWN -> matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90F))
            }
            matrices.scale(0.98F, 0.98F, 0.98F)
            RenderHelper.renderColoredCuboid(matrices, consumer, color, light, (3.98F/16F) * scaling, (3.98F/16F) * scaling, 6.2F/16F, identifier,  true)
        }
        else {
            matrices.translate(5.02/16.0, 5.02/16.0, 5.02/16.0)
            matrices.scale(0.98F, 0.98F, 0.98F)
            RenderHelper.renderColoredCuboid(matrices, consumer, color, light, 5.98F/16F, (5.98F/16F) * scaling, 6.2F/16F, identifier,  false)
        }
        matrices.pop()

        if(entity.io.isNotEmpty())
            for (direction in entity.io){
                matrices.push()
                matrices.translate(8.02/16.0, 8.02/16.0, 8.0/16)
                when(direction) {
                    Direction.NORTH -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180F))
                    Direction.EAST -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90F))
                    Direction.WEST -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90F))
                    Direction.UP -> matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90F))
                    Direction.DOWN -> matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90F))
                }
                matrices.translate(0.0, 0.0, 5.5/16)
                matrices.scale(0.98F, 0.98F, 0.98F)
                RenderHelper.renderColoredCuboid(matrices, consumer, color, light, (3.98F/16F) * scaling, (3.98F/16F) * scaling, 5/16F, identifier,  true)
                matrices.pop()
            }
    }

}

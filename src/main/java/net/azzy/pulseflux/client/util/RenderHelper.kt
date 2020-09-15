package net.azzy.pulseflux.client.util

import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

object RenderHelper {
    const val MAX_LIGHT = 0x00F000F0
    fun renderOverlayCuboid(matrices: MatrixStack, consumers: VertexConsumerProvider, r: Int, g: Int, b: Int, a: Int, sizeX: Float, sizeY: Float, sizeZ: Float, offset: Boolean) {
        val consumer = consumers.getBuffer(FFRenderLayers.OVERLAY)
        val model = matrices.peek().model
        if (offset) {
            matrices.translate(-0.001, -0.001, -0.001)
            matrices.scale(1.002f, 1.002f, 1.002f)
        }
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f))
        matrices.translate(0.0, 0.0, -sizeZ.toDouble())
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        matrices.translate(1.0, 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.translate(0.0, sizeY.toDouble(), 0.0)
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
    }

    fun renderOverlayCuboid(matrices: MatrixStack, consumers: VertexConsumerProvider, r: Int, g: Int, b: Int, a: Int, sizeX: Float, sizeY: Float, sizeZ: Float) {
        val consumer = consumers.getBuffer(FFRenderLayers.OVERLAY)
        val model = matrices.peek().model
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f))
        matrices.translate(0.0, 0.0, -sizeZ.toDouble())
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        matrices.translate(1.0, 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.translate(0.0, sizeY.toDouble(), 0.0)
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
    }

    fun renderCuboid(matrices: MatrixStack, consumer: VertexConsumer, a: Int, light: Int, sizeX: Float, sizeY: Float, sizeZ: Float, texture: Identifier?) {
        val model = matrices.peek().model
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(0f, 0f).light(light).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(200, 200, 200, a).texture(0f, 1f).light(light).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(200, 200, 200, a).texture(1f, 1f).light(light).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(200, 200, 200, a).texture(1f, 0f).light(light).next()
        matrices.translate(1.0, 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(0f, 0f).light(light).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(200, 200, 200, a).texture(0f, 1f).light(light).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(200, 200, 200, a).texture(1f, 1f).light(light).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(200, 200, 200, a).texture(1f, 0f).light(light).next()
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f))
        matrices.translate(0.0, 0.0, -sizeZ.toDouble())
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(0f, 0f).light(light).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(200, 200, 200, a).texture(0f, 1f).light(light).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(200, 200, 200, a).texture(1f, 1f).light(light).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(200, 200, 200, a).texture(1f, 0f).light(light).next()
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(0f, 0f).light(light).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(200, 200, 200, a).texture(0f, 1f).light(light).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(200, 200, 200, a).texture(1f, 1f).light(light).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(200, 200, 200, a).texture(1f, 0f).light(light).next()
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(0f, 0f).light(light).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(200, 200, 200, a).texture(0f, 1f).light(light).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(200, 200, 200, a).texture(1f, 1f).light(light).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(200, 200, 200, a).texture(1f, 0f).light(light).next()
        matrices.translate(0.0, sizeY.toDouble(), 0.0)
        consumer.vertex(model, 0f, 0f, sizeZ).color(200, 200, 200, a).texture(0f, 0f).light(light).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(200, 200, 200, a).texture(0f, 1f).light(light).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(200, 200, 200, a).texture(1f, 1f).light(light).next()
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(1f, 0f).light(light).next()
    }

    fun renderScaledCuboid(matrices: MatrixStack, consumer: VertexConsumer, a: Int, light: Int, sizeX: Float, sizeY: Float, sizeZ: Float, texture: Identifier?, scalingMode: Scaling?, centered: Boolean) {

        //0x00F000F0
        val model = matrices.peek().model
        val matrix = matrices.peek().normal
        val sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(texture)
        var u = sprite.maxU
        var v = sprite.maxV
        var minU = sprite.minU
        var minV = sprite.minV
        val offsetX = Math.max(0f, (1 - sizeX) / 2)
        val offsetY = Math.max(0f, (1 - sizeY) / 2)
        val offsetZ = Math.max(0f, (1 - sizeZ) / 2)
        if (centered) matrices.translate(-sizeX / 2.toDouble(), -sizeY / 2.toDouble(), -sizeZ / 2.toDouble())
        when (scalingMode) {
            Scaling.MAX -> {
                u = Math.min(1f, sizeX)
                v = Math.min(1f, sizeY)
            }
            Scaling.MIN -> {
                minU = Math.max(0f, 1 - sizeX)
                minV = Math.max(0f, 1 - sizeY)
                u = 1f
                v = 1f
            }
            Scaling.CENTER -> {
                minU = 0 + offsetX
                minV = 0 + offsetY
                u = 1 - offsetX
                v = 1 - offsetY
            }
        }
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(200, 200, 200, a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(200, 200, 200, a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(200, 200, 200, a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        when (scalingMode) {
            Scaling.MAX -> {
                u = Math.min(1f, sizeZ)
                v = Math.min(1f, sizeY)
            }
            Scaling.MIN -> {
                minU = Math.max(0f, 1 - sizeZ)
                minV = Math.max(0f, 1 - sizeY)
                u = 1f
                v = 1f
            }
            Scaling.CENTER -> {
                minU = 0 + offsetZ
                minV = 0 + offsetY
                u = 1 - offsetZ
                v = 1 - offsetY
            }
        }
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(200, 200, 200, a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(200, 200, 200, a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(200, 200, 200, a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        when (scalingMode) {
            Scaling.MAX -> {
                u = Math.min(1f, sizeX)
                v = Math.min(1f, sizeY)
            }
            Scaling.MIN -> {
                minU = Math.max(0f, 1 - sizeX)
                minV = Math.max(0f, 1 - sizeY)
                u = 1f
                v = 1f
            }
            Scaling.CENTER -> {
                minU = 0 + offsetX
                minV = 0 + offsetY
                u = 1 - offsetX
                v = 1 - offsetY
            }
        }
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f))
        matrices.translate(0.0, 0.0, -sizeZ.toDouble())
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(200, 200, 200, a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(200, 200, 200, a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(200, 200, 200, a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        when (scalingMode) {
            Scaling.MAX -> {
                u = Math.min(1f, sizeZ)
                v = Math.min(1f, sizeY)
            }
            Scaling.MIN -> {
                minU = Math.max(0f, 1 - sizeZ)
                minV = Math.max(0f, 1 - sizeY)
                u = 1f
                v = 1f
            }
            Scaling.CENTER -> {
                minU = 0 + offsetZ
                minV = 0 + offsetY
                u = 1 - offsetZ
                v = 1 - offsetY
            }
        }
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(200, 200, 200, a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(200, 200, 200, a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(200, 200, 200, a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        when (scalingMode) {
            Scaling.MAX -> {
                u = Math.min(1f, sizeZ)
                v = Math.min(1f, sizeX)
            }
            Scaling.MIN -> {
                minU = Math.max(0f, 1 - sizeZ)
                minV = Math.max(0f, 1 - sizeX)
                u = 1f
                v = 1f
            }
            Scaling.CENTER -> {
                minU = 0 + offsetZ
                minV = 0 + offsetX
                u = 1 - offsetZ
                v = 1 - offsetX
            }
        }
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(200, 200, 200, a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(200, 200, 200, a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(200, 200, 200, a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        matrices.translate(0.0, sizeY.toDouble(), 0.0)
        consumer.vertex(model, 0f, 0f, sizeZ).color(200, 200, 200, a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(200, 200, 200, a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(200, 200, 200, a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, 0f, 0f).color(200, 200, 200, a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        matrices.translate(0.0, -sizeY.toDouble(), 0.0)
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-180f))
        matrices.translate(0.0, 0.0, sizeZ.toDouble())
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        matrices.translate(0.0, 0.0, -sizeZ * 2.toDouble())
        if (centered) matrices.translate(sizeX / 2.toDouble(), sizeY / 2.toDouble(), sizeZ / 2.toDouble())
    }

    fun renderColoredCuboid(matrices: MatrixStack, consumer: VertexConsumer, wrapper: RenderMathHelper.RGBAWrapper, light: Int, sizeX: Float, sizeY: Float, sizeZ: Float, texture: Identifier?, centered: Boolean) {

        //0x00F000F0
        val model = matrices.peek().model
        val matrix = matrices.peek().normal
        val sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(texture)
        val u = sprite.maxU
        val v = sprite.maxV
        val minU = sprite.minU
        val minV = sprite.minV
        if (centered) matrices.translate(-sizeX / 2.toDouble(), -sizeY / 2.toDouble(), -sizeZ / 2.toDouble())
        consumer.vertex(model, 0f, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f))
        matrices.translate(0.0, 0.0, -sizeZ.toDouble())
        consumer.vertex(model, 0f, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        matrices.translate(0.0, sizeY.toDouble(), 0.0)
        consumer.vertex(model, 0f, 0f, sizeZ).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(minU, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, v).light(light).normal(matrix, 1f, 1f, 1f).next()
        consumer.vertex(model, 0f, 0f, 0f).color(wrapper.r, wrapper.g, wrapper.b, wrapper.a).texture(u, minV).light(light).normal(matrix, 1f, 1f, 1f).next()
        matrices.translate(0.0, -sizeY.toDouble(), 0.0)
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-180f))
        matrices.translate(0.0, 0.0, sizeZ.toDouble())
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        matrices.translate(0.0, 0.0, -sizeZ * 2.toDouble())
        if (centered) matrices.translate(sizeX / 2.toDouble(), sizeY / 2.toDouble(), sizeZ / 2.toDouble())
    }

    @JvmStatic
    fun renderScaledOverlayCuboid(matrices: MatrixStack, consumers: VertexConsumerProvider, r: Int, g: Int, b: Int, a: Int, sizeX: Float, sizeY: Float, sizeZ: Float, centered: Boolean) {

        //0x00F000F0
        var sizeX = sizeX
        var sizeY = sizeY
        var sizeZ = sizeZ
        val model = matrices.peek().model
        val consumer = consumers.getBuffer(FFRenderLayers.getOverlayBloomLayer())
        sizeX /= 16f
        sizeY /= 16f
        sizeZ /= 16f
        if (centered) matrices.translate(-sizeX / 2.toDouble(), -sizeY / 2.toDouble(), -sizeZ / 2.toDouble())
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f))
        matrices.translate(0.0, 0.0, -sizeZ.toDouble())
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, sizeY, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        matrices.translate(0.0, sizeY.toDouble(), 0.0)
        consumer.vertex(model, 0f, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, sizeZ).color(r, g, b, a).next()
        consumer.vertex(model, sizeX, 0f, 0f).color(r, g, b, a).next()
        consumer.vertex(model, 0f, 0f, 0f).color(r, g, b, a).next()
        matrices.translate(0.0, -sizeY.toDouble(), 0.0)
        matrices.translate(sizeX.toDouble(), 0.0, 0.0)
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-180f))
        matrices.translate(0.0, 0.0, sizeZ.toDouble())
        matrices.translate(-sizeX.toDouble(), 0.0, 0.0)
        matrices.translate(0.0, 0.0, -sizeZ * 2.toDouble())
        if (centered) matrices.translate(sizeX / 2.toDouble(), sizeY / 2.toDouble(), sizeZ / 2.toDouble())
    }

    fun applyPermutations(matrices: MatrixStack, direction: Direction?) {
        when (direction) {
            Direction.EAST -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90f))
            Direction.SOUTH -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f))
            Direction.WEST -> matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270f))
        }
    }

    enum class Scaling {
        MIN, MAX, CENTER
    }
}
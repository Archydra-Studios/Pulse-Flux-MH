package net.azzy.pulseflux.client.util

import net.azzy.pulseflux.ClientInit
import net.azzy.pulseflux.client.util.RenderHelper.renderScaledOverlayCuboid
import net.azzy.pulseflux.client.util.RenderMathHelper.RGBAWrapper
import net.azzy.pulseflux.util.energy.PulseNode.Polarity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.util.math.Vector3f
import net.minecraft.util.math.Direction

interface PulseRenderer<T : PulseRenderingEntity?> {
    fun renderPulse(matrices: MatrixStack, consumers: VertexConsumerProvider?, entity: T, tickDelta: Float) {
        if (entity!!.pulseAlpha <= 0.01f) return
        var color: RGBAWrapper
        val directions = entity.pulseDirections
        for (direction in directions) {
            var pulseLength = (entity.getPulseDistance(direction) * 16).toFloat() - 16
            val sender = if (entity.getSender(direction) is PulseOffsetEntity) entity.getSender(direction) as PulseOffsetEntity else null
            val offset: Float = if (entity is PulseOffsetEntity) -(entity as PulseOffsetEntity).pixelOffset / 16f else 0F
            pulseLength += offset * -16 + (sender?.pixelOffset ?: 0)
            if (pulseLength <= 0) continue
            color = when (entity.getPulsePolarity(direction)) {
                Polarity.NEGATIVE -> ClientInit.NEGATIVE_COLOR
                Polarity.POSITIVE -> ClientInit.POSITIVE_COLOR
                else -> ClientInit.NEUTRAL_COLOR
            }
            val a = (entity.pulseAlpha * 120).toInt()
            matrices.push()
            if (direction == Direction.NORTH) {
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90f))
                matrices.translate(-1.0, 0.0, 0.0)
            } else if (direction == Direction.WEST) {
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180f))
                matrices.translate(-1.0, 0.0, -1.0)
            } else if (direction == Direction.SOUTH) {
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270f))
                matrices.translate(0.0, 0.0, -1.0)
            } else if (direction == Direction.UP) {
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90f))
                matrices.translate(0.0, -1.0, 0.0)
            } else if (direction == Direction.DOWN) {
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(270f))
                matrices.translate(-1.0, 0.0, 0.0)
            }
            matrices.translate(1 + offset.toDouble(), 6.0 / 16, 6.0 / 16)
            renderScaledOverlayCuboid(matrices, consumers!!, color.r, color.g, color.b, a, pulseLength, 4f, 4f, false)
            matrices.translate(0.0, 1.0 / 16, 1.0 / 16)
            renderScaledOverlayCuboid(matrices, consumers, color.r, color.g, color.b, (a + entity.pulseAlpha * 50).toInt(), pulseLength, 2f, 2f, false)
            matrices.pop()
        }
    }
}
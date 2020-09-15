package net.azzy.pulseflux.client.util

import net.azzy.pulseflux.client.util.RenderHelper.renderOverlayCuboid
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Direction
import java.util.*

interface IORenderer<T : IORenderingEntity?> {
    fun renderIO(matrices: MatrixStack, consumers: VertexConsumerProvider?, entity: T, tickDelta: Double) {
        if (entity!!.renderTickTime <= 0) return
        val `in`: List<Direction> = LinkedList(entity.renderInputs)
        val out: MutableList<Direction> = LinkedList(entity.renderOutputs)
        val alpha = Math.min(entity.renderTickTime, 100)
        for (direction in `in`) {
            matrices.push()
            translateDirection(matrices, direction, false)
            if (out.contains(direction)) {
                out.remove(direction)
                renderOverlayCuboid(matrices, consumers!!, 252, 222, 116, alpha, 1f, 1f, 1f, true)
                matrices.pop()
                continue
            }
            renderOverlayCuboid(matrices, consumers!!, 105, 255, 147, alpha, 1f, 1f, 1f, true)
            matrices.pop()
        }
        for (direction in out) {
            matrices.push()
            translateDirection(matrices, direction, false)
            renderOverlayCuboid(matrices, consumers!!, 255, 75, 39, alpha, 1f, 1f, 1f, false)
            matrices.pop()
        }
    }

    fun translateDirection(matrices: MatrixStack, direction: Direction?, inverse: Boolean) {
        val multiplier = if (inverse) -1 else 1
        when (direction) {
            Direction.SOUTH -> matrices.translate(0.0, 0.0, multiplier.toDouble())
            Direction.EAST -> matrices.translate(multiplier.toDouble(), 0.0, 0.0)
            Direction.WEST -> matrices.translate(-multiplier.toDouble(), 0.0, 0.0)
            Direction.UP -> matrices.translate(0.0, multiplier.toDouble(), 0.0)
            Direction.DOWN -> matrices.translate(0.0, -multiplier.toDouble(), 0.0)
            else -> matrices.translate(0.0, 0.0, -multiplier.toDouble())
        }
    }
}
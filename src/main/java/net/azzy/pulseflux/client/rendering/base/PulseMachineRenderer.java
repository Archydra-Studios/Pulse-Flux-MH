package net.azzy.pulseflux.client.rendering.base;

import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.azzy.pulseflux.client.util.IORenderer;
import net.azzy.pulseflux.client.util.IORenderingEntity;
import net.azzy.pulseflux.client.util.PulseRenderer;
import net.azzy.pulseflux.client.util.PulseRenderingEntity;
import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class PulseMachineRenderer<T extends BlockEntity & IORenderingEntity & PulseRenderingEntity & PulseNode> extends IOMachineRenderer<T> implements PulseRenderer<T> {
    public PulseMachineRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);
        renderPulse(matrices, vertexConsumers, entity, tickDelta);
    }
}

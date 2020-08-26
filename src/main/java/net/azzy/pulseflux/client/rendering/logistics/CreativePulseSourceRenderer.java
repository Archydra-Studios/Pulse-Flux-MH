package net.azzy.pulseflux.client.rendering.logistics;

import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity;
import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.azzy.pulseflux.client.util.IORenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class CreativePulseSourceRenderer extends BlockEntityRenderer<CreativePulseSourceEntity> implements IORenderer<CreativePulseSourceEntity> {

    public CreativePulseSourceRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(CreativePulseSourceEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        renderIO(matrices, vertexConsumers, entity, tickDelta);
    }
}

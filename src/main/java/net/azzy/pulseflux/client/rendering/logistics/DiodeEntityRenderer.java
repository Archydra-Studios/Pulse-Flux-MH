package net.azzy.pulseflux.client.rendering.logistics;

import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.azzy.pulseflux.client.util.IORenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class DiodeEntityRenderer<T extends DiodeEntity> extends BlockEntityRenderer<T> implements IORenderer<T> {

    public DiodeEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        renderIO(matrices, vertexConsumers, entity, tickDelta);
    }
}

package net.azzy.pulseflux.client.rendering.base;

import net.azzy.pulseflux.client.util.IORenderer;
import net.azzy.pulseflux.client.util.IORenderingEntity;
import net.azzy.pulseflux.client.util.PulseRenderer;
import net.azzy.pulseflux.client.util.PulseRenderingEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class IOMachineRenderer<T extends BlockEntity & IORenderingEntity>  extends BlockEntityRenderer<T> implements IORenderer<T> {


    public IOMachineRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public boolean rendersOutsideBoundingBox(T blockEntity) {
        return true;
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        renderIO(matrices, vertexConsumers, entity, tickDelta);
    }
}

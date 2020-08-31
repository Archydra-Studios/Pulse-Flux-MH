package net.azzy.pulseflux.client.rendering.logistics;

import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity;
import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.azzy.pulseflux.client.rendering.base.IOMachineRenderer;
import net.azzy.pulseflux.client.util.IORenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class CreativePulseSourceRenderer extends IOMachineRenderer<CreativePulseSourceEntity> {

    public CreativePulseSourceRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
}

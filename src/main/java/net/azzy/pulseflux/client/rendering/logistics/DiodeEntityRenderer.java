package net.azzy.pulseflux.client.rendering.logistics;

import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.azzy.pulseflux.client.rendering.base.PulseMachineRenderer;
import net.azzy.pulseflux.client.util.IORenderer;
import net.azzy.pulseflux.client.util.PulseRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class DiodeEntityRenderer<T extends DiodeEntity> extends PulseMachineRenderer<T> {

    public DiodeEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
}

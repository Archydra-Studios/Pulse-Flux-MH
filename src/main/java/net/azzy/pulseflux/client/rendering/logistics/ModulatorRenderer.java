package net.azzy.pulseflux.client.rendering.logistics;

import net.azzy.pulseflux.blockentity.logistic.ModulatorEntity;
import net.azzy.pulseflux.client.rendering.base.PulseMachineRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

public class ModulatorRenderer <T extends ModulatorEntity> extends PulseMachineRenderer<T> {

    public ModulatorRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
}

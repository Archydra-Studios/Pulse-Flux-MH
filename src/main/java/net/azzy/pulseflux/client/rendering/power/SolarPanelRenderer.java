package net.azzy.pulseflux.client.rendering.power;

import net.azzy.pulseflux.blockentity.power.SolarPanelEntity;
import net.azzy.pulseflux.client.rendering.base.IOMachineRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

public class SolarPanelRenderer extends IOMachineRenderer<SolarPanelEntity> {

    public SolarPanelRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
}

package net.azzy.pulseflux.client.rendering.logistics;

import net.azzy.pulseflux.blockentity.logistic.FailingPulseCarryingEntity;
import net.azzy.pulseflux.blockentity.logistic.misc.SolenoidSplittingEntity;
import net.azzy.pulseflux.client.rendering.base.PulseMachineRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

public class SolenoidRenderer<T extends FailingPulseCarryingEntity> extends PulseMachineRenderer<T> {

    public SolenoidRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
}

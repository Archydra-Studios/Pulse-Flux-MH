package net.azzy.pulseflux.registry;

import net.azzy.pulseflux.client.rendering.base.IOMachineRenderer;
import net.azzy.pulseflux.client.rendering.base.PulseMachineRenderer;
import net.azzy.pulseflux.client.rendering.logistics.EverfullUrnRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;

import java.util.function.Function;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.*;

public class RenderRegistry {

    public static void init() {
        register(STEEL_DIODE_ENTITY, PulseMachineRenderer::new);
        register(MODULATOR_2_ENTITY, PulseMachineRenderer::new);
        register(MODULATOR_4_ENTITY, PulseMachineRenderer::new);
        register(MODULATOR_8_ENTITY, PulseMachineRenderer::new);
        register(SOLENOID_SPLITTING_ENTITY, PulseMachineRenderer::new);
        register(SOLENOID_MERGING_ENTITY, PulseMachineRenderer::new);
        register(SOLAR_PANEL_ENTITY, IOMachineRenderer::new);
        register(EVERFULL_URN_ENTITY, EverfullUrnRenderer::new);
        register(THERMAL_DYNAMO_ENTITY, IOMachineRenderer::new);
    }

    private static <T extends BlockEntity> void register(BlockEntityType<T> item, Function<BlockEntityRenderDispatcher, BlockEntityRenderer<T>> rendererProvider){
        BlockEntityRendererRegistry.INSTANCE.register(item, rendererProvider);
    }
}

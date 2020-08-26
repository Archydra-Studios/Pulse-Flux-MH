package net.azzy.pulseflux.registry;

import net.azzy.pulseflux.client.rendering.logistics.CreativePulseSourceRenderer;
import net.azzy.pulseflux.client.rendering.logistics.DiodeEntityRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;

import java.util.function.Function;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.*;

public class RenderRegistry {

    public static void init() {
        register(STEEL_DIODE_ENTITY, DiodeEntityRenderer::new);
        register(CREATIVE_PULSE_SOURCE, CreativePulseSourceRenderer::new);
    }

    private static <T extends BlockEntity> void register(BlockEntityType<T> item, Function<BlockEntityRenderDispatcher, BlockEntityRenderer<T>> rendererProvider){
        BlockEntityRendererRegistry.INSTANCE.register(item, rendererProvider);
    }
}

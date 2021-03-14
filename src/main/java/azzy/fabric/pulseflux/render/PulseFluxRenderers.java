package azzy.fabric.pulseflux.render;

import azzy.fabric.pulseflux.block.PulseFluxBlocks;
import azzy.fabric.pulseflux.render.logistic.CreativePulseSourceRenderer;
import azzy.fabric.pulseflux.render.logistic.DiodeRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

public class PulseFluxRenderers {

    public static void init() {
        BlockEntityRendererRegistry.INSTANCE.register(PulseFluxBlocks.HSLA_STEEL_DIODE_BLOCK_ENTITY, DiodeRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(PulseFluxBlocks.CREATIVE_PULSE_SOURCE_BLOCK_ENTITY, CreativePulseSourceRenderer::new);
    }
}

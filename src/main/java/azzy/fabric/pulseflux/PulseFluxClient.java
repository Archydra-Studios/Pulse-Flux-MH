package azzy.fabric.pulseflux;

import azzy.fabric.pulseflux.block.PulseFluxBlock;
import azzy.fabric.pulseflux.block.PulseFluxBlocks;
import azzy.fabric.pulseflux.render.PulseFluxRenderers;
import net.fabricmc.api.ClientModInitializer;

public class PulseFluxClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PulseFluxBlocks.registerRenderLayers();
        PulseFluxRenderers.init();
    }
}

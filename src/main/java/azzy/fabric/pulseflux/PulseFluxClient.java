package azzy.fabric.pulseflux;


import azzy.fabric.pulseflux.block.PulseFluxBlocks;
import azzy.fabric.pulseflux.render.PulseFluxRenderers;
import net.fabricmc.api.ClientModInitializer;

@SuppressWarnings("unused")
public class PulseFluxClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PulseFluxBlocks.registerRenderLayers();
        PulseFluxRenderers.init();
    }
}

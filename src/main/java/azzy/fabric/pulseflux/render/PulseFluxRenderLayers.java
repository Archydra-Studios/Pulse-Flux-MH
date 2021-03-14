package azzy.fabric.pulseflux.render;

import azzy.fabric.pulseflux.PulseFluxCommon;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class PulseFluxRenderLayers extends RenderLayer {

    public PulseFluxRenderLayers(String name, VertexFormat vertexFormat, int drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static final RenderLayer SOFT_BLOOM = RenderLayer.of("pulseflux:soft_bloom", VertexFormats.POSITION_COLOR, GL11.GL_QUADS, 256, false, true, RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(PulseFluxCommon.id("textures/special/blank.png"), false, false)).diffuseLighting(DISABLE_DIFFUSE_LIGHTING).transparency(TRANSLUCENT_TRANSPARENCY).target(RenderPhase.ITEM_TARGET).lightmap(DISABLE_LIGHTMAP).fog(NO_FOG).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).build(true));
}

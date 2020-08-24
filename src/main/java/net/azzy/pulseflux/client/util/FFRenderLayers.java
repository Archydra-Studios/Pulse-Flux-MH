package net.azzy.pulseflux.client.util;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;


public abstract class FFRenderLayers extends RenderLayer {



    public static final RenderLayer IRIDESCENT = RenderLayer.of("pulseflux:iridescent", VertexFormats.POSITION_COLOR_TEXTURE, 7, 256, false, true, MultiPhaseParameters.builder().texture(MIPMAP_BLOCK_ATLAS_TEXTURE).lightmap(RenderPhase.DISABLE_LIGHTMAP).diffuseLighting(DISABLE_DIFFUSE_LIGHTING).transparency(TRANSLUCENT_TRANSPARENCY).target(TRANSLUCENT_TARGET).build(true));
    public static final RenderLayer OVERLAY = RenderLayer.of("pulseflux:overlay", VertexFormats.POSITION_COLOR, GL11.GL_QUADS, 256, false, true, MultiPhaseParameters.builder().texture(new Texture(new Identifier(MOD_ID, "textures/special/overlay.png"), false, false)).diffuseLighting(DISABLE_DIFFUSE_LIGHTING).transparency(TRANSLUCENT_TRANSPARENCY).target(RenderPhase.ITEM_TARGET).lightmap(DISABLE_LIGHTMAP).fog(NO_FOG).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).build(true));


    public FFRenderLayers(String name, VertexFormat vertexFormat, int drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
}

package net.azzy.pulseflux.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.azzy.pulseflux.client.shaders.ShaderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import static net.azzy.pulseflux.PulseFlux.CANVAS_LOADED;
import static net.azzy.pulseflux.PulseFlux.MOD_ID;


public abstract class FFRenderLayers extends RenderLayer {

    public static final RenderLayer IRIDESCENT = RenderLayer.of("pulseflux:iridescent", VertexFormats.POSITION_COLOR_TEXTURE, 7, 256, false, true, MultiPhaseParameters.builder().texture(MIPMAP_BLOCK_ATLAS_TEXTURE).lightmap(RenderPhase.DISABLE_LIGHTMAP).diffuseLighting(DISABLE_DIFFUSE_LIGHTING).transparency(TRANSLUCENT_TRANSPARENCY).target(TRANSLUCENT_TARGET).build(true));
    public static final RenderPhase.Target OVERLAY_TARGET = new RenderPhase.Target("pulseflux:overlay_target", () -> {
        ShaderManager.BLOOM_BUFFER.beginWrite(false);
        RenderSystem.depthMask(false);
    }, () -> {
        MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
        RenderSystem.depthMask(true);
    });
    public static final RenderLayer OVERLAY = RenderLayer.of(
            "pulseflux:overlay",
            VertexFormats.POSITION_COLOR,
            GL11.GL_QUADS,
            256,
            false,
            true,
            MultiPhaseParameters.builder()
                    .texture(new Texture(new Identifier(MOD_ID, "textures/special/overlay.png"), false, false))
                    .diffuseLighting(DISABLE_DIFFUSE_LIGHTING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .target(OVERLAY_TARGET)
                    .lightmap(DISABLE_LIGHTMAP)
                    .fog(NO_FOG)
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .build(true)
    );

    public static final RenderLayer COMPAT_OVERLAY = RenderLayer.of(
            "pulseflux:overlay",
            VertexFormats.POSITION_COLOR,
            GL11.GL_QUADS,
            256,
            false,
            true,
            MultiPhaseParameters.builder()
                    .texture(new Texture(new Identifier(MOD_ID, "textures/special/overlay.png"), false, false))
                    .diffuseLighting(DISABLE_DIFFUSE_LIGHTING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .target(RenderPhase.TRANSLUCENT_TARGET)
                    .lightmap(DISABLE_LIGHTMAP)
                    .fog(NO_FOG)
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .build(true)
    );

    public static final RenderLayer FLUID_BLOOM = RenderLayer.of(
            "pulseflux:fluid_bloom",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            GL11.GL_QUADS,
            256,
            false,
            false,
            MultiPhaseParameters.builder()
                    .texture(BLOCK_ATLAS_TEXTURE)
                    .diffuseLighting(DISABLE_DIFFUSE_LIGHTING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .target(OVERLAY_TARGET)
                    .lightmap(DISABLE_LIGHTMAP)
                    .fog(NO_FOG)
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .build(true)
    );

    public static final RenderLayer TRANSLUCENT_FLUID_BLOOM = RenderLayer.of(
            "pulseflux:trans_fluid_bloom",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            GL11.GL_QUADS,
            256,
            false,
            true,
            MultiPhaseParameters.builder()
                    .texture(BLOCK_ATLAS_TEXTURE)
                    .diffuseLighting(DISABLE_DIFFUSE_LIGHTING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .target(OVERLAY_TARGET)
                    .lightmap(DISABLE_LIGHTMAP)
                    .fog(NO_FOG)
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .build(true)
    );


    public FFRenderLayers(String name, VertexFormat vertexFormat, int drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static RenderLayer getOverlayBloomLayer(){
        return !CANVAS_LOADED ? OVERLAY : COMPAT_OVERLAY;
    }
}

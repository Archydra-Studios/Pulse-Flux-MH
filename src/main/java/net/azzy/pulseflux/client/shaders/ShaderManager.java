package net.azzy.pulseflux.client.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ManagedShaderProgram;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.managed.uniform.Uniform2f;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class ShaderManager {

    public static final ManagedShaderEffect PULSE__BLOOM = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/pulse_bloom.json"), shader -> {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getFramebuffer().beginWrite(false);
        int depthTexture = client.getFramebuffer().getDepthAttachment();
        if (depthTexture > -1) {
            ShaderManager.BLOOM_BUFFER.beginWrite(false);
            // Use the same depth texture for our framebuffer as the main one
            GlStateManager.framebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthTexture, 0);
        }
    });
    public static final ManagedFramebuffer BLOOM_BUFFER = PULSE__BLOOM.getTarget("in");
    //private static final Uniform1f uniformTicktime = PULSE__BLOOM.findUniform1f("tickTime"), uniformFragAlpha = PULSE__BLOOM.findUniform1f("tickTime");
    private static int ticks;

    public static void init(){
        ClientTickCallback.EVENT.register(client -> ticks++);
        ClientTickCallback.EVENT.register(minecraftClient -> {
        });
        ShaderEffectRenderCallback.EVENT.register(e -> {
            BLOOM_BUFFER.clear();
        });
        //EntitiesPreRenderCallback.EVENT.register((camera, frustum, tickDelta) -> uniformTicktime.set((ticks + tickDelta) * 0.05f));
        //uniformFragAlpha.set(0.5f);
    }
}

package net.azzy.pulseflux.client.shaders;

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
import net.minecraft.util.Identifier;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class ShaderManager {

    public static final ManagedShaderEffect PULSE__BLOOM = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/pulse_bloom.json"));
    public static final ManagedFramebuffer BLOOM_BUFFER = PULSE__BLOOM.getTarget("in");
    //private static final Uniform1f uniformTicktime = PULSE__BLOOM.findUniform1f("tickTime"), uniformFragAlpha = PULSE__BLOOM.findUniform1f("tickTime");
    private static int ticks;

    public static void init(){
        ClientTickCallback.EVENT.register(client -> ticks++);
        ClientTickCallback.EVENT.register(minecraftClient -> {
        });
        ShaderEffectRenderCallback.EVENT.register(e -> {
            PULSE__BLOOM.render(1);
        });
        //EntitiesPreRenderCallback.EVENT.register((camera, frustum, tickDelta) -> uniformTicktime.set((ticks + tickDelta) * 0.05f));
        //uniformFragAlpha.set(0.5f);
    }
}

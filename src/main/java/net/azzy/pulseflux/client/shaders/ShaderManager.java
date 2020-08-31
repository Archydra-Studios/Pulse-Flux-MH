package net.azzy.pulseflux.client.shaders;

import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ManagedShaderProgram;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class ShaderManager {

    public static final ManagedShaderEffect GREYSCALE_SHADER = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/greyscale.json"));
    public static final ManagedShaderProgram PULSE__BLOOM = ShaderEffectManager.getInstance().manageProgram(new Identifier("pulseflux", "pulse"));
    private static final Uniform1f uniformTicktime = PULSE__BLOOM.findUniform1f("tickTime"), uniformFragAlpha = PULSE__BLOOM.findUniform1f("tickTime");
    private static int ticks;

    public static void init(){
        ShaderEffectRenderCallback.EVENT.register(GREYSCALE_SHADER::render);
        ClientTickCallback.EVENT.register(client -> ticks++);
        EntitiesPreRenderCallback.EVENT.register((camera, frustum, tickDelta) -> uniformTicktime.set((ticks + tickDelta) * 0.05f));
        uniformFragAlpha.set(0.5f);
    }
}

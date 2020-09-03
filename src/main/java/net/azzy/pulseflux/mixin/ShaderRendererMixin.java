package net.azzy.pulseflux.mixin;


import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.azzy.pulseflux.client.shaders.ShaderManager.BLOOM_BUFFER;
import static net.azzy.pulseflux.client.shaders.ShaderManager.PULSE__BLOOM;

@Mixin(WorldRenderer.class)
public class ShaderRendererMixin {

    @Inject(method = "render", at = @At("RETURN"))
    void renderShaders(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci){
        PULSE__BLOOM.render(tickDelta);
    }
}

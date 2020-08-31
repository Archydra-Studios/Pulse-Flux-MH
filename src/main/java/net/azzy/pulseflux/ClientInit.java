package net.azzy.pulseflux;

import net.azzy.pulseflux.client.shaders.ShaderManager;
import net.azzy.pulseflux.client.util.RenderMathHelper;
import net.azzy.pulseflux.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.azzy.pulseflux.registry.FluidRegistry.FLUID_PAIRS;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {

    //public static ConfigBuilder builder;

    public static final RenderMathHelper.RGBAWrapper NEGATIVE_COLOR = RenderMathHelper.fromHex(0xb9f367);
    public static final RenderMathHelper.RGBAWrapper POSITIVE_COLOR = RenderMathHelper.fromHex(0xffd265);
    public static final RenderMathHelper.RGBAWrapper NEUTRAL_COLOR = RenderMathHelper.fromHex(0xef65ff);

    public static void initTransparency(List<Block> transparentblocks) {
        for (Block item : transparentblocks)
            BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getTranslucent());
    }

    public static void initFluidTransparency(List<Fluid> transparentfluids) {
        for (Fluid item : transparentfluids)
            BlockRenderLayerMap.INSTANCE.putFluid(item, RenderLayer.getTranslucent());
    }

    public static void initPartialblocks(List<Block> partialblocks) {
        for (Block item : partialblocks)
            BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getCutoutMipped());
    }

    @Override
    public void onInitializeClient() {
        BlockRegistry.initTransparency();
        initTransparency(BlockRegistry.REGISTRY_TRANS);
        BlockRegistry.initPartialblocks();
        initPartialblocks(BlockRegistry.REGISTRY_PARTIAL);
        FluidRegistry.initTransparency();
        initFluidTransparency(FluidRegistry.FLUID_TRANS);
        GuiRegistry.init();
        RenderRegistry.init();
        ColorRegistry.init();
        ParticleRegistry.initClient();
        ShaderManager.init();

        for (FluidRegistry.FluidPair temp : FLUID_PAIRS) {
            FluidRenderRegistry.setupFluidRendering(temp.getStillState(), temp.getFlowState(), new Identifier("minecraft", "water"), temp.getColor());
        }
    }
}

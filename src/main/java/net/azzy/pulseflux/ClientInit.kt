package net.azzy.pulseflux

import net.azzy.pulseflux.PulseFlux.CANVAS_LOADED
import net.azzy.pulseflux.PulseFlux.PFLog
import net.azzy.pulseflux.client.shaders.ShaderManager
import net.azzy.pulseflux.client.util.RenderMathHelper.fromHex
import net.azzy.pulseflux.registry.*
import net.azzy.pulseflux.registry.BlockRegistry.initPartialblocks
import net.azzy.pulseflux.registry.FluidRegistry.FLUID_PAIRS
import net.azzy.pulseflux.registry.FluidRenderRegistry.setupFluidRendering
import net.azzy.pulseflux.registry.ParticleRegistry.initClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Block
import net.minecraft.client.render.RenderLayer
import net.minecraft.fluid.Fluid
import net.minecraft.util.Identifier
import kotlin.math.pow

@Environment(EnvType.CLIENT)
class ClientInit : ClientModInitializer {

    override fun onInitializeClient() {
        BlockRegistry.initTransparency()
        initTransparency(BlockRegistry.REGISTRY_TRANS)
        initPartialblocks()
        initPartialblocks(BlockRegistry.REGISTRY_PARTIAL)
        FluidRegistry.initTransparency()
        initFluidTransparency(FluidRegistry.FLUID_TRANS)
        GuiRegistry.init()
        RenderRegistry.init()
        ColorRegistry.init()
        initClient()
        ShaderManager.init()
        for (temp in FLUID_PAIRS) {
            setupFluidRendering(temp.stillState, temp.flowState, Identifier("minecraft", "water"), temp.color)
        }
    }

    companion object {
        //public static ConfigBuilder builder;
        @JvmField
        val NEGATIVE_COLOR = fromHex(0xb9f367)
        @JvmField
        val POSITIVE_COLOR = fromHex(0xffd265)
        @JvmField
        val NEUTRAL_COLOR = fromHex(0xef65ff)

        fun initTransparency(transparentblocks: List<Block?>) {
            for (item in transparentblocks) BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getTranslucent())
        }

        fun initFluidTransparency(transparentfluids: List<Fluid?>) {
            for (item in transparentfluids) BlockRenderLayerMap.INSTANCE.putFluid(item, RenderLayer.getTranslucent())
        }

        fun initPartialblocks(partialblocks: List<Block?>) {
            for (item in partialblocks) BlockRenderLayerMap.INSTANCE.putBlock(item, RenderLayer.getCutoutMipped())
        }
    }
}
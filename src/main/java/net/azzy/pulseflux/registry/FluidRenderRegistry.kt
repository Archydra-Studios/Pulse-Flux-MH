package net.azzy.pulseflux.registry

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockRenderView

object FluidRenderRegistry {
    //Oh god oh fuck
    @JvmStatic
    fun setupFluidRendering(still: Fluid?, flowing: Fluid?, texture: Identifier, color: Int) {
        val stillTexture = Identifier(texture.namespace, "block/" + texture.path + "_still")
        val flowTexture = Identifier(texture.namespace, "block/" + texture.path + "_flow")
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(ClientSpriteRegistryCallback { atlas: SpriteAtlasTexture?, registry: ClientSpriteRegistryCallback.Registry ->
            registry.register(stillTexture)
            registry.register(flowTexture)
        })
        val fluidId = Registry.FLUID.getId(still)
        val listenerId = Identifier(fluidId.namespace, fluidId.path + "_reload_listener")
        val sprites = arrayOfNulls<Sprite>(2)
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
                .registerReloadListener(object : SimpleSynchronousResourceReloadListener {
                    override fun apply(manager: ResourceManager) {
                        val atlas = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)
                        sprites[0] = atlas.apply(stillTexture)
                        sprites[1] = atlas.apply(flowTexture)
                    }

                    override fun getFabricId(): Identifier {
                        return listenerId
                    }
                })
        val renderHandler: FluidRenderHandler = object : FluidRenderHandler {
            override fun getFluidSprites(view: BlockRenderView, pos: BlockPos, state: FluidState): Array<Sprite?> {
                return sprites
            }

            override fun getFluidColor(view: BlockRenderView, pos: BlockPos, state: FluidState): Int {
                return color
            }
        }
        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler)
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler)
    }
}
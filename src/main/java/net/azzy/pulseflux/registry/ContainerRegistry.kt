package net.azzy.pulseflux.registry

import net.azzy.pulseflux.block.entity.logistic.DiodeBlock
import net.azzy.pulseflux.block.entity.logistic.EverfullUrnBlock
import net.azzy.pulseflux.block.entity.production.BlastFurnaceMachine
import net.azzy.pulseflux.util.gui.controller.BlastFurnaceController
import net.azzy.pulseflux.util.gui.controller.CreativeDiodeController
import net.azzy.pulseflux.util.gui.controller.EverfullUrnController
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.Identifier

object ContainerRegistry {
    @JvmStatic
    fun init() {
        ContainerProviderRegistry.INSTANCE.registerFactory(BlastFurnaceMachine.GID) { syncID: Int, id: Identifier?, player: PlayerEntity, buf: PacketByteBuf -> BlastFurnaceController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())) }
        ContainerProviderRegistry.INSTANCE.registerFactory(DiodeBlock.CREATIVE_GID) { syncID: Int, id: Identifier?, player: PlayerEntity, buf: PacketByteBuf -> CreativeDiodeController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())) }
        ContainerProviderRegistry.INSTANCE.registerFactory(EverfullUrnBlock.GID) { syncID: Int, id: Identifier?, player: PlayerEntity, buf: PacketByteBuf -> EverfullUrnController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())) }
    }
}
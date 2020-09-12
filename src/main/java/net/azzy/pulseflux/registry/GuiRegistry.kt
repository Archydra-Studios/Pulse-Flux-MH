package net.azzy.pulseflux.registry

import net.azzy.pulseflux.block.entity.logistic.DiodeBlock
import net.azzy.pulseflux.block.entity.logistic.EverfullUrnBlock
import net.azzy.pulseflux.block.entity.production.BlastFurnaceMachine
import net.azzy.pulseflux.util.gui.controller.BlastFurnaceController
import net.azzy.pulseflux.util.gui.controller.CreativeDiodeController
import net.azzy.pulseflux.util.gui.controller.EverfullUrnController
import net.azzy.pulseflux.util.gui.screen.BlastFurnaceMachineScreen
import net.azzy.pulseflux.util.gui.screen.CreativeDiodeScreen
import net.azzy.pulseflux.util.gui.screen.EverfullUrnScreen
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.Identifier

object GuiRegistry {
    @JvmStatic
    @Environment(EnvType.CLIENT)
    fun init() {
        ScreenProviderRegistry.INSTANCE.registerFactory(BlastFurnaceMachine.GID) { syncID: Int, id: Identifier?, player: PlayerEntity, buf: PacketByteBuf -> BlastFurnaceMachineScreen(BlastFurnaceController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())), player) }
        ScreenProviderRegistry.INSTANCE.registerFactory(DiodeBlock.CREATIVE_GID) { syncID: Int, id: Identifier?, player: PlayerEntity, buf: PacketByteBuf -> CreativeDiodeScreen(CreativeDiodeController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())), player) }
        ScreenProviderRegistry.INSTANCE.registerFactory(EverfullUrnBlock.GID) { syncID: Int, id: Identifier?, player: PlayerEntity, buf: PacketByteBuf -> EverfullUrnScreen(EverfullUrnController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())), player) }
    }
}
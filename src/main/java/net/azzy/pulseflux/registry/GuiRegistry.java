package net.azzy.pulseflux.registry;

import net.azzy.pulseflux.block.entity.production.BlastFurnaceMachine;
import net.azzy.pulseflux.util.gui.controller.BlastFurnaceController;
import net.azzy.pulseflux.util.gui.screen.BlastFurnaceMachineScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.screen.ScreenHandlerContext;

public class GuiRegistry {

    @Environment(EnvType.CLIENT)
    public static void init() {
        ScreenProviderRegistry.INSTANCE.registerFactory(BlastFurnaceMachine.GID, (syncID, id, player, buf) -> new BlastFurnaceMachineScreen(new BlastFurnaceController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())), player));
    }
}
package net.azzy.pulseflux.registry;

import net.azzy.pulseflux.block.entity.production.BlastFurnaceMachine;
import net.azzy.pulseflux.util.gui.controller.BlastFurnaceController;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.screen.ScreenHandlerContext;

public class ContainerRegistry {

    public static void init() {
        ContainerProviderRegistry.INSTANCE.registerFactory(BlastFurnaceMachine.GID, (syncID, id, player, buf) -> new BlastFurnaceController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));
    }
}

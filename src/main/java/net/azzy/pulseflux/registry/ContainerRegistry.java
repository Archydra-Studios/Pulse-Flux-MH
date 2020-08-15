package net.azzy.pulseflux.registry;

public class ContainerRegistry {

    public static void init() {
        //ContainerProviderRegistry.INSTANCE.registerFactory(BlastFurnaceMachine.GID, (syncID, id, player, buf) -> new BlastFurnaceController(syncID, player.inventory, ScreenHandlerContext.create(player.world, buf.readBlockPos())));
    }
}

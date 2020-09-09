package net.azzy.pulseflux.util.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.azzy.pulseflux.util.gui.controller.CreativeDiodeController;
import net.azzy.pulseflux.util.gui.controller.EverfullUrnController;
import net.minecraft.entity.player.PlayerEntity;

public class EverfullUrnScreen extends PFScreen<EverfullUrnController> {

    public EverfullUrnScreen(EverfullUrnController description, PlayerEntity player) {
        super(description, player);
    }

}

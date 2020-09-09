package net.azzy.pulseflux.util.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.azzy.pulseflux.util.gui.controller.BlastFurnaceController;
import net.azzy.pulseflux.util.gui.controller.CreativeDiodeController;
import net.minecraft.entity.player.PlayerEntity;

public class CreativeDiodeScreen extends PFScreen<CreativeDiodeController> {

    public CreativeDiodeScreen(CreativeDiodeController description, PlayerEntity player) {
        super(description, player);
    }

}

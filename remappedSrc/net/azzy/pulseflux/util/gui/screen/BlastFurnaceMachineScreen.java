package net.azzy.pulseflux.util.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.azzy.pulseflux.util.gui.controller.BlastFurnaceController;
import net.minecraft.entity.player.PlayerEntity;

public class BlastFurnaceMachineScreen extends PFScreen<BlastFurnaceController> {

    public BlastFurnaceMachineScreen(BlastFurnaceController description, PlayerEntity player) {
        super(description, player);
    }

}

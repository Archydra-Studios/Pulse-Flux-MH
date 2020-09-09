package net.azzy.pulseflux.util.gui.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;

public abstract class PFScreen <T extends SyncedGuiDescription> extends CottonInventoryScreen<T> {

    public PFScreen(T description, PlayerEntity player) {
        super(description, player, new TranslatableText(""));
    }
}

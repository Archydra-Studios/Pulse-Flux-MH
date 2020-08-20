package net.azzy.pulseflux.util.gui.controller;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class BaseController extends SyncedGuiDescription {
    protected WPlainPanel root = new WPlainPanel();
    protected int slotY, slotX, sizeY, sizeX, spacing, alignment;
    protected String name;
    protected WItemSlot itemSlot;

    BaseController(ScreenHandlerType recipeType, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(recipeType, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        assembleGridSize();
        setRootPanel(root);
        root.setSize(sizeX, sizeY);
        assembleInventory(blockInventory.size(), spacing, spacing);
        root.add(new WLabel(name, 0x3f3f3f), alignment, 0);
        build();
    }

    //Slot sprite size is 18 pixels
    //Inventories should aim to be 162 pixels wide, unless they are not displaying the player's inventory

    protected void assembleGridSize() {
        slotY = 1;
        slotX = 1;
        spacing = 1;
        name = "null";
        sizeY = 96;
        sizeX = 162;
        alignment = 1;
    }

    protected void assembleInventory(int slots, int gapX, int gapY) {
        int s = 0;
        for (int i = 0; i < slotY; i++) {
            for (int j = 0; j < slotX; j++) {
                itemSlot = WItemSlot.of(blockInventory, s);
                root.add(itemSlot, j * gapX + 2, 13 + i * gapY);
                s++;
            }
        }
    }

    protected void build() {
        root.add(this.createPlayerInventoryPanel(), 1, sizeY + 12);
        root.validate(this);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
    }
}

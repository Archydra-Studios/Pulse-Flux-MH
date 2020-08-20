package net.azzy.pulseflux.util.gui.controller;

import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WDynamicLabel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.azzy.pulseflux.blockentity.production.BlastFurnaceMachineEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;
import static net.azzy.pulseflux.registry.BlockRegistry.BLAST_FURNACE_MACHINE;


public class BlastFurnaceController extends BaseController {

    private static Predicate<ItemStack> ingotFilter = e -> e.getItem() == Items.IRON_INGOT || e.getItem() == Items.IRON_BLOCK;
    private static Predicate<ItemStack> catalystFilter = e -> e.getItem() == Items.CHARCOAL || ItemTags.SAND.contains(e.getItem()) || e.getItem() == Items.CLAY_BALL || e.getItem() == Items.COAL || e.getItem() == Items.GUNPOWDER;

    public BlastFurnaceController(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(null, syncId, playerInventory, context);
    }

    @Override
    protected void assembleGridSize() {
        super.assembleGridSize();
        name = I18n.translate(BLAST_FURNACE_MACHINE.getTranslationKey());
    }

    @Override
    protected void assembleInventory(int slots, int gapX, int gapY) {
        //Ingot slots
        root.add(WItemSlot.of(blockInventory, 3).setFilter(ingotFilter), 54, 50);
        root.add(WItemSlot.of(blockInventory, 4).setFilter(ingotFilter), 72, 50);
        root.add(WItemSlot.of(blockInventory, 5).setFilter(ingotFilter), 90, 50);
        root.add(WItemSlot.of(blockInventory, 6).setFilter(ingotFilter), 54, 32);
        root.add(WItemSlot.of(blockInventory, 7).setFilter(ingotFilter), 72, 32);
        root.add(WItemSlot.of(blockInventory, 8).setFilter(ingotFilter), 90, 32);

        //Catalyst slots
        root.add(WItemSlot.of(blockInventory, 0).setFilter(catalystFilter), 54, 86);
        root.add(WItemSlot.of(blockInventory, 1).setFilter(catalystFilter), 72, 86);
        root.add(WItemSlot.of(blockInventory, 2).setFilter(catalystFilter), 90, 86);

        //Output slot
        root.add(WItemSlot.of(blockInventory, 9).setInsertingAllowed(false), 135, 40);

        if(world.isClient()){
            if(((BlastFurnaceMachineEntity) blockInventory).getHeat() >= 1000)
                root.add(new WDynamicLabel(() -> (int) ((BlastFurnaceMachineEntity) blockInventory).getHeat() + "C°"), 13, 21);
            else if(((BlastFurnaceMachineEntity) blockInventory).getHeat() >= 100)
                root.add(new WDynamicLabel(() -> (int) ((BlastFurnaceMachineEntity) blockInventory).getHeat() + "C°"), 16, 21);
            else
                root.add(new WDynamicLabel(() -> (int) ((BlastFurnaceMachineEntity) blockInventory).getHeat() + "C°"), 19, 21);
            WBar temp = new WBar(new Identifier(MOD_ID, "/textures/gui/bars/temp_gauge_large_base.png"), new Identifier(MOD_ID, "/textures/gui/bars/temp_gauge_large_fill.png"), 0, 1, WBar.Direction.UP);
            root.add(temp, 20, 31, 16, 73);

            WBar progressMain = new WBar(new Identifier(MOD_ID, "textures/gui/bars/temp_progress.png"), new Identifier(MOD_ID, "textures/gui/bars/temp_progress_fill.png"), 2, 3, WBar.Direction.RIGHT);
            root.add(progressMain, 113, 41);
            root.add(new WBar(new Identifier(MOD_ID, "textures/gui/bars/melt_progress.png"), new Identifier(MOD_ID, "textures/gui/bars/melt_progress_fill.png"), 2, 3, WBar.Direction.UP), 54, 68);
            root.add(new WBar(new Identifier(MOD_ID, "textures/gui/bars/melt_progress.png"), new Identifier(MOD_ID, "textures/gui/bars/melt_progress_fill.png"), 2, 3, WBar.Direction.UP), 72, 68);
            root.add(new WBar(new Identifier(MOD_ID, "textures/gui/bars/melt_progress.png"), new Identifier(MOD_ID, "textures/gui/bars/melt_progress_fill.png"), 2, 3, WBar.Direction.UP), 90, 68);
        }
    }
}

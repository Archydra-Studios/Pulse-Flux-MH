package net.azzy.pulseflux.item;

import net.azzy.pulseflux.util.interaction.ScrewableEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class ScrewdriverItem extends Item {

    public ScrewdriverItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        boolean sneaking = player.isSneaking();
        BlockEntity entity = context.getWorld().getBlockEntity(context.getBlockPos());
        if(sneaking && entity instanceof ScrewableEntity){
            ((ScrewableEntity) entity).onScrewed(player);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}

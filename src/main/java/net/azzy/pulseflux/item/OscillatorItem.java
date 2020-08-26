package net.azzy.pulseflux.item;

import net.azzy.pulseflux.client.util.IORenderingEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class OscillatorItem extends Item {
    public OscillatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        BlockEntity entity = context.getWorld().getBlockEntity(pos);
        if(entity instanceof IORenderingEntity) {
            ((IORenderingEntity) entity).requestDisplay();
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}

package net.azzy.pulseflux.item;

import net.azzy.pulseflux.util.interaction.PressureHolder;
import net.azzy.pulseflux.util.interaction.WorldPressure;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ManometerItem extends Item {

    public ManometerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        DimensionType dimensionType = world.getDimension();
        PlayerEntity player = context.getPlayer();
        BlockEntity entity = context.getWorld().getBlockEntity(context.getBlockPos());
        if (entity instanceof PressureHolder) {
            player.sendMessage(new LiteralText("Pressure - " + ((PressureHolder) entity).getPressure() / 1000 + "KPa"), true);
            return ActionResult.CONSUME;
        }
        else if (WorldPressure.isDimRegistered(world.getDimension())) {
            player.sendMessage(new LiteralText("Atmospheric Pressure - " + WorldPressure.getDimPressure(world.getDimension(), player.getBlockPos()) / 1000 + "KPa"), true);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}

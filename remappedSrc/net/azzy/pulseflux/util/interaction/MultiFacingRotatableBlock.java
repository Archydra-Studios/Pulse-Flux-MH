package net.azzy.pulseflux.util.interaction;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface MultiFacingRotatableBlock {

    void setInput(World world, BlockPos pos, ItemUsageContext ctx);

    void setOutput(World world, BlockPos pos, ItemUsageContext ctx);
}

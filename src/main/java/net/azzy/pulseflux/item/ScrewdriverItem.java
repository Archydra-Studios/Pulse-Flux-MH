package net.azzy.pulseflux.item;

import net.azzy.pulseflux.util.interaction.MultiFacingRotatableBlock;
import net.azzy.pulseflux.util.interaction.RotatableBlock;
import net.azzy.pulseflux.util.interaction.ScrewableEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScrewdriverItem extends Item {

    public ScrewdriverItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        boolean sneaking = player.isSneaking();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockEntity entity = world.getBlockEntity(blockPos);
        BlockState state = world.getBlockState(blockPos);
        if(sneaking && entity instanceof ScrewableEntity){
            ((ScrewableEntity) entity).onScrewed(player);
            return ActionResult.CONSUME;
        }
        else {
            Block block = state.getBlock();
            if(block instanceof RotatableBlock){
                ((RotatableBlock) block).rotate(world, blockPos, player);
                return ActionResult.CONSUME;
            }
            else if(block instanceof MultiFacingRotatableBlock){
                if(player.isSneaking())
                    ((MultiFacingRotatableBlock) block).setInput(world, blockPos, context);
                else
                    ((MultiFacingRotatableBlock) block).setOutput(world, blockPos, context);
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }
}

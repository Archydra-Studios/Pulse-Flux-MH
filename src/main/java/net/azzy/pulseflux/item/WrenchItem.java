package net.azzy.pulseflux.item;

import net.azzy.pulseflux.util.interaction.MachineBlock;
import net.azzy.pulseflux.util.interaction.ScrewableEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.azzy.pulseflux.item.misc.PFMaterials.HSLA_STEEL;

public class WrenchItem extends SwordItem {

    public WrenchItem(Settings settings) {
        super(HSLA_STEEL, 6, -3F, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        Block block = world.getBlockState(pos).getBlock();
        BlockEntity entity = world.getBlockEntity(pos);
        if(player.isSneaking()){
            if(block instanceof MachineBlock ) {
                world.breakBlock(pos, true);
                if(!world.isClient())
                context.getStack().damage(1, player, e -> {
                    e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                });
                return ActionResult.CONSUME;
            }
        }
        else if(entity instanceof ScrewableEntity){
            ((ScrewableEntity) entity).onScrewed(player);
            return ActionResult.CONSUME;
        }
        return super.useOnBlock(context);
    }
}

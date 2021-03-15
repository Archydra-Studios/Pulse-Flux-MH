package azzy.fabric.pulseflux.item;

import azzy.fabric.pulseflux.energy.PulseFluxEnergyAPIs;
import azzy.fabric.pulseflux.energy.PulseIo;
import azzy.fabric.pulseflux.util.UpdateTimeProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ScrewdriverItem extends ToolItem {

    public ScrewdriverItem(Settings settings) {
        super(PulseFluxToolMaterials.HSLA_STEEL, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction side = context.getSide();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PulseIo provider = PulseFluxEnergyAPIs.PULSE.find(world, pos, side);
        if(provider != null) {
            if(world instanceof ServerWorld) {
                PlayerEntity playerEntity = context.getPlayer();
                if(playerEntity != null) {
                    if(playerEntity.isSneaking()) {
                        provider.setPrimaryInput(side);
                    }
                    else {
                        provider.setPrimaryOutput(side);
                    }

                    if(!playerEntity.isCreative()) {
                        context.getStack().damage(1, playerEntity, (p) -> p.sendToolBreakStatus(context.getHand()));
                    }
                }
            }
            if(provider instanceof UpdateTimeProvider)
                ((UpdateTimeProvider) provider).update(world);
            return ActionResult.success(world.isClient());
        }
        return super.useOnBlock(context);
    }
}

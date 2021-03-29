package azzy.fabric.pulseflux.item;

import azzy.fabric.pulseflux.energy.HeatIo;
import azzy.fabric.pulseflux.energy.PulseFluxEnergyAPIs;
import dev.technici4n.fasttransferlib.api.fluid.FluidApi;
import dev.technici4n.fasttransferlib.api.fluid.FluidIo;
import dev.technici4n.fasttransferlib.api.fluid.FluidTextHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class HeatProbeItem extends Item {

    public HeatProbeItem(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if(player != null && world != null) {
            HeatIo heatIo = PulseFluxEnergyAPIs.HEAT.find(world, context.getBlockPos(), context.getSide());
            if(heatIo != null) {
                player.sendMessage(new LiteralText(String.format("%1.1f", heatIo.getTemperature()) + "℃"), true);
                return ActionResult.success(world.isClient());
            }
        }
        return super.useOnBlock(context);
    }
}

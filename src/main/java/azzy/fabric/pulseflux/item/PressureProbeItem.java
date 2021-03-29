package azzy.fabric.pulseflux.item;

import azzy.fabric.pulseflux.energy.HeatIo;
import azzy.fabric.pulseflux.energy.PressureIo;
import azzy.fabric.pulseflux.energy.PulseFluxEnergyAPIs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class PressureProbeItem extends Item {

    public PressureProbeItem(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if(player != null && world != null) {
            PressureIo pressureIo = PulseFluxEnergyAPIs.PRESSURE.find(world, context.getBlockPos(), context.getSide());
            if(pressureIo != null) {
                player.sendMessage(new LiteralText(String.format("%1.1f", pressureIo.getPressure()) + "kPa"), true);
                return ActionResult.success(world.isClient());
            }
        }
        return super.useOnBlock(context);
    }
}

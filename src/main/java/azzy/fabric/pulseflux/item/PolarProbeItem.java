package azzy.fabric.pulseflux.item;

import azzy.fabric.pulseflux.energy.PulseCarrier;
import azzy.fabric.pulseflux.energy.PulseFluxEnergyAPIs;
import azzy.fabric.pulseflux.energy.PulseIo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class PolarProbeItem extends Item {

    public PolarProbeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if(player != null && world != null) {
            PulseIo pulseIo = PulseFluxEnergyAPIs.PULSE.find(world, context.getBlockPos(), context.getSide());
            if(pulseIo != null) {
                PulseCarrier carrier = pulseIo.getStoredPulse();
                player.sendMessage(new LiteralText("Inductance - " + carrier.getFrequency() + "Fi | Frequency - " + carrier.getFrequency() + "Hz"), true);
                return ActionResult.success(world.isClient());
            }
        }
        return super.useOnBlock(context);
    }
}

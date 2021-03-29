package azzy.fabric.pulseflux.item;

import azzy.fabric.pulseflux.energy.PulseCarrier;
import azzy.fabric.pulseflux.energy.PulseFluxEnergyAPIs;
import azzy.fabric.pulseflux.energy.PulseIo;
import dev.technici4n.fasttransferlib.api.fluid.FluidApi;
import dev.technici4n.fasttransferlib.api.fluid.FluidIo;
import dev.technici4n.fasttransferlib.api.fluid.FluidTextHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class FluidProbeItem extends Item {

    public FluidProbeItem(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();

        if(player != null && world != null) {
            FluidIo fluid = FluidApi.SIDED.find(world, context.getBlockPos(), context.getSide());

            if(fluid != null) {
                Fluid innerFluid = fluid.getFluid(0);
                String name = "";
                boolean empty = false;

                if(innerFluid == Fluids.EMPTY) {
                    empty = true;
                }
                else if(innerFluid.getBucketItem() == null){
                    name = "Unkown";
                }
                else
                    name = innerFluid.getBucketItem().getName().asString().replace("Bucket", "").trim() + " ";

                player.sendMessage(empty ? new LiteralText("Empty") : new LiteralText(name + FluidTextHelper.getUnicodeMillibuckets(fluid.getFluidAmount(0), true) + "mb"), true);
                return ActionResult.success(world.isClient());
            }
        }
        return super.useOnBlock(context);
    }
}

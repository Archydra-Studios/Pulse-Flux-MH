package net.azzy.pulseflux.item;

import net.azzy.pulseflux.blockentity.logistic.FailingPulseCarryingEntity;
import net.azzy.pulseflux.util.interaction.HeatHolder;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.PulseNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SensorItem extends Item {

    public SensorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();

        if(world.getBlockEntity(pos) instanceof PulseNode && world.isClient()){
            PulseNode holder = (PulseNode) world.getBlockEntity(pos);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Block: " + I18n.translate(world.getBlockState(pos).getBlock().getTranslationKey())), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Flux Inductance: " + holder.getAmplitude() + "Fi"), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Frequency: " + holder.getFrequency() + "Hz"), null);
            if(world.getBlockEntity(pos) instanceof FailingPulseCarryingEntity){
                MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Failure Inductance " + ((FailingPulseCarryingEntity) world.getBlockEntity(pos)).getMaxAmplitude() + "Fi"), null);
                MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Decoherence Point " + ((FailingPulseCarryingEntity) world.getBlockEntity(pos)).getMaxFrequency() + "Hz"), null);
            }
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);
        }

        return super.useOnBlock(context);
    }
}

package net.azzy.pulseflux.item;

import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ProbeItem extends Item {

    public ProbeItem(Settings settings) {
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
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Polarity: " + holder.getPolarity().name()), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);

        }

        return super.useOnBlock(context);
    }
}

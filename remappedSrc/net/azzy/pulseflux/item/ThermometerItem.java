package net.azzy.pulseflux.item;

import net.azzy.pulseflux.util.interaction.HeatHolder;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThermometerItem extends Item {

    public ThermometerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();

        if(!HeatTransferHelper.isHeatSource(world.getBlockState(pos).getBlock()) && !(world.getBlockEntity(pos) instanceof HeatHolder))
            pos = pos.up();
        if(world.getBlockEntity(pos) instanceof HeatHolder && world.isClient()){
            HeatHolder holder = (HeatHolder) world.getBlockEntity(pos);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Block: " + I18n.translate(world.getBlockState(pos).getBlock().getTranslationKey())), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Current Temperature: " + (int) holder.getHeat() + "C°"), null);
            //if(world.getBlockEntity(pos) instanceof FailingTransferEntity)
            //    MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Failure Temperature: " + ((SpeenTransferEntity) world.getBlockEntity(pos)).getMaxTemp() + "C°"), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);
        }
        else if(HeatTransferHelper.isHeatSource(world.getBlockState(pos).getBlock()) && world.isClient()){
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Block: " + I18n.translate(world.getBlockState(pos).getBlock().getTranslationKey())), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("Temperature: " + HeatTransferHelper.HeatSource.getSource(world.getBlockState(pos).getBlock()).getTemp() + "C°"), null);
            MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText(""), null);
        }

        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient() && user.isSneaking()){
            user.sendMessage(new LiteralText("Ambient Temperature: " + (int) HeatTransferHelper.translateBiomeHeat(world.getBiome(user.getBlockPos())) + "C°"), true);
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}

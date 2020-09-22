package net.azzy.pulseflux.block.entity.logistic;

import net.azzy.pulseflux.block.entity.PulseCarryingBlock;
import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class DiodeBlock<T extends BlockEntity> extends PulseCarryingBlock<T> {

    public static final Identifier CREATIVE_GID = new Identifier(MOD_ID, "creative_diode_gui");

    public DiodeBlock(Settings settings, Supplier<T> blockEntitySupplier) {
        super(settings, blockEntitySupplier, false);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.isInSneakingPose() && world.getBlockEntity(pos) instanceof CreativePulseSourceEntity) {
            if(!world.isClient())
                ContainerProviderRegistry.INSTANCE.openContainer(CREATIVE_GID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
            return ActionResult.SUCCESS;
        }
        else if(player.isInSneakingPose() && world.getBlockEntity(pos) instanceof CreativePulseSourceEntity && player.getStackInHand(hand).isEmpty())
            if(!world.isClient())
                ((CreativePulseSourceEntity) world.getBlockEntity(pos)).cyclePolarity();
        return ActionResult.PASS;
    }

}

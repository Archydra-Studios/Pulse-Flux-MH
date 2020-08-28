package net.azzy.pulseflux.block.entity.logistic;

import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity;
import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class LinearDiodeBlock <T extends BlockEntity> extends PulseCarryingBlock<T> {

    public static final Identifier CREATIVE_GID = new Identifier(MOD_ID, "creative_diode_gui");

    public LinearDiodeBlock(Settings settings, Supplier<T> blockEntitySupplier) {
        super(settings, blockEntitySupplier);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getPlayerLookDirection();
        return super.getPlacementState(ctx).with(FACING.get(facing.getOpposite()), true);
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

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity entity = world.getBlockEntity(pos);
            for(Direction direction : Direction.values()){
                if(state.get(FACING.get(direction))) {
                    if(entity instanceof DiodeEntity)
                        ((DiodeEntity) entity).recalcIO(direction, state, true);
                    else
                        ((CreativePulseSourceEntity) entity).recalcIO(direction);
                }
        }
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}

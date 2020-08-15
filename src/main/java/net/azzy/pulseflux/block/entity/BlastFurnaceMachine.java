package net.azzy.pulseflux.block.entity;

import azzy.fabric.pulseflux.block.BaseMachine;
import azzy.fabric.pulseflux.staticentities.blockentity.production.BlastFurnaceMachineEntity;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import static azzy.fabric.pulseflux.PulseFlux.MOD_ID;


public class BlastFurnaceMachine extends BaseMachine {

    public static final BooleanProperty LIT;
    public static final Identifier GID = new Identifier(MOD_ID, "blast_furnace_gui");

    public BlastFurnaceMachine(FabricBlockSettings material, VoxelShape bounds) {
        super(material, bounds);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(LIT, false);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(LIT);
        super.appendProperties(stateManager);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.isInSneakingPose() && !world.isClient()) {
            ContainerProviderRegistry.INSTANCE.openContainer(GID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
            return ActionResult.SUCCESS;
        }
        if(!player.isInSneakingPose())
            return ActionResult.SUCCESS;
        return ActionResult.PASS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new BlastFurnaceMachineEntity();
    }

    static {
        LIT = Properties.LIT;
    }
}

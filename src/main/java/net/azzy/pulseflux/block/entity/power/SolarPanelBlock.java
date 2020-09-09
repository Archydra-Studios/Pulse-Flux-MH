package net.azzy.pulseflux.block.entity.power;

import net.azzy.pulseflux.block.entity.PulseCarryingDirectionalBlock;
import net.azzy.pulseflux.blockentity.power.SolarPanelEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.function.Supplier;

public class SolarPanelBlock extends PulseCarryingDirectionalBlock<SolarPanelEntity> {

    public SolarPanelBlock(Settings settings, Supplier<SolarPanelEntity> blockEntitySupplier) {
        super(settings, blockEntitySupplier, true);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0, 0, 0, 16, 32, 16);
    }
}

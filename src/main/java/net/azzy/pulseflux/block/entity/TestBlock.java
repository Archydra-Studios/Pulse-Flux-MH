package net.azzy.pulseflux.block.entity;

import azzy.fabric.pulseflux.block.BaseMachine;
import azzy.fabric.pulseflux.staticentities.blockentity.production.TestEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class TestBlock extends BaseMachine {
    public TestBlock(FabricBlockSettings settings) {
        super(settings, VoxelShapes.fullCube());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new TestEntity();
    }
}

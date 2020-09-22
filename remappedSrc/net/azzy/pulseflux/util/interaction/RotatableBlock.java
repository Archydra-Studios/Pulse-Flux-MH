package net.azzy.pulseflux.util.interaction;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface RotatableBlock {

    void rotate(World world, BlockPos pos, PlayerEntity playerEntity);
}

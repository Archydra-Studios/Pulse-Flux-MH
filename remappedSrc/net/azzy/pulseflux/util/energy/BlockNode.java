package net.azzy.pulseflux.util.energy;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

import java.util.List;

public interface BlockNode {

    List<Direction> getIO(BlockState state);

    List<Direction> getInputs(BlockState state);

    List<Direction> getOutputs(BlockState state);
}

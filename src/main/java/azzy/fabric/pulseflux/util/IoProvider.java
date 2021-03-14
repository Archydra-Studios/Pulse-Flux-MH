package azzy.fabric.pulseflux.util;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collections;
import java.util.Set;

public interface IoProvider {

    default Set<Direction> getInputs() {
        return Collections.emptySet();
    }

    default Set<Direction> getOutputs() {
        return Collections.emptySet();
    }

    default Set<Direction> getActiveOutputs() {
        return Collections.emptySet();
    }

    default void setPrimaryInput(Direction output) {}

    default void setPrimaryOutput(Direction input) {}
}

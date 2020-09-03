package net.azzy.pulseflux.util.energy;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;

public interface PulseNode {

    void accept(Direction direction, BlockPos senders);

    default boolean simulate(World world, BlockEntity sender, boolean noFailure, PulseNode receiver, double max){
        return true;
    }

    long getInductance();

    long getMaxInductance();

    Polarity getPolarity();

    double getFrequency();

    double getMaxFrequency();

    default double getPulseMultiplier(){
        return 1;
    }

    boolean canFail();

    boolean isOverloaded();

    default Collection<Direction> getInputs(){
        return Collections.emptyList();
    }

    default Collection<Direction> getOutputs(){
        return Collections.emptyList();
    }

    enum Polarity{
        POSITIVE,
        NEGATIVE,
        NEUTRAL
    }
}

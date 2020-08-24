package net.azzy.pulseflux.client.util;

import net.minecraft.util.math.Direction;

import java.util.Collection;

public interface IORenderingEntity {
    Collection<Direction> getInputs();

    Collection<Direction> getOutputs();

    int getRenderTickTime();
}

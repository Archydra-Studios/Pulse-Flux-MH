package net.azzy.pulseflux.client.util;

import net.minecraft.util.math.Direction;

import java.util.Collection;
import java.util.Collections;

public interface IORenderingEntity {
    int getRenderTickTime();

    void requestDisplay();

    default Collection<Direction> getRenderInputs(){
        return Collections.emptyList();
    }

    default Collection<Direction> getRenderOutputs(){
        return Collections.emptyList();
    }
}

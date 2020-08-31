package net.azzy.pulseflux.client.util;

import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.util.math.Direction;

import java.util.Set;

public interface PulseRenderingEntity {

    double getPulseDistance();

    float getPulseAlpha();

    Set<Direction> getPulseDirections();

    PulseNode.Polarity getPulsePolarity(Direction direction);
}

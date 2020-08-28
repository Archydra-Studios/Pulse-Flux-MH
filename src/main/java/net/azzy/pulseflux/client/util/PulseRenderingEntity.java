package net.azzy.pulseflux.client.util;

import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.util.math.Direction;

public interface PulseRenderingEntity {

    double getPulseDistance();

    float getPulseAlpha();

    Direction getPulseDirection();

    PulseNode.Polarity getPulsePolarity();
}

package net.azzy.pulseflux.blockentity.logistic.diodes;

import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.minecraft.block.entity.BlockEntityType;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.*;

public class SteelDiodeEntity extends DiodeEntity {

    public SteelDiodeEntity() {
        super(STEEL_DIODE_ENTITY, 1260, 2500);
    }
}

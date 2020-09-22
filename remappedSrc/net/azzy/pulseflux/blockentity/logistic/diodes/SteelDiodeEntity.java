package net.azzy.pulseflux.blockentity.logistic.diodes;

import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.entity.BlockEntityType;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.*;

public class SteelDiodeEntity extends DiodeEntity {

    public SteelDiodeEntity() {
        super(STEEL_DIODE_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, 1260, 2500);
    }
}

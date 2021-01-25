package net.azzy.pulseflux.blockentity.logistic.transport;

import net.azzy.pulseflux.util.interaction.HeatTransferHelper;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.LIQUID_PIPE_ENTITY;

public class BasicLiquidPipeEntity extends FluidPipeEntity{

    public BasicLiquidPipeEntity() {
        super(LIQUID_PIPE_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, 8000, 420000000, false);
    }

    //@Override
    //public void tick() {
    //    super.tick();
    //}
}

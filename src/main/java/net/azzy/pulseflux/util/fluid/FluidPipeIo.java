package net.azzy.pulseflux.util.fluid;

import dev.technici4n.fasttransferlib.api.fluid.FluidIo;

public interface FluidPipeIo extends FluidIo {
    @Override
    default boolean supportsFluidInsertion() {
        return true;
    }

    @Override
    default boolean supportsFluidExtraction() {
        return true;
    }
}

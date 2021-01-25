package net.azzy.pulseflux.registry

import dev.technici4n.fasttransferlib.api.fluid.FluidApi
import net.azzy.pulseflux.blockentity.logistic.transport.EverfullUrnEntity
import net.azzy.pulseflux.blockentity.logistic.transport.FluidPipeEntity

object FTLRegistry {

    @JvmStatic
    fun init() {
        FluidApi.SIDED.registerForBlockEntities({ blockEntity, _ -> if(blockEntity is FluidPipeEntity) blockEntity else null}, BlockEntityRegistry.LIQUID_PIPE_ENTITY)
        FluidApi.SIDED.registerForBlockEntities({ blockEntity, _ -> if(blockEntity is EverfullUrnEntity) blockEntity else null }, BlockEntityRegistry.EVERFULL_URN_ENTITY)
    }
}
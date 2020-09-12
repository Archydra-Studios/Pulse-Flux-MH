package net.azzy.pulseflux.registry

import net.azzy.pulseflux.PulseFlux
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.particle.DefaultParticleType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ParticleRegistry {
    @JvmStatic
    @Environment(EnvType.CLIENT)
    fun initClient() {
    }

    fun register(name: String?, alwaysShow: Boolean): DefaultParticleType {
        return Registry.register(Registry.PARTICLE_TYPE, Identifier(PulseFlux.MOD_ID, name), FabricParticleTypes.simple(alwaysShow))
    }
}
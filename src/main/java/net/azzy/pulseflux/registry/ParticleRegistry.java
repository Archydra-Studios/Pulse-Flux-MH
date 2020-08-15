package net.azzy.pulseflux.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class ParticleRegistry {


    @Environment(EnvType.CLIENT)
    public static void initClient() {
    }

    public static DefaultParticleType register(String name, boolean alwaysShow) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(MOD_ID, name), FabricParticleTypes.simple(alwaysShow));
    }
}

package azzy.fabric.pulseflux.util;

import azzy.fabric.pulseflux.PulseFluxCommon;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.registry.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class PulseFluxRegistries {

    public static void init() {}

    public static final RegistryKey<Registry<Material>> MATERIAL_REGISTRY_KEY = RegistryKey.ofRegistry(PulseFluxCommon.id("material"));
    public static final Registry<Material> MATERIAL = (Registry<Material>) ((MutableRegistry) Registry.REGISTRIES).add(MATERIAL_REGISTRY_KEY, new SimpleRegistry<>(MATERIAL_REGISTRY_KEY, Lifecycle.experimental()), Lifecycle.experimental());
}

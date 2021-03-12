package azzy.fabric.pulseflux.util;

import azzy.fabric.pulseflux.PulseFluxCommon;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class PulseFluxMaterials {

    public static void init() {}

    public static final Material HSLA_STEEL = register("hsla_steel", new Material(1000, 0.25, 1024, 2048));

    public static Material register(String id, Material material) {
        return Registry.register(PulseFluxRegistries.MATERIAL, PulseFluxCommon.id(id), material);
    }
}

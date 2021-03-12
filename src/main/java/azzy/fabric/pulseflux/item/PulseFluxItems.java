package azzy.fabric.pulseflux.item;

import azzy.fabric.incubus_core.datagen.ModelJsonGen;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.pulseflux.PulseFluxCommon.*;

@SuppressWarnings("unused")
public class PulseFluxItems {

    public static void init() {}

    private static Item registerGeneratedItem(String name, Item item, boolean genModel) {
        Identifier id = new Identifier(MODID, name);
        if (DEV_ENV && REGEN_ITEMS && genModel)
            ModelJsonGen.genItemJson(METADATA, id);
        return Registry.register(Registry.ITEM, id, item);
    }
}

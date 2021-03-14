package azzy.fabric.pulseflux.item;

import azzy.fabric.incubus_core.datagen.ModelJsonGen;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static azzy.fabric.pulseflux.PulseFluxCommon.*;

@SuppressWarnings("unused")
public class PulseFluxItems {

    public static FabricItemSettings genericSettings(ItemGroup group, Rarity rarity) {
        return new FabricItemSettings().group(group).rarity(rarity);
    }

    //Tools
    public static final Item SCREWDRIVER = registerItem("screwdriver", new ScrewdriverItem(genericSettings(TOOLS, Rarity.COMMON)), true);
    public static final Item POLAR_PROBE = registerItem("polar_probe", new PolarProbeItem(genericSettings(TOOLS, Rarity.COMMON)), true);

    //Materials
    public static final Item HSLA_STEEL_INGOT = registerItem("hsla_steel_ingot", new Item(genericSettings(MACHINE_MATERIALS, Rarity.COMMON)), true);
    public static final Item HSLA_STEEL_NUGGET = registerItem("hsla_steel_nugget", new Item(genericSettings(MACHINE_MATERIALS, Rarity.COMMON)), true);

    public static void init() {}

    private static Item registerItem(String name, Item item, boolean genModel) {
        Identifier id = new Identifier(MODID, name);
        if (DEV_ENV && REGEN_ITEMS && genModel)
            ModelJsonGen.genItemJson(METADATA, id);
        return Registry.register(Registry.ITEM, id, item);
    }
}

package net.azzy.pulseflux.registry;

import net.azzy.pulseflux.PulseFlux;
import net.azzy.pulseflux.item.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.azzy.pulseflux.PulseFlux.*;

public class ItemRegistry extends Item {

    private ItemRegistry(Settings settings) {
        super(settings);
    }
    private static final Settings MATERIAL = new Settings().group(MACHINE_MATERIALS);
    private static final Settings TOOL = new Settings().group(PulseFlux.TOOLS).maxCount(1);

    //Crafting
    public final static Item STEEL_INGOT = register("hsla_steel_ingot", new Item(MATERIAL));
    public final static Item TITANIUM_INGOT = register("titanium_ingot", new Item(MATERIAL));

    //Tools
    public final static Item THERMOMETER = register("thermometer", new ThermometerItem(TOOL));
    public static final Item MANOMETER = register("manometer", new ManometerItem(TOOL));
    public final static Item SENSOR = register("sensor", new SensorItem(TOOL));
    public final static Item PROBE = register("probe", new ProbeItem(TOOL));
    public static final Item OSCILLATOR = register("oscillator", new OscillatorItem(TOOL));
    public static final Item SCREWDRIVER = register("screwdriver", new ScrewdriverItem(TOOL));
    public static final Item WRENCH = register("hammer", new WrenchItem(TOOL));

    public static void init(){}

    private static Item register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static Item registerBucket(String name, FlowableFluid item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BucketItem(item, new Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(MACHINE_MATERIALS)));
    }
}



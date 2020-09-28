package net.azzy.pulseflux.registry

import net.azzy.pulseflux.PulseFlux
import net.azzy.pulseflux.PulseFlux.PFLog
import net.azzy.pulseflux.item.*
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.mixin.resource.loader.ResourcePackManagerAccessor
import net.minecraft.client.MinecraftClient
import net.minecraft.client.resource.language.I18n
import net.minecraft.fluid.FlowableFluid
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
object ItemRegistry {

    private val MATERIAL = Item.Settings().group(PulseFlux.MACHINE_MATERIALS)
    private val TOOL = Item.Settings().group(PulseFlux.TOOLS).maxCount(1)

    //Crafting
    @JvmField
    val STEEL_INGOT = register("hsla_steel_ingot", Item(MATERIAL))
    @JvmField
    val TITANIUM_INGOT = register("titanium_ingot", Item(MATERIAL))
    val TITANIUM_HEWN = register("hewn_titanium_ore", Item(MATERIAL.fireproof()))
    val TUNGSTEN_HEWN = register("hewn_tungsten_ore", Item(MATERIAL.fireproof()))
    val COPPER_INGOT = register("copper_ingot", Item(MATERIAL))
    val COPPER_HEWN = register("hewn_copper_ore", Item(MATERIAL))
    val ALUMINIUM_INGOT = register("aluminum_ingot", Item(MATERIAL))
    val ALUMINIUM_HEWN = register("hewn_aluminum_ore", Item(MATERIAL))

    //Ore Processing
    val HEWN_IRON = register("hewn_iron_ore", Item(MATERIAL))
    val HEWN_GOLD = register("hewn_gold_ore", Item(MATERIAL))

    //Tools
    val THERMOMETER = register("thermometer", ThermometerItem(TOOL))
    val MANOMETER = register("manometer", ManometerItem(TOOL))
    val SENSOR = register("sensor", SensorItem(TOOL))
    val PROBE = register("probe", ProbeItem(TOOL))
    val OSCILLATOR = register("oscillator", OscillatorItem(TOOL))

    @JvmField
    val SCREWDRIVER = register("screwdriver", ScrewdriverItem(TOOL))
    val WRENCH = register("hammer", WrenchItem(TOOL))

    //Compat


    @JvmStatic
    fun init() {}

    fun register(name: String, item: Item): Item {
        Registry.register(Registry.ITEM, Identifier(PulseFlux.MOD_ID, name), item)
        return item
    }

    fun registerBucket(name: String?, item: FlowableFluid?): Item {
        return Registry.register(Registry.ITEM, Identifier(PulseFlux.MOD_ID, name), BucketItem(item, Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(PulseFlux.MACHINE_MATERIALS)))
    }

}
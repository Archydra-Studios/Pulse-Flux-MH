package net.azzy.pulseflux.registry

import net.azzy.pulseflux.PulseFlux
import net.azzy.pulseflux.item.*
import net.minecraft.fluid.FlowableFluid
import net.minecraft.item.BucketItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class ItemRegistry private constructor(settings: Settings) : Item(settings) {

    companion object {
        private val MATERIAL = Settings().group(PulseFlux.MACHINE_MATERIALS)
        private val TOOL = Settings().group(PulseFlux.TOOLS).maxCount(1)

        //Crafting
        @JvmField
        val STEEL_INGOT = register("hsla_steel_ingot", Item(MATERIAL))
        @JvmField
        val TITANIUM_INGOT = register("titanium_ingot", Item(MATERIAL))

        //Tools
        val THERMOMETER = register("thermometer", ThermometerItem(TOOL))
        val MANOMETER = register("manometer", ManometerItem(TOOL))
        val SENSOR = register("sensor", SensorItem(TOOL))
        val PROBE = register("probe", ProbeItem(TOOL))
        val OSCILLATOR = register("oscillator", OscillatorItem(TOOL))

        @JvmField
        val SCREWDRIVER = register("screwdriver", ScrewdriverItem(TOOL))
        val WRENCH = register("hammer", WrenchItem(TOOL))

        @JvmStatic
        fun init() {}


        private fun register(name: String, item: Item): Item {
            Registry.register(Registry.ITEM, Identifier(PulseFlux.MOD_ID, name), item)
            return item
        }

        fun registerBucket(name: String?, item: FlowableFluid?): Item {
            return Registry.register(Registry.ITEM, Identifier(PulseFlux.MOD_ID, name), BucketItem(item, Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(PulseFlux.MACHINE_MATERIALS)))
        }
    }
}
package net.azzy.pulseflux.registry

import net.azzy.pulseflux.PulseFlux.MOD_ID
import net.azzy.pulseflux.registry.BlockRegistry.OBSIDIAN_PANEL
import net.azzy.pulseflux.registry.ItemRegistry.COPPER_HEWN
import net.azzy.pulseflux.registry.ItemRegistry.HEWN_GOLD
import net.azzy.pulseflux.registry.ItemRegistry.HEWN_IRON
import net.azzy.pulseflux.registry.ItemRegistry.SCREWDRIVER
import net.azzy.pulseflux.registry.ItemRegistry.STEEL_INGOT
import net.azzy.pulseflux.registry.recipe.GrinderRecipe
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener
import net.fabricmc.fabric.api.tag.TagRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items.*
import net.minecraft.recipe.*
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceReloadListener
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.profiler.Profiler
import net.minecraft.util.registry.Registry
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor


object RecipeRegistry {

    val GRINDING = register<GrinderRecipe>("grinding")
    val GRINDING_SERIALIZER = registerSerializer("grinding", GrinderRecipe.GrinderSerializer)

    fun awaken() {}

   //init {
   //    recipeMap[RecipeType.CRAFTING] = mutableMapOf()
   //    recipeMap[RecipeType.BLASTING] = mutableMapOf()
   //    recipeMap[GRINDING] = mutableMapOf()

   //    //Crafting

   //    //Cum Blast
   //    with(recipeMap[RecipeType.BLASTING]){
   //        this?.set(Identifier(MOD_ID, "hewn_iron"), blastingRecipe(CraftGroup.MATERIAL, "hewn_iron", of(HEWN_IRON), ItemStack(IRON_INGOT, 2)))
   //        this?.set(Identifier(MOD_ID, "hewn_gold"), blastingRecipe(CraftGroup.MATERIAL, "hewn_gold", of(HEWN_GOLD), ItemStack(GOLD_INGOT, 2)))
   //        this?.set(Identifier(MOD_ID, "hewn_copper"), blastingRecipe(CraftGroup.MATERIAL, "hewn_copper", of(ItemRegistry.COPPER_HEWN), ItemStack(ItemRegistry.COPPER_INGOT, 2)))
   //        this?.set(Identifier(MOD_ID, "hewn_aluminum"), blastingRecipe(CraftGroup.MATERIAL, "hewn_aluminum", of(ItemRegistry.ALUMINIUM_HEWN), ItemStack(ItemRegistry.ALUMINIUM_INGOT, 2)))
   //        this?.set(Identifier(MOD_ID, "hewn_silver"), blastingRecipe(CraftGroup.MATERIAL, "hewn_silver", of(ItemRegistry.SILVER_HEWN), ItemStack(ItemRegistry.SILVER_INGOT, 2)))
   //        this?.set(Identifier(MOD_ID, "hewn_lead"), blastingRecipe(CraftGroup.MATERIAL, "hewn_lead", of(ItemRegistry.LEAD_HEWN), ItemStack(ItemRegistry.LEAD_INGOT, 2)))
   //        this?.set(Identifier(MOD_ID, "hewn_tin"), blastingRecipe(CraftGroup.MATERIAL, "hewn_tin", of(ItemRegistry.TIN_HEWN), ItemStack(ItemRegistry.TIN_INGOT, 2)))
   //        this?.set(Identifier(MOD_ID, "hewn_zinc"), blastingRecipe(CraftGroup.MATERIAL, "hewn_zinc", of(ItemRegistry.ZINC_HEWN), ItemStack(ItemRegistry.ZINC_INGOT, 2)))
   //    }
   //}

    private fun <T : Recipe<*>> register(name: String): RecipeType<T> {
        return Registry.register(Registry.RECIPE_TYPE, Identifier(MOD_ID, name), object : RecipeType<T> {
            override fun toString(): String {
                return name
            }
        }) as RecipeType<T>
    }

    fun <S : RecipeSerializer<T>?, T : Recipe<*>?> registerSerializer(name: String, serializer: S): S {
        return Registry.register(Registry.RECIPE_SERIALIZER, Identifier(MOD_ID, name), serializer)
    }

    enum class CraftGroup(val group: String){
        MACHINE("pulseflux_machines"),
        LOGISTIC("pulseflux_logistics"),
        TOOL("pulseflux_tools"),
        MATERIAL("pulseflux_materials"),
        MISC("pulseflux_misc")
    }
}
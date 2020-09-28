package net.azzy.pulseflux.registry

import me.orangemonkey68.injectablerecipes.InjectableRecipes
import me.orangemonkey68.injectablerecipes.RecipeHolder
import net.azzy.pulseflux.PulseFlux
import net.azzy.pulseflux.PulseFlux.*
import net.azzy.pulseflux.registry.BlockRegistry.OBSIDIAN_PANEL
import net.azzy.pulseflux.registry.ItemRegistry.HEWN_GOLD
import net.azzy.pulseflux.registry.ItemRegistry.HEWN_IRON
import net.azzy.pulseflux.registry.ItemRegistry.SCREWDRIVER
import net.azzy.pulseflux.registry.ItemRegistry.STEEL_INGOT
import net.azzy.pulseflux.registry.recipe.GrinderRecipe
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items.*
import net.minecraft.recipe.*
import net.minecraft.tag.ItemTags
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.registry.Registry


object RecipeRegistry : RecipeHolder {

    val recipeMap = mutableMapOf<RecipeType<*>, MutableMap<Identifier, Recipe<*>>>()
    val GRINDING = register<GrinderRecipe>("grinding")
    val oreComp = listOf("silver", "lead", "tin", "zinc")

    fun awaken() {}

    init {
        recipeMap[RecipeType.CRAFTING] = mutableMapOf()
        recipeMap[RecipeType.BLASTING] = mutableMapOf()
        recipeMap[GRINDING] = mutableMapOf()

        //Crafting
        with(recipeMap[RecipeType.CRAFTING]){
            this?.set(Identifier(MOD_ID, "screwdriver"), shapedRecipe(CraftGroup.TOOL, "screwdriver",
                    DefaultedList.copyOf(Ingredient.EMPTY,
                            Ingredient.EMPTY, Ingredient.EMPTY, of(STEEL_INGOT),
                            Ingredient.EMPTY, of(STEEL_INGOT), Ingredient.EMPTY,
                            of(OBSIDIAN_PANEL.asItem()), Ingredient.EMPTY, Ingredient.EMPTY
                    ),
                    ItemStack(SCREWDRIVER)
            ))
            InjectableRecipes.register(this@RecipeRegistry)
        }

        //Cum Blast
        with(recipeMap[RecipeType.BLASTING]){
            this?.set(Identifier(MOD_ID, "hewn_iron"), blastingRecipe(CraftGroup.MATERIAL, "hewn_iron", of(HEWN_IRON), ItemStack(IRON_INGOT, 2)))
            this?.set(Identifier(MOD_ID, "hewn_gold"), blastingRecipe(CraftGroup.MATERIAL, "hewn_gold", of(HEWN_GOLD), ItemStack(GOLD_INGOT, 2)))
        }

        //Grinding
        recipeMap[GRINDING]?.set(Identifier(MOD_ID, "iron_ore"), GrinderRecipe("iron_ore", listOf(IRON_ORE),
                listOf(Pair(ItemStack(GRAVEL), 0.1), Pair(ItemStack(HEWN_IRON), 0.8), Pair(ItemStack(FLINT), 1.0))
        ))
        recipeMap[GRINDING]?.set(Identifier(MOD_ID, "gold_ore"), GrinderRecipe("gold_ore", listOf(GOLD_ORE),
                listOf(Pair(ItemStack(GRAVEL), 0.1), Pair(ItemStack(HEWN_GOLD), 0.8), Pair(ItemStack(FLINT), 1.0))
        ))
        recipeMap[GRINDING]?.set(Identifier(MOD_ID, "nether_gold"), GrinderRecipe("nether_gold", listOf(NETHER_GOLD_ORE),
                listOf(Pair(ItemStack(GRAVEL), 0.15), Pair(ItemStack(GOLD_NUGGET, 20), 0.05), Pair(ItemStack(GOLD_NUGGET, 12), 0.5), Pair(ItemStack(GOLD_NUGGET, 4), 0.25), Pair(ItemStack(NETHERRACK), 1.0))
        ))

        //Compat
        for(material in oreComp)
            genOreRecipes(material)
    }

    override fun getRecipes(): MutableMap<RecipeType<*>, MutableMap<Identifier, Recipe<*>>> {
        return recipeMap
    }

    private fun <T : Recipe<*>> register(name: String): RecipeType<T> {
        return Registry.register(Registry.RECIPE_TYPE, Identifier(MOD_ID, name), object : RecipeType<T> {
            override fun toString(): String {
                return name
            }
        }) as RecipeType<T>
    }

    private fun shapedRecipe(group: CraftGroup, name: String, input: DefaultedList<Ingredient>, output: ItemStack): ShapedRecipe{
        return ShapedRecipe(Identifier(MOD_ID, name + "_recipe"), group.group, 3, 3, input, output)
    }

    private fun blastingRecipe(group: CraftGroup, name: String, input: Ingredient, output: ItemStack): BlastingRecipe{
        return BlastingRecipe(Identifier(MOD_ID, name), group.group, input, output, 10F, 200)
    }

    private fun genOreRecipes(material: String){
        val ore = ItemTags.getTagGroup().getTagOrEmpty(Identifier("c", material + "_ores")).values()
        val ingot = ItemRegistry.register(material + "_ingot", Item(Item.Settings().group(MACHINE_MATERIALS)))
        val hewn = ItemRegistry.register("hewn_" + material + "_ore", Item(Item.Settings().group(MACHINE_MATERIALS)))
        recipeMap[GRINDING]?.set(Identifier(MOD_ID + "_compat", material), GrinderRecipe(material, ore,
                listOf(Pair(ItemStack(GRAVEL), 0.075), Pair(ItemStack(hewn, 2), 0.05), Pair(ItemStack(hewn), 0.85), Pair(ItemStack(FLINT), 1.0))
        ))
        recipeMap[RecipeType.BLASTING]?.set(Identifier(MOD_ID + "_compat", material), blastingRecipe(CraftGroup.MATERIAL, material, of(hewn), ItemStack(ingot, 2)))

        PFLog.info("Successfully loaded ore processing compat for $material")
    }

    private infix fun of(item: Item): Ingredient {
        return Ingredient.ofItems(item)
    }

    enum class CraftGroup(val group: String){
        MACHINE("pulseflux_machines"),
        LOGISTIC("pulseflux_logistics"),
        TOOL("pulseflux_tools"),
        MATERIAL("pulseflux_materials"),
        MISC("pulseflux_misc")
    }
}
package net.azzy.pulseflux.registry

import me.orangemonkey68.injectablerecipes.InjectableRecipes
import me.orangemonkey68.injectablerecipes.RecipeHolder
import net.azzy.pulseflux.PulseFlux.MOD_ID
import net.azzy.pulseflux.registry.BlockRegistry.OBSIDIAN_PANEL
import net.azzy.pulseflux.registry.ItemRegistry.Companion.SCREWDRIVER
import net.azzy.pulseflux.registry.ItemRegistry.Companion.STEEL_INGOT
import net.devtech.arrp.impl.RuntimeResourcePackImpl
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList


class RecipeRegistry : RecipeHolder {

    init {
        recipeMap[RecipeType.CRAFTING] = mutableMapOf()

        //Crafting
        recipeMap[RecipeType.CRAFTING]?.set(Identifier(MOD_ID, "screwdriver"), shapedRecipe(CraftGroup.TOOL, "screwdriver",
                DefaultedList.copyOf(Ingredient.EMPTY,
                        Ingredient.EMPTY, Ingredient.EMPTY, of(STEEL_INGOT),
                        Ingredient.EMPTY, of(STEEL_INGOT), Ingredient.EMPTY,
                        of(OBSIDIAN_PANEL.asItem()), Ingredient.EMPTY, Ingredient.EMPTY
                ),
                ItemStack(SCREWDRIVER)
        ))
        InjectableRecipes.register(this)
    }

    override fun getRecipes(): MutableMap<RecipeType<*>, MutableMap<Identifier, Recipe<*>>> {
        return recipeMap
    }

    companion object{
        val recipeMap = mutableMapOf<RecipeType<*>, MutableMap<Identifier, Recipe<*>>>()
    }

    private fun shapedRecipe(group: CraftGroup, name: String, input: DefaultedList<Ingredient>, output: ItemStack): ShapedRecipe{
        return ShapedRecipe(Identifier(MOD_ID, name + "_recipe"), group.group, 3, 3, input, output)
    }

    private fun of(item: Item): Ingredient {
        return Ingredient.ofItems(item)
    }

    enum class CraftGroup(val group: String){
        MACHINE("pulseflux_machines"),
        LOGISTIC("pulseflux_logistics"),
        TOOL("pulseflux_tools"),
        MISC("pulseflux_misc")
    }
}
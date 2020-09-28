package net.azzy.pulseflux.registry.recipe

import net.azzy.pulseflux.PulseFlux.MOD_ID
import net.azzy.pulseflux.PulseFlux.PFRandom
import net.azzy.pulseflux.blockentity.production.GrinderBlockEntity
import net.azzy.pulseflux.registry.RecipeRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items.DIAMOND
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.tag.ItemTags
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.world.World

data class GrinderRecipe(private val recipeID: String, val input: List<Item>, private val outputs: List<Pair<ItemStack, Double>>) : Recipe<GrinderBlockEntity> {

    constructor(recipeID: String, input: Tag<Item>, outputs: List<Pair<ItemStack, Double>>) : this(recipeID, input.values(), outputs)

    override fun matches(inv: GrinderBlockEntity, world: World?): Boolean {
        return  input.contains(inv.inventory[0].item)
    }

    override fun craft(inv: GrinderBlockEntity): ItemStack {
        inv.inventory[1] = rollOutputs()
        inv.inventory[0].decrement(1)
        return ItemStack.EMPTY
    }

    private fun rollOutputs(): ItemStack {
        for(roll in outputs){
            if(roll.second == 1.0|| PFRandom.nextDouble() <= roll.second)
                return roll.first.copy()
        }
        return ItemStack.EMPTY
    }

    override fun fits(width: Int, height: Int): Boolean {
        return false
    }

    override fun getOutput(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun getId(): Identifier {
        return Identifier(MOD_ID, recipeID)
    }

    override fun getSerializer(): RecipeSerializer<*>? {
        return null
    }

    override fun getType(): RecipeType<*> {
        return RecipeRegistry.GRINDING
    }
}
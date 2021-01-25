package net.azzy.pulseflux.registry.recipe

import com.google.gson.JsonObject
import net.azzy.pulseflux.PulseFlux.*
import net.azzy.pulseflux.blockentity.production.GrinderBlockEntity
import net.azzy.pulseflux.registry.RecipeRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.tag.ItemTags
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import kotlin.system.exitProcess

data class GrinderRecipe(private val recipeID: String, val input: Ingredient, private val outputs: List<Pair<ItemStack, Double>>) : Recipe<GrinderBlockEntity> {

    override fun matches(inv: GrinderBlockEntity, world: World?): Boolean {
        return input.test(inv.inventory[0])
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
        return RecipeRegistry.GRINDING_SERIALIZER
    }

    override fun getType(): RecipeType<*> {
        return RecipeRegistry.GRINDING
    }

    object GrinderSerializer : RecipeSerializer<GrinderRecipe>{

        override fun read(id: Identifier, json: JsonObject): GrinderRecipe {
            val input = Ingredient.fromJson(json["input"])
            val rawOut = json["outputs"].asJsonArray
            val outputs = mutableListOf<Pair<ItemStack, Double>>()
            for(output in rawOut){
                val target = output.asJsonObject
                val iden = Identifier.tryParse(target["item"].asString)
                outputs.add(Pair(
                        ItemStack(Registry.ITEM[iden], target["quantity"].asInt),
                        target["chance"].asDouble
                ))
            }
            return GrinderRecipe(id.path, input, outputs)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): GrinderRecipe {
            val recipeId = buf.readString()
            val input = Ingredient.fromPacket(buf)
            val outputs = mutableListOf<Pair<ItemStack, Double>>()
            var counter = buf.readInt()
            while(counter > 0){
                counter--
                outputs.add(Pair(buf.readItemStack(), buf.readDouble()))
            }
            return GrinderRecipe(recipeId, input, outputs)
        }

        override fun write(buf: PacketByteBuf, recipe: GrinderRecipe) {
            buf.writeString(recipe.recipeID)
            recipe.input.write(buf)
            buf.writeInt(recipe.outputs.size)
            for(output in recipe.outputs.toList()){
                buf.writeItemStack(output.first)
                buf.writeDouble(output.second)
            }
        }

    }
}
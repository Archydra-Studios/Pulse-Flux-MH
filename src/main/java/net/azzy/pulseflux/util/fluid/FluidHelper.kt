package net.azzy.pulseflux.util.fluid

import net.minecraft.fluid.Fluid
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object FluidHelper {

    fun fluidToTag(fluid: Fluid, amount: Long): CompoundTag {
        val tag = CompoundTag()
        tag.putString("fluid", Registry.FLUID.getId(fluid).toString())
        tag.putLong("amount", amount)
        return tag
    }

    fun tankToTag(compoundTag: CompoundTag, fluid: Fluid, amount: Long) {
        compoundTag.put("tank", fluidToTag(fluid, amount))
    }

    fun fluidFromTag(tag: CompoundTag): Pair<Fluid, Long> {
        return Pair(
                Registry.FLUID[Identifier.tryParse(tag.getString("fluid"))],
                tag.getLong("amount")
        )
    }

    fun tankFromTag(tag: CompoundTag): Pair<Fluid, Long> {
        return fluidFromTag(tag)
    }
}
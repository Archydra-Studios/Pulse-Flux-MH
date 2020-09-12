package net.azzy.pulseflux.registry.damagetypes

import net.minecraft.entity.damage.DamageSource

object DamageIncandescence : DamageSource {
    val INCANDESCENCE = DamageIncandescence().setBypassesArmor().setExplosive() as DamageIncandescence
}
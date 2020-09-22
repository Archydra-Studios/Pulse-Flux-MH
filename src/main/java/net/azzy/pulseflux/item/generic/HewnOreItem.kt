package net.azzy.pulseflux.item.generic

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Rarity

class HewnOreItem(rarity: Rarity) : Item(FabricItemSettings().fireproof().rarity(rarity)) {
}
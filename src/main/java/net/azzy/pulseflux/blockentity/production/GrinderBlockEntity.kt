package net.azzy.pulseflux.blockentity.production

import net.azzy.pulseflux.PulseFlux.PFLog
import net.azzy.pulseflux.block.entity.production.GrinderBlock
import net.azzy.pulseflux.blockentity.PulseDrainingEntity
import net.azzy.pulseflux.registry.BlockEntityRegistry
import net.azzy.pulseflux.util.interaction.HeatTransferHelper
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.particle.BlockStateParticleEffect
import net.minecraft.particle.ItemStackParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.screen.PropertyDelegate
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import java.util.function.Supplier

class GrinderBlockEntity : PulseDrainingEntity(BlockEntityRegistry.GRINDER_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, 9.toShort(), Supplier { DefaultedList.ofSize(2, ItemStack.EMPTY) }) {

    override fun tick() {
        super.tick()
        if(!(inductance >= 50 && frequency >= 10) && cachedState.get(GrinderBlock.ACTIVE))
            world?.setBlockState(pos, cachedState.with(GrinderBlock.ACTIVE, false))
        else if((inductance >= 50 && frequency >= 10)) {
            if(!cachedState.get(GrinderBlock.ACTIVE))
               world?.setBlockState(pos, cachedState.with(GrinderBlock.ACTIVE, true))
            fetchDroppedEntities()
            if(!world?.isClient()!! && !inventory[0].isEmpty){
                (world as ServerWorld).spawnParticles(ItemStackParticleEffect(ParticleTypes.ITEM, inventory[0]), pos.x + 0.5, pos.y + 13/16.0, pos.z + 0.5, 2, 0.165, 0.1, 0.165, 0.1)
                (world as ServerWorld).spawnParticles(ItemStackParticleEffect(ParticleTypes.ITEM, inventory[0]), pos.x + 0.5, pos.y + 4/16.0, pos.z + 0.5, 1, 0.22, 0.0, 0.22, 0.0)
                tryGrind()
            }
        }
    }

    private fun fetchDroppedEntities() {
        val entities: List<Entity> = world?.getOtherEntities(null, Box(pos.x.toDouble(), pos.y + 12 / 16.0, pos.z.toDouble(), pos.x + 1.0, pos.y + 1.0, pos.z + 1.0)) as List<Entity>
        for(entity in entities) {
            if(entity is LivingEntity){
                entity.damage(DamageSource.MAGIC, 0.666F)
            }
            else if(entity is ItemEntity){
                val inv = inventory[0]
                val stack = entity.stack
                if(inv.isEmpty && !stack.isEmpty){
                    inventory[0] = ItemStack(stack.item)
                    stack.decrement(1)
                }
                else if(inv.item == stack.item && inv.count < inv.maxCount){
                    inv.increment(1)
                    stack.decrement(1)
                }
                if(stack.isEmpty)
                    entity.kill()
            }
        }
    }

    private fun tryGrind() {
        progress++
        if(progress >= 200){
            progress = 0
            val item = ItemEntity(world, pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, ItemStack(Items.DIAMOND))
            item.setVelocity(0.0, 0.0, 0.0)
            world?.spawnEntity(item)
            inventory[0].decrement(1)
        }
    }

    override fun playTransferSound() {
        if (world!!.time % 40 == 0L) {
            world!!.playSound(null, pos, SoundEvents.ENTITY_BEE_LOOP_AGGRESSIVE, SoundCategory.BLOCKS, 0.35f, 0.1f)
        }
        if (!inventory[0].isEmpty && world!!.time % 2 == 0L) {
            world!!.playSound(null, pos, SoundEvents.BLOCK_ANCIENT_DEBRIS_BREAK, SoundCategory.BLOCKS, 1.75f, 0.5f)
        }
    }

    override fun getPropertyDelegate(): PropertyDelegate? {
        return null
    }

    override fun getArea(): Double {
        return 1 / 3.0
    }

    override fun getPixelOffset(): Int {
        return -1
    }

    override fun getAvailableSlots(side: Direction): IntArray {
        return IntArray(0)
    }

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean {
        return false
    }

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean {
        return false
    }
}
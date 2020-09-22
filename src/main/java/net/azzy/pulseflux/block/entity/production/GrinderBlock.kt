package net.azzy.pulseflux.block.entity.production

import net.azzy.pulseflux.block.entity.PulseCarryingDirectionalBlock
import net.azzy.pulseflux.blockentity.PulseGeneratingEntity
import net.azzy.pulseflux.blockentity.PulseRenderingEntityImpl
import net.azzy.pulseflux.blockentity.production.GrinderBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World


class GrinderBlock(settings: Settings) : PulseCarryingDirectionalBlock<GrinderBlockEntity>(settings, ::GrinderBlockEntity, true) {

    init {
        defaultState = defaultState.with(ACTIVE, false)
    }

    companion object{
        val ACTIVE = BooleanProperty.of("active")
    }

    //Kotlin literally fucking dies without this
    override fun onPlaced(world: World?, pos: BlockPos?, state: BlockState?, placer: LivingEntity?, itemStack: ItemStack?) {
        super.onPlaced(world, pos, state, placer, itemStack)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(ACTIVE)
        super.appendProperties(builder)
    }

    override fun getOutlineShape(state: BlockState?, world: BlockView?, pos: BlockPos?, context: ShapeContext?): VoxelShape {
        return Block.createCuboidShape(0.0, 4.0, 0.0, 16.0, 12.0, 16.0)
    }
}
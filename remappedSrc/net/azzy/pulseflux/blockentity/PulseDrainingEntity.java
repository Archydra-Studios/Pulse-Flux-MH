package net.azzy.pulseflux.blockentity;

import net.azzy.pulseflux.block.MultiFacingBlock;
import net.azzy.pulseflux.block.entity.PulseCarryingDirectionalBlock;
import net.azzy.pulseflux.blockentity.logistic.FailingPulseCarryingEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public abstract class PulseDrainingEntity extends FailingPulseCarryingEntity {

    public PulseDrainingEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, short range, Supplier<DefaultedList<ItemStack>> invSupplier) {
        super(type, material, range, invSupplier, -1, -1, true);
    }

    @Override
    public void recalcIO(boolean straight, Direction direction, BlockState state) {
        direction = direction.getOpposite();
        super.recalcIO(straight, direction, state);
    }

    @Override
    public void recalcIO(Direction direction, BlockState state, boolean io) {
        direction = direction.getOpposite();
        super.recalcIO(direction, state, io);
    }

    @Override
    public Collection<Direction> getOutputs() {
        return Collections.EMPTY_SET;
    }
}

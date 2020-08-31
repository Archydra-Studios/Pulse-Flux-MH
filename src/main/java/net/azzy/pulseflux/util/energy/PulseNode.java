package net.azzy.pulseflux.util.energy;

import net.azzy.pulseflux.blockentity.logistic.FailingPulseCarryingEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public interface PulseNode {

    void accept(Direction direction, BlockPos pos);

    default boolean simulate(World world, BlockEntity sender, boolean noFailure, PulseNode receiver, double max){
        if(noFailure) {
            if(sender instanceof PulseNode) {
                PulseNode node = (PulseNode) sender;
                return (((node.getMaxInductance() >= node.getInductance() && node.getMaxFrequency() >= node.getFrequency()) || !node.canFail()));
            }
        }
        if(sender instanceof FailingPulseCarryingEntity && receiver.canFail())
            return receiver.getMaxFrequency() >= ((FailingPulseCarryingEntity) sender).getFrequency();
        return (sender instanceof PulseNode);
    }

    long getInductance();

    long getMaxInductance();

    Polarity getPolarity();

    double getFrequency();

    double getMaxFrequency();

    default double getPulseMultiplier(){
        return 1;
    }

    boolean canFail();

    boolean isOverloaded();

    default Direction getInput(){
        return null;
    }

    default Direction getOutput(){
        return null;
    }

    enum Polarity{
        POSITIVE,
        NEGATIVE,
        NEUTRAL
    }
}

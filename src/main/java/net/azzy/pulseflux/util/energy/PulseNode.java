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
        if(sender instanceof FailingPulseCarryingEntity)
            return receiver.getMaxFrequency() >= ((FailingPulseCarryingEntity) sender).getFrequency();
        return (sender instanceof PulseNode);
    }

    default DirectionPos scanIOExclusive(World world, BlockPos pos, Direction[] exemptions, int max){
        List<Direction> exempt = Arrays.asList(exemptions);

        for(Direction direction : Direction.values()) {
            BlockPos scannedPos = pos;
            if (exempt.contains(direction))
                continue;
            for (int i = 0; i < max; i++) {
                scannedPos.offset(direction);
                BlockEntity entity = world.getBlockEntity(scannedPos);
                if(entity instanceof PulseNode)
                    return new DirectionPos(scannedPos, direction);
            }
        }
        return null;
    }

    default DirectionPos scanIOInclusive(World world, BlockPos pos, Direction[] directions, int max){
        for(Direction direction : directions) {
            BlockPos scannedPos = pos;
            for (int i = 0; i < max; i++) {
                scannedPos.offset(direction);
                BlockEntity entity = world.getBlockEntity(scannedPos);
                if(entity instanceof PulseNode)
                    return new DirectionPos(scannedPos, direction);
            }
        }
        return null;
    }

    long getInductance();

    long getMaxInductance();

    Polarity getPolarity();

    double getFrequency();

    double getMaxFrequency();

    boolean canFail();

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

    class DirectionPos{
        private final BlockPos pos;
        private final Direction direction;

        private DirectionPos(BlockPos pos, Direction direction){
            this.pos = pos;
            this.direction = direction;
        }

        public BlockPos getPos() {
            return pos;
        }

        public Direction getDirection() {
            return direction;
        }
    }
}

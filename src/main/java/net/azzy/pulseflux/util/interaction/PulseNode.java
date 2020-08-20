package net.azzy.pulseflux.util.interaction;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public interface PulseNode {

    void accept(long amplitude, double frequency, Polarity polarity, ItemStack medium, Direction direction, BlockPos sender);

    default boolean offer(World world, BlockEntity sender, long inductance, double frequency, Polarity polarity, ItemStack medium, Direction direction, BlockPos receiver, double max){
        BlockEntity entity = world.getBlockEntity(receiver);
        if( entity instanceof PulseNode && (receiver.isWithinDistance(sender.getPos(), max) || max < 0)){
            ((PulseNode) entity).accept(inductance, frequency, polarity, medium, direction, sender.getPos());
            return true;
        }
        return false;
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

    long getAmplitude();

    Polarity getPolarity();

    double getFrequency();

    Item getMedium();

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

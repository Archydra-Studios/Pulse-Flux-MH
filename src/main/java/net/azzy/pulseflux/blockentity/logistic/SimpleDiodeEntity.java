package net.azzy.pulseflux.blockentity.logistic;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashMap;

public class SimpleDiodeEntity extends DiodeEntity{

    private final HashMap<Direction, BlockPos> cachedNodes = new HashMap<>();
    private final double maxRange;

    public SimpleDiodeEntity(BlockEntityType<?> type, long maxAmplitude, double maxFrequency, double maxRange) {
        super(type, maxAmplitude, maxFrequency);
        this.maxRange = maxRange;
    }

    @Override
    public void tick() {
        super.tick();
        for(Direction direction : io){
        }
    }

    private void recalcOutput(){

    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        for(Direction direction : io){

        }
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        for(int i = 0; i < 2; i++){

        }
        super.fromTag(state, tag);
    }

    private void processOutput(Direction direction) {
        if(direction != lastInput && cachedNodes.containsKey(direction)){
            offer(world, this, amplitude, direction, cachedNodes.get(direction), polarity, maxRange);
        }
    }
}

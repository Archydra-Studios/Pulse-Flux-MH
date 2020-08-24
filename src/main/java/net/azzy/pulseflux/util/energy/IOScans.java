package net.azzy.pulseflux.util.energy;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class IOScans {

    public static BlockPos seekInputNode(BlockPos pos, Direction input, World world){
        BlockPos scanPos = pos;
        for(int i = 1; i <= 16; i++){
            scanPos = scanPos.offset(input);
            BlockEntity entity = world.getBlockEntity(scanPos);
            if(entity instanceof PulseNode){
                return scanPos;
            }
            if(world.getBlockState(scanPos).isOpaque())
                return null;
        }
        return null;
    }

    public static BlockPos seekInputNode(BlockPos pos, Direction output, World world, int max){
        BlockPos scanPos = pos;
        for(int i = 1; i <= max; i++){
            scanPos = scanPos.offset(output);
            BlockEntity entity = world.getBlockEntity(scanPos);
            if(entity instanceof PulseNode){
                return scanPos;
            }
            if(!world.getBlockState(pos).isTranslucent(world, scanPos))
                break;
        }
        return null;
    }

}

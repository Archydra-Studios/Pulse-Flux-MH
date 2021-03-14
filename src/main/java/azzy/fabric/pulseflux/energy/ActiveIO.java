package azzy.fabric.pulseflux.energy;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ActiveIO<T extends BlockEntity & PulseIo> {

    public final Direction io;
    public final T connection;
    public final BlockPos pos;
    public final int distance;

    public ActiveIO(Direction io, T connection, int distance, BlockPos pos) {
        this.io = io;
        this.connection = connection;
        this.distance = distance;
        this.pos = pos;
    }

    public boolean isInvalid(BlockPos parent) {
        return connection == null || connection.isRemoved() || !connection.getPos().equals(pos) || !parent.offset(io, distance).equals(pos);
    }

    public boolean revalidate(BlockPos parent, World world) {
        for (int i = 2; i < distance; i++) {
            BlockPos test = parent.offset(io, i);
            BlockState state = world.getBlockState(test);
            if(state.isSideSolidFullSquare(world, test, io) || state.isSideSolidFullSquare(world, test, io.getOpposite())) {
                return false;
            }
        }
        return true;
    }
}

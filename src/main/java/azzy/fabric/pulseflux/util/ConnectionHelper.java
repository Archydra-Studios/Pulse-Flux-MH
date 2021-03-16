package azzy.fabric.pulseflux.util;

import azzy.fabric.pulseflux.energy.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static azzy.fabric.pulseflux.block.logistics.DiodeBlock.INPUT;

public class ConnectionHelper {

    public static @NotNull Direction getOutputOrElse(BlockPos start, int range, World world, Stream<Direction> testDirs, Direction fallback) {
        return testDirs
                .filter(direction -> {
                    for (int i = 1; i <= range; i++) {
                        BlockPos test = start.offset(direction, i);
                        PulseIo ioProvider = PulseFluxEnergyAPIs.PULSE.find(world, test, direction.getOpposite());
                        if(ioProvider != null && i > 1) {
                            return ioProvider.getOutputs().contains(direction.getOpposite());
                        }
                        else if(checkPathInavlid(world.getBlockState(test), test, world, direction))
                            break;
                    }
                    return false;
                })
                .findFirst()
                .orElse(fallback);
    }

    public static <T extends BlockEntity & PulseIo> @Nullable ActiveIO<?> getActiveOutput(T seeker, BlockPos start, int range, Direction testDir) {
        World world = seeker.getWorld();
        if(world != null) {
            for (int i = 2; i <= range; i++) {
                BlockPos test = start.offset(testDir, i);
                BlockState state = world.getBlockState(test);
                PulseIo io = PulseFluxEnergyAPIs.PULSE.find(world, test, testDir);
                if(io != null && io.shouldConnect(seeker.getStoredPulse(), testDir.getOpposite(), MutationType.INSERTION)) {
                    return new ActiveIO(testDir, (BlockEntity) io, i, test);
                }
                else if(checkPathInavlid(state, test, world, testDir)) {
                    break;
                }
            }
        }
        return null;
    }

    public static boolean checkPathInavlid(BlockState state, BlockPos pos, World world, Direction axis) {
        return (state.isSideSolidFullSquare(world, pos, axis) || state.isSideSolidFullSquare(world, pos, axis.getOpposite())) && !state.isTranslucent(world, pos);
    }

    public static boolean standardConnectionCheck(@NotNull PulseIo receiver, @NotNull BlockPos receiverPos, @NotNull PulseCarrier pulse, @NotNull Direction queryDir, Direction input) {
        return (queryDir == input || input == null) &&
                !PulseCarrier.isEmpty(pulse) &&
                pulse.polarity.matches(receiver.getStoredPulse().polarity) &&
                !pulse.hasPassed(receiverPos);
    }
}

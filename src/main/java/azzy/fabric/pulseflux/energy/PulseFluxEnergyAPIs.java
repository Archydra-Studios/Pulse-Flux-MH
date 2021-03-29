package azzy.fabric.pulseflux.energy;

import azzy.fabric.pulseflux.PulseFluxCommon;
import dev.technici4n.fasttransferlib.api.fluid.FluidApi;
import dev.technici4n.fasttransferlib.api.fluid.FluidIo;
import jdk.nashorn.internal.objects.NativeWeakSet;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

public class PulseFluxEnergyAPIs {
    public static final BlockApiLookup<HeatIo, @NotNull Direction> HEAT =
            BlockApiLookup.get(PulseFluxCommon.id("heat_system"), HeatIo.class, Direction.class);

    public static final BlockApiLookup<MechanicalIo, @NotNull Direction> MECHANICAL =
            BlockApiLookup.get(PulseFluxCommon.id("mechanical_system"), MechanicalIo.class, Direction.class);

    public static final BlockApiLookup<KineticIO, @NotNull Direction> KINETIC =
            BlockApiLookup.get(PulseFluxCommon.id("velocity_system"), KineticIO.class, Direction.class);

    public static final BlockApiLookup<PressureIo, @NotNull Direction> PRESSURE =
            BlockApiLookup.get(PulseFluxCommon.id("pressure_system"), PressureIo.class, Direction.class);

    public static final BlockApiLookup<PulseIo, @NotNull Direction> PULSE =
            BlockApiLookup.get(PulseFluxCommon.id("pulse_system"), PulseIo.class, Direction.class);

    private static  final Set<BlockEntity> IoCache = new HashSet<>();

    public static void registerThermodynamicBE(BlockEntityType<?>... blockEntityTypes) {
        HEAT.registerSelf(blockEntityTypes);
        PRESSURE.registerSelf(blockEntityTypes);
        FluidApi.SIDED.registerSelf(blockEntityTypes);
    }

    public static boolean isThermodynamic(Object entity) {
        if(IoCache.contains(entity) || entity instanceof BlockEntity && entity instanceof HeatIo && entity instanceof FluidIo && entity instanceof PressureIo) {
            IoCache.add((BlockEntity) entity);
            return true;
        }
        return false;
    }

    public static long pressureInverse(double pressure) {
        if(pressure <= 0) {
            return 0;
        }
        return (long) Math.max(0, (Math.sqrt(pressure) * 36586.544243));
    }

    public static double accelerationFromPressureGradient(double self, double other) {
        return Math.sqrt(Math.abs(self - other)) * ((self < other) ? -1 : 1);
    }
}

package azzy.fabric.pulseflux.energy;

import azzy.fabric.pulseflux.PulseFluxCommon;
import azzy.fabric.pulseflux.util.IoProvider;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public class PulseFluxEnergyAPIs {
    public static final BlockApiLookup<HeatIo, @NotNull Direction> HEAT =
            BlockApiLookup.get(PulseFluxCommon.id("heat_system"), HeatIo.class, Direction.class);

    public static final BlockApiLookup<MechanicalIo, @NotNull Direction> MECHANICAL =
            BlockApiLookup.get(PulseFluxCommon.id("mechanical_system"), MechanicalIo.class, Direction.class);

    public static final BlockApiLookup<PressureIo, @NotNull Direction> PRESSURE =
            BlockApiLookup.get(PulseFluxCommon.id("pressure_system"), PressureIo.class, Direction.class);

    public static final BlockApiLookup<PulseIo, @NotNull Direction> PULSE =
            BlockApiLookup.get(PulseFluxCommon.id("pulse_system"), PulseIo.class, Direction.class);

    public static final BlockApiLookup<IoProvider, @NotNull Direction> IO_LOOKUP =
            BlockApiLookup.get(PulseFluxCommon.id("io_lookup"), IoProvider.class, Direction.class);
}

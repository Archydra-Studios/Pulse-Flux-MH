package net.azzy.pulseflux.util.interaction;

import net.azzy.pulseflux.util.misc.DimensionAccessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;

public class WorldPressure {

    public static final HashMap<RegistryKey<DimensionType>, Long> PRESSURE_MAP = new HashMap<>();

    public static void init(){
        PRESSURE_MAP.put(DimensionType.OVERWORLD_REGISTRY_KEY, 101325L);
        PRESSURE_MAP.put(DimensionType.THE_NETHER_REGISTRY_KEY, (long) (101325 * 116));
        PRESSURE_MAP.put(DimensionType.THE_END_REGISTRY_KEY, (long) (101325 / 4.18));
    }

    public static long getRawPressure(DimensionType dimensionType){
        if(PRESSURE_MAP.containsKey(dimensionType))
            return PRESSURE_MAP.get(dimensionType);
        else
            return -1;
    }

    public static long getDimPressure(DimensionType dimensionType, BlockPos pos){
        int y = pos.getY();
        long basePressure = getRawPressure(dimensionType);
        return (long) (y < 64 ? Math.pow(basePressure, (1 + (-(y - 64) / 426.666))) : y > 64 ? Math.pow(-basePressure, (y - 64) / 226.0) + basePressure : basePressure);
    }

    public static boolean isDimRegistered(DimensionType dimensionType){
        return PRESSURE_MAP.containsKey(dimensionType);
    }
}

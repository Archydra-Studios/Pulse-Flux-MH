package net.azzy.pulseflux.util.interaction;

import net.azzy.pulseflux.util.misc.DimensionAccessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashMap;

public class WorldPressure {

    public static final HashMap<Biome, Long> PRESSURE_MAP = new HashMap<>();

    public static void init(){
    }

    public static long getRawPressure(Biome biome){
        if(biome.getCategory() == Biome.Category.NETHER)
            return 101325 * 116;
        else if(biome.getCategory() == Biome.Category.THEEND)
            return (long) (101325 / 4.18);
        return 101325;
    }

    public static long getDimPressure(Biome biome, BlockPos pos){
        int y = pos.getY();
        long basePressure = getRawPressure(biome);
        if(y < 64)
            return basePressure * (((-(y - 64) / 64) * basePressure) + basePressure);
        else if(y > 128)
            return basePressure - ((((y - 128) / 128) * basePressure) / 2);
        return basePressure;
    }

    public static boolean isDimRegistered(DimensionType dimensionType){
        return PRESSURE_MAP.containsKey(dimensionType);
    }
}

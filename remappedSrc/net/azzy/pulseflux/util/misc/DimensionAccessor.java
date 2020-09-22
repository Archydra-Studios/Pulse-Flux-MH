package net.azzy.pulseflux.util.misc;

import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

public class DimensionAccessor extends DimensionType {

    protected DimensionAccessor(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultrawarm, boolean natural, double coordinateScale, boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks, boolean hasRaids, int logicalHeight, Identifier infiniburn, Identifier skyProperties, float ambientLight) {
        super(fixedTime, hasSkylight, hasCeiling, ultrawarm, natural, coordinateScale, piglinSafe, bedWorks, respawnAnchorWorks, hasRaids, logicalHeight, infiniburn, skyProperties, ambientLight);
    }

    public static DimensionType getOverworld(){
        return OVERWORLD;
    }

    public static DimensionType getNether(){
        return THE_NETHER;
    }

    public static DimensionType getTheEnd(){
        return THE_END;
    }
}

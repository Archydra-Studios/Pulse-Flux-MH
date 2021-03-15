package azzy.fabric.pulseflux.util;

import net.minecraft.world.World;

public interface UpdateTimeProvider {

    long getLastUpdateTime();

    void update(World world);
}

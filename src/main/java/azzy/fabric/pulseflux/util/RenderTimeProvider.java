package azzy.fabric.pulseflux.util;

import net.minecraft.world.World;

public interface RenderTimeProvider {

    long getLastRenderUpdate();

    void updateRenderTime(World world);
}

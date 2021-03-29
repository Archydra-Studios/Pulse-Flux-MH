package azzy.fabric.pulseflux.energy;

import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public interface KineticIO {

    @NotNull
    Vec3d getVelocity();

    void setVelocity(@NotNull Vec3d newVelocity);

    @NotNull
    default Vec3d drawVelocity() {
        Vec3d velocity = getVelocity();
        setVelocity(Vec3d.ZERO);
        return velocity;
    }

    void permute(@NotNull Vec3d other);

    default void permute(double x, double y, double z) {
        permute(new Vec3d(x, y, z));
    }

    default boolean supportsPermutation() {
        return true;
    }
}

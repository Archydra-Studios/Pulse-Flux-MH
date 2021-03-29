package azzy.fabric.pulseflux.energy;

import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public interface InputSensitiveBlockEntity {
    void notifyInput(@NotNull Direction direction);
}

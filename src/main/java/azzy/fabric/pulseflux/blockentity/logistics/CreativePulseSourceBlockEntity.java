package azzy.fabric.pulseflux.blockentity.logistics;

import azzy.fabric.pulseflux.block.PulseFluxBlocks;
import azzy.fabric.pulseflux.energy.ActiveIO;
import azzy.fabric.pulseflux.energy.Polarity;
import azzy.fabric.pulseflux.energy.PulseCarrier;
import azzy.fabric.pulseflux.energy.PulseIo;
import azzy.fabric.pulseflux.util.ConnectionHelper;
import azzy.fabric.pulseflux.util.IoProvider;
import azzy.fabric.pulseflux.util.RenderTimeProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public class CreativePulseSourceBlockEntity extends BlockEntity implements Tickable, PulseIo, IoProvider, RenderTimeProvider {

    private long renderUpdateTime;
    private ActiveIO<?> activeOutput;
    private final PulseCarrier pulse = new PulseCarrier(1000, 1000, Polarity.NEUTRAL);

    public CreativePulseSourceBlockEntity() {
        super(PulseFluxBlocks.CREATIVE_PULSE_SOURCE_BLOCK_ENTITY);
    }

    @Override
    public void tick() {
        if(world != null) {
            if(renderUpdateTime == 0)
                renderUpdateTime = world.getTime();

            if(activeOutput == null || activeOutput.isInvalid(pos)) {
                activeOutput = ConnectionHelper.getActiveOutput(this, pos, 16, getCachedState().get(Properties.FACING));
            }
            else if(world.getTime() % 20 == 0 && !activeOutput.revalidate(pos, world)) {
                activeOutput = null;
            }

            if(activeOutput != null) {
                activeOutput.connection.set(pulse, activeOutput.io.getOpposite());
            }
        }
    }

    @Override
    public Set<Direction> getOutputs() {
        return Collections.singleton(getCachedState().get(Properties.FACING));
    }

    public ActiveIO<?> getActiveOutput() {
        return activeOutput;
    }

    @Override
    public void setPrimaryOutput(Direction output) {
        if(world != null) {
            BlockState state = getCachedState();
            state = state.with(Properties.FACING, output);
            world.setBlockState(pos, state);
        }
    }

    @Override
    public Set<Direction> getActiveOutputs() {
        return null;
    }

    @Override
    public @NotNull PulseCarrier getStoredPulse() {
        return pulse;
    }

    @Override
    public long getLastRenderUpdate() {
        return renderUpdateTime;
    }

    @Override
    public void updateRenderTime(World world) {
        renderUpdateTime = world.getTime();
    }
}

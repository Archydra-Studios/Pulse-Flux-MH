package azzy.fabric.pulseflux.blockentity;

import azzy.fabric.pulseflux.energy.ActiveIO;
import azzy.fabric.pulseflux.energy.MutationType;
import azzy.fabric.pulseflux.energy.PulseCarrier;
import azzy.fabric.pulseflux.energy.PulseIo;
import azzy.fabric.pulseflux.util.ConnectionHelper;
import azzy.fabric.pulseflux.util.Material;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;


public class PulseFluxMonoPulseMachine extends PulseFluxMachine implements Tickable, PulseIo {

    protected boolean ticked;
    protected final Material material;
    protected ActiveIO<?> activeOutput;
    @NotNull
    protected PulseCarrier pulse = PulseCarrier.EMPTY;
    @NotNull
    protected DirectionProperty output;
    @Nullable DirectionProperty input;

    public PulseFluxMonoPulseMachine(BlockEntityType<?> type, Material material, @NotNull DirectionProperty output, @Nullable DirectionProperty input) {
        super(type);
        this.material = material;
        this.output = output;
        this.input = input;
    }

    @Override
    public void tick() {
        pulseTick();
    }

    protected void pulseTick() {
        if(world != null) {
            if(lastUpdateTime == 0)
                lastUpdateTime = world.getTime();

            if(activeOutput == null || activeOutput.isInvalid(pos)) {
                activeOutput = ConnectionHelper.getActiveOutput(this, pos, 16, getCachedState().get(output));
                if(!world.isClient()) {
                    sync();
                }
            }

            else if(world.getTime() % 20 == 0 && !activeOutput.revalidate(pos, world)) {
                activeOutput = null;
                if(!world.isClient()) {
                    sync();
                }
            }

            if(activeOutput != null && !PulseCarrier.isEmpty(pulse) && pulse.getFrequency() <= material.maxFrequency) {
                if(preProcessOutputPulse()) {
                    activeOutput.connection.set(pulse, activeOutput.io.getOpposite());
                    markDirty();
                }
            }

            if(!ticked && !PulseCarrier.isEmpty(pulse)) {
                pulse = PulseCarrier.EMPTY;
                markDirty();
                if(!world.isClient()) {
                    sync();
                }
            }

            ticked = false;
        }
    }

    protected boolean preProcessOutputPulse() {
        pulse.markPassed(pos);
        return true;
    }

    @Override
    public @NotNull PulseCarrier getStoredPulse() {
        return pulse;
    }

    @Override
    public void setPrimaryOutput(Direction out) {
        if(world != null) {
            BlockState state = getCachedState();
            if(input != null) {
                Direction in = state.get(input);
                if(in == out) {
                    state = state.with(input, state.get(output));
                }
            }
            state = state.with(output, out);
            world.setBlockState(pos, state);
        }
    }

    @Override
    public void setPrimaryInput(Direction in) {
        if(world != null && input != null) {
            BlockState state = getCachedState();
            Direction out = state.get(output);
            if(out == in) {
                state = state.with(output, state.get(input));
            }
            state = state.with(input, in);
            world.setBlockState(pos, state);
        }
    }

    @Override
    public boolean shouldConnect(PulseCarrier pulse, @NotNull Direction direction, @NotNull MutationType type) {
        return input != null && ConnectionHelper.standardConnectionCheck(this, pos, pulse, direction, getCachedState().get(input));
    }

    @Override
    public boolean set(@NotNull PulseCarrier amount, Direction direction) {
        if(direction == getCachedState().get(input)) {
            pulse = amount;
            ticked = true;
            return true;
        }
        return false;
    }

    @Override
    public List<ActiveIO<?>> getActiveOutputs() {
        return Collections.singletonList(activeOutput);
    }

    @Override
    public Set<Direction> getOutputs() {
        return Collections.singleton(getCachedState().get(output));
    }

    @Override
    public Set<Direction> getInputs() {
        return input == null ? Collections.emptySet() : Collections.singleton(getCachedState().get(input));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.put("pulse", pulse.toTag());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        pulse = PulseCarrier.fromTag(tag.getCompound("pulse"));
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        super.fromClientTag(tag);
        pulse = PulseCarrier.fromTag(tag.getCompound("pulse"));
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.put("pulse", pulse.toTag());
        return super.toClientTag(tag);
    }
}

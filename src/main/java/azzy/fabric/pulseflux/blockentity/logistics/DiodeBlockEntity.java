package azzy.fabric.pulseflux.blockentity.logistics;

import azzy.fabric.pulseflux.energy.*;
import azzy.fabric.pulseflux.util.ConnectionHelper;
import azzy.fabric.pulseflux.util.IoProvider;
import azzy.fabric.pulseflux.util.Material;
import azzy.fabric.pulseflux.util.RenderTimeProvider;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

import static azzy.fabric.pulseflux.block.logistics.DiodeBlock.INPUT;
import static azzy.fabric.pulseflux.block.logistics.DiodeBlock.OUTPUT;

public class DiodeBlockEntity extends BlockEntity implements Tickable, PulseIo, IoProvider, RenderTimeProvider, BlockEntityClientSerializable {

    private final Material material;
    private long renderUpdateTime;
    private ActiveIO<?> activeOutput;
    boolean ticked;

    @NotNull
    private PulseCarrier pulse = PulseCarrier.EMPTY;

    public DiodeBlockEntity(BlockEntityType<?> type, Material material) {
        super(type);
        this.material = material;
    }

    @Override
    public void tick() {
        if(world != null) {
            if(renderUpdateTime == 0)
                renderUpdateTime = world.getTime();

            if(activeOutput == null || activeOutput.isInvalid(pos)) {
                activeOutput = ConnectionHelper.getActiveOutput(this, pos, 16, getCachedState().get(OUTPUT));
            }
            else if(world.getTime() % 20 == 0 && !activeOutput.revalidate(pos, world)) {
                activeOutput = null;
            }

            if(activeOutput != null && pulse.getFrequency() <= material.maxFrequency) {
                activeOutput.connection.set(pulse, activeOutput.io.getOpposite());
            }

            if(!ticked) {
                pulse = PulseCarrier.EMPTY;
            }

            ticked = false;
        }
    }

    @Override
    public boolean set(@NotNull PulseCarrier amount, Direction direction) {
        if(direction == getCachedState().get(INPUT)) {
            pulse = amount;
            ticked = true;
            return true;
        }
        return false;
    }

    public ActiveIO<?> getActiveOutput() {
        return activeOutput;
    }

    @Override
    public @NotNull PulseCarrier getStoredPulse() {
        return pulse;
    }

    @Override
    public boolean shouldConnect(Polarity polarity, @NotNull Direction direction, @NotNull MutationType type) {
        return direction == getCachedState().get(INPUT) && type == MutationType.INSERTION && pulse.polarity.matches(polarity);
    }

    @Override
    public Set<Direction> getOutputs() {
        return Collections.singleton(getCachedState().get(OUTPUT));
    }

    @Override
    public Set<Direction> getInputs() {
        return Collections.singleton(getCachedState().get(INPUT));
    }

    @Override
    public void setPrimaryOutput(Direction output) {
        if(world != null) {
            BlockState state = getCachedState();
            Direction input = state.get(INPUT);
            if(input == output) {
                state = state.with(INPUT, state.get(OUTPUT));
            }
            state = state.with(OUTPUT, output);
            world.setBlockState(pos, state);
        }
    }

    @Override
    public void setPrimaryInput(Direction input) {
        if(world != null) {
            BlockState state = getCachedState();
            Direction output = state.get(OUTPUT);
            if(output == input) {
                state = state.with(OUTPUT, state.get(INPUT));
            }
            state = state.with(INPUT, input);
            world.setBlockState(pos, state);
        }
    }

    @Override
    public long getLastRenderUpdate() {
        return renderUpdateTime;
    }

    @Override
    public void updateRenderTime(World world) {
        renderUpdateTime = world.getTime();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putLong("lastRenderUpdate", renderUpdateTime);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        renderUpdateTime = tag.getLong("lastRenderUpdate");
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        renderUpdateTime = tag.getLong("lastRenderUpdate");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putLong("lastRenderUpdate", renderUpdateTime);
        return tag;
    }
}

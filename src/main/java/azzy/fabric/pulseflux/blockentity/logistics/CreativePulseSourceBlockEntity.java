package azzy.fabric.pulseflux.blockentity.logistics;

import azzy.fabric.pulseflux.block.PulseFluxBlocks;
import azzy.fabric.pulseflux.blockentity.PulseFluxMonoPulseMachine;
import azzy.fabric.pulseflux.energy.Polarity;
import azzy.fabric.pulseflux.energy.PulseCarrier;
import azzy.fabric.pulseflux.util.ConnectionHelper;
import azzy.fabric.pulseflux.util.PulseFluxMaterials;
import net.minecraft.state.property.Properties;

public class CreativePulseSourceBlockEntity extends PulseFluxMonoPulseMachine {

    public CreativePulseSourceBlockEntity() {
        super(PulseFluxBlocks.CREATIVE_PULSE_SOURCE_BLOCK_ENTITY, PulseFluxMaterials.UNOBTANIUM, Properties.FACING, null);
        pulse = new PulseCarrier(1000, 1000, Polarity.NEUTRAL);
    }

    @Override
    public void pulseTick() {
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
                pulse.markPassed(pos);
                if(activeOutput.connection.set(pulse, activeOutput.io.getOpposite()))
                    pulse = new PulseCarrier(1000, 1000, Polarity.NEUTRAL);
                markDirty();
            }

            if(!ticked && !PulseCarrier.isEmpty(pulse)) {
                markDirty();
                if(!world.isClient()) {
                    sync();
                }
            }

            ticked = false;
        }
    }
}

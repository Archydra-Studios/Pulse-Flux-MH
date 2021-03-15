package azzy.fabric.pulseflux.blockentity.logistics;

import azzy.fabric.pulseflux.blockentity.PulseFluxMonoPulseMachine;
import azzy.fabric.pulseflux.util.Material;
import net.minecraft.block.entity.BlockEntityType;

import static azzy.fabric.pulseflux.block.logistics.DiodeBlock.INPUT;
import static azzy.fabric.pulseflux.block.logistics.DiodeBlock.OUTPUT;

public class DiodeBlockEntity extends PulseFluxMonoPulseMachine {

    public DiodeBlockEntity(BlockEntityType<?> type, Material material) {
        super(type, material, OUTPUT, INPUT);
    }
}

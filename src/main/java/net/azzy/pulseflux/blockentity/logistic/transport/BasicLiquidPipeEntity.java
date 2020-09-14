package net.azzy.pulseflux.blockentity.logistic.transport;

import net.azzy.pulseflux.util.fluid.FluidHelper;
import net.azzy.pulseflux.util.fluid.FluidPackage;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.LIQUID_PIPE_ENTITY;

public class BasicLiquidPipeEntity extends FluidPipeEntity{

    public BasicLiquidPipeEntity() {
        super(LIQUID_PIPE_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, 8000, 420000000, false);
    }

    @Override
    public void tick() {
        super.tick();
        if(!FluidHelper.isEmpty(tank)){
            for(Direction direction : IO){
                tryPush(world, pos.offset(direction), direction, IO.size());
            }
        }
    }
}

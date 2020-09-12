package net.azzy.pulseflux.util.fluid;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static net.azzy.pulseflux.PulseFlux.PFLog;

public interface FluidHolder {

    FluidPackage getFluid();

    void setFluid(FluidPackage fluid);

    default boolean testFluid(FluidPackage fluid) {
        return FluidHelper.isEmpty(fluid) || (getFluid().getWrappedFluid() == fluid.getWrappedFluid() && getFluid().getGas() == fluid.getGas());
    }

    void recalcPressure();

    long addFluid(long amount);

    long extractFluid(long amount);

    boolean gasCarrying();

    default boolean canConnect(Direction direction){
        return true;
    }

    default boolean canExtract(Direction direction){
        return true;
    }

    default boolean canInsert(Direction direction){
        return true;
    }

    default void tryPush(World world, BlockPos pos, Direction direction, int divisor){
        if(world.isClient())
            return;
        //-213, 71, 86
        BlockEntity entity = world.getBlockEntity(pos);
        FluidPackage offer = getFluid();
        if (!FluidHelper.isEmpty(offer) && entity instanceof FluidHolder && ((FluidHolder) entity).canInsert(direction) && ((FluidHolder) entity).gasCarrying() == offer.getGas()) {
            FluidPackage receiver = ((FluidHolder) entity).getFluid();
            if(testFluid(receiver) && offer.getPressure() > 0 && offer.getPressure() > receiver.getPressure()){
                double modifier = Math.min((((double) receiver.getPressure() / offer.getPressure()) * -1) + 1, 0.9 / divisor);
                long moved = (long) (modifier * Math.max(0, offer.getAmount() - receiver.getAmount()));
                if(FluidHelper.isEmpty(receiver)){
                    ((FluidHolder) entity).setFluid(FluidHelper.copyOf(offer, moved));
                }
                else
                    moved -= ((FluidHolder) entity).addFluid(moved);
                this.extractFluid(moved);
                ((FluidHolder) entity).recalcPressure();
                recalcPressure();
            }
        }
    }

    default void forcePush(World world, BlockPos pos, Direction direction, int divisor, int min){
        if(world.isClient())
            return;
        //-213, 71, 86
        BlockEntity entity = world.getBlockEntity(pos);
        FluidPackage offer = getFluid();
        if (!FluidHelper.isEmpty(offer) && entity instanceof FluidHolder && ((FluidHolder) entity).canInsert(direction) && ((FluidHolder) entity).gasCarrying() == offer.getGas()) {
            FluidPackage receiver = ((FluidHolder) entity).getFluid();
            if(testFluid(receiver) && offer.getPressure() > 0 && offer.getPressure() > receiver.getPressure()){
                double modifier = Math.min((((double) receiver.getPressure() / offer.getPressure()) * -1) + 1, 0.9 / divisor);
                long moved = (long) Math.max(min, (modifier * Math.max(0, offer.getAmount() - receiver.getAmount())));
                if(FluidHelper.isEmpty(receiver)){
                    ((FluidHolder) entity).setFluid(FluidHelper.copyOf(offer, moved));
                }
                else
                    moved -= ((FluidHolder) entity).addFluid(moved);
                this.extractFluid(moved);
                ((FluidHolder) entity).recalcPressure();
                recalcPressure();
            }
        }
    }
}

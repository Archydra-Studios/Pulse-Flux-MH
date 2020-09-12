package net.azzy.pulseflux.blockentity.logistic.transport;


import net.azzy.pulseflux.block.MultiFacingBlock;
import net.azzy.pulseflux.util.fluid.FluidHelper;
import net.azzy.pulseflux.util.fluid.FluidHolder;
import net.azzy.pulseflux.util.fluid.FluidPackage;
import net.azzy.pulseflux.util.interaction.HeatHolder;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.PressureHolder;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import java.util.HashSet;
import java.util.Set;

import static net.azzy.pulseflux.PulseFlux.PFRandom;
import static net.azzy.pulseflux.block.entity.logistic.PipeBlock.CENTER;
import static net.azzy.pulseflux.block.entity.logistic.PipeBlock.DIRECTION;
import static net.minecraft.block.Blocks.AIR;

public class FluidPipeEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable, HeatHolder, PressureHolder, FluidHolder {

    protected double heat;
    protected final HeatTransferHelper.HeatMaterial material;
    protected long maxPressure;
    protected  int baseCapacity;
    protected FluidPackage tank = FluidHelper.empty();
    protected final int delay;
    protected final Set<Direction> IO = new HashSet<>();
    protected boolean straight = false;
    protected final boolean gasCarrying;

    public FluidPipeEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, int baseCapacity, long maxPressure, boolean gasCarrying) {
        super(type);
        this.material = material;
        this.maxPressure = maxPressure;
        this.delay = PFRandom.nextInt(20);
        this.baseCapacity = baseCapacity;
        this.gasCarrying = gasCarrying;
    }

    @Override
    public void tick() {
        if(IO.isEmpty())
            revalidateConnections();
        if(!world.isClient() && (world.getTime() + delay) % 10 == 0){
            for(Direction direction : Direction.values()){
                BlockEntity entity = world.getBlockEntity(pos.offset(direction));
                if(entity instanceof FluidHolder && (((FluidHolder) entity).gasCarrying() == gasCarrying) && canConnect(direction.getOpposite())){
                    if(!getCachedState().get(MultiFacingBlock.getFACING().get(direction)))
                        world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(direction), true));
                    }
                else
                    world.setBlockState(pos, world.getBlockState(pos).with(MultiFacingBlock.getFACING().get(direction), false));
            }
            connectionTest();
            revalidateConnections();
        }
        if(!world.isClient()){
            if(tank.getPressure() < 1)
                tank.setPressure(1);
            else
                recalcPressure();
            if(tank.getPressure() >= maxPressure) {
                if (FluidHelper.isEmpty(tank)) {
                    world.setBlockState(pos, AIR.getDefaultState());
                } else {
                    world.setBlockState(pos, tank.getWrappedFluid().getDefaultState().getBlockState());
                }
            }
        }
    }

    private void connectionTest(){
        BlockState state = world.getBlockState(pos);
        Direction valid = null, valid2 = null;
        for(Direction direction : Direction.values()){
            if(valid == null && state.get(MultiFacingBlock.getFACING().get(direction)) && state.get(MultiFacingBlock.getFACING().get(direction.getOpposite()))){
                valid = direction;
                valid2 = direction.getOpposite();
                continue;
            }
            if(state.get(MultiFacingBlock.getFACING().get(direction)) && (direction != valid && direction != valid2)) {
                world.setBlockState(pos, world.getBlockState(pos).with(CENTER, true), 3);
                return;
            }
        }
        if(valid != null && valid2 != null)
            world.setBlockState(pos, world.getBlockState(pos).with(CENTER, false).with(DIRECTION, valid));
    }

    private void revalidateConnections(){
        IO.clear();
        straight = false;
        BlockState state = world.getBlockState(pos);
        if(!state.get(CENTER)){
            Direction facing = state.get(DIRECTION);
            IO.add(facing);
            IO.add(facing.getOpposite());
            straight = true;
        }
        for(Direction direction : Direction.values()){
            if(state.get(MultiFacingBlock.getFACING().get(direction)))
                IO.add(direction);
        }
    }

    @Override
    public double getHeat() {
        return heat;
    }

    @Override
    public void recalcPressure() {
        double fluid = tank.getAmount() / 1000.0;
        tank.setPressure((long) Math.pow(fluid, 4));
        markDirty();
        sync();
    }

    @Override
    public void moveHeat(double change) {
        heat += change;
    }

    @Override
    public double getArea() {
        return 0.0625;
    }

    @Override
    public HeatTransferHelper.HeatMaterial getMaterial() {
        return material;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putDouble("heat", heat);
        tag.put("fluid", tank.toTag());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        heat = tag.getDouble("heat");
        tank = FluidPackage.fromTag(tag.getCompound("fluid"));
        super.fromTag(state, tag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        heat = compoundTag.getDouble("heat");
        tank = FluidPackage.fromTag(compoundTag.getCompound("fluid"));
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putDouble("heat", heat);
        compoundTag.put("fluid", tank.toTag());
        return compoundTag;
    }

    @Override
    public long getPressure() {
        return tank.getPressure();
    }

    @Override
    public void setPressure(long pressure) {
        tank.setPressure(pressure);
    }

    @Override
    public FluidPackage getFluid() {
        return tank;
    }

    @Override
    public void setFluid(FluidPackage fluid) {
        tank = fluid;
    }

    @Override
    public long addFluid(long amount) {
        tank.changeAmount(amount);
        return 0;
    }

    @Override
    public long extractFluid(long amount) {
        tank.changeAmount(-amount);
        return 0;
    }

    @Override
    public boolean gasCarrying() {
        return gasCarrying;
    }

    @Override
    public boolean canInsert(Direction direction) {
        return IO.contains(direction.getOpposite());
    }

    @Override
    public boolean canExtract(Direction direction) {
        return IO.contains(direction.getOpposite());
    }
}

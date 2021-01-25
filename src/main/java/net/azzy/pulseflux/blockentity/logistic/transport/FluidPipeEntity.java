package net.azzy.pulseflux.blockentity.logistic.transport;


import dev.technici4n.fasttransferlib.api.fluid.FluidApi;
import dev.technici4n.fasttransferlib.api.fluid.FluidIo;
import net.azzy.pulseflux.block.MultiFacingBlock;
import net.azzy.pulseflux.block.entity.logistic.FluidPipeBlock;
import net.azzy.pulseflux.util.fluid.DirectionalVectorEnergy;
import net.azzy.pulseflux.util.fluid.FluidPipeIo;
import net.azzy.pulseflux.util.interaction.HeatHolder;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.PressureHolder;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import java.util.HashSet;
import java.util.Set;

import static net.azzy.pulseflux.PulseFlux.PFRandom;
import static net.azzy.pulseflux.block.entity.logistic.PipeBlock.CENTER;
import static net.azzy.pulseflux.block.entity.logistic.PipeBlock.DIRECTION;

public class FluidPipeEntity extends BlockEntity implements Tickable, BlockEntityClientSerializable, HeatHolder, PressureHolder, FluidPipeIo {

    protected DirectionalVectorEnergy vectorEnergy = new DirectionalVectorEnergy();
    protected double heat;
    protected final HeatTransferHelper.HeatMaterial material;
    protected final long maxPressure;
    protected long pressure;
    protected final int baseCapacity;
    protected Fluid fluid;
    protected long fluidQuantity;
    protected final int delay;
    protected final Set<Direction> IO = new HashSet<>();
    protected boolean straight = false;
    public final boolean gasCarrying;

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
        if(this.IO.isEmpty())
            revalidateConnections();
        if((this.world.getTime() + this.delay) % 10 == 0){
            for(Direction direction : Direction.values()){
                FluidIo fluidIo = FluidApi.SIDED.get(this.world, this.pos.offset(direction), direction.getOpposite());
                if(fluidIo != null){
                    if(!getCachedState().get(MultiFacingBlock.getFACING().get(direction))) {
                        this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(MultiFacingBlock.getFACING().get(direction), true));
                        this.IO.add(direction);
                    }
                    }
                else {
                    this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(MultiFacingBlock.getFACING().get(direction), false));
                    getIO().remove(direction);
                }
            }
            connectionTest();
            revalidateConnections();
        }
    }

    private void recalcHeat(){
        for(Direction direction : this.IO){
            BlockEntity entity = this.world.getBlockEntity(this.pos.offset(direction));
            if(entity instanceof HeatHolder && this.world.getBlockState(this.pos.offset(direction)).getBlock() instanceof FluidPipeBlock) {
                for(int i = 0; i < 4; i++)
                HeatTransferHelper.simulateHeat(this.material, this, ((HeatHolder) entity));
            }
        }
        for(int i = 0; i < 16; i++)
            HeatTransferHelper.simulateAmbientHeat(this, this.world.getBiome(this.pos));
    }

    public boolean isStraight() {
        return this.straight;
    }

    public Set<Direction> getIO() {
        return this.IO;
    }

    public Fluid getFluid() {
        return this.fluid;
    }

    private void connectionTest(){
        BlockState state = this.world.getBlockState(this.pos);
        Direction valid = null, valid2 = null;
        for(Direction direction : Direction.values()){
            if(valid == null && state.get(MultiFacingBlock.getFACING().get(direction)) && state.get(MultiFacingBlock.getFACING().get(direction.getOpposite()))){
                valid = direction;
                valid2 = direction.getOpposite();
                continue;
            }
            if(state.get(MultiFacingBlock.getFACING().get(direction)) && (direction != valid && direction != valid2)) {
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(CENTER, true), 3);
                return;
            }
        }
        if(valid != null && valid2 != null)
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(CENTER, false).with(DIRECTION, valid));
    }

    private void revalidateConnections(){
        this.IO.clear();
        this.straight = false;
        BlockState state = this.world.getBlockState(this.pos);
        if(!state.get(CENTER)){
            Direction facing = state.get(DIRECTION);
            this.IO.add(facing);
            this.IO.add(facing.getOpposite());
            this.straight = true;
        }
        for(Direction direction : Direction.values()){
            if(state.get(MultiFacingBlock.getFACING().get(direction)))
                this.IO.add(direction);
        }
    }

    @Override
    public double getHeat() {
        return 0;
    }

    @Override
    public void moveHeat(double change) {
    }

    @Override
    public double getArea() {
        return 0.5;
    }

    @Override
    public HeatTransferHelper.HeatMaterial getMaterial() {
        return this.material;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putDouble("heat", this.heat);
        this.vectorEnergy.toTag(tag);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        this.heat = tag.getDouble("heat");
        this.vectorEnergy = DirectionalVectorEnergy.Companion.fromTag(tag);
        super.fromTag(state, tag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        this.heat = compoundTag.getDouble("heat");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putDouble("heat", this.heat);
        return compoundTag;
    }

    @Override
    public long getPressure() {
        return this.pressure;
    }

    @Override
    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    @Override
    public int getFluidSlotCount() {
        return 1;
    }

    @Override
    public Fluid getFluid(int i) {
        return this.fluid;
    }

    @Override
    public long getFluidAmount(int slot) {
        return this.fluidQuantity;
    }
}

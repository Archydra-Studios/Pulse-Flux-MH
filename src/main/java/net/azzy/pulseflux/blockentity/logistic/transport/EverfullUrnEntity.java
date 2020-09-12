package net.azzy.pulseflux.blockentity.logistic.transport;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.azzy.pulseflux.mixin.BucketFluidAccessor;
import net.azzy.pulseflux.util.fluid.FluidHelper;
import net.azzy.pulseflux.util.fluid.FluidHolder;
import net.azzy.pulseflux.util.fluid.FluidPackage;
import net.azzy.pulseflux.util.gui.ExtendedPropertyDelegate;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.InventoryWrapper;
import net.azzy.pulseflux.util.networking.Syncable;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import static net.azzy.pulseflux.registry.BlockEntityRegistry.EVERFULL_URN_ENTITY;

public class EverfullUrnEntity extends BlockEntity implements BlockEntityClientSerializable, Syncable, FluidHolder, InventoryWrapper, PropertyDelegateHolder, Tickable {

    private long pressure;
    private FluidPackage tank = FluidHelper.empty();
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public EverfullUrnEntity() {
        super(EVERFULL_URN_ENTITY);
    }

    @Override
    public void tick() {
        for(Direction direction : Direction.values())
            forcePush(world, pos.offset(direction), direction, 1, 1000);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, inventory);
        tag.putLong("pressure", pressure);
        tag.put("tank", tank.toTag());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        Inventories.fromTag(tag, inventory);
        pressure = tag.getLong("pressure");
        tank = FluidPackage.fromTag(tag.getCompound("tank"));
        super.fromTag(state, tag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        pressure = compoundTag.getLong("pressure");
        tank = FluidPackage.fromTag(compoundTag.getCompound("tank"));
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putLong("pressure", pressure);
        compoundTag.put("tank", tank.toTag());
        return compoundTag;
    }

    @Override
    public void synchronize(SyncPacket packet) {
        this.pressure = (long) packet.unpack();
        if(!inventory.isEmpty() && inventory.get(0).getItem() instanceof BucketItem){
            Fluid fluid = ((BucketFluidAccessor) inventory.get(0).getItem()).getFluid();
            tank = fluid == Fluids.EMPTY ? FluidHelper.empty() : new FluidPackage(fluid, HeatTransferHelper.translateBiomeHeat(world.getBiome(pos)), pressure, 8000, false);
        }
        else
            tank = FluidHelper.empty();
        markDirty();
        sync();
    }

    @Override
    public FluidPackage getFluid() {
        return tank;
    }

    @Override
    public void setFluid(FluidPackage fluid) {
    }

    @Override
    public boolean testFluid(FluidPackage fluid) {
        return true;
    }

    @Override
    public void recalcPressure() {

    }

    @Override
    public long addFluid(long amount) {
        return 0;
    }

    @Override
    public long extractFluid(long amount) {
        return 0;
    }

    @Override
    public boolean gasCarrying() {
        return false;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    private final PropertyDelegate delegate = new ExtendedPropertyDelegate() {
        @Override
        public long getLong(int index) {
            return pressure;
        }

        @Override
        public void setLong(int index, long value) {

        }

        @Override
        public BlockPos getpos() {
            return pos;
        }
    };

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return delegate;
    }
}

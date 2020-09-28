package net.azzy.pulseflux.blockentity;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.azzy.pulseflux.util.interaction.HeatHolder;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.InventoryWrapper;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.function.Supplier;


public abstract class MachineEntity extends BlockEntity implements Tickable, InventoryWrapper, HeatHolder, PropertyDelegateHolder, BlockEntityClientSerializable, SidedInventory, InventoryProvider {

    //DEFAULT VALUES, DO NOT FORGET TO OVERRIDE THESE

    protected final HeatTransferHelper.HeatMaterial material;
    protected DefaultedList<ItemStack> inventory;
    protected final Supplier<DefaultedList<ItemStack>> invSupplier;
    protected int progress;
    protected boolean heatInit;
    protected double heat;

    public MachineEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, Supplier<DefaultedList<ItemStack>> invSupplier) {
        super(type);
        this.material = material;
        this.invSupplier = invSupplier;
        this.inventory = invSupplier.get();
    }

    public void calcHeat(){
        for(int i = 0; i < 4; i++){
            HeatTransferHelper.simulateAmbientHeat(this, this.world.getBiome(pos));
            simulateSurroundingHeat(pos, this);
        }
    }

    public static <T extends BlockEntity & HeatHolder> void simulateSurroundingHeat(BlockPos pos, T bodyA){
        BlockPos bodyB;
        World world = bodyA.getWorld();

        if(world == null)
            return;

        for(Direction direction : Direction.values()){
            bodyB = pos.offset(direction);

            if((world.getBlockEntity(bodyB) instanceof  HeatHolder)){
                if(((HeatHolder) world.getBlockEntity(bodyB)).getMaterial() != null)
                    HeatTransferHelper.simulateHeat(((HeatHolder) world.getBlockEntity(bodyB)).getMaterial(), (HeatHolder) world.getBlockEntity(bodyB), bodyA);
                else if(bodyA.getMaterial() != null)
                    HeatTransferHelper.simulateHeat(bodyA.getMaterial(), (HeatHolder) world.getBlockEntity(bodyB), bodyA);
                else
                    HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, (HeatHolder) world.getBlockEntity(bodyB), bodyA);
            }
            else if(HeatTransferHelper.isHeatSource(world.getBlockState(bodyB).getBlock())){
                HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, bodyA, world.getBlockState(bodyB).getBlock());
            }
        }
    }

    public static <T extends BlockEntity & HeatHolder> void simulateSurroundingHeat(BlockPos pos, T bodyA, Direction direction){
        BlockPos bodyB;
        World world = bodyA.getWorld();

        if(world == null)
            return;

        bodyB = pos.offset(direction);
        if((world.getBlockEntity(bodyB) instanceof  HeatHolder)){
            if(((HeatHolder) world.getBlockEntity(bodyB)).getMaterial() != null)
                HeatTransferHelper.simulateHeat(((HeatHolder) world.getBlockEntity(bodyB)).getMaterial(), (HeatHolder) world.getBlockEntity(bodyB), bodyA);
            else if(bodyA.getMaterial() != null)
                HeatTransferHelper.simulateHeat(bodyA.getMaterial(), (HeatHolder) world.getBlockEntity(bodyB), bodyA);
            else
                HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, (HeatHolder) world.getBlockEntity(bodyB), bodyA);
        }
        else if(HeatTransferHelper.isHeatSource(world.getBlockState(bodyB).getBlock())){
            HeatTransferHelper.simulateHeat(HeatTransferHelper.HeatMaterial.AIR, bodyA, world.getBlockState(bodyB).getBlock());
        }
    }


    protected void meltDown(boolean cold, BlockState state){
        if(cold && state != null){
            world.setBlockState(pos, state);
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f, false);
            if(!world.isClient()){
                ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, pos.getX()+0.25, pos.up().getY(), pos.getZ()+0.25, 11, 0.25, 0, 0.25, 0);
            }
        }
        else{
            world.setBlockState(pos, Blocks.LAVA.getDefaultState());
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.2f, 0.8f, false);
            if(!world.isClient()){
                ((ServerWorld) world).spawnParticles(ParticleTypes.LARGE_SMOKE, pos.getX()+0.25, pos.up().getY(), pos.getZ()+0.25, 11, 0.25, 0, 0.25, 0);
            }
        }
    }

    @Override
    public double getSquaredRenderDistance() {
        return 256;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public double getHeat() {
        return heat;
    }

    @Override
    public void moveHeat(double change) {
        heat += change;
    }

    @Override
    public HeatTransferHelper.HeatMaterial getMaterial() {
        return material;
    }

    @Override
    public void tick() {
        if(!heatInit) {
            this.heat = HeatTransferHelper.translateBiomeHeat(world.getBiome(pos));
            heatInit = true;
        }
        calcHeat();
        if(!world.isClient()) {
            markDirty();
            sync();
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, inventory);
        tag.putDouble("heat", heat);
        tag.putInt("progress", progress);
        tag.putBoolean("heatinit", heatInit);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        this.inventory = invSupplier.get();
        Inventories.fromTag(tag, inventory);
        heat = tag.getDouble("heat");
        progress = tag.getInt("progress");
        heatInit = tag.getBoolean("heatinit");
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        heat = compoundTag.getDouble("heat");
        progress = compoundTag.getInt("progress");
        heatInit = compoundTag.getBoolean("heatinit");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putDouble("heat", heat);
        compoundTag.putInt("progress", progress);
        compoundTag.putBoolean("heatinit", heatInit);
        return compoundTag;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return this;
    }

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }
}

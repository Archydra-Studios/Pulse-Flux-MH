package net.azzy.pulseflux.blockentity.production;

import net.azzy.pulseflux.blockentity.MachineEntity;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

import static net.azzy.pulseflux.block.entity.production.BlastFurnaceMachine.LIT;
import static net.azzy.pulseflux.registry.BlockEntityRegistry.BLAST_FURNACE_ENTITY;

public class BlastFurnaceMachineEntity extends MachineEntity {

    public BlastFurnaceMachineEntity() {
        super(BLAST_FURNACE_ENTITY, HeatTransferHelper.HeatMaterial.STEEL, () -> DefaultedList.ofSize(10, ItemStack.EMPTY));
    }

    @Override
    public void tick() {
        super.tick();

        if(!this.hasWorld())
            return;

        if(!(progress > 0) && this.getCachedState().get(LIT))
            world.setBlockState(pos, this.getCachedState().with(LIT, false));

        if(heat > 1680) {
            meltDown(false, null);
        }
    }

    @Override
    public double getArea() {
        return 1;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        if(slot == 9)
            return false;
        else if(slot > 2 && slot < 9)
            return stack.getItem() == Items.IRON_INGOT || stack.getItem() == Items.IRON_BLOCK;
        else if (slot <= 2)
            return stack.getItem() == Items.CHARCOAL || ItemTags.SAND.contains(stack.getItem()) || stack.getItem() == Items.REDSTONE;
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return slot == 9;
    }

    PropertyDelegate delegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch(index){
                case (0): return (int) heat;
                case (1): return 1680;
                case (2): return progress;
                case (3): return 200;
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
        }

        @Override
        public int size() {
            return 4;
        }
    };

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return delegate;
    }
}

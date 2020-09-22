package net.azzy.pulseflux.blockentity.logistic;

import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.interaction.ScrewableEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ModulatorEntity extends FailingPulseCarryingEntity implements ScrewableEntity {

    private final int modifier;
    private boolean mode = true;

    public ModulatorEntity(BlockEntityType<?> type, HeatTransferHelper.HeatMaterial material, int modifier, long maxAmplitude, double maxFrequency) {
        super(type, material, (short) 9, () -> DefaultedList.ofSize(0, ItemStack.EMPTY), maxAmplitude, maxFrequency, false);
        this.modifier = modifier;
    }

    @Override
    public void accept(Direction direction, BlockPos sender) {
        super.accept(direction, sender);
        if(checkIO(direction, sender)){
            inductance = mode ? inductance * modifier : inductance / modifier;
            frequency = mode ? frequency / modifier : frequency * modifier;
        }
    }

    @Override
    public void onScrewed(PlayerEntity entity) {
        mode = !mode;
        entity.sendMessage(new TranslatableText("block.pulseflux.modulator.mode_" + mode), true);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putBoolean("mode", mode);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        mode = tag.getBoolean("mode");
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        compoundTag.putBoolean("mode", mode);
        return super.toClientTag(compoundTag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        mode = compoundTag.getBoolean("mode");
        super.fromClientTag(compoundTag);
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return null;
    }

    @Override
    public double getArea() {
        return 0.125;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}

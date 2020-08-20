package net.azzy.pulseflux.util.gui.controller;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.netty.buffer.Unpooled;
import net.azzy.pulseflux.util.gui.ExtendedPropertyDelegate;
import net.azzy.pulseflux.util.networking.ClientPacketRegistry;
import net.azzy.pulseflux.util.networking.Syncable;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerContext;

import java.util.function.Predicate;

import static net.azzy.pulseflux.registry.BlockRegistry.CREATIVE_PULSE_SOURCE;


public class CreativeDiodeController extends BaseController {

    private WTextField inductanceField;
    private WTextField frequencyField;
    private ExtendedPropertyDelegate extendedDelegate;

    public CreativeDiodeController(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(null, syncId, playerInventory, context);
        extendedDelegate = (ExtendedPropertyDelegate) propertyDelegate;
    }

    @Override
    protected void assembleGridSize() {
        super.assembleGridSize();
        name = I18n.translate(CREATIVE_PULSE_SOURCE.getTranslationKey());
    }

    @Override
    protected void assembleInventory(int slots, int gapX, int gapY) {
        Predicate<ItemStack> filter = e -> e.getItem() == Items.REDSTONE;
        root.add(WItemSlot.of(blockInventory, 0).setFilter(filter), 72, 39);
        extendedDelegate = (ExtendedPropertyDelegate) propertyDelegate;

        inductanceField = new WTextField();
        frequencyField = new WTextField();
        inductanceField.setText(String.valueOf(extendedDelegate.getLong(0)));
        frequencyField.setText(String.valueOf(extendedDelegate.getDouble(0)));

        if(world.isClient()){
            root.add(inductanceField, 0, 39, 54, 10);
            root.add(frequencyField, 108, 39, 54, 10);
        }
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        if(world.isClient()){
            try{
                long inductance = Long.parseLong(inductanceField.getText());
                double frequency = Double.parseDouble(frequencyField.getText());
                extendedDelegate.setLong(0, inductance);
                extendedDelegate.setDouble(0, frequency);
                PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
                packet.writeBlockPos(extendedDelegate.getpos());
                packet.writeLong(inductance);
                packet.writeDouble(frequency);
                ClientSidePacketRegistry.INSTANCE.sendToServer(ClientPacketRegistry.CREATIVE_DIODE_SYNC, packet);
            }
            catch (Exception ignored){}
        }
    }
}

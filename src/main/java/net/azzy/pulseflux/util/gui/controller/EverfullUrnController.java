package net.azzy.pulseflux.util.gui.controller;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.netty.buffer.Unpooled;
import net.azzy.pulseflux.util.gui.ExtendedPropertyDelegate;
import net.azzy.pulseflux.util.networking.ClientPacketRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerContext;

import static net.azzy.pulseflux.registry.BlockRegistry.CREATIVE_PULSE_SOURCE;
import static net.azzy.pulseflux.registry.BlockRegistry.EVERFULL_URN;


public class EverfullUrnController extends BaseController {

    private WTextField pressureField;
    private ExtendedPropertyDelegate extendedDelegate;

    public EverfullUrnController(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(null, syncId, playerInventory, context);
        extendedDelegate = (ExtendedPropertyDelegate) propertyDelegate;
    }

    @Override
    protected void assembleGridSize() {
        super.assembleGridSize();
        name = I18n.translate(EVERFULL_URN.getTranslationKey());
    }

    @Override
    protected void assembleInventory(int slots, int gapX, int gapY) {
        extendedDelegate = (ExtendedPropertyDelegate) propertyDelegate;

        pressureField = new WTextField();
        pressureField.setText(String.valueOf(extendedDelegate.getLong(0)));
        root.add(WItemSlot.outputOf(blockInventory, 0), 72, 39);

        if(world.isClient()){
            root.add(pressureField, 0, 73, 160, 10);
        }
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        if(world.isClient()){
            try{
                long pressure = Long.parseLong(pressureField.getText());
                PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
                packet.writeBlockPos(extendedDelegate.getpos());
                packet.writeLong(pressure);
                ClientSidePacketRegistry.INSTANCE.sendToServer(ClientPacketRegistry.EVERFULL_URN_SYNC, packet);
            }
            catch (Exception ignored){}
        }
    }
}

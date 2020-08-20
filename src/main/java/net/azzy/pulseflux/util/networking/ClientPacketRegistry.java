package net.azzy.pulseflux.util.networking;

import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class ClientPacketRegistry {

    public static final Identifier CREATIVE_DIODE_SYNC = new Identifier(MOD_ID, "creative_diode_sync");

    public static void init() {

        ServerSidePacketRegistry.INSTANCE.register(CREATIVE_DIODE_SYNC, (packetContext, packetByteBuf) -> {
            BlockPos pos = packetByteBuf.readBlockPos();
            long inductance = packetByteBuf.readLong();
            double frequency = packetByteBuf.readDouble();
            sendPacket(packetContext, pos, new Syncable.SyncPacket(inductance, frequency), CreativePulseSourceEntity.class);
        });
    }

    private static void sendPacket(PacketContext context, BlockPos pos, Syncable.SyncPacket packet, Class<?> entityClass){
        context.getTaskQueue().execute(() -> {
            BlockEntity entity = context.getPlayer().getEntityWorld().getBlockEntity(pos);
            if(entity instanceof Syncable && entity.getClass() == entityClass){
                Syncable.requestSync((Syncable) entity, packet);
            }
        });
    }
}

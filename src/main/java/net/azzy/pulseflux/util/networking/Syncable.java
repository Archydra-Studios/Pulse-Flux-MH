package net.azzy.pulseflux.util.networking;

import net.minecraft.block.entity.BlockEntity;

import java.util.*;

public interface Syncable {

    void syncrhonize(SyncPacket packet);

    static void requestSync(Syncable entity, SyncPacket packet){
        entity.syncrhonize(packet);
    }

    class SyncPacket{

        private final Queue<Object> contents = new LinkedList<Object>() {
        };

        public SyncPacket(Object... contents){
            Collections.addAll(this.contents, contents);
        }

        public Object unpack(){
            return contents.poll();
        }
    }
}

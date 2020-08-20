package net.azzy.pulseflux.util.networking;

import net.minecraft.block.entity.BlockEntity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public interface Syncable {

    void syncrhonize(SyncPacket packet);

    static <T extends BlockEntity & Syncable> void requestSync(Syncable entity, SyncPacket packet){
        entity.syncrhonize(packet);
    }

    class SyncPacket{

        private final Queue<Object> contents = new LinkedList<Object>() {
        };

        public SyncPacket(Object... contents){
            for(Object content : contents)
                this.contents.add(content);
        }

        public Object unpack(){
            return contents.poll();
        }
    }
}

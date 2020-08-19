package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.math.position.ChunkBlockPosition;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.util.math.ChunkPos;

public class EventChunkDataReceive extends Event {

    private final ChunkPos rawPos;
    private final ChunkBlockPosition pos;

    public boolean isInitialFullChunk, updatedIsFullChunk;

    private final ChunkDataS2CPacket rootPacket;

    public ChunkDataS2CPacket getRootPacket() {
        return rootPacket;
    }

    public ChunkPos getRawPos() {
        return rawPos;
    }

    public ChunkBlockPosition getPos() {
        return pos;
    }

    public EventChunkDataReceive(ChunkDataS2CPacket rootPacket) {
        this.rootPacket = rootPacket;
        this.rawPos = new ChunkPos(rootPacket.getX(), rootPacket.getZ());
        this.pos = new ChunkBlockPosition(rawPos);
        isInitialFullChunk = rootPacket.isFullChunk();
        updateFullChunk(rootPacket);
    }

    private void updateFullChunk(ChunkDataS2CPacket rootPacket) {
        updatedIsFullChunk = rootPacket.isFullChunk();
    }

}

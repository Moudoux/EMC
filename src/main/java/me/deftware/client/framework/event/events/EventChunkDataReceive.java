package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.world.IChunkPos;
import net.minecraft.client.network.packet.ChunkDataS2CPacket;
import net.minecraft.util.math.ChunkPos;

public class EventChunkDataReceive extends Event {

    private ChunkPos rawPos;
    private IChunkPos pos;

    public boolean isInitialFullChunk, updatedIsFullChunk;

    private ChunkDataS2CPacket rootPacket;

    public ChunkDataS2CPacket getRootPacket() {
        return rootPacket;
    }

    public ChunkPos getRawPos() {
        return rawPos;
    }

    public IChunkPos getPos() {
        return pos;
    }

    public EventChunkDataReceive(ChunkDataS2CPacket rootPacket) {
        this.rootPacket = rootPacket;
        this.rawPos = new ChunkPos(rootPacket.getX(), rootPacket.getZ());
        this.pos = new IChunkPos(rawPos);
        isInitialFullChunk = rootPacket.isFullChunk();
        updateFullChunk(rootPacket);
    }

    private void updateFullChunk(ChunkDataS2CPacket rootPacket) {
        updatedIsFullChunk = rootPacket.isFullChunk();
    }
}

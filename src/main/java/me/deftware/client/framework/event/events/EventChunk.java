package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.world.chunk.ChunkAccessor;

public class EventChunk extends Event {

    private final ChunkAccessor chunk;
    private final Action action;
    private final int x, z;

    public EventChunk(ChunkAccessor chunk, Action action, int x, int z) {
        this.chunk = chunk;
        this.action = action;
        this.x = x;
        this.z = z;
    }

    public ChunkAccessor getChunk() {
        return chunk;
    }

    public Action getAction() {
        return action;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public enum Action {
        LOAD, UNLOAD
    }

}

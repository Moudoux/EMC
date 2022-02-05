package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.world.block.Block;
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

    public static class EventDeltaChunk extends Event {

        private final int x, z, y;
        private final short[] positions;
        private final Block[] blocks;

        public EventDeltaChunk(int x, int y, int z, short[] positions, Block[] blocks) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.positions = positions;
            this.blocks = blocks;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public short[] getPositions() {
            return positions;
        }

        public Block[] getBlocks() {
            return blocks;
        }

    }

}

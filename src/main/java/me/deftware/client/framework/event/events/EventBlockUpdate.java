package me.deftware.client.framework.event.events;

import lombok.Getter;
import me.deftware.client.framework.entity.EntityHand;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.world.block.Block;

@Getter
public class EventBlockUpdate extends Event {

    private final State state;
    private final BlockPosition position;
    private final Block block;

    private final EntityHand hand;

    public EventBlockUpdate(State state, BlockPosition position, Block block, EntityHand hand) {
        this.state = state;
        this.position = position;
        this.block = block;
        this.hand = hand;
    }

    public enum State {
        /**
         * When a block is placed
         */
        Place,
        /**
         * When a block is being broken
         */
        Break,
        /**
         * When a block is broken
         */
        Broken
    }

}

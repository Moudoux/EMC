package me.deftware.client.framework.world.chunk;

import me.deftware.client.framework.world.block.Block;

public interface SectionAccessor {

    Block getBlock(int x, int y, int z);

}

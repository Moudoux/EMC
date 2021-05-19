package me.deftware.client.framework.world.classifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.math.position.BlockPosition;
import net.minecraft.block.Block;

/**
 * @author Deftware
 */
public @Data @AllArgsConstructor class ClassifiedBlock {

	private final BlockPosition position;
	private final BoundingBox box;
	private final Block block;

}

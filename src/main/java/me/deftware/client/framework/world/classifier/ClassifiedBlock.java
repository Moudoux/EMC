package me.deftware.client.framework.world.classifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.deftware.client.framework.math.box.BoundingBox;
import net.minecraft.block.Block;

/**
 * @author Deftware
 */
public @Data @AllArgsConstructor class ClassifiedBlock {

	private final BoundingBox box;
	private final Block block;

}

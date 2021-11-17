package me.deftware.client.framework.world.chunk;

import java.util.ArrayList;
import java.util.List;

/**
 * A block classifier accepts all blocks in a chunk
 * whilst it renders to classify and store the blocks
 *
 * @author Deftware
 */
public interface BlockClassifier {

	List<BlockClassifier> CLASSIFIERS = new ArrayList<>();

	/**
	 * @param position The packed block position
	 * @param identifier The name of the block
	 */
	void classify(long position, String identifier);

	/**
	 * Called when a given chunk is unloaded from the game
	 */
	void unload(int x, int z);

	/**
	 * Clears all classified blocks
	 */
	void clear();

	/**
	 * @param position Packed block position
	 * @return If the classifier has a classified block at a given position
	 */
	boolean contains(long position);

	/**
	 * @return If the classifier should accept new blocks
	 */
	boolean isActive();

}

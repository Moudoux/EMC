package me.deftware.client.framework.registry;

import me.deftware.client.framework.world.block.Block;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
public enum BlockRegistry implements IRegistry<Block, net.minecraft.block.Block> {

	INSTANCE;

	private final HashMap<String, Block> blocks = new HashMap<>();

	@Override
	public Stream<Block> stream() {
		return blocks.values().stream();
	}

	@Override
	public void register(String id, net.minecraft.block.Block object) {
		blocks.putIfAbsent(id, Block.newInstance(object));
	}

	@Override
	public Optional<Block> find(String id) {
		return stream().filter(block ->
			block.getIdentifierKey().equalsIgnoreCase(id)
		).findFirst();
	}

}

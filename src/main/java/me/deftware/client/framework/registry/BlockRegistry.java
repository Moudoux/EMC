package me.deftware.client.framework.registry;

import me.deftware.client.framework.world.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
public enum BlockRegistry implements IRegistry<Block, net.minecraft.block.Block> {

	INSTANCE;

	private final HashMap<String, Block> blocks = new HashMap<>();

	private final Map<net.minecraft.block.Block, Block> map = new HashMap<>();

	@Override
	public Stream<Block> stream() {
		return blocks.values().stream();
	}

	@Override
	public void register(String id, net.minecraft.block.Block object) {
		Block block = Block.newInstance(object);
		blocks.putIfAbsent(id, block);
		map.putIfAbsent(object, block);
	}

	@Override
	public Optional<Block> find(String id) {
		return stream().filter(block ->
			block.getTranslationKey().equalsIgnoreCase(id) ||
					block.getTranslationKey().substring("block.minecraft:".length()).equalsIgnoreCase(id)
		).findFirst();
	}

	public Block getBlock(net.minecraft.block.Block block) {
		return map.get(block);
	}

}

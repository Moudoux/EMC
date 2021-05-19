package me.deftware.client.framework.world.classifier;

import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.math.box.DoubleBoundingBox;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.math.position.DoubleBlockPosition;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Deftware
 */
public class BlockClassifier {

	private @Getter static final ArrayList<BlockClassifier> classifiers = new ArrayList<>();

	private @Getter final Long2ObjectArrayMap<ClassifiedBlock> classifiedBlocks = new Long2ObjectArrayMap<>();
	private @Getter @Setter boolean running = false;
	private final BiPredicate<Integer, Integer> validator;
	private final Predicate<Long> filter;

	public BlockClassifier(BiPredicate<Integer, Integer> validator, Predicate<Long> filter) {
		this.validator = validator;
		this.filter = filter;
	}

	public int getSize() {
		return classifiedBlocks.size();
	}

	public void classify(Block block, BlockPos pos, int id) {
		if (running && validator.test(id, pos.getY())) {
			classifiedBlocks.computeIfAbsent(pos.asLong(), key -> new ClassifiedBlock(DoubleBlockPosition.fromMinecraftBlockPos(pos), new DoubleBoundingBox(pos), block));
		}
	}

	public static void clear(ChunkPos chunkPos) {
		classifiers.forEach(classifier -> {
			if (!classifier.running) return;
			classifier.classifiedBlocks.keySet().stream().filter(
					classifier.filter
			).collect(Collectors.toList()).forEach(box ->
					classifier.classifiedBlocks.remove(box.longValue())
			);
		});
	}

}

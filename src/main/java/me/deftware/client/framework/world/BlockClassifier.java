package me.deftware.client.framework.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.math.box.DoubleBoundingBox;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.stream.Collectors;

/**
 * @author Deftware
 */
public class BlockClassifier {

	private static @Getter final Long2ObjectArrayMap<BoundingBox> classifiedBlocks = new Long2ObjectArrayMap<>();
	private static @Getter @Setter float unloadDistance = 100f;
	private static @Getter @Setter boolean running = false;

	public static int getSize() {
		return classifiedBlocks.size();
	}

	public static void clear(ChunkPos chunkPos) {
		if (!running) return;
		classifiedBlocks.keySet().stream().filter(pos ->
			Math.abs(BlockPos.unpackLongX(pos) - MinecraftClient.getInstance().player.getPos().x) > unloadDistance ||
					Math.abs(BlockPos.unpackLongZ(pos) - MinecraftClient.getInstance().player.getPos().z) > unloadDistance
		).collect(Collectors.toList()).forEach(box ->
			classifiedBlocks.remove(box.longValue())
		);
	}

	public static void classify(BlockPos pos, int id) {
		if (running && SettingsMap.hasValue(id, "outline")) {
			classifiedBlocks.computeIfAbsent(pos.asLong(), key -> new DoubleBoundingBox(pos));
		}
	}

}

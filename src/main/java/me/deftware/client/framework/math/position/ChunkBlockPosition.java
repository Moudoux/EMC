package me.deftware.client.framework.math.position;

import me.deftware.client.framework.math.box.DoubleBoundingBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

/**
 * @author Deftware
 */
public class ChunkBlockPosition extends BlockPosition {

	private final ChunkPos chunk;

	public ChunkBlockPosition(ChunkPos chunk) {
		super(null);
		this.chunk = chunk;
	}

	@Override
	public double getX() {
		return chunk.getStartPos().getX();
	}

	@Override
	public double getY() {
		return chunk.getStartPos().getY();
	}

	@Override
	public double getZ() {
		return chunk.getStartPos().getZ();
	}

	public double getStartX() {
		return chunk.getStartX();
	}

	public double getEndX() {
		return chunk.getEndX();
	}

	public double getStartZ() {
		return chunk.getStartZ();
	}

	public double getEndZ() {
		return chunk.getEndZ();
	}

	@Override
	public BlockPos getMinecraftBlockPos() {
		return chunk.getStartPos();
	}

	public BlockPosition getCenterBlockPos() {
		return DoubleBlockPosition.fromMinecraftBlockPos(getMinecraftBlockPos());
	}

	public DoubleBoundingBox getBoundingBox() {
		return new DoubleBoundingBox(getStartX(), 0, getStartZ(), getEndX(), 255, getEndZ());
	}

}

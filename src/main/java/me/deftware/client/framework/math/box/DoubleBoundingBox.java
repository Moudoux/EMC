package me.deftware.client.framework.math.box;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/**
 * @author Deftware
 */
public class DoubleBoundingBox extends BoundingBox {

	protected Box box;
	public long asLong = 0L;

	public DoubleBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		super(null);
		this.box = new Box(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public DoubleBoundingBox(BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
		asLong = pos.asLong();
	}

	public DoubleBoundingBox(Box box) {
		super(null);
		this.box = box;
	}

	@Override
	public Box getMinecraftBox() {
		return box;
	}

}

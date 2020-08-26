package me.deftware.client.framework.math.box;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

/**
 * @author Deftware
 */
public class DoubleBoundingBox extends BoundingBox {

	protected Box box;
	public long asLong = 0L;

	public DoubleBoundingBox(double x, double y, double z, double x1, double y1, double z1) {
		super(null);
		this.box = new Box(x, y, z, x1, y1, z1);
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

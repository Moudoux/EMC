package me.deftware.client.framework.math.box;

import net.minecraft.util.math.Box;

/**
 * @author Deftware
 */
public class DoubleBoundingBox extends BoundingBox {

	protected Box box;

	public DoubleBoundingBox(double x, double y, double z, double x1, double y1, double z1) {
		super(null);
		this.box = new Box(x, y, z, x1, y1, z1);
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

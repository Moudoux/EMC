package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.util.math.AxisAlignedBB;

public class IAxisAlignedBB {

	private AxisAlignedBB bb;

	public IAxisAlignedBB(double x, double y, double z, double x1, double y1, double z1) {
		this.bb = new AxisAlignedBB(x, y, z, x1, y1, z1);
	}

	public AxisAlignedBB getAABB() {
		return bb;
	}

}

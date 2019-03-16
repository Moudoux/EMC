package me.deftware.client.framework.wrappers.math;


import net.minecraft.util.math.AxisAlignedBB;

public class IAxisAlignedBB {

	private AxisAlignedBB bb;

	public IAxisAlignedBB(double x, double y, double z, double x1, double y1, double z1) {
		bb = new AxisAlignedBB(x, y, z, x1, y1, z1);
	}

	public IAxisAlignedBB(AxisAlignedBB aabb) {
		bb = aabb;
	}

	public AxisAlignedBB getAABB() {
		return bb;
	}

}

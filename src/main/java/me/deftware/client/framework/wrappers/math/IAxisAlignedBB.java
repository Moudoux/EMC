package me.deftware.client.framework.wrappers.math;

import net.minecraft.util.math.Box;

public class IAxisAlignedBB {

    private Box bb;

    public IAxisAlignedBB(Box aabb) {
        bb = aabb;
    }

    public IAxisAlignedBB(double x, double y, double z, double x1, double y1, double z1) {
        bb = new Box(x, y, z, x1, y1, z1);
    }

    public Box getAABB() {
        return bb;
    }

}

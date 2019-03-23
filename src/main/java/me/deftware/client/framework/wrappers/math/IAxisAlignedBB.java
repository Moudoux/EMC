package me.deftware.client.framework.wrappers.math;

import net.minecraft.util.math.BoundingBox;

public class IAxisAlignedBB {

    private BoundingBox bb;

    public IAxisAlignedBB(BoundingBox aabb) {
        bb = aabb;
    }

    public IAxisAlignedBB(double x, double y, double z, double x1, double y1, double z1) {
        bb = new BoundingBox(x, y, z, x1, y1, z1);
    }

    public BoundingBox getAABB() {
        return bb;
    }

}

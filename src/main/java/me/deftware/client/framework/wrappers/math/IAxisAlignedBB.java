package me.deftware.client.framework.wrappers.math;

import net.minecraft.util.math.Box;

public class IAxisAlignedBB {

    public final double x1;
    public final double y1;
    public final double z1;
    public final double x2;
    public final double y2;
    public final double z2;

    private Box bb;

    public IAxisAlignedBB(Box aabb) {
        bb = aabb;
        x1 = bb.x1;
        y1 = bb.y1;
        z1 = bb.z1;
        x2 = bb.x2;
        y2 = bb.y2;
        z2 = bb.z2;
    }

    public IAxisAlignedBB(double x, double y, double z, double x1, double y1, double z1) {
        bb = new Box(x, y, z, x1, y1, z1);
        this.x1 = bb.x1;
        this.y1 = bb.y1;
        this.z1 = bb.z1;
        x2 = bb.x2;
        y2 = bb.y2;
        z2 = bb.z2;
    }

    public Box getAABB() {
        return bb;
    }

    public IAxisAlignedBB offSet(double x, double y, double z) {
        return new IAxisAlignedBB(bb.offset(x, y, z));
    }

}

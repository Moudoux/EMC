package me.deftware.client.framework.wrappers.math;

import net.minecraft.util.math.shapes.VoxelShape;

public class IVoxelShape {

    private final VoxelShape shape;

    public IVoxelShape(VoxelShape shape) {
        this.shape = shape;
    }

    public IAxisAlignedBB getBoundingBox() {
        return new IAxisAlignedBB(shape.getBoundingBox());
    }

    public VoxelShape getVoxelShape() {
        return shape;
    }

}

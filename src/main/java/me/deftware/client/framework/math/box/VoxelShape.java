package me.deftware.client.framework.math.box;

import net.minecraft.util.shape.VoxelShapes;

/**
 * @author Deftware
 */
public class VoxelShape {

	public static final VoxelShape SOLID = new VoxelShape(VoxelShapes.fullCube()),
			EMPTY = new VoxelShape(VoxelShapes.empty());

	private final net.minecraft.util.shape.VoxelShape shape;
	private final VoxelBoundingBox boundingBox;

	public VoxelShape(net.minecraft.util.shape.VoxelShape shape) {
		this.shape = shape;
		this.boundingBox = new VoxelBoundingBox(shape);
	}

	public VoxelBoundingBox getBoundingBox() {
		return boundingBox;
	}

	public net.minecraft.util.shape.VoxelShape getMinecraftVoxelShape() {
		return shape;
	}

	public static VoxelShape makeCuboidShape(double x1, double y1, double z1, double x2, double y2, double z2) {
		return new VoxelShape(net.minecraft.block.Block.createCuboidShape(x1, y1, z1, x2, y2, z2));
	}

}

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

	public static VoxelShape makeCuboidShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		return new VoxelShape(net.minecraft.block.Block.createCuboidShape(minX, minY, minZ, maxX, maxY, maxZ));
	}

}

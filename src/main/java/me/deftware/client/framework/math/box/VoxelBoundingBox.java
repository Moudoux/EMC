package me.deftware.client.framework.math.box;

import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

/**
 * @author Deftware
 */
public class VoxelBoundingBox extends BoundingBox {

	protected final VoxelShape voxelShape;

	public VoxelBoundingBox(VoxelShape voxelShape) {
		super(null);
		this.voxelShape = voxelShape;
	}

	@Override
	public Box getMinecraftBox() {
		return voxelShape.getBoundingBox();
	}

}

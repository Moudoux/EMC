package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.math.IAxisAlignedBB;
import me.deftware.client.framework.wrappers.world.IBlock;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class EventVoxelShape extends Event {

	public VoxelShape shape;
	public boolean modified = false;
	public IBlock block;

	public EventVoxelShape(VoxelShape shape, IBlock block) {
		this.shape = shape;
		this.block = block;
	}

	public IAxisAlignedBB getBoundingBox() {
		return new IAxisAlignedBB(shape.getBoundingBox());
	}

	public void setFullCube() {
		shape = VoxelShapes.fullCube();
	}

	public void setEmpty() {
		shape = VoxelShapes.empty();
	}

	public void setShape(IAxisAlignedBB bb) {
		modified = true;
		shape = VoxelShapes.cuboid(bb.getAABB());
	}

}

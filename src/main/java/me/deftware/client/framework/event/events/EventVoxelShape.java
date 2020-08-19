package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.math.box.DoubleBoundingBox;
import me.deftware.client.framework.world.block.Block;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class EventVoxelShape extends Event {

	public VoxelShape shape;
	public boolean modified = false;
	public Block block;

	public EventVoxelShape(VoxelShape shape, Block block) {
		this.shape = shape;
		this.block = block;
	}

	public BoundingBox getBoundingBox() {
		return new DoubleBoundingBox(shape.getBoundingBox());
	}

	public void setFullCube() {
		shape = VoxelShapes.fullCube();
	}

	public void setEmpty() {
		shape = VoxelShapes.empty();
	}

	public void setShape(BoundingBox bb) {
		modified = true;
		shape = VoxelShapes.cuboid(bb.getMinecraftBox());
	}

}

package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EventBlockhardness extends Event {

	private IBlockState state;
	private EntityPlayer player;
	private World world;
	private BlockPos pos;

	private float hardness;

	public EventBlockhardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos, float hardness) {
		this.state = state;
		this.player = player;
		this.world = worldIn;
		this.pos = pos;
		this.hardness = hardness;
	}

	public boolean canHarvest() {
		return player.canHarvestBlock(state);
	}

	public float getDigSpeed() {
		return player.getDigSpeed(state);
	}

	public float getHardness() {
		return hardness;
	}

	public void setHardness(float hardness) {
		this.hardness = hardness;
	}

}

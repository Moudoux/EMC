package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IBlock;
import net.minecraft.util.BlockRenderLayer;

public class EventBlockLayer extends Event {

	private IBlock block;
	private BlockRenderLayer value;

	public EventBlockLayer(IBlock block, BlockRenderLayer value) {
		this.block = block;
		this.value = value;
	}

	public BlockRenderLayer getValue() {
		return value;
	}

	public void setValue(IBlockRenderLayer value) {
		if (value.equals(IBlockRenderLayer.SOLID)) {
			this.value = BlockRenderLayer.SOLID;
		} else if (value.equals(IBlockRenderLayer.TRANSLUCENT)) {
			this.value = BlockRenderLayer.TRANSLUCENT;
		}
	}

	public IBlock getBlock() {
		return block;
	}

	public static enum IBlockRenderLayer {
		SOLID, TRANSLUCENT
	}

}

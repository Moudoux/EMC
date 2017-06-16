package me.deftware.client.framework.Event.Events;

import java.util.List;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IBlock;
import net.minecraft.client.renderer.block.model.BakedQuad;

public class EventRenderModel extends Event {

	private IBlock block;
	private List<BakedQuad> list;
	private boolean override = false;

	public EventRenderModel(IBlock block) {
		this.block = block;
		this.list = null;
	}

	public EventRenderModel(IBlock block, List<BakedQuad> list) {
		this.block = block;
		this.list = list;
	}

	public IBlock getBlock() {
		return block;
	}

	public boolean isQuadListNull() {
		return list == null;
	}

	public int getQuadListSize() {
		return list.size();
	}

	public boolean isQuadListEmpty() {
		return list.isEmpty();
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

}

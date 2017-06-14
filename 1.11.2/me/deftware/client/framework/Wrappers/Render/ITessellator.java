package me.deftware.client.framework.Wrappers.Render;

import net.minecraft.client.renderer.Tessellator;

public class ITessellator {

	private Tessellator tessellator;

	public ITessellator() {
		this.tessellator = Tessellator.getInstance();
	}

	public Tessellator getTessellator() {
		return tessellator;
	}

	public void draw() {
		this.tessellator.draw();
	}

}

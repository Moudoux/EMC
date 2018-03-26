package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.renderer.Tessellator;

public class ITessellator {

	private Tessellator tessellator;

	public ITessellator() {
		tessellator = Tessellator.getInstance();
	}

	public Tessellator getTessellator() {
		return tessellator;
	}

	public void draw() {
		tessellator.draw();
	}

}

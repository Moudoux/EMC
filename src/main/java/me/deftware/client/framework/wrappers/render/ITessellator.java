package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.render.Tessellator;

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

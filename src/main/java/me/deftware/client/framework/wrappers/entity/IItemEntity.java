package me.deftware.client.framework.wrappers.entity;

import net.minecraft.entity.Entity;

public class IItemEntity {

    private Entity item;

    public IItemEntity(Entity item) {
        this.item = item;
    }

    public double getPosX() {
        return item.getX();
    }

    public double getPosY() {
        return item.getY();
    }

    public double getPosZ() {
        return item.getZ();
    }

    public double getLastTickPosX() {
        return item.prevRenderX;
    }

    public double getLastTickPosY() {
        return item.prevRenderY;
    }

    public double getLastTickPosZ() {
        return item.prevRenderZ;
    }

    public float getHeight() {
        return item.getHeight();
    }

    public Entity getItem() {
        return item;
    }

    public void setGlowing(boolean state) {
        item.setGlowing(state);
    }

}
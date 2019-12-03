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
        return item.lastRenderX;
    }

    public double getLastTickPosY() {
        return item.lastRenderY;
    }

    public double getLastTickPosZ() {
        return item.lastRenderZ;
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
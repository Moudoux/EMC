package me.deftware.client.framework.wrappers.entity;

import me.deftware.client.framework.wrappers.world.IWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;

public class IMob {

    private Entity mob;

    public IMob(Entity mob) {
        this.mob = mob;
    }

    public double getPosX() {
        return mob.x;
    }

    public double getPosY() {
        return mob.y;
    }

    public double getPosZ() {
        return mob.z;
    }

    public double getLastTickPosX() {
        return mob.prevRenderX;
    }

    public double getLastTickPosY() {
        return mob.prevRenderY;
    }

    public double getLastTickPosZ() {
        return mob.prevRenderZ;
    }

    public float getHeight() {
        return mob.getHeight();
    }

    public Entity getMob() {
        return mob;
    }

    public void setGlowing(boolean state) {
        mob.setGlowing(state);
    }

    public IWorld getWorld() {
        return new IWorld(mob.world);
    }

    public String getName() {
        return ((MobEntity) mob).getType().getName().asFormattedString();
    }

}

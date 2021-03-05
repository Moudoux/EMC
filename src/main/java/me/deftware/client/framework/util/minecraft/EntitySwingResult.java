package me.deftware.client.framework.util.minecraft;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.util.hitresult.CrosshairResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class EntitySwingResult extends CrosshairResult {

    public EntitySwingResult(HitResult hitResult) {
        super(hitResult);
    }

    @Override
    public EntityHitResult getMinecraftHitResult() {
        return (EntityHitResult) hitResult;
    }

    public Entity getEntity() {
        return Entity.newInstance(getMinecraftHitResult().getEntity());
    }

}

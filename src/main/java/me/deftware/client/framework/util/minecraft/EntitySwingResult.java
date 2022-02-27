package me.deftware.client.framework.util.minecraft;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.util.hitresult.CrosshairResult;
import me.deftware.client.framework.world.ClientWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class EntitySwingResult extends CrosshairResult {

    public EntitySwingResult(HitResult hitResult) {
        super(hitResult);
    }

    public EntitySwingResult(Entity entity) {
        super(new EntityHitResult(entity.getMinecraftEntity()));
    }

    @Override
    public EntityHitResult getMinecraftHitResult() {
        return (EntityHitResult) hitResult;
    }

    public Entity getEntity() {
        return ClientWorld.getClientWorld().getEntityByReference(getMinecraftHitResult().getEntity());
    }

}

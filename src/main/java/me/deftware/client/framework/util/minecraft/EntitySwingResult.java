package me.deftware.client.framework.util.minecraft;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.util.hitresult.CrosshairResult;
import me.deftware.client.framework.world.World;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.stream.Collectors;

public class EntitySwingResult extends CrosshairResult {

    public EntitySwingResult(HitResult hitResult) {
        super(hitResult);
    }

    @Override
    public EntityHitResult getMinecraftHitResult() {
        return (EntityHitResult) hitResult;
    }

    public Entity getEntity() {
        // Find entity in the world
        for (Entity entity : World.getLoadedEntities().collect(Collectors.toList())) {
            if (entity.getMinecraftEntity() == getMinecraftHitResult().getEntity())
                return entity;
        }
        return null;
    }

}
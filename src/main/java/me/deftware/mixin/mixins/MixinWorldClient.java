package me.deftware.mixin.mixins;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mixin(ClientWorld.class)
public class MixinWorldClient implements IMixinWorldClient {

    @Shadow
    @Final
    private Int2ObjectMap<Entity> regularEntities;

    @Unique
    private final HashMap<Integer, IEntity> entities = new HashMap<>();

    @ModifyVariable(method = "randomBlockDisplayTick(IIIILjava/util/Random;ZLnet/minecraft/util/math/BlockPos$Mutable;)V", at = @At("HEAD"))
    public boolean randomBlockDisplayTick(boolean p_animateTick_6_) {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "render_barrier_blocks", false)) {
            return true;
        }
        return p_animateTick_6_;
    }

    @Inject(method = "addEntityPrivate", at = @At("TAIL"))
    private void addEntityPrivate(int id, Entity entity, CallbackInfo ci) {
        entities.put(id, IEntity.fromEntity(entity));
    }

    @Inject(method = "removeEntity", at = @At("TAIL"))
    public void removeEntity(int entityId, CallbackInfo ci) {
        entities.remove(entityId);
    }

    @Override
    public HashMap<Integer, IEntity> getIEntities() {
        return entities;
    }

    @Override
    public Int2ObjectMap<Entity> getLoadedEntities() {
        return regularEntities;
    }

}

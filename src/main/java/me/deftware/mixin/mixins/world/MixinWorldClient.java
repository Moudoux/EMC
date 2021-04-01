package me.deftware.mixin.mixins.world;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.types.EntityPlayer;
import me.deftware.client.framework.event.events.EventEntityUpdated;
import me.deftware.client.framework.event.events.EventWorldLoad;
import me.deftware.client.framework.global.GameKeys;
import me.deftware.client.framework.global.GameMap;
import me.deftware.client.framework.world.classifier.BlockClassifier;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public class MixinWorldClient implements IMixinWorldClient {

    @Unique
    private final Int2ObjectMap<Entity> entities = new Int2ObjectOpenHashMap<>();

    /*@ModifyVariable(method = "randomBlockDisplayTick(IIIILjava/util/Random;ZLnet/minecraft/util/math/BlockPos$Mutable;)V", at = @At("HEAD"))
    public boolean randomBlockDisplayTick(boolean spawnBarrierBlocks) {
        if (GameMap.INSTANCE.get(GameKeys.FULL_BARRIER_TEXTURE, false))
            return true;
        return spawnBarrierBlocks;
    }*/

    @Inject(method = "<init>(Lnet/minecraft/client/network/ClientPlayNetworkHandler;Lnet/minecraft/client/world/ClientWorld$Properties;Lnet/minecraft/util/registry/RegistryKey;Lnet/minecraft/world/dimension/DimensionType;ILjava/util/function/Supplier;Lnet/minecraft/client/render/WorldRenderer;ZJ)V", at = @At("TAIL"))
    private void onConstructed(ClientPlayNetworkHandler clientPlayNetworkHandler, ClientWorld.Properties properties, RegistryKey<World> registryKey, DimensionType dimensionType, int i, Supplier<Profiler> supplier, WorldRenderer worldRenderer, boolean debugWorld, long seed, CallbackInfo ci) {
        new EventWorldLoad().broadcast();
        BlockClassifier.getClassifiers().forEach(blockClassifier -> blockClassifier.getClassifiedBlocks().clear());
    }

    @Inject(method = "addEntityPrivate", at = @At("TAIL"))
    private void addEntityPrivate(int id, net.minecraft.entity.Entity entity, CallbackInfo ci) {
        Entity e = entity instanceof PlayerEntity ?
                new EntityPlayer((PlayerEntity) entity) : Entity.newInstance(entity);
        entities.put(id, e);
        new EventEntityUpdated(EventEntityUpdated.Change.Added, e).broadcast();
    }

    @Inject(method = "removeEntity", at = @At("TAIL"))
    public void removeEntity(int entityId, net.minecraft.entity.Entity.RemovalReason reason, CallbackInfo ci) {
        new EventEntityUpdated(EventEntityUpdated.Change.Removed, entities.remove(entityId)).broadcast();
    }

    @Override
    @Unique
    public Int2ObjectMap<Entity> getLoadedEntitiesAccessor() {
        return entities;
    }

}

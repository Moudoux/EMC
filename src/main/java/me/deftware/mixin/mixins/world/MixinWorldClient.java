package me.deftware.mixin.mixins.world;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.event.events.EventEntityUpdated;
import me.deftware.client.framework.event.events.EventWorldLoad;
import me.deftware.client.framework.global.GameKeys;
import me.deftware.client.framework.global.GameMap;
import me.deftware.client.framework.world.classifier.BlockClassifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
@Mixin(ClientWorld.class)
public abstract class MixinWorldClient extends MixinWorld implements me.deftware.client.framework.world.ClientWorld {

    @Shadow
    public abstract void addParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ);

    @Unique
    private final Int2ObjectMap<Entity> entities = new Int2ObjectOpenHashMap<>();

    @SuppressWarnings("ConstantConditions")
    @ModifyVariable(method = "randomBlockDisplayTick", at = @At("TAIL"))
    private BlockPos.Mutable onGetBlockParticle(BlockPos.Mutable pos) {
        boolean barrier = GameMap.INSTANCE.get(GameKeys.FULL_BARRIER_TEXTURE, false),
                light = GameMap.INSTANCE.get(GameKeys.FULL_LIGHT_TEXTURE, false);
        if (
                barrier || light
        ) {
            BlockState blockState = ((World) (Object) this).getBlockState(pos);
            Block block = blockState.getBlock();
            if (barrier && block == Blocks.BARRIER)
                this.addParticle(ParticleTypes.BARRIER, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            if (light && block == Blocks.LIGHT)
                this.addParticle(ParticleTypes.LIGHT, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
        }
        return pos;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructed(ClientPlayNetworkHandler clientPlayNetworkHandler, ClientWorld.Properties properties, RegistryKey<World> registryKey, DimensionType dimensionType, int i, int j, Supplier<Profiler> supplier, WorldRenderer worldRenderer, boolean bl, long l, CallbackInfo ci) {
        new EventWorldLoad().broadcast();
        BlockClassifier.getClassifiers().forEach(blockClassifier -> blockClassifier.getClassifiedBlocks().clear());
    }

    @Inject(method = "addEntityPrivate", at = @At("TAIL"))
    private void addEntityPrivate(int id, net.minecraft.entity.Entity entity, CallbackInfo ci) {
        Entity e = Entity.newInstance(entity);
        entities.put(id, e);
        new EventEntityUpdated(EventEntityUpdated.Change.Added, e).broadcast();
    }

    @Inject(method = "removeEntity", at = @At("TAIL"))
    public void removeEntity(int entityId, net.minecraft.entity.Entity.RemovalReason reason, CallbackInfo ci) {
        new EventEntityUpdated(EventEntityUpdated.Change.Removed, entities.remove(entityId)).broadcast();
    }


    @Override
    public Stream<Entity> getLoadedEntities() {
        return entities.values().stream();
    }

    @Override
    public Entity _getEntityById(int id) {
        return entities.get(id);
    }

    @Override
    public void _addEntity(int id, Entity entity) {
        ((ClientWorld) (Object) this).addEntity(id, entity.getMinecraftEntity());
    }

    @Override
    public void _removeEntity(int id) {
        ((ClientWorld) (Object) this).removeEntity(id, net.minecraft.entity.Entity.RemovalReason.DISCARDED);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Entity> @Nullable T getEntityByReference(net.minecraft.entity.Entity reference) {
        if (reference != null) {
            return (T) entities.get(reference.getId());
        }
        return null;
    }

}

package me.deftware.client.framework.wrappers.entity;

import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.math.IAxisAlignedBB;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.client.framework.wrappers.world.IChunkPos;
import me.deftware.mixin.imp.IMixinAbstractClientPlayer;
import me.deftware.mixin.imp.IMixinNetworkPlayerInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import java.math.BigDecimal;

@SuppressWarnings("All")
public class IEntity {

    private Entity entity;

    public IEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public IAxisAlignedBB getBoundingBox() {
        return new IAxisAlignedBB(entity.getBoundingBox());
    }

    public IBlockPos getBlockPos() {
        return new IBlockPos(entity.getBlockPos());
    }

    public boolean isOnGround() {
        return entity.isOnGround();
    }

    public float getStepHeight() {
        return entity.stepHeight;
    }

    public void setStepHeight(float height) {
        entity.stepHeight = height;
    }

    public float getDistanceToPlayer() {
        return entity.distanceTo(MinecraftClient.getInstance().player);
    }

    public float getDistanceToEntity() {
        return MinecraftClient.getInstance().player.distanceTo(entity);
    }

    public String getName() {
        return entity instanceof PlayerEntity ? ((PlayerEntity) entity).getGameProfile().getName() : null;
    }

    public String getFormattedDisplayName() {
        return ChatProcessor.getStringFromText(entity.getDisplayName());
    }

    public int getTicksExisted() {
        return entity.age;
    }

    public boolean isDead() {
        return !entity.isAlive();
    }

    @Deprecated
    public boolean isMod() {
        return entity instanceof MobEntity || entity instanceof LivingEntity;
    }

    public boolean isMob() {
        return entity instanceof MobEntity || entity instanceof LivingEntity;
    }

    public String getEntityTypeName() {
        return ChatProcessor.getStringFromText(entity.getType().getName());
    }

    public boolean isPlayer() {
        return entity instanceof PlayerEntity;
    }

    public boolean isItem() {
        return entity instanceof ItemEntity;
    }

    public IItemEntity getIItemEntity() {
        return new IItemEntity(entity);
    }

    public IMob getIMob() {
        return new IMob(entity);
    }

    public IPlayer getIPlayer() {
        return new IPlayer((PlayerEntity) entity);
    }

    public float getHealth() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity) entity).getHealth();
        }
        return 0;
    }

    public int getResponseTime() {
        return MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(entity.getUuid()).getLatency();
    }

    public float getRotationYaw(boolean fullCircleCalc) {
        float currentYaw = entity.yaw % 360;

        if (fullCircleCalc) {
            currentYaw = (currentYaw + 360) % 360;
        } else if (currentYaw > 180) {
            currentYaw -= 360;
        }

        return currentYaw;
    }

    public float getRotationYaw() {
        return getRotationYaw(false);
    }

    public float getRotationPitch() {
        return entity.pitch;
    }

    public IDirection getDirection() {
        return IDirection.getFrom(entity.yaw);
    }

    public double getLastTickPosX() {
        return entity.lastRenderX;
    }

    public double getLastTickPosY() {
        return entity.lastRenderY;
    }

    public double getLastTickPosZ() {
        return entity.lastRenderZ;
    }

    public float getNametagSize() {
        return getDistanceToEntity() / 2.5F <= 1.5F ? 2.0F
                : getDistanceToEntity() / 2.5F;
    }

    public float getHeight() {
        return entity.getHeight();
    }

    public float getMaxHealth() {
        if (entity instanceof LivingEntity) {
            return ((LivingEntity) entity).getMaxHealth();
        }
        return 0;
    }

    /**
     * Typically Mojangs AI has positions up to 0.999
     * <p>
     * However, considering the randomness of normal players their positions are far more exacted
     *
     * @param positionValue The X, Y, or Z position of an Entity
     * @param invalidDivider The point at which an entity is considered non ai
     * @return Whether that position is that of an AI or not
     */
    public boolean isNonAIPositionValue(double positionValue, int invalidDivider) {
        return (BigDecimal.valueOf(positionValue).scale() > invalidDivider);
    }

    public int getEntityID() {
        return entity.getEntityId();
    }

    public INetworkPlayerInfo getPlayerNetworkInfo() {
        if (entity instanceof AbstractClientPlayerEntity) {
            return new INetworkPlayerInfo(((IMixinAbstractClientPlayer) (AbstractClientPlayerEntity) entity).getPlayerNetworkInfo());
        }
        return null;
    }

    public void reloadSkin() {
        if (entity instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractEntity = (AbstractClientPlayerEntity) entity;
            if (abstractEntity.canRenderCapeTexture()) {
                ((IMixinNetworkPlayerInfo) ((IMixinAbstractClientPlayer) abstractEntity).getPlayerNetworkInfo()).reloadTextures();
            }
        }
    }

    public boolean isPlayerOwned() {
        if (entity instanceof WolfEntity) {
            return ((WolfEntity) entity).isOwner(MinecraftClient.getInstance().player);
        }
        return false;
    }

    public boolean isSleeping() {
        return entity instanceof PlayerEntity && ((LivingEntity) entity).isSleeping();
    }

    public boolean isInvisible() {
        return entity.isInvisible();
    }

    public boolean isInvisibleToPlayer() {
        return entity.isInvisibleTo(MinecraftClient.getInstance().player);
    }

    public boolean canBeSeen() {
        return MinecraftClient.getInstance().player.canSee(entity);
    }

    public boolean isSelf() {
        return entity == MinecraftClient.getInstance().player;
    }

    public double getPosX() {
        return entity.getX();
    }

    public double getPosY() {
        return entity.getY();
    }

    public double getPosZ() {
        return entity.getZ();
    }

    public double getPrevPosX() {
        return entity.prevX;
    }

    public double getPrevPosY() {
        return entity.prevY;
    }

    public double getPrevPosZ() {
        return entity.prevZ;
    }

    public double getEyeHeight() {
        return entity.getEyeHeight(entity.getPose());
    }

    public double getEyeHeight(EntityPose pose) {
        return entity.getEyeHeight(pose);
    }

    public boolean isWithinChunk(IChunkPos chunkPos) {
        return getPosX() >= chunkPos.getStartX() && getPosX() <= chunkPos.getEndX() && getPosZ() >= chunkPos.getStartZ() && getPosZ() <= chunkPos.getEndZ();
    }

    public boolean isHostile() {
        if (entity instanceof HostileEntity) {
            return true;
        } else if (entity instanceof ChickenEntity) {
            return ((ChickenEntity) entity).jockey;
        }
        return false;
    }

    public boolean instanceOf(EntityType e) {
        // Generic types and players
        if (e.equals(EntityType.ENTITY_PLAYER_SP)) {
            return entity instanceof ClientPlayerEntity;
        } else if (e.equals(EntityType.EntityOtherPlayerMP)) {
            return entity instanceof OtherClientPlayerEntity;
        } else if (e.equals(EntityType.ENTITY_PLAYER)) {
            return entity instanceof PlayerEntity;
        } else if (e.equals(EntityType.ENTITY_LIVING_BASE)) {
            return entity instanceof LivingEntity;
        } else if (e.equals(EntityType.ENTITY_LIVING)) {
            return entity instanceof LivingEntity;
        } else if (e.equals(EntityType.ENTITY_ITEM)) {
            return entity instanceof ItemEntity;
        } else if (e.equals(EntityType.ENTITY_PROJECTILE)) {
            return entity instanceof ProjectileEntity;
        } else if (e.equals(EntityType.Entity_Ageable)) {
            return entity instanceof PassiveEntity;
        } else if (e.equals(EntityType.EntityAmbientCreature)) {
            return entity instanceof AmbientEntity;
        } else if (e.equals(EntityType.EntityWaterMob)) {
            return entity instanceof WaterCreatureEntity;
        } else if (e.equals(EntityType.EntityMob)) {
            return entity instanceof MobEntity;
        } else if (e.equals(EntityType.EntityAnimal)) {
            return entity instanceof AnimalEntity;
        }
        // Passives
        else if (e.equals(EntityType.ENTITY_BAT)) {
            return entity instanceof BatEntity;
        } else if (e.equals(EntityType.ENTITY_CHICKEN)) {
            return entity instanceof ChickenEntity;
        } else if (e.equals(EntityType.ENTITY_COW)) {
            return entity instanceof CowEntity;
        } else if (e.equals(EntityType.ENTITY_FISH)) {
            return entity instanceof FishEntity;
        } else if (e.equals(EntityType.ENTITY_MOOSHROOM)) {
            return entity instanceof MooshroomEntity;
        } else if (e.equals(EntityType.ENTITY_OCELOT)) {
            return entity instanceof OcelotEntity;
        } else if (e.equals(EntityType.ENTITY_PIG)) {
            return entity instanceof PigEntity;
        } else if (e.equals(EntityType.ENTITY_POLAR_BEAR)) {
            return entity instanceof PolarBearEntity;
        } else if (e.equals(EntityType.ENTITY_RABBIT)) {
            return entity instanceof RabbitEntity;
        } else if (e.equals(EntityType.ENTITY_SHEEP)) {
            return entity instanceof SheepEntity;
        } else if (e.equals(EntityType.ENTITY_SQUID)) {
            return entity instanceof SquidEntity;
        } else if (e.equals(EntityType.ENTITY_TURTLE)) {
            return entity instanceof TurtleEntity;
        } else if (e.equals(EntityType.ENTITY_VILLAGER)) {
            return entity instanceof VillagerEntity;
        } else if (e.equals(EntityType.ENTITY_DOLPHIN)) {
            return entity instanceof DolphinEntity;
        } else if (e.equals(EntityType.ENTITY_DONKEY)) {
            return entity instanceof DonkeyEntity;
        } else if (e.equals(EntityType.ENTITY_MULE)) {
            return entity instanceof MuleEntity;
        } else if (e.equals(EntityType.ENTITY_HORSE)) {
            return entity instanceof HorseEntity;
        } else if (e.equals(EntityType.ENTITY_PARROT)) {
            return entity instanceof ParrotEntity;
        }
        // Hostiles
        else if (e.equals(EntityType.EntitySlime) || e.equals(EntityType.ENTITY_SLIME)) {
            return entity instanceof SlimeEntity;
        } else if (e.equals(EntityType.EntityFlying)) {
            return entity instanceof FlyingEntity;
        } else if (e.equals(EntityType.EntityGolem)) {
            return entity instanceof GolemEntity;
        } else if (e.equals(EntityType.ENTITY_SPIDER)) {
            return entity instanceof SpiderEntity;
        } else if (e.equals(EntityType.ENTITY_ZOMBIE_PIGMAN)) {
            return entity instanceof ZombifiedPiglinEntity;
        } else if (e.equals(EntityType.ENTITY_ENDERMAN)) {
            return entity instanceof EndermanEntity;
        } else if (e.equals(EntityType.ENTITY_WITHER_SKELETON)) {
            return entity instanceof WitherSkeletonEntity;
        } else if (e.equals(EntityType.ENTITY_WITHER)) {
            return entity instanceof WitherEntity;
        } else if (e.equals(EntityType.ENTITY_DRAGON)) {
            return entity instanceof EnderDragonEntity;
        } else if (e.equals(EntityType.ENTITY_PHANTOM)) {
            return entity instanceof PhantomEntity;
        } else if (e.equals(EntityType.ENTITY_DROWNED)) {
            return entity instanceof DrownedEntity;
        } else if (e.equals(EntityType.ENTITY_EVOKER)) {
            return entity instanceof EvokerEntity;
        } else if (e.equals(EntityType.ENTITY_STRAY)) {
            return entity instanceof StrayEntity;
        } else if (e.equals(EntityType.ENTITY_ELDER_GUARDIAN)) {
            return entity instanceof ElderGuardianEntity;
        } else if (e.equals(EntityType.ENTITY_CREEPER)) {
            return entity instanceof CreeperEntity;
        } else if (e.equals(EntityType.ENTITY_VINDICATOR)) {
            return entity instanceof VindicatorEntity;
        } else if (e.equals(EntityType.ENTITY_ILLUSIONER)) {
            return entity instanceof IllusionerEntity;
        } else if (e.equals(EntityType.ENTITY_HUSK)) {
            return entity instanceof HuskEntity;
        } else if (e.equals(EntityType.ENTITY_ZOMBIE)) {
            return entity instanceof ZombieEntity;
        } else if (e.equals(EntityType.ENTITY_SKELETON)) {
            return entity instanceof SkeletonEntity;
        } else if (e.equals(EntityType.ENTITY_SHULKER)) {
            return entity instanceof ShulkerEntity;
        } else if (e.equals(EntityType.ENTITY_GUARDIAN)) {
            return entity instanceof GuardianEntity;
        } else if (e.equals(EntityType.ENTITY_VEX)) {
            return entity instanceof VexEntity;
        } else if (e.equals(EntityType.ENTITY_SILVERFISH)) {
            return entity instanceof SilverfishEntity;
        } else if (e.equals(EntityType.ENTITY_WITCH)) {
            return entity instanceof WitchEntity;
        } else if (e.equals(EntityType.ENTITY_GIANT)) {
            return entity instanceof GiantEntity;
        } else if (e.equals(EntityType.ENTITY_BLAZE)) {
            return entity instanceof BlazeEntity;
        } else if (e.equals(EntityType.ENTITY_ENDERMITE)) {
            return entity instanceof EndermiteEntity;
        } else if (e.equals(EntityType.ENTITY_GHAST)) {
            return entity instanceof GhastEntity;
        } else if (e.equals(EntityType.ENTITY_MAGMA_CUBE)) {
            return entity instanceof MagmaCubeEntity;
        } else if (e.equals(EntityType.ENTITY_CAVE_SPIDER)) {
            return entity instanceof CaveSpiderEntity;
        }
        // Neutrals
        else if (e.equals(EntityType.ENTITY_WOLF)) {
            return entity instanceof WolfEntity;
        } else if (e.equals(EntityType.ENTITY_LLAMA)) {
            return entity instanceof LlamaEntity;
        } else if (e.equals(EntityType.ENTITY_IRON_GOLEM)) {
            return entity instanceof IronGolemEntity;
        } else if (e.equals(EntityType.ENTITY_SNOW_GOLEM)) {
            return entity instanceof SnowGolemEntity;
        } else if (e.equals(EntityType.ENTITY_PUFFERFISH)) {
            return entity instanceof PufferfishEntity;
        }
        return false;
    }

    public enum EntityType {
        ENTITY_PLAYER_SP, EntityOtherPlayerMP, ENTITY_PLAYER, EntityAnimal, EntitySlime, EntityGolem, EntityFlying, EntityMob, EntityWaterMob,
        ENTITY_LIVING_BASE, ENTITY_LIVING, Entity_Ageable, EntityAmbientCreature, ENTITY_ITEM, ENTITY_PROJECTILE,
        /*
         * Passive mobs
         */
        ENTITY_BAT, ENTITY_CHICKEN, ENTITY_COW, ENTITY_FISH, ENTITY_MOOSHROOM, ENTITY_OCELOT, ENTITY_PIG, ENTITY_POLAR_BEAR,
        ENTITY_RABBIT, ENTITY_SHEEP, ENTITY_SQUID, ENTITY_TURTLE, ENTITY_VILLAGER, ENTITY_DOLPHIN, ENTITY_DONKEY, ENTITY_HORSE,
        ENTITY_MULE, ENTITY_PARROT,
        /*
         * Hostile mobs
         */
        ENTITY_ENDERMAN, ENTITY_ZOMBIE_PIGMAN, ENTITY_SPIDER, ENTITY_WITHER_SKELETON, ENTITY_WITHER, ENTITY_DRAGON, ENTITY_PHANTOM, ENTITY_DROWNED,
        ENTITY_EVOKER, ENTITY_STRAY, ENTITY_ELDER_GUARDIAN, ENTITY_CREEPER, ENTITY_VINDICATOR, ENTITY_ILLUSIONER, ENTITY_ZOMBIE, ENTITY_HUSK,
        ENTITY_SKELETON, ENTITY_SHULKER, ENTITY_SLIME, ENTITY_GUARDIAN, ENTITY_VEX, ENTITY_SILVERFISH, ENTITY_WITCH, ENTITY_GIANT, ENTITY_BLAZE,
        ENTITY_ENDERMITE, ENTITY_GHAST, ENTITY_MAGMA_CUBE, ENTITY_CAVE_SPIDER,
        /*
         * Neutral mobs
         */
        ENTITY_WOLF, ENTITY_LLAMA, ENTITY_IRON_GOLEM, ENTITY_SNOW_GOLEM, ENTITY_PUFFERFISH
    }

}

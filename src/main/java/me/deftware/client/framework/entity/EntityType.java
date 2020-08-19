package me.deftware.client.framework.entity;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;

/**
 * @author Deftware
 */
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
	ENTITY_WOLF, ENTITY_LLAMA, ENTITY_IRON_GOLEM, ENTITY_SNOW_GOLEM, ENTITY_PUFFERFISH;

	public static boolean isInstance(Entity emcEntity, EntityType type) {
		net.minecraft.entity.Entity entity = emcEntity.getMinecraftEntity();
		// Generic types and players
		if (type.equals(EntityType.ENTITY_PLAYER_SP)) {
			return entity instanceof ClientPlayerEntity;
		} else if (type.equals(EntityType.EntityOtherPlayerMP)) {
			return entity instanceof OtherClientPlayerEntity;
		} else if (type.equals(EntityType.ENTITY_PLAYER)) {
			return entity instanceof PlayerEntity;
		} else if (type.equals(EntityType.ENTITY_LIVING_BASE)) {
			return entity instanceof LivingEntity;
		} else if (type.equals(EntityType.ENTITY_LIVING)) {
			return entity instanceof LivingEntity;
		} else if (type.equals(EntityType.ENTITY_ITEM)) {
			return entity instanceof ItemEntity;
		} else if (type.equals(EntityType.ENTITY_PROJECTILE)) {
			return entity instanceof ProjectileEntity;
		} else if (type.equals(EntityType.Entity_Ageable)) {
			return entity instanceof PassiveEntity;
		} else if (type.equals(EntityType.EntityAmbientCreature)) {
			return entity instanceof AmbientEntity;
		} else if (type.equals(EntityType.EntityWaterMob)) {
			return entity instanceof WaterCreatureEntity;
		} else if (type.equals(EntityType.EntityMob)) {
			return entity instanceof MobEntity;
		} else if (type.equals(EntityType.EntityAnimal)) {
			return entity instanceof AnimalEntity;
		}
		// Passives
		else if (type.equals(EntityType.ENTITY_BAT)) {
			return entity instanceof BatEntity;
		} else if (type.equals(EntityType.ENTITY_CHICKEN)) {
			return entity instanceof ChickenEntity;
		} else if (type.equals(EntityType.ENTITY_COW)) {
			return entity instanceof CowEntity;
		} else if (type.equals(EntityType.ENTITY_FISH)) {
			return entity instanceof FishEntity;
		} else if (type.equals(EntityType.ENTITY_MOOSHROOM)) {
			return entity instanceof MooshroomEntity;
		} else if (type.equals(EntityType.ENTITY_OCELOT)) {
			return entity instanceof OcelotEntity;
		} else if (type.equals(EntityType.ENTITY_PIG)) {
			return entity instanceof PigEntity;
		} else if (type.equals(EntityType.ENTITY_POLAR_BEAR)) {
			return entity instanceof PolarBearEntity;
		} else if (type.equals(EntityType.ENTITY_RABBIT)) {
			return entity instanceof RabbitEntity;
		} else if (type.equals(EntityType.ENTITY_SHEEP)) {
			return entity instanceof SheepEntity;
		} else if (type.equals(EntityType.ENTITY_SQUID)) {
			return entity instanceof SquidEntity;
		} else if (type.equals(EntityType.ENTITY_TURTLE)) {
			return entity instanceof TurtleEntity;
		} else if (type.equals(EntityType.ENTITY_VILLAGER)) {
			return entity instanceof VillagerEntity;
		} else if (type.equals(EntityType.ENTITY_DOLPHIN)) {
			return entity instanceof DolphinEntity;
		} else if (type.equals(EntityType.ENTITY_DONKEY)) {
			return entity instanceof DonkeyEntity;
		} else if (type.equals(EntityType.ENTITY_MULE)) {
			return entity instanceof MuleEntity;
		} else if (type.equals(EntityType.ENTITY_HORSE)) {
			return entity instanceof HorseEntity;
		} else if (type.equals(EntityType.ENTITY_PARROT)) {
			return entity instanceof ParrotEntity;
		}
		// Hostiles
		else if (type.equals(EntityType.EntitySlime) || type.equals(EntityType.ENTITY_SLIME)) {
			return entity instanceof SlimeEntity;
		} else if (type.equals(EntityType.EntityFlying)) {
			return entity instanceof FlyingEntity;
		} else if (type.equals(EntityType.EntityGolem)) {
			return entity instanceof GolemEntity;
		} else if (type.equals(EntityType.ENTITY_SPIDER)) {
			return entity instanceof SpiderEntity;
		} else if (type.equals(EntityType.ENTITY_ZOMBIE_PIGMAN)) {
			return entity instanceof ZombifiedPiglinEntity;
		} else if (type.equals(EntityType.ENTITY_ENDERMAN)) {
			return entity instanceof EndermanEntity;
		} else if (type.equals(EntityType.ENTITY_WITHER_SKELETON)) {
			return entity instanceof WitherSkeletonEntity;
		} else if (type.equals(EntityType.ENTITY_WITHER)) {
			return entity instanceof WitherEntity;
		} else if (type.equals(EntityType.ENTITY_DRAGON)) {
			return entity instanceof EnderDragonEntity;
		} else if (type.equals(EntityType.ENTITY_PHANTOM)) {
			return entity instanceof PhantomEntity;
		} else if (type.equals(EntityType.ENTITY_DROWNED)) {
			return entity instanceof DrownedEntity;
		} else if (type.equals(EntityType.ENTITY_EVOKER)) {
			return entity instanceof EvokerEntity;
		} else if (type.equals(EntityType.ENTITY_STRAY)) {
			return entity instanceof StrayEntity;
		} else if (type.equals(EntityType.ENTITY_ELDER_GUARDIAN)) {
			return entity instanceof ElderGuardianEntity;
		} else if (type.equals(EntityType.ENTITY_CREEPER)) {
			return entity instanceof CreeperEntity;
		} else if (type.equals(EntityType.ENTITY_VINDICATOR)) {
			return entity instanceof VindicatorEntity;
		} else if (type.equals(EntityType.ENTITY_ILLUSIONER)) {
			return entity instanceof IllusionerEntity;
		} else if (type.equals(EntityType.ENTITY_HUSK)) {
			return entity instanceof HuskEntity;
		} else if (type.equals(EntityType.ENTITY_ZOMBIE)) {
			return entity instanceof ZombieEntity;
		} else if (type.equals(EntityType.ENTITY_SKELETON)) {
			return entity instanceof SkeletonEntity;
		} else if (type.equals(EntityType.ENTITY_SHULKER)) {
			return entity instanceof ShulkerEntity;
		} else if (type.equals(EntityType.ENTITY_GUARDIAN)) {
			return entity instanceof GuardianEntity;
		} else if (type.equals(EntityType.ENTITY_VEX)) {
			return entity instanceof VexEntity;
		} else if (type.equals(EntityType.ENTITY_SILVERFISH)) {
			return entity instanceof SilverfishEntity;
		} else if (type.equals(EntityType.ENTITY_WITCH)) {
			return entity instanceof WitchEntity;
		} else if (type.equals(EntityType.ENTITY_GIANT)) {
			return entity instanceof GiantEntity;
		} else if (type.equals(EntityType.ENTITY_BLAZE)) {
			return entity instanceof BlazeEntity;
		} else if (type.equals(EntityType.ENTITY_ENDERMITE)) {
			return entity instanceof EndermiteEntity;
		} else if (type.equals(EntityType.ENTITY_GHAST)) {
			return entity instanceof GhastEntity;
		} else if (type.equals(EntityType.ENTITY_MAGMA_CUBE)) {
			return entity instanceof MagmaCubeEntity;
		} else if (type.equals(EntityType.ENTITY_CAVE_SPIDER)) {
			return entity instanceof CaveSpiderEntity;
		}
		// Neutrals
		else if (type.equals(EntityType.ENTITY_WOLF)) {
			return entity instanceof WolfEntity;
		} else if (type.equals(EntityType.ENTITY_LLAMA)) {
			return entity instanceof LlamaEntity;
		} else if (type.equals(EntityType.ENTITY_IRON_GOLEM)) {
			return entity instanceof IronGolemEntity;
		} else if (type.equals(EntityType.ENTITY_SNOW_GOLEM)) {
			return entity instanceof SnowGolemEntity;
		} else if (type.equals(EntityType.ENTITY_PUFFERFISH)) {
			return entity instanceof PufferfishEntity;
		}
		return false;
	}

}

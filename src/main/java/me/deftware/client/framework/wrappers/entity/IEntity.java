package me.deftware.client.framework.wrappers.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class IEntity {

	private Entity entity;

	public IEntity(Entity e) {
		entity = e;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isOnGround() {
		return ((EntityLivingBase) entity).onGround;
	}

	public void setStepHeight(float height) {
		entity.stepHeight = height;
	}

	public float getStepHeight() {
		return entity.stepHeight;
	}

	public Color getEntityColor() {
		if ((entity instanceof EntityAnimal)) {
			return Color.white;
		}
		if ((entity instanceof EntityMob)) {
			return Color.red;
		}
		if ((entity instanceof EntitySlime)) {
			return Color.green;
		}
		if ((entity instanceof EntityVillager)) {
			return new Color(245, 245, 220);
		}
		if ((entity instanceof EntityBat)) {
			return Color.BLACK;
		}
		if ((entity instanceof EntitySquid)) {
			return Color.PINK;
		}
		return Color.white;
	}

	public float getDistanceToPlayer() {
		return entity.getDistance(Minecraft.getMinecraft().player);
	}

	public String getName() {
		if (entity instanceof EntityPlayer) {
			return ((EntityPlayer) entity).getGameProfile().getName();
		}
		return "";
	}

	public boolean isDead() {
		return entity.isDead;
	}

	public boolean hasIMob() {
		return (entity instanceof EntityMob || entity instanceof EntityLiving);
	}

	public boolean isMod() {
		return hasIMob();
	}

	public boolean isPlayer() {
		return entity instanceof EntityPlayer;
	}

	public boolean isItem() {
		return entity instanceof EntityItem;
	}

	public IItemEntity getIItemEntity() {
		return new IItemEntity(entity);
	}

	public IMob getIMob() {
		return new IMob(entity);
	}

	public IPlayer getIPlayer() {
		return new IPlayer((EntityPlayer) entity);
	}

	public float getHealth() {
		if (entity instanceof EntityLivingBase) {
			return ((EntityLivingBase) entity).getHealth();
		}
		return 0;
	}

	public boolean isPlayerOwned() {
		if (entity instanceof EntityWolf) {
			if (((EntityWolf) entity).isOwner(Minecraft.getMinecraft().player)) {
				return true;
			}
		}
		return false;
	}

	public boolean isSleeping() {
		if (entity instanceof EntityPlayer) {
			return ((EntityLivingBase) entity).isPlayerSleeping();
		}
		return false;
	}

	public boolean isInvisible() {
		if (entity instanceof EntityPlayer) {
			return ((EntityLivingBase) entity).isInvisible();
		}
		return false;
	}

	public boolean isInvisibleToPlayer() {
		return entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);
	}

	public boolean isSelf() {
		return entity == Minecraft.getMinecraft().player;
	}

	public double getPosX() {
		return entity.posX;
	}

	public double getPosY() {
		return entity.posY;
	}

	public double getPosZ() {
		return entity.posZ;
	}

	public double getPrevPosX() {
		return entity.prevPosX;
	}

	public double getPrevPosY() {
		return entity.prevPosY;
	}

	public double getPrevPosZ() {
		return entity.prevPosZ;
	}

	public double getEyeHeight() {
		return entity.getEyeHeight();
	}

	public boolean canBeSeen() {
		return Minecraft.getMinecraft().player.canEntityBeSeen(entity);
	}

	public boolean isHostile() {
		if (entity instanceof net.minecraft.entity.monster.IMob) {
			return true;
		} else if (entity instanceof EntityChicken) {
			if (((EntityChicken) entity).chickenJockey) {
				return true;
			}
		}
		return false;
	}

	public boolean instanceOf(EntityType e) {
		// Generic types and players
		if (e.equals(EntityType.ENTITY_PLAYER_SP)) {
			return entity instanceof EntityPlayerSP;
		} else if (e.equals(EntityType.EntityOtherPlayerMP)) {
			return entity instanceof EntityOtherPlayerMP;
		} else if (e.equals(EntityType.ENTITY_PLAYER)) {
			return entity instanceof EntityPlayer;
		} else if (e.equals(EntityType.ENTITY_LIVING_BASE)) {
			return entity instanceof EntityLivingBase;
		} else if (e.equals(EntityType.ENTITY_LIVING)) {
			return entity instanceof EntityLiving;
		} else if (e.equals(EntityType.ENTITY_ITEM)) {
			return entity instanceof EntityItem;
		} else if (e.equals(EntityType.ENTITY_PROJECTILE)) {
			return entity instanceof IProjectile;
		} else if (e.equals(EntityType.Entity_Ageable)) {
			return entity instanceof EntityAgeable;
		} else if (e.equals(EntityType.EntityAmbientCreature)) {
			return entity instanceof EntityAmbientCreature;
		} else if (e.equals(EntityType.EntityWaterMob)) {
			return entity instanceof EntityWaterMob;
		} else if (e.equals(EntityType.EntityMob)) {
			return entity instanceof EntityMob;
		}
		// Passives
		  else if (e.equals(EntityType.ENTITY_BAT)) {
			return entity instanceof EntityBat;
		} else if (e.equals(EntityType.ENTITY_CHICKEN)) {
			return entity instanceof EntityChicken;
		} else if (e.equals(EntityType.ENTITY_COW)) {
			return entity instanceof EntityCow;
		} else if (e.equals(EntityType.ENTITY_FISH)) {
			return entity instanceof AbstractFish;
		} else if (e.equals(EntityType.ENTITY_MOOSHROOM)) {
			return entity instanceof EntityMooshroom;
		} else if (e.equals(EntityType.ENTITY_OCELOT)) {
			return entity instanceof EntityOcelot;
		} else if (e.equals(EntityType.ENTITY_PIG)) {
			return entity instanceof EntityPig;
		} else if (e.equals(EntityType.ENTITY_POLAR_BEAR)) {
			return entity instanceof EntityPolarBear;
		} else if (e.equals(EntityType.ENTITY_RABBIT)) {
			return entity instanceof EntityRabbit;
		} else if (e.equals(EntityType.ENTITY_SHEEP)) {
			return entity instanceof EntitySheep;
		} else if (e.equals(EntityType.ENTITY_SQUID)) {
			return entity instanceof EntitySquid;
		} else if (e.equals(EntityType.ENTITY_TURTLE)) {
			return entity instanceof EntityTurtle;
		} else if (e.equals(EntityType.ENTITY_VILLAGER)) {
			return entity instanceof EntityVillager;
		} else if (e.equals(EntityType.ENTITY_DOLPHIN)) {
			return entity instanceof EntityDolphin;
		} else if (e.equals(EntityType.ENTITY_DONKEY)) {
			return entity instanceof EntityDonkey;
		} else if (e.equals(EntityType.ENTITY_MULE)) {
			return entity instanceof EntityMule;
		} else if (e.equals(EntityType.ENTITY_HORSE)) {
			return entity instanceof EntityHorse;
		} else if (e.equals(EntityType.ENTITY_PARROT)) {
			return entity instanceof EntityParrot;
		}
		// Hostiles
		  else if (e.equals(EntityType.EntitySlime) || e.equals(EntityType.ENTITY_SLIME)) {
			return entity instanceof EntitySlime;
		} else if (e.equals(EntityType.EntityFlying)) {
			return entity instanceof EntityFlying;
		} else if (e.equals(EntityType.EntityGolem)) {
			return entity instanceof EntityGolem;
		} else if (e.equals(EntityType.ENTITY_SPIDER)) {
			return entity instanceof EntitySpider;
		} else if (e.equals(EntityType.ENTITY_ZOMBIE_PIGMAN)) {
			return entity instanceof EntityZombie;
		} else if (e.equals(EntityType.ENTITY_ENDERMAN)) {
			return entity instanceof EntityEnderman;
		} else if (e.equals(EntityType.ENTITY_WITHER_SKELETON)) {
		    return entity instanceof EntityWitherSkeleton;
		} else if (e.equals(EntityType.ENTITY_WITHER)) {
			return entity instanceof EntityWither;
		} else if (e.equals(EntityType.ENTITY_DRAGON)) {
			return entity instanceof EntityDragon;
		} else if (e.equals(EntityType.ENTITY_PHANTOM)) {
			return entity instanceof EntityPhantom;
		} else if (e.equals(EntityType.ENTITY_DROWNED)) {
			return entity instanceof EntityDrowned;
		} else if (e.equals(EntityType.ENTITY_EVOKER)) {
			return entity instanceof EntityEvoker;
		} else if (e.equals(EntityType.ENTITY_STRAY)) {
			return entity instanceof EntityStray;
		} else if (e.equals(EntityType.ENTITY_ELDER_GUARDIAN)) {
			return entity instanceof EntityElderGuardian;
		} else if (e.equals(EntityType.ENTITY_CREEPER)) {
			return entity instanceof EntityCreeper;
		} else if (e.equals(EntityType.ENTITY_VINDICATOR)) {
			return entity instanceof EntityVindicator;
		} else if (e.equals(EntityType.ENTITY_ILLUSIONER)) {
			return entity instanceof EntityIllusionIllager;
		} else if (e.equals(EntityType.ENTITY_HUSK)) {
			return entity instanceof EntityHusk;
		} else if (e.equals(EntityType.ENTITY_ZOMBIE)) {
			return entity instanceof EntityZombie;
		} else if (e.equals(EntityType.ENTITY_SKELETON)) {
			return entity instanceof EntitySkeleton;
		} else if (e.equals(EntityType.ENTITY_SHULKER)) {
			return entity instanceof EntityShulker;
		} else if (e.equals(EntityType.ENTITY_GUARDIAN)) {
			return entity instanceof EntityGuardian;
		} else if (e.equals(EntityType.ENTITY_VEX)) {
			return entity instanceof EntityVex;
		} else if (e.equals(EntityType.ENTITY_SILVERFISH)) {
			return entity instanceof EntitySilverfish;
		} else if (e.equals(EntityType.ENTITY_WITCH)) {
			return entity instanceof EntityWitch;
		} else if (e.equals(EntityType.ENTITY_GIANT)) {
			return entity instanceof EntityGiantZombie;
		} else if (e.equals(EntityType.ENTITY_BLAZE)) {
			return entity instanceof EntityBlaze;
		} else if (e.equals(EntityType.ENTITY_ENDERMITE)) {
			return entity instanceof EntityEndermite;
		} else if (e.equals(EntityType.ENTITY_GHAST)) {
			return entity instanceof EntityGhast;
		} else if (e.equals(EntityType.ENTITY_MAGMA_CUBE)) {
			return entity instanceof EntityMagmaCube;
		} else if (e.equals(EntityType.ENTITY_CAVE_SPIDER)) {
			return entity instanceof EntityCaveSpider;
		}
		// Neutrals
		  else if (e.equals(EntityType.ENTITY_WOLF)) {
			return entity instanceof EntityWolf;
		} else if (e.equals(EntityType.ENTITY_LLAMA)) {
			return entity instanceof EntityLlama;
		} else if (e.equals(EntityType.ENTITY_IRON_GOLEM)) {
		  	return entity instanceof EntityIronGolem;
		} else if (e.equals(EntityType.ENTITY_SNOW_GOLEM)) {
		  	return entity instanceof EntitySnowman;
		} else if (e.equals(EntityType.ENTITY_PUFFERFISH)) {
		  	return entity instanceof EntityPufferFish;
		}
		return false;
	}

	public enum EntityType {
		ENTITY_PLAYER_SP, EntityOtherPlayerMP, ENTITY_PLAYER, EntitySlime, EntityGolem, EntityFlying, EntityMob, EntityWaterMob,
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

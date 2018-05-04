package me.deftware.client.framework.wrappers.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.boss.EntityWither;
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
		return entity.getDistanceToEntity(Minecraft.getMinecraft().player);
	}

	public String getName() {
		if (entity instanceof EntityPlayer) {
			return ((EntityPlayer) entity).getName();
		}
		return "";
	}

	public boolean isDead() {
		return entity.isDead;
	}

	public boolean isMod() {
		return (entity instanceof EntityMob || entity instanceof EntityLiving) && !(entity instanceof EntityPlayer) && !(entity instanceof EntityItem);
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
		if (entity instanceof EntityBlaze || entity instanceof EntityCreeper || entity instanceof EntityElderGuardian
				|| entity instanceof EntityEndermite || entity instanceof EntityEvoker || entity instanceof EntityGhast
				|| entity instanceof EntityGuardian || entity instanceof EntityHusk || entity instanceof EntityMagmaCube
				|| entity instanceof EntityShulker || entity instanceof EntitySilverfish
				|| entity instanceof EntitySkeleton || entity instanceof EntitySlime || entity instanceof EntitySpider
				|| entity instanceof EntityStray || entity instanceof EntityVex || entity instanceof EntityVindicator
				|| entity instanceof EntityWitch || entity instanceof EntityWitherSkeleton
				|| entity instanceof EntityZombie || entity instanceof EntityZombieVillager
				|| entity instanceof EntityWither) {
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
		}
		// Mobs
		if (e.equals(EntityType.ENTITY_WOLF)) {
			return entity instanceof EntityWolf;
		} else if (e.equals(EntityType.Entity_Ageable)) {
			return entity instanceof EntityAgeable;
		} else if (e.equals(EntityType.EntityAmbientCreature)) {
			return entity instanceof EntityAmbientCreature;
		} else if (e.equals(EntityType.EntityWaterMob)) {
			return entity instanceof EntityWaterMob;
		} else if (e.equals(EntityType.EntityMob)) {
			return entity instanceof EntityMob;
		} else if (e.equals(EntityType.EntitySlime)) {
			return entity instanceof EntitySlime;
		} else if (e.equals(EntityType.EntityFlying)) {
			return entity instanceof EntityFlying;
		} else if (e.equals(EntityType.EntityGolem)) {
			return entity instanceof EntityGolem;
		} else if (e.equals(EntityType.ENTITY_SPIDER)) {
			return entity instanceof EntitySpider;
		} else if (e.equals(EntityType.ENTITY_SPIDER)) {
			return entity instanceof EntitySpider;
		} else if (e.equals(EntityType.ENTITY_ZOMBIE_PIGMAN)) {
			return entity instanceof EntityZombie;
		} else if (e.equals(EntityType.ENTITY_ENDERMAN)) {
			return entity instanceof EntityEnderman;
		}
		return false;
	}

	public static enum EntityType {
		ENTITY_PLAYER_SP, EntityOtherPlayerMP, ENTITY_PLAYER, EntitySlime, EntityGolem, EntityFlying, EntityMob, EntityWaterMob, ENTITY_WOLF, ENTITY_LIVING_BASE, ENTITY_LIVING, Entity_Ageable, EntityAmbientCreature, ENTITY_ITEM,
		/*
		 * Hostile mobs
		 */
		ENTITY_ENDERMAN, ENTITY_ZOMBIE_PIGMAN, ENTITY_SPIDER
	}

}

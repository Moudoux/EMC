package me.deftware.client.framework.Wrappers.Entity;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;

public class IEntity {

	private Entity entity;

	public IEntity(Entity e) {
		this.entity = e;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isOnGround() {
		return ((EntityLivingBase) entity).onGround;
	}

	public void setStepHeight(float height) {
		this.entity.stepHeight = height;
	}

	public float getStepHeight() {
		return this.entity.stepHeight;
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

	/**
	 * Returns the distance between this entity and the player
	 * 
	 * @return
	 */
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
		return this.entity.isDead;
	}

	/**
	 * Returns the health if this entity is a LivingBase, if not it returns 0
	 * 
	 * @return
	 */
	public float getHealth() {
		if (this.entity instanceof EntityLivingBase) {
			return ((EntityLivingBase) entity).getHealth();
		}
		return 0;
	}

	/**
	 * Returns true if this is a wolf and is owned by the player
	 * 
	 * @return
	 */
	public boolean isPlayerOwned() {
		if (this.entity instanceof EntityWolf) {
			if (((EntityWolf) entity).isOwner(Minecraft.getMinecraft().player)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if this is a EntityPlayer and is sleeping
	 * 
	 * @return
	 */
	public boolean isSleeping() {
		if (this.entity instanceof EntityPlayer) {
			return ((EntityLivingBase) entity).isPlayerSleeping();
		}
		return false;
	}

	/**
	 * Returns true if this is a EntityPlayer and is invisible
	 * 
	 * @return
	 */
	public boolean isInvisible() {
		if (this.entity instanceof EntityPlayer) {
			return ((EntityLivingBase) entity).isInvisible();
		}
		return false;
	}

	/**
	 * If this is invisible the the player
	 * 
	 * @return
	 */
	public boolean isInvisibleToPlayer() {
		return entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);
	}

	/**
	 * Is this entity the player ?
	 * 
	 * @return
	 */
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

	/**
	 * Returns true if this entity can be seen by the player (Aka if the entity is
	 * behind a wall)
	 * 
	 * @return
	 */
	public boolean canBeSeen() {
		return Minecraft.getMinecraft().player.canEntityBeSeen(this.entity);
	}

	public boolean instanceOf(EntityType e) {
		// Generic types and players
		if (e.equals(EntityType.ENTITY_PLAYER_SP)) {
			return entity instanceof EntityPlayerSP;
		} else if (e.equals(EntityType.ENTITY_PLAYER)) {
			return entity instanceof EntityPlayer;
		} else if (e.equals(EntityType.ENTITY_LIVING_BASE)) {
			return entity instanceof EntityLivingBase;
		} else if (e.equals(EntityType.ENTITY_LIVING)) {
			return entity instanceof EntityLiving;
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
		}
		return false;
	}

	public static enum EntityType {
		ENTITY_PLAYER_SP, ENTITY_PLAYER, EntitySlime, EntityGolem, EntityFlying, EntityMob, EntityWaterMob, ENTITY_WOLF, ENTITY_LIVING_BASE, ENTITY_LIVING, Entity_Ageable, EntityAmbientCreature
	}

}

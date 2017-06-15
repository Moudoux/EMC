package me.deftware.client.framework.Wrappers.Entity;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;

public class IEntity {

	private Entity entity;

	public IEntity(Entity e) {
		this.entity = e;
	}

	public Entity getEntity() {
		return entity;
	}

	private Color getEntityColor() {
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

	public boolean instanceOf(EntityType e) {
		if (e.equals(EntityType.PLAYER_SP)) {
			return entity instanceof EntityPlayerSP;
		}
		return false;
	}

	public double getPosX() {
		return entity.posX;
	}

	public double getPosY() {
		return entity.posZ;
	}

	public double getPosZ() {
		return entity.posZ;
	}

	public static enum EntityType {
		PLAYER_SP, PLAYER
	}

}

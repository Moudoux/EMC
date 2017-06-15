package me.deftware.client.framework.Wrappers;

import java.util.ArrayList;

import me.deftware.client.framework.Wrappers.Entity.IItemEntity;
import me.deftware.client.framework.Wrappers.Entity.IMob;
import me.deftware.client.framework.Wrappers.Entity.IPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;

public class IWorld {

	/*
	 * Cache
	 */
	private static ArrayList<IPlayer> iPlayerCacheArray = new ArrayList<IPlayer>();
	private static ArrayList<IMob> iMobCacheArray = new ArrayList<IMob>();
	private static ArrayList<IItemEntity> iItemCacheArray = new ArrayList<IItemEntity>();
	private static int iPlayerCache = 0, iMobCache = 0, iItemCache = 0;

	/**
	 * Converts the loaded enity list to IPlayers and returns it
	 * 
	 * @return
	 */
	public static ArrayList<IPlayer> getIEntityPlayers() {
		try {
			if (iPlayerCache == Minecraft.getMinecraft().world.loadedEntityList.size()) {
				return iPlayerCacheArray;
			}
			ArrayList<IPlayer> result = new ArrayList<IPlayer>();
			for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
				if (e instanceof EntityPlayer) {
					result.add(new IPlayer((EntityPlayer) e));
				}
			}
			iPlayerCache = Minecraft.getMinecraft().world.loadedEntityList.size();
			iPlayerCacheArray = result;
			return result;
		} catch (Exception ex) {
			return new ArrayList<IPlayer>();
		}
	}

	/**
	 * Converts the loaded enity list to IMobs and returns it
	 * 
	 * @return
	 */
	public static ArrayList<IMob> getIEntityMobs() {
		try {
			if (iMobCache == Minecraft.getMinecraft().world.loadedEntityList.size()) {
				return iMobCacheArray;
			}
			ArrayList<IMob> result = new ArrayList<IMob>();
			for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
				if (e instanceof EntityMob) {
					result.add(new IMob(e));
				}
			}
			iMobCache = Minecraft.getMinecraft().world.loadedEntityList.size();
			iMobCacheArray = result;
			return result;
		} catch (Exception ex) {
			return new ArrayList<IMob>();
		}
	}

	/**
	 * Converts the loaded enity list to IMobs and returns it
	 * 
	 * @return
	 */
	public static ArrayList<IItemEntity> getIEntityItems() {
		try {
			if (iItemCache == Minecraft.getMinecraft().world.loadedEntityList.size()) {
				return iItemCacheArray;
			}
			ArrayList<IItemEntity> result = new ArrayList<IItemEntity>();
			for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
				if (e instanceof EntityItem) {
					result.add(new IItemEntity(e));
				}
			}
			iItemCache = Minecraft.getMinecraft().world.loadedEntityList.size();
			iItemCacheArray = result;
			return result;
		} catch (Exception ex) {
			return new ArrayList<IItemEntity>();
		}
	}

}

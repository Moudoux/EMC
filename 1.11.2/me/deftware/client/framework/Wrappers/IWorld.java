package me.deftware.client.framework.Wrappers;

import java.util.ArrayList;

import me.deftware.client.framework.Wrappers.EntityPlayer.IPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class IWorld {
	
	/*
	 * Cache
	 */
	private static ArrayList<IPlayer> iPlayerCacheArray = new ArrayList<IPlayer>();
	private static int iPlayerCache = 0;

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

}

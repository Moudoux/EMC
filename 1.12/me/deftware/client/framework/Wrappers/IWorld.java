package me.deftware.client.framework.Wrappers;

import java.awt.Color;
import java.util.ArrayList;

import me.deftware.client.framework.Wrappers.Entity.IEntity;
import me.deftware.client.framework.Wrappers.Entity.IItemEntity;
import me.deftware.client.framework.Wrappers.Entity.IMob;
import me.deftware.client.framework.Wrappers.Entity.IPlayer;
import me.deftware.client.framework.Wrappers.Objects.IBlockPos;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;

public class IWorld {
	
	/*
	 * Cache
	 */
	private static ArrayList<IPlayer> iPlayerCacheArray = new ArrayList<IPlayer>();
	private static ArrayList<IMob> iMobCacheArray = new ArrayList<IMob>();
	private static ArrayList<IItemEntity> iItemCacheArray = new ArrayList<IItemEntity>();
	private static ArrayList<IChest> iChestCacheArray = new ArrayList<IChest>();
	private static ArrayList<IEntity> iEntityCacheArray = new ArrayList<IEntity>();
	private static int iEntityCache = 0, iPlayerCache = 0, iMobCache = 0, iItemCache = 0, iChestCache = 0;

	/**
	 * Converts the loaded entity list to IEntites and returns it
	 * 
	 * @return
	 */
	public static ArrayList<IEntity> getIEntity() {
		try {
			if (iEntityCache == Minecraft.getMinecraft().world.loadedEntityList.size()) {
				return iEntityCacheArray;
			}
			ArrayList<IEntity> result = new ArrayList<IEntity>();
			for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
				result.add(new IEntity(e));
			}
			iEntityCache = Minecraft.getMinecraft().world.loadedEntityList.size();
			iEntityCacheArray = result;
			return result;
		} catch (Exception ex) {
			return new ArrayList<IEntity>();
		}
	}

	/**
	 * Converts the loaded entity list to IPlayers and returns it
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
	 * Converts the loaded entity list to IMobs and returns it
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
	 * Converts the loaded entity list to IMobs and returns it
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

	/**
	 * Returns all loaded chests
	 * 
	 * @return
	 */
	public static ArrayList<IChest> getIChests() {
		try {
			if (iChestCache == Minecraft.getMinecraft().world.loadedTileEntityList.size()) {
				return iChestCacheArray;
			}
			ArrayList<IChest> result = new ArrayList<IChest>();
			for (Object o : Minecraft.getMinecraft().world.loadedTileEntityList) {
				if (o instanceof TileEntityChest) {
					BlockPos p = ((TileEntityChest) o).getPos();
					if (((TileEntityChest) o).getChestType() == BlockChest.Type.TRAP) {
						result.add(new IChest(IChestType.TRAPPED_CHEST, new IBlockPos(p.getX(), p.getY(), p.getZ()),
								Color.RED));
					} else {
						result.add(new IChest(IChestType.CHEST, new IBlockPos(p.getX(), p.getY(), p.getZ()),
								Color.ORANGE));
					}
				} else if (o instanceof TileEntityEnderChest) {
					BlockPos p = ((TileEntityEnderChest) o).getPos();
					result.add(new IChest(IChestType.ENDER_CHEST, new IBlockPos(p.getX(), p.getY(), p.getZ()),
							Color.BLUE));
				} else if (o instanceof TileEntityShulkerBox) {
					BlockPos p = ((TileEntityShulkerBox) o).getPos();
					result.add(new IChest(IChestType.SHULKER_BOX, new IBlockPos(p.getX(), p.getY(), p.getZ()),
							Color.PINK));
				}
			}
			iChestCache = Minecraft.getMinecraft().world.loadedTileEntityList.size();
			iChestCacheArray = result;
			return result;
		} catch (Exception ex) {
			return new ArrayList<IChest>();
		}
	}

	public static class IChest {

		private IChestType type;
		private Color color;
		private IBlockPos pos;

		public IChest(IChestType type, IBlockPos pos) {
			this(type, pos, Color.ORANGE);
		}

		public IChest(IChestType type, IBlockPos pos, Color color) {
			this.type = type;
			this.pos = pos;
			this.color = color;
		}

		public Color getColor() {
			return color;
		}

		public IChestType getType() {
			return type;
		}

		public IBlockPos getPos() {
			return pos;
		}

	}

	public static enum IChestType {
		TRAPPED_CHEST, CHEST, ENDER_CHEST, SHULKER_BOX, MINECART_CHEST
	}

}

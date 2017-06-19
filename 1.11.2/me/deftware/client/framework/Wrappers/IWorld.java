package me.deftware.client.framework.Wrappers;

import java.awt.Color;
import java.util.ArrayList;

import me.deftware.client.framework.Utils.ICachedList;
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
	
	private static final ICachedList iPlayerCacheArray = new ICachedList() {
		@Override
		public void execute() {
			new Thread() {
				@Override
				public void run() {
					ArrayList<IPlayer> result = new ArrayList<IPlayer>();
					for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
						if (e instanceof EntityPlayer) {
							result.add(new IPlayer((EntityPlayer) e));
						}
					}
					list = result;
				}
			}.start();
		}
	};

	private static final ICachedList iMobCacheArray = new ICachedList() {
		@Override
		public void execute() {
			new Thread() {
				@Override
				public void run() {
					ArrayList<IMob> result = new ArrayList<IMob>();
					for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
						if (e instanceof EntityMob) {
							result.add(new IMob(e));
						}
					}
					list = result;
				}
			}.start();
		}
	};

	private static final ICachedList iItemCacheArray = new ICachedList() {
		@Override
		public void execute() {
			new Thread() {
				@Override
				public void run() {
					ArrayList<IItemEntity> result = new ArrayList<IItemEntity>();
					for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
						if (e instanceof EntityItem) {
							result.add(new IItemEntity(e));
						}
					}
					list = result;
				}
			}.start();
		}
	};

	private static final ICachedList iEntityCacheArray = new ICachedList() {
		@Override
		public void execute() {
			new Thread() {
				@Override
				public void run() {
					ArrayList<IEntity> result = new ArrayList<IEntity>();
					for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
						result.add(new IEntity(e));
					}
					list = result;
				}
			}.start();
		}
	};

	private static final ICachedList iChestArray = new ICachedList() {
		@Override
		public void execute() {
			new Thread() {
				@Override
				public void run() {
					ArrayList<IChest> result = new ArrayList<IChest>();
					if (Minecraft.getMinecraft().world == null) {
						list = result;
						return;
					}
					for (Object o : Minecraft.getMinecraft().world.loadedTileEntityList) {
						if (o instanceof TileEntityChest) {
							BlockPos p = ((TileEntityChest) o).getPos();
							if (((TileEntityChest) o).getChestType() == BlockChest.Type.TRAP) {
								result.add(new IChest(IChestType.TRAPPED_CHEST,
										new IBlockPos(p.getX(), p.getY(), p.getZ()), Color.RED));
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
					list = result;
				}
			}.start();
		}
	};

	public static ArrayList<IChest> getIChests() {
		iChestArray.execute();
		return (ArrayList<IChest>) iChestArray.getList();
	}

	public static ArrayList<IEntity> getIEntity() {
		iEntityCacheArray.execute();
		return (ArrayList<IEntity>) iEntityCacheArray.getList();
	}

	public static ArrayList<IPlayer> getIEntityPlayers() {
		iPlayerCacheArray.execute();
		return (ArrayList<IPlayer>) iPlayerCacheArray.getList();
	}

	public static ArrayList<IMob> getIEntityMobs() {
		iMobCacheArray.execute();
		return (ArrayList<IMob>) iMobCacheArray.getList();
	}

	public static ArrayList<IItemEntity> getIEntityItems() {
		iItemCacheArray.execute();
		return (ArrayList<IItemEntity>) iItemCacheArray.getList();
	}

	public static IBlock getBlockFromPos(IBlockPos pos) {
		return new IBlock(Minecraft.getMinecraft().world.getBlockState(pos.getPos()).getBlock());
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

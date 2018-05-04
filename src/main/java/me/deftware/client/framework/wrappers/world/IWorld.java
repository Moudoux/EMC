package me.deftware.client.framework.wrappers.world;

import me.deftware.client.framework.utils.ICachedList;
import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.entity.IItemEntity;
import me.deftware.client.framework.wrappers.entity.IMob;
import me.deftware.client.framework.wrappers.entity.IPlayer;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;

public class IWorld {

	private static final ICachedList ILoadedEntityList = new ICachedList() {
		@Override
		public void execute() {
			new Thread(() -> {
				ArrayList<IEntity> entities = new ArrayList<>();
				new ArrayList<>(Minecraft.getMinecraft().world.loadedEntityList).forEach((entity) -> {
					entities.add(new IEntity(entity));
				});
				list = entities;
			}).start();
		}
	};

	private static final ICachedList iChestArray = new ICachedList() {
		@Override
		public void execute() {
			new Thread(() -> {
				ArrayList<IChest> chests = new ArrayList<>();
				new ArrayList<>(Minecraft.getMinecraft().world.loadedTileEntityList).forEach((entity) -> {
					IChestType type = entity instanceof TileEntityChest ?
							((TileEntityChest) entity).getChestType() == BlockChest.Type.TRAP ? IChestType.TRAPPED_CHEST : IChestType.CHEST :
							entity instanceof TileEntityEnderChest ? IChestType.ENDER_CHEST : entity instanceof TileEntityShulkerBox ? IChestType.SHULKER_BOX : null;
					if (type != null) {
						BlockPos p = ((TileEntity) entity).getPos();
						Color color = type.equals(IChestType.TRAPPED_CHEST) ? Color.RED : type.equals(IChestType.CHEST) ? Color.ORANGE : type.equals(IChestType.ENDER_CHEST) ? Color.BLUE : Color.PINK;
						chests.add(new IChest(type,
								new IBlockPos(p.getX(), p.getY(), p.getZ()), color));
					}
				});
				list = chests;
			}).start();
		}
	};

	public static IEntity getEntityFromName(String username) {
		for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
			if (entity instanceof EntityOtherPlayerMP) {
				EntityOtherPlayerMP player = (EntityOtherPlayerMP) entity;
				if (player.getName().toLowerCase().equals(username.toLowerCase())) {
					return new IEntity(player);
				}
			}
		}
		return null;
	}

	public static void sendQuittingPacket() {
		if (Minecraft.getMinecraft().world != null) {
			Minecraft.getMinecraft().world.sendQuittingDisconnectingPacket();
		}
	}

	public static void leaveWorld() {
		Minecraft.getMinecraft().loadWorld(null);
	}

	public static ArrayList<IChest> getIChests() {
		IWorld.iChestArray.execute();
		return (ArrayList<IChest>) IWorld.iChestArray.getList();
	}

	public static ArrayList<IEntity> getILoadedEntityList() {
		IWorld.ILoadedEntityList.execute();
		return (ArrayList<IEntity>) IWorld.ILoadedEntityList.getList();
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

	public enum IChestType {
		TRAPPED_CHEST, CHEST, ENDER_CHEST, SHULKER_BOX
	}

}

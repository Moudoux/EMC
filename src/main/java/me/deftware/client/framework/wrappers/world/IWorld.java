package me.deftware.client.framework.wrappers.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import me.deftware.client.framework.utils.ICachedList;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntityTrappedChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class IWorld {

	private static final ICachedList ILoadedEntityList = new ICachedList() {
		@Override
		public void execute() {
			new Thread(() -> {
				ArrayList<IEntity> entities = new ArrayList<>();
				new ArrayList<>(Minecraft.getInstance().world.loadedEntityList).forEach((entity) -> entities.add(new IEntity(entity)));
				list = entities;
			}).start();
		}
	};

	private static final ICachedList iChestArray = new ICachedList() {
		@Override
		public void execute() {
			new Thread(() -> {
				ArrayList<IChest> chests = new ArrayList<>();
				new ArrayList<>(Minecraft.getInstance().world.loadedTileEntityList).forEach((entity) -> {
					IChestType type = entity instanceof TileEntityChest
							? entity instanceof TileEntityTrappedChest ? IChestType.TRAPPED_CHEST : IChestType.CHEST
							: entity instanceof TileEntityEnderChest ? IChestType.ENDER_CHEST
							: entity instanceof TileEntityShulkerBox ? IChestType.SHULKER_BOX : null;
					if (type != null) {
						BlockPos p = entity.getPos();
						Color color = type.equals(IChestType.TRAPPED_CHEST) ? Color.RED
								: type.equals(IChestType.CHEST) ? Color.ORANGE
								: type.equals(IChestType.ENDER_CHEST) ? Color.BLUE : Color.PINK;
						chests.add(new IChest(type, new IBlockPos(p.getX(), p.getY(), p.getZ()), color));
					}
				});
				list = chests;
			}).start();
		}
	};

	public static IEntity getEntityFromName(String username) {
		for (Entity entity : Minecraft.getInstance().world.loadedEntityList) {
			if (entity instanceof EntityOtherPlayerMP) {
				EntityOtherPlayerMP player = (EntityOtherPlayerMP) entity;
				if (player.getGameProfile().getName().toLowerCase().equals(username.toLowerCase())) {
					return new IEntity(player);
				}
			}
		}
		return null;
	}

	public static void sendQuittingPacket() {
		if (Minecraft.getInstance().world != null) {
			Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
		}
	}

	public static void leaveWorld() {
		Minecraft.getInstance().loadWorld(null);
	}

	public static ArrayList<IChest> getIChests() {
		IWorld.iChestArray.execute();
		return (ArrayList<IChest>) IWorld.iChestArray.getList();
	}

	public static ArrayList<IEntity> getILoadedEntityList() {
		IWorld.ILoadedEntityList.execute();
		return (ArrayList<IEntity>) IWorld.ILoadedEntityList.getList();
	}

	public static void updateLists() {

	}

	public static IBlock getBlockFromPos(IBlockPos pos) {
		return new IBlock(Minecraft.getInstance().world.getBlockState(pos.getPos()).getBlock());
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

	/*
	 * Collision
	 */

	public static List<AxisAlignedBB> getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb) {
		List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();

		if (entityIn != null) {
			List<Entity> list1 = Minecraft.getInstance().world.getEntitiesWithinAABBExcludingEntity(entityIn,
					aabb.grow(0.25D));

			for (int i = 0; i < list1.size(); ++i) {
				Entity entity = list1.get(i);

				if (!entityIn.isRidingSameEntity(entity)) {
					AxisAlignedBB axisalignedbb = entity.getCollisionBoundingBox();

					if (axisalignedbb != null && axisalignedbb.intersects(aabb)) {
						list.add(axisalignedbb);
					}

					axisalignedbb = entityIn.getCollisionBox(entity);

					if (axisalignedbb != null && axisalignedbb.intersects(aabb)) {
						list.add(axisalignedbb);
					}
				}
			}
		}

		return list;
	}

}

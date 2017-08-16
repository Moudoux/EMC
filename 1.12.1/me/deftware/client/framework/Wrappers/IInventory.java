package me.deftware.client.framework.Wrappers;

import me.deftware.client.framework.Wrappers.Entity.IEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class IInventory {

	private static IEntity entity;

	public static int getFirstEmptyStack() {
		return Minecraft.getMinecraft().player.inventory.getFirstEmptyStack();
	}

	public static int getCurrentItem() {
		return Minecraft.getMinecraft().player.inventory.currentItem;
	}

	public static void setCurrentItem(int id) {
		Minecraft.getMinecraft().player.inventory.currentItem = id;
	}

	public static int getItemInUseCount() {
		return Minecraft.getMinecraft().player.activeItemStackUseCount;
	}

	public static void swapHands() {
		Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayerDigging(
				CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
	}

	public static void openEntityInventory(IEntity entity) {
		IInventory.entity = entity;
	}

	public static void onRender() {
		if (entity != null) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiInventory((EntityOtherPlayerMP) entity.getEntity()));
			entity = null;
		}
	}

}

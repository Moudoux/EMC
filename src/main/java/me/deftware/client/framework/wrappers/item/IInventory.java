package me.deftware.client.framework.wrappers.item;

import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.mixin.imp.IMixinEntityLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class IInventory {

	private static IEntity entity;

	public static int getFirstEmptyStack() {
		return Minecraft.getInstance().player.inventory.getFirstEmptyStack();
	}

	public static int getCurrentItem() {
		return Minecraft.getInstance().player.inventory.currentItem;
	}

	public static void setCurrentItem(int id) {
		Minecraft.getInstance().player.inventory.currentItem = id;
	}

	public static int getItemInUseCount() {
		return ((IMixinEntityLivingBase) Minecraft.getInstance().player).getActiveItemStackUseCount();
	}

	public static void swapHands() {
		Minecraft.getInstance().player.connection.sendPacket(new CPacketPlayerDigging(
				CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
	}

	public static void openEntityInventory(IEntity entity) {
		IInventory.entity = entity;
	}

	public static void onRender() {
		if (IInventory.entity != null) {
			Minecraft.getInstance().displayGuiScreen(new GuiInventory((EntityOtherPlayerMP) IInventory.entity.getEntity()));
			IInventory.entity = null;
		}
	}

}

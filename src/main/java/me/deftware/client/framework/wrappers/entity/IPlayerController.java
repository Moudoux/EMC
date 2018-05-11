package me.deftware.client.framework.wrappers.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.EnumHand;

public class IPlayerController {

	public static void windowClick(int id, int next, IClickType type) {
		IPlayerController.windowClick(0, id, next, type);
	}

	public static void windowClick(int windowID, int id, int next, IClickType type) {
		ClickType t = ClickType.THROW;
		if (type.equals(IClickType.THROW)) {
			t = ClickType.THROW;
		} else if (type.equals(IClickType.QUICK_MOVE)) {
			t = ClickType.QUICK_MOVE;
		} else {
			return;
		}
		Minecraft.getMinecraft().playerController.windowClick(windowID, id, next, t,
				Minecraft.getMinecraft().player);
	}

	public static void processRightClick(boolean offhand) {
		Minecraft.getMinecraft().playerController.processRightClick(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
	}

	public enum IClickType {
		THROW, QUICK_MOVE
	}

}

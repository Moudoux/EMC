package me.deftware.client.framework.wrappers.entity;

import me.deftware.mixin.imp.IMixinPlayerControllerMP;
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
		} else if (type.equals(IClickType.PICKUP)) {
			t = ClickType.PICKUP;
		} else {
			return;
		}
		Minecraft.getInstance().playerController.windowClick(windowID, id, next, t,
				Minecraft.getInstance().player);
	}

	public static void resetBlockRemoving() {
		Minecraft.getInstance().playerController.resetBlockRemoving();
	}

	public static void setPlayerHittingBlock(boolean state) {
		((IMixinPlayerControllerMP) Minecraft.getInstance().playerController).setPlayerHittingBlock(state);
	}

	public static void processRightClick(boolean offhand) {
		Minecraft.getInstance().playerController.processRightClick(Minecraft.getInstance().player, Minecraft.getInstance().world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
	}

	public enum IClickType {
		THROW, QUICK_MOVE, PICKUP
	}

}

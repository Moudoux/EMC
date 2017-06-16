package me.deftware.client.framework.Wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;

public class IPlayerController {

	public static void windowClick(int id, IClickType type) {
		ClickType t = ClickType.THROW;
		if (type.equals(IClickType.THROW)) {
			t = ClickType.THROW;
		} else {
			return;
		}
		Minecraft.getMinecraft().playerController.windowClick(0, id, 1, t,
				Minecraft.getMinecraft().player);
	}

	public static enum IClickType {
		THROW
	}

}

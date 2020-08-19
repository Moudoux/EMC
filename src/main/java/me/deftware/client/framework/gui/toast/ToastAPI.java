package me.deftware.client.framework.gui.toast;

import net.minecraft.client.MinecraftClient;

/**
 * A wrapper for sending toasts using the minecraft toast system
 *
 * @author Deftware
 */
public class ToastAPI {

	public static void addToast(ToastImpl toast) {
		MinecraftClient.getInstance().getToastManager().add(toast);
	}

}

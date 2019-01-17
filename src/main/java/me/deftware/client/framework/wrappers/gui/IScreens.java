package me.deftware.client.framework.wrappers.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.realms.RealmsBridge;

public class IScreens {

	public static enum Screen {
		Multiplayer, WorldSelection, Options, MainMenu
	}

	public static GuiScreen translate(Screen type, IGuiScreen parent) {
		GuiScreen screen = null;
		if (type.equals(Screen.Multiplayer)) {
			screen = new GuiMultiplayer(parent);
		} else if (type.equals(Screen.MainMenu)) {
			screen = new GuiMainMenu();
		} else if (type.equals(Screen.WorldSelection)) {
			screen = new GuiWorldSelection(parent);
		} else if (type.equals(Screen.Options)) {
			screen = new GuiOptions(parent, Minecraft.getInstance().gameSettings);
		}
		return screen;
	}

	public static void displayGuiScreen(Screen type, IGuiScreen parent) {
		Minecraft.getInstance().displayGuiScreen(IScreens.translate(type, parent));
	}

	public static void switchToRealms(IGuiScreen parent) {
		RealmsBridge realmsbridge = new RealmsBridge();
		realmsbridge.switchToRealms(parent);
	}

}

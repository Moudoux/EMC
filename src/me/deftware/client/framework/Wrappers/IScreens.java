package me.deftware.client.framework.Wrappers;

import me.deftware.client.framework.Wrappers.Objects.IGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.realms.RealmsBridge;

public class IScreens {

	public static enum Screen {
		Multiplayer, WorldSelection, Options
	}

	public static GuiScreen translate(Screen type, IGuiScreen parent) {
		GuiScreen screen = null;
		if (type.equals(Screen.Multiplayer)) {
			screen = new GuiMultiplayer(parent);
		} else if (type.equals(Screen.WorldSelection)) {
			screen = new GuiWorldSelection(parent);
		} else if (type.equals(Screen.Options)) {
			screen = new GuiOptions(parent, Minecraft.getMinecraft().gameSettings);
		}
		return screen;
	}

	public static void displayGuiScreen(Screen type, IGuiScreen parent) {
		Minecraft.getMinecraft().displayGuiScreen(translate(type, parent));
	}

	public static void switchToRealms(IGuiScreen parent) {
		RealmsBridge realmsbridge = new RealmsBridge();
		realmsbridge.switchToRealms(parent);
	}

}

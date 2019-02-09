package me.deftware.client.framework.wrappers.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.MainMenuScreen;
import net.minecraft.client.gui.menu.LevelSelectScreen;
import net.minecraft.client.gui.menu.MultiplayerScreen;
import net.minecraft.client.gui.menu.SettingsScreen;
import net.minecraft.realms.RealmsBridge;

public class IScreens {

    public static net.minecraft.client.gui.Screen translate(Screen type, IGuiScreen parent) {
        net.minecraft.client.gui.Screen screen = null;
        if (type.equals(Screen.Multiplayer)) {
            screen = new MultiplayerScreen(parent);
        } else if (type.equals(Screen.MainMenu)) {
            screen = new MainMenuScreen();
        } else if (type.equals(Screen.WorldSelection)) {
            screen = new LevelSelectScreen(parent);
        } else if (type.equals(Screen.Options)) {
            screen = new SettingsScreen(parent, MinecraftClient.getInstance().options);
        }
        return screen;
    }

    public static void displayGuiScreen(Screen type, IGuiScreen parent) {
        MinecraftClient.getInstance().openScreen(IScreens.translate(type, parent));
    }

    public static void switchToRealms(IGuiScreen parent) {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(parent);
    }

    public static enum Screen {
        Multiplayer, WorldSelection, Options, MainMenu
    }

}

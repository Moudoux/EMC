package me.deftware.client.framework.wrappers.gui;

import io.github.prospector.modmenu.gui.ModsScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.realms.RealmsBridge;

public class IScreens {

    public static net.minecraft.client.gui.screen.Screen translate(Screen type, IGuiScreen parent) {
        net.minecraft.client.gui.screen.Screen screen = null;
        if (type.equals(Screen.Multiplayer)) {
            screen = new MultiplayerScreen(parent);
        } else if (type.equals(Screen.MainMenu)) {
            screen = new TitleScreen();
        } else if (type.equals(Screen.WorldSelection)) {
            screen = new SelectWorldScreen(parent);
        } else if (type.equals(Screen.Options)) {
            screen = new SettingsScreen(parent, MinecraftClient.getInstance().options);
        } else if (type.equals(Screen.Mods)) {
            if (FabricLoader.getInstance().isModLoaded("modmenu")) {
                screen = new ModsScreen(parent);
            }
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

    public enum Screen {
        Multiplayer, WorldSelection, Options, MainMenu, Mods
    }

}

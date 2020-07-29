package me.deftware.client.framework.wrappers.gui;

import me.deftware.client.framework.utils.ResourceUtils;
import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.mixin.imp.IMixinTitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.util.Pair;

import java.util.Objects;

public class IScreens {

    public static Screen translate(ScreenType type, IGuiScreen parent) {
        Screen screen = null;
        if (type.equals(ScreenType.Multiplayer)) {
            screen = new MultiplayerScreen(parent);
        } else if (type.equals(ScreenType.MainMenu)) {
            screen = new TitleScreen();
        } else if (type.equals(ScreenType.WorldSelection)) {
            screen = new SelectWorldScreen(parent);
        } else if (type.equals(ScreenType.Options)) {
            screen = new OptionsScreen(parent, MinecraftClient.getInstance().options);
        } else if (type.equals(ScreenType.Mods)) {
            if (ResourceUtils.hasSpecificMod("modmenu")) {
                screen = Objects.requireNonNull(IMinecraft.createScreenInstance("io.github.prospector.modmenu.gui.ModsScreen", new Pair<>(Screen.class, parent)));
            }
        }
        return screen;
    }

    public static void displayGuiScreen(ScreenType type, IGuiScreen parent) {
        MinecraftClient.getInstance().openScreen(IScreens.translate(type, parent));
    }

    public static void switchToRealms(IGuiScreen parent) {
        TitleScreen screen = new TitleScreen();
        ((IMixinTitleScreen) screen).switchToRealmsPub();
    }

    public enum ScreenType {
        Multiplayer, WorldSelection, Options, MainMenu, Mods
    }

}

package me.deftware.client.framework.gui;

import me.deftware.client.framework.helper.ScreenHelper;
import me.deftware.client.framework.util.ResourceUtils;
import me.deftware.mixin.imp.IMixinTitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.util.Pair;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Deftware
 */
public enum MinecraftScreens {

	Multiplayer(MultiplayerScreen::new), WorldSelection(SelectWorldScreen::new),
	Options(parent -> new OptionsScreen(parent, MinecraftClient.getInstance().options)), MainMenu(parent -> new TitleScreen()),
	Mods(parent -> {
		// Important: Change "modmenu" to what's relevant on this mc version
		if (ResourceUtils.hasSpecificMod("modmenu")) {
			return Objects.requireNonNull(ScreenHelper.createScreenInstance("io.github.prospector.modmenu.gui.ModsScreen", new Pair<>(Screen.class, parent)));
		}
		return null;
	});

	private final Function<GuiScreen, Screen> screenFunction;

	MinecraftScreens(Function<GuiScreen, Screen> screenFunction) {
		this.screenFunction = screenFunction;
	}

	public void open(GuiScreen parent) {
		MinecraftClient.getInstance().openScreen(screenFunction.apply(parent));
	}

	@SuppressWarnings("ConstantConditions")
	public static void openRealms() {
		((IMixinTitleScreen) new TitleScreen()).switchToRealmsPub();
	}

}

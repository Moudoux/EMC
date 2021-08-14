package me.deftware.client.framework.gui;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Deftware
 */
public enum ScreenRegistry {

	Multiplayer(MultiplayerScreen.class),
	WorldSelection(SelectWorldScreen.class),
	Options(OptionsScreen.class, args -> new OptionsScreen((Screen) args[0], MinecraftClient.getInstance().options)),
	MainMenu(TitleScreen.class, parent -> new TitleScreen()),

	IngameMenu(GameMenuScreen.class),
	Disconnected(DisconnectedScreen.class),
	Container(HandledScreen.class),
	Chat(ChatScreen.class),
	Death(DeathScreen.class),

	Realms(RealmsMainScreen.class);

	@Getter
	private final Class<? extends Screen> clazz;
	private final CatchableFunction supplier;

	ScreenRegistry(Class<? extends Screen> clazz) {
		this.clazz = clazz;
		this.supplier = (args) ->
			this.clazz.getDeclaredConstructor(
					Arrays.stream(args).map(o -> {
						if (o instanceof Screen)
							return Screen.class;
						return o.getClass();
					}).toArray(Class[]::new)
			).newInstance(
					args
			);
	}

	ScreenRegistry(Class<? extends Screen> clazz, CatchableFunction supplier) {
		this.clazz = clazz;
		this.supplier = supplier;
	}

	public void open(Object... params) {
		try {
			Screen screen = this.supplier.apply(params);
			if (screen == null)
				throw new Exception("Null screen");
			MinecraftClient.getInstance().openScreen(screen);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean isOpen() {
		Screen current = MinecraftClient.getInstance().currentScreen;
		return current != null && this.clazz.isAssignableFrom(current.getClass());
	}

	public static Optional<ScreenRegistry> valueOf(Class<? extends Screen> screen) {
		return Arrays.stream(ScreenRegistry.values())
				.filter(m -> m.getClazz().isAssignableFrom(screen))
				.findFirst();
	}

	@FunctionalInterface
	private interface CatchableFunction {

		Screen apply(Object... args) throws Exception;

	}

}

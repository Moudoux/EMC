package me.deftware.client.framework.event.events;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.gui.screens.MinecraftScreen;
import net.minecraft.client.gui.screen.Screen;

/**
 * @author Deftware
 */
public class EventScreen extends Event {

	private final Screen screen;

	@Getter
	private Type type = Type.Init;

	@Setter
	@Getter
	private int mouseX, mouseY;

	public EventScreen(Screen screen) {
		this.screen = screen;
	}

	public EventScreen setType(Type type) {
		setCanceled(false);
		this.type = type;
		return this;
	}

	public MinecraftScreen getScreen() {
		return (MinecraftScreen) screen;
	}

	public enum Type {

		/**
		 * When the screen is constructed
		 */
		Init,

		/**
		 * When the screen is initially set up, or each time
		 * the screen is resized
		 */
		Setup,

		/**
		 * Called 20 times a second
		 */
		Tick,

		/**
		 * When the screen is drawn
		 */
		Draw, PostDraw

	}

}

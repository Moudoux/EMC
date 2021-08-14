package me.deftware.client.framework.gui.screens;

import me.deftware.client.framework.event.events.EventScreen;
import me.deftware.client.framework.gui.ScreenRegistry;
import me.deftware.client.framework.gui.widgets.GenericComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;

/**
 * Represents a Minecraft screen instance
 *
 * @since 17.0.0
 * @author Deftware
 */
public interface MinecraftScreen extends GenericScreen {

	/**
	 * @return The type of the current screen
	 */
	default ScreenRegistry getScreenType() {
		return ScreenRegistry.valueOf(
				((Screen) this).getClass()
		).orElse(null);
	}

	/**
	 * Closes the current screen
	 */
	default void close() {
		MinecraftClient.getInstance().openScreen(null);
	}

	/**
	 * @return The first component of a specific type
	 */
	default <T extends GenericComponent> T getFirstOfType(Class<T> clazz) {
		List<T> children = this.getChildren(clazz);
		if (!children.isEmpty())
			return children.get(0);
		return null;
	}

	/**
	 * @return A list of all components of a given class
	 */
	<T extends GenericComponent> List<T> getChildren(Class<T> clazz);

	/**
	 * Clears all children from the screen
	 */
	void _clearChildren();

	/**
	 * Adds a custom EMC component
	 *
	 * @param component EMC component
	 * @param index Array index
	 */
	void addScreenComponent(GenericComponent component, int index);

	/**
	 * Convenience method for addScreenComponent,
	 * with default index of {@link List#size()}
	 *
	 * @param component EMC component
	 */
	default void addScreenComponent(GenericComponent component) {
		this.addScreenComponent(component, -1);
	}

	/**
	 * @return The associated screen event handler
	 */
	EventScreen getEventScreen();

}

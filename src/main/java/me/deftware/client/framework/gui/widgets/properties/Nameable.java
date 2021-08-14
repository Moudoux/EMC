package me.deftware.client.framework.gui.widgets.properties;

import me.deftware.client.framework.chat.ChatMessage;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A UI widget that can be named
 *
 * @since 17.0.0
 * @author Deftware
 */
public interface Nameable<T> {

	/**
	 * @return The component label
	 */
	ChatMessage getComponentLabel();

	/**
	 * Sets the current label
	 *
	 * @param label Some text
	 * @return The component
	 */
	T setComponentLabel(ChatMessage label);

	/**
	 * Sets the label to some text
	 * after a specified amount of time
	 *
	 * @param ms Delay in ms
	 * @param text Some text
	 */
	default void resetToAfter(int ms, ChatMessage text) {
		Executors.newSingleThreadScheduledExecutor().schedule(() -> {
			setComponentLabel(text);
		}, ms, TimeUnit.MILLISECONDS);
	}

}

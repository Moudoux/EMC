package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft keyboard listener
 */
public class EventCharacter extends Event {

	private int modifiers;
	private int keyCode;

	public EventCharacter(int keyCode, int modifiers) {
		this.modifiers = modifiers;
		this.keyCode = keyCode;
	}

	public char getChar() {
		return (char) keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public int getModifiers() {
		return modifiers;
	}

}

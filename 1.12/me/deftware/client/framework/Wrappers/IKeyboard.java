package me.deftware.client.framework.Wrappers;

import org.lwjgl.input.Keyboard;

public class IKeyboard {

	public static String getKeyName(int key) {
		return Keyboard.getKeyName(key);
	}

}

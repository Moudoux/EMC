package me.deftware.client.framework.wrappers;

import org.lwjgl.input.Keyboard;

public class IKeyboard {

	public static String getKeyName(int key) {
		return Keyboard.getKeyName(key);
	}

}

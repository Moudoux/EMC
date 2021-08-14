package me.deftware.client.framework.gui.screens;

/**
 * @author Deftware
 */
public interface SignEditScreen extends MinecraftScreen {

	int _getCurrentLine();

	String _getLine(int line);

	void _setLine(int line, String text);

	void _save();

}

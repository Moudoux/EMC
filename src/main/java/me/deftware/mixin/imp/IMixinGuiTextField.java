package me.deftware.mixin.imp;

import net.minecraft.client.gui.FontRenderer;

public interface IMixinGuiTextField {

	int getHeight();

	FontRenderer getFontRendererInstance();

	int getCursorCounter();

	int getSelectionEnd();

	int getLineScrollOffset();

}

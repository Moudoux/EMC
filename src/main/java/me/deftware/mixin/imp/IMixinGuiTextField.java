package me.deftware.mixin.imp;

import me.deftware.client.framework.fonts.EMCFont;
import net.minecraft.client.gui.FontRenderer;

public interface IMixinGuiTextField {

	int getHeight();

	FontRenderer getFontRendererInstance();

	int getCursorCounter();

	int getSelectionEnd();

	int getLineScrollOffset();

	void setUseMinecraftScaling(boolean state);

	void setUseCustomFont(boolean state);

	void setCustomFont(EMCFont font);

}

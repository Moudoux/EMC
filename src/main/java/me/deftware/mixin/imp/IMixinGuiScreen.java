package me.deftware.mixin.imp;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

import java.util.List;

public interface IMixinGuiScreen {

	List<GuiButton> getButtonList();

	FontRenderer getFontRenderer();

}

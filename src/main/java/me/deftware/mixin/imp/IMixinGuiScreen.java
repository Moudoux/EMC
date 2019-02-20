package me.deftware.mixin.imp;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiEventListener;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;

public interface IMixinGuiScreen {

    List<ButtonWidget> getButtonList();

    TextRenderer getFontRenderer();

    List<GuiEventListener> getEventList();

}

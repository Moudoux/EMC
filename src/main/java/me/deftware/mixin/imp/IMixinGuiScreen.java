package me.deftware.mixin.imp;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.List;

public interface IMixinGuiScreen {

    List<AbstractButtonWidget> getButtonList();

    TextRenderer getFont();

    List<Element> getEventList();

}

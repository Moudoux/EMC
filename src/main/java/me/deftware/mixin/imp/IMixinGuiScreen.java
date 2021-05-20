package me.deftware.mixin.imp;

import me.deftware.client.framework.gui.widgets.Button;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;

import java.util.List;

public interface IMixinGuiScreen {

    List<Button> getEmcButtons();

    List<Drawable> getButtonList();

    TextRenderer getFont();

    List<Element> getEventList();

    void addChildElement(Element element);

}

package me.deftware.mixin.imp;

import me.deftware.client.framework.gui.widgets.Button;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.List;

public interface IMixinGuiScreen {

    List<Button> getEmcButtons();

    List<AbstractButtonWidget> getButtonList();

    TextRenderer getFont();

    List<Element> getEventList();

}

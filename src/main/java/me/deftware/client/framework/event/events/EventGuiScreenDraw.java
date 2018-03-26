package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.gui.IGuiButton;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class EventGuiScreenDraw extends Event {

	private GuiScreen screen;

	public EventGuiScreenDraw(GuiScreen screen) {
		this.screen = screen;
	}

	public boolean instanceOf(CommonScreenTypes type) {
		if (type.equals(CommonScreenTypes.GuiDisconnected)) {
			return screen instanceof GuiDisconnected;
		} else if (type.equals(CommonScreenTypes.GuiIngameMenu)) {
			return screen instanceof GuiIngameMenu;
		}
		return false;
	}

	public void addButton(IGuiButton button) {
		((IMixinGuiScreen) screen).getButtonList().add(button);
	}

	public ArrayList<IGuiButton> getIButtonList() {
		ArrayList<IGuiButton> list = new ArrayList<>();
		for (GuiButton b : ((IMixinGuiScreen) screen).getButtonList()) {
			if (b instanceof IGuiButton) {
				list.add((IGuiButton) b);
			}
		}
		return list;
	}

	public int getWidth() {
		return screen.width;
	}

	public int getHeight() {
		return screen.height;
	}

	public static enum CommonScreenTypes {
		GuiDisconnected, GuiIngameMenu
	}

}

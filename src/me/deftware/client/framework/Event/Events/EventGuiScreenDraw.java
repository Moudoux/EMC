package me.deftware.client.framework.Event.Events;

import java.util.ArrayList;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.Objects.IGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;

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
		screen.buttonList.add(button);
	}

	public ArrayList<IGuiButton> getIButtonList() {
		ArrayList<IGuiButton> list = new ArrayList<IGuiButton>();
		for (GuiButton b : screen.buttonList) {
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

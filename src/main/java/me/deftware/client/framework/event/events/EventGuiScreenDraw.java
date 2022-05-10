package me.deftware.client.framework.event.events;


import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.gui.IGuiButton;
import me.deftware.client.framework.wrappers.gui.imp.GuiContainerInstance;
import me.deftware.client.framework.wrappers.gui.imp.ScreenInstance;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.ArrayList;

/**
 * Triggered every time a gui is drawn on screen.
 * This event also contains a list of buttons that will be drawn in that particular gui
 */
public class EventGuiScreenDraw extends Event {

	private GuiScreen screen;
	private int x,y;

	public EventGuiScreenDraw(GuiScreen screen, int x, int y) {
		this.screen = screen;
		this.x = x;
		this.y = y;
	}

	public boolean instanceOf(CommonScreenTypes type) {
		if (type.equals(CommonScreenTypes.GuiDisconnected)) {
			return screen instanceof GuiDisconnected;
		} else if (type.equals(CommonScreenTypes.GuiIngameMenu)) {
			return screen instanceof GuiIngameMenu;
		} else if (type.equals(CommonScreenTypes.GuiContainer)) {
			return screen instanceof GuiContainer;
		}
		return false;
	}

	public ScreenInstance getInstance() {
		if (screen instanceof GuiContainer) {
			return new GuiContainerInstance(screen);
		}
		return new ScreenInstance(screen);
	}

	public void addButton(IGuiButton button) {
		((IMixinGuiScreen) screen).getButtonList().add(button);
		((IMixinGuiScreen) screen).getEventList().add(button);
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

	public int getMouseX() {
		return x;
	}

	public int getMouseY() {
		return y;
	}

	public enum CommonScreenTypes {
		GuiDisconnected, GuiIngameMenu, GuiContainer
	}

}


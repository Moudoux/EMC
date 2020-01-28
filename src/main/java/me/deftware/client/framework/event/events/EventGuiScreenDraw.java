package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.gui.IGuiButton;
import me.deftware.client.framework.wrappers.gui.imp.GuiContainerInstance;
import me.deftware.client.framework.wrappers.gui.imp.ScreenInstance;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

import java.util.ArrayList;

/**
 * Triggered every time a gui is drawn on screen.
 * This event also contains a list of buttons that will be drawn in that particular gui
 */
public class EventGuiScreenDraw extends Event {

    private Screen screen;
    private int x, y;

    public EventGuiScreenDraw(Screen screen, int x, int y) {
        this.screen = screen;
        this.x = x;
        this.y = y;
    }

    public boolean instanceOf(CommonScreenTypes type) {
        if (type.equals(CommonScreenTypes.GuiDisconnected)) {
            return screen instanceof DisconnectedScreen;
        } else if (type.equals(CommonScreenTypes.GuiIngameMenu)) {
            return screen instanceof GameMenuScreen;
        } else if (type.equals(CommonScreenTypes.GuiContainer)) {
            return screen instanceof ContainerScreen;
        }
        return false;
    }

    public ScreenInstance getInstance() {
        if (screen instanceof ContainerScreen) {
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
        for (AbstractButtonWidget b : ((IMixinGuiScreen) screen).getButtonList()) {
            if (b instanceof IGuiButton) {
                list.add((IGuiButton) b);
            }
        }
        return list;
    }

    public int getMouseX() {
        return x;
    }

    public int getMouseY() {
        return y;
    }

    public int getWidth() {
        return screen.width;
    }

    public int getHeight() {
        return screen.height;
    }

    public enum CommonScreenTypes {
        GuiDisconnected, GuiIngameMenu, GuiContainer
    }

}


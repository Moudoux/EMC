package me.deftware.mixin.imp;

import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;

import javax.annotation.Nullable;

public interface IMixinMinecraft {

	Session getSession();

	void setSession(Session session);

	Timer getTimer();

	MainWindow getMainWindow();

	void displayGuiScreen(@Nullable GuiScreen guiScreenIn);

	void doRightClickMouse();

	void doClickMouse();

	void doMiddleClickMouse();

	void setRightClickDelayTimer(int delay);

	boolean getIsWindowFocused();

}

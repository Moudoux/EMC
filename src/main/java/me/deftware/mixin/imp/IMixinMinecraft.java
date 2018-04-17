package me.deftware.mixin.imp;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;

public interface IMixinMinecraft {

	Session getSession();

	void setSession(Session session);

	Timer getTimer();

	void displayGuiScreen(@Nullable GuiScreen guiScreenIn);

	void doRightClickMouse();

	void doClickMouse();

	void setRightClickDelayTimer(int delay);

}

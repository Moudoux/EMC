package me.deftware.mixin.imp;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;

import javax.annotation.Nullable;

public interface IMixinMinecraft {

	Session getSession();

	void setSession(Session session);

	Timer getTimer();

	void displayGuiScreen(@Nullable GuiScreen guiScreenIn);

	void doRightClickMouse();

}

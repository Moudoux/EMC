package me.deftware.client.framework.Wrappers;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import me.deftware.client.framework.Wrappers.EntityPlayer.IPlayer;
import me.deftware.client.framework.Wrappers.Objects.IGuiScreen;
import me.deftware.client.framework.Wrappers.Objects.ISession;
import me.deftware.client.framework.Wrappers.Objects.ITimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.realms.RealmsSharedConstants;

public class IMinecraft {

	/**
	 * Sets the window title 
	 * 
	 * @param title
	 */
	public static void setTitle(String title) {
		Display.setTitle(title);
	}
	
	/**
	 * If the F3 overlay is on
	 * 
	 * @return
	 */
	public static boolean isDebugInfoShown() {
		return Minecraft.getMinecraft().gameSettings.showDebugInfo;
	}
	
	/**
	 * Gets the current gui screen
	 * 
	 * @return
	 */
	public static GuiScreen getCurrentScreen() {
		return Minecraft.getMinecraft().currentScreen;
	}
	
	/**
	 * Displays a new gui screen, ONLY IGuiScreen instances!
	 * 
	 * @param screen
	 */
	public static void setGuiScreen(IGuiScreen screen) {
		Minecraft.getMinecraft().displayGuiScreen(screen);
	}
	
	/**
	 * Shuts down Minecraft
	 */
	public static void shutdown() {
		Minecraft.getMinecraft().shutdown();
	}
	
	/**
	 * Sets the current session
	 * 
	 * @param session
	 */
	public static void setSession(ISession session) {
		Minecraft.getMinecraft().session = session;
	}
		
	/**
	 * Sets the Minecraft timer
	 * 
	 * @param timer
	 */
	public static void setTimer(ITimer timer) {
		Minecraft.getMinecraft().timer = timer;
	}
	
	/**
	 * Converts the Minecraft timer to a ITimer and returns it
	 * @return
	 */
	public static ITimer getTimer() {
		ITimer timer = new ITimer(Minecraft.getMinecraft().timer.timerSpeed);
		setTimer(timer);
		return timer;
	}
	
	/**
	 * Set's the gamma
	 * 
	 * @param value
	 */
	public static void setGamma(float value) {
		Minecraft.getMinecraft().gameSettings.gammaSetting = value;
	}
	
	/**
	 * Get's the gamma
	 * 
	 * @return
	 */
	public static float getGamma() {
		return Minecraft.getMinecraft().gameSettings.gammaSetting;
	}
	
	/**
	 * Sets the right click delay
	 * 
	 * @param delay
	 */
	public static void setRightClickDelayTimer(int delay) {
		Minecraft.getMinecraft().rightClickDelayTimer = delay;
	}
	
	/**
	 * Get's the current Minecraft version
	 */
	public static String getMinecraftVersion() {
		return RealmsSharedConstants.VERSION_STRING;
	}
	
	/**
	 * Get's the current Minecraft protocol version
	 */
	public static int getMinecraftProtocolVersion() {
		return RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
	}
	
}

package me.deftware.client.framework.Wrappers;

import org.lwjgl.opengl.Display;

import me.deftware.client.framework.Wrappers.Objects.IGuiScreen;
import me.deftware.client.framework.Wrappers.Objects.IServerData;
import me.deftware.client.framework.Wrappers.Objects.ITimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.realms.RealmsSharedConstants;

public class IMinecraft {

	private static IServerData iServerCache = null;

	public synchronized static IServerData getCurrentServer() {
		if (Minecraft.getMinecraft().getCurrentServerData() == null) {
			return null;
		}
		if (iServerCache != null && Minecraft.getMinecraft().getCurrentServerData() != null) {
			if (iServerCache.serverIP.equals(Minecraft.getMinecraft().getCurrentServerData().serverIP)) {
				return iServerCache;
			}
		}
		ServerData sd = Minecraft.getMinecraft().getCurrentServerData();
		iServerCache = new IServerData(sd.serverName,sd.serverIP,sd.isOnLAN());
		iServerCache.gameVersion = sd.gameVersion;
		return iServerCache;
	}

	/**
	 * Sets the window title 
	 * 
	 * @param title
	 */
	public static void setTitle(String title) {
		Display.setTitle(title);
	}
	
	/**
	 * Returns the Minecraft gui scale
	 * 
	 * @return
	 */
	public static int getGuiScale() {
		return Minecraft.getMinecraft().gameSettings.guiScale;
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
		// TODO: 
		//ITimer timer = new ITimer(Minecraft.getMinecraft().timer.timerSpeed);
		//setTimer(timer);
		//return timer;
		return null;
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
	 * Checks if the Minecraft chat is open
	 * 
	 * @return
	 */
	public static boolean isChatOpen() {
		if (Minecraft.getMinecraft().currentScreen != null) {
			if (Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
				return true;
			}
		}
		return false;
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

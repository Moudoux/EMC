package me.deftware.client.framework.Wrappers;

import org.lwjgl.opengl.Display;

import me.deftware.client.framework.Wrappers.Entity.IEntity;
import me.deftware.client.framework.Wrappers.Entity.IEntity.EntityType;
import me.deftware.client.framework.Wrappers.Objects.IGuiScreen;
import me.deftware.client.framework.Wrappers.Objects.IServerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.EntityLivingBase;
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

	public static void reloadRenderers() {
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
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
	 * Checks if the player is in a container
	 * 
	 * @return
	 */
	public static boolean isContainerOpen() {
		if (Minecraft.getMinecraft().currentScreen != null) {
			if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer
					&& !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the player has the inventory open
	 * 
	 * @return
	 */
	public static boolean isInventoryOpen() {
		if (Minecraft.getMinecraft().currentScreen != null) {
			if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer
					&& Minecraft.getMinecraft().currentScreen instanceof GuiInventory) {
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
	
	/**
	 * Returns true if the mouse is over a object
	 * 
	 * @return
	 */
	public static boolean isMouseOver() {
		if (Minecraft.getMinecraft().objectMouseOver != null) {
			return true;
		}
		return false;
	}

	public static IEntity getHit() {
		if (!isMouseOver()) {
			return null;
		}
		return new IEntity(Minecraft.getMinecraft().objectMouseOver.entityHit);
	}

	/**
	 * Checks if the entity hit is a certain type
	 * 
	 * @param type
	 * @return
	 */
	public static boolean entityHitInstanceOf(EntityType type) {
		if (!isMouseOver()) {
			return false;
		}
		if (type.equals(EntityType.ENTITY_LIVING_BASE)) {
			return Minecraft.getMinecraft().objectMouseOver.entityHit instanceof EntityLivingBase;
		}
		return false;
	}

}

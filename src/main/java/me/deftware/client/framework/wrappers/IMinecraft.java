package me.deftware.client.framework.wrappers;

import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.entity.IEntity.EntityType;
import me.deftware.client.framework.wrappers.gui.IGuiInventory;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import me.deftware.client.framework.wrappers.gui.IScreens;
import me.deftware.client.framework.wrappers.gui.IScreens.Screen;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiConnecting;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.main.Main;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.realms.RealmsSharedConstants;

import java.io.File;
import java.net.URISyntaxException;

public class IMinecraft {

	public static IServerData lastServer = null;
	private static IServerData iServerCache = null;

	public static File getMinecraftFile() throws URISyntaxException {
		return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	}

	public static void clickMouse() {
		((IMixinMinecraft) Minecraft.getInstance()).doClickMouse();
	}

	public synchronized static IServerData getCurrentServer() {
		if (Minecraft.getInstance().getCurrentServerData() == null) {
			return null;
		}
		if (iServerCache != null && Minecraft.getInstance().getCurrentServerData() != null) {
			if (iServerCache.serverIP.equals(Minecraft.getInstance().getCurrentServerData().serverIP)) {
				return iServerCache;
			}
		}
		ServerData sd = Minecraft.getInstance().getCurrentServerData();
		iServerCache = new IServerData(sd.serverName, sd.serverIP, sd.isOnLAN());
		iServerCache.gameVersion = sd.gameVersion;
		return iServerCache;
	}

	public static String getRunningLocation() throws URISyntaxException {
		return new File(Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
				.getParent();
	}

	public static IGuiScreen getIScreen() {
		if (Minecraft.getInstance().currentScreen != null) {
			if (Minecraft.getInstance().currentScreen instanceof IGuiScreen) {
				return (IGuiScreen) Minecraft.getInstance().currentScreen;
			}
		}
		return null;
	}

	public static float getRenderPartialTicks() {
		return Minecraft.getInstance().getRenderPartialTicks();
	}

	public static void leaveServer() {
		Minecraft.getInstance().player.connection.sendPacket(new CPacketChatMessage(new String(new char[]{167})));
	}

	public static IBlockPos getBlockOver() {
		if (!IMinecraft.isMouseOver()) {
			return null;
		}
		if (Minecraft.getInstance().objectMouseOver.getBlockPos() != null) {
			return new IBlockPos(Minecraft.getInstance().objectMouseOver.getBlockPos());
		}
		return null;
	}

	public static IEntity getPointedEntity() {
		Entity pointedEntity = Minecraft.getInstance().pointedEntity;
		if ((pointedEntity != null) && ((pointedEntity instanceof EntityPlayer))) {
			return new IEntity(pointedEntity);
		}
		return null;
	}

	public static boolean isEntityHit() {
		return Minecraft.getInstance().objectMouseOver.entity != null;
	}

	public static int getFPS() {
		return Minecraft.getDebugFPS();
	}

	public static boolean isInGame() {
		return Minecraft.getInstance().currentScreen == null;
	}

	public static void reloadRenderers() {
		Minecraft.getInstance().worldRenderer.loadRenderers();
	}

	public static void addEntityToWorld(int id, IEntity entity) {
		Minecraft.getInstance().world.addEntityToWorld(id, entity.getEntity());
	}

	public static void removeEntityFromWorld(int id) {
		Minecraft.getInstance().world.removeEntityFromWorld(id);
	}

	public static void connectToServer(IServerData server) {
		Minecraft.getInstance()
				.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(null), Minecraft.getInstance(), server));
	}

	public static int thridPersonView() {
		return Minecraft.getInstance().gameSettings.thirdPersonView;
	}

	public static int getGuiScale() {
		return Minecraft.getInstance().gameSettings.guiScale;
	}

	public static boolean isDebugInfoShown() {
		return Minecraft.getInstance().gameSettings.showDebugInfo;
	}

	public static IGuiScreen getCurrentScreen() {
		if (Minecraft.getInstance().currentScreen != null) {
			if (Minecraft.getInstance().currentScreen instanceof IGuiScreen) {
				return (IGuiScreen) Minecraft.getInstance().currentScreen;
			}
		}
		return null;
	}

	public static void setGuiScreen(IGuiScreen screen) {
		Minecraft.getInstance().displayGuiScreen(screen);
	}

	public static void openInventory(IGuiInventory inventory) {
		Minecraft.getInstance().displayGuiScreen(inventory);
	}

	public static void setGuiScreenType(Screen screen) {
		Minecraft.getInstance().displayGuiScreen(IScreens.translate(screen, null));
	}

	public static void shutdown() {
		Minecraft.getInstance().shutdown();
	}

	public static void setGamma(double value) {
		Minecraft.getInstance().gameSettings.gammaSetting = value;
	}

	public static double getGamma() {
		return Minecraft.getInstance().gameSettings.gammaSetting;
	}

	public static void setRightClickDelayTimer(int delay) {
		((IMixinMinecraft) Minecraft.getInstance()).setRightClickDelayTimer(delay);
	}

	public static boolean isChatOpen() {
		if (Minecraft.getInstance().currentScreen != null) {
			if (Minecraft.getInstance().currentScreen instanceof GuiChat) {
				return true;
			}
		}
		return false;
	}

	public static boolean isContainerOpen() {
		if (Minecraft.getInstance().currentScreen != null) {
			if (Minecraft.getInstance().currentScreen instanceof GuiContainer
					&& !(Minecraft.getInstance().currentScreen instanceof GuiInventory)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInventoryOpen() {
		if (Minecraft.getInstance().currentScreen != null) {
			if (Minecraft.getInstance().currentScreen instanceof GuiContainer
					&& (Minecraft.getInstance().currentScreen instanceof GuiInventory
					|| Minecraft.getInstance().currentScreen instanceof GuiContainerCreative)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isChestOpen() {
		if (Minecraft.getInstance().player.openContainer != null) {
			if (Minecraft.getInstance().player.openContainer instanceof ContainerChest) {
				return true;
			}
		}
		return false;
	}

	public static String getMinecraftVersion() {
		return RealmsSharedConstants.VERSION_STRING;
	}

	public static int getMinecraftProtocolVersion() {
		return RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
	}

	public static boolean isMouseOver() {
		if (Minecraft.getInstance().objectMouseOver != null) {
			return true;
		}
		return false;
	}

	public static IEntity getHit() {
		if (!isMouseOver()) {
			return null;
		}
		return new IEntity(Minecraft.getInstance().objectMouseOver.entity);
	}

	public static boolean entityHitInstanceOf(EntityType type) {
		if (!isMouseOver()) {
			return false;
		}
		if (type.equals(EntityType.ENTITY_LIVING_BASE)) {
			return Minecraft.getInstance().objectMouseOver.entity instanceof EntityLivingBase;
		}
		return false;
	}

}
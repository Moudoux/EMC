package me.deftware.client.framework.Wrappers.Entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;

/**
 * EntityPlayerSP wrapper
 * 
 * @author deftware
 *
 */
public class IEntityPlayer {
	
	private static int ping = 0;

	public static float getRotationYaw() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.rotationYaw;
	}

	public static float getRotationPitch() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.rotationPitch;
	}

	public static double getPosX() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.posX;
	}

	public static double getPosY() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.posY;
	}

	public static double getPosZ() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.posZ;
	}

	public static boolean hasPotionEffects() {
		if (!Minecraft.getMinecraft().player.getActivePotionEffects().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isSingleplayer() {
		if (isNull()) {
			return true;
		}
		return Minecraft.getMinecraft().isSingleplayer();
	}

	public static String getDisplayX() {
		if (isNull()) {
			return "0";
		}
		return String.format("%.3f",
				new Object[] { Double.valueOf(Minecraft.getMinecraft().getRenderViewEntity().posX) });
	}

	public static String getDisplayY() {
		if (isNull()) {
			return "0";
		}
		return String.format("%.5f",
				new Object[] { Double.valueOf(Minecraft.getMinecraft().getRenderViewEntity().posY) });
	}

	public static String getDisplayZ() {
		if (isNull()) {
			return "0";
		}
		return String.format("%.3f",
				new Object[] { Double.valueOf(Minecraft.getMinecraft().getRenderViewEntity().posZ) });
	}

	public static int getPing() {
		if (isNull()) {
			return 0;
		}
		new Thread() {
			@Override
			public void run() {
				NetHandlerPlayClient nethandlerplayclient = Minecraft.getMinecraft().player.connection;
				List<NetworkPlayerInfo> list = GuiPlayerTabOverlay.ENTRY_ORDERING
						.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());

				for (NetworkPlayerInfo networkplayerinfo : list) {
					String uuid = networkplayerinfo.getGameProfile().getId().toString();
					if (uuid.equals(Minecraft.getMinecraft().player.getUniqueID().toString())) {
						ping = networkplayerinfo.getResponseTime();
					}
				}
			}
		}.start();
		return ping;
	}

	/**
	 * Which dimension the player is in (-1 = the Nether, 0 = normal world)
	 * 
	 * @return
	 */
	public static int getDimension() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.dimension;
	}

	public static boolean isRowingBoat() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isRowingBoat();
	}

	public static boolean isInLiquid() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isInWater() || Minecraft.getMinecraft().player.isInLava();
	}

	public static void setFlying(boolean state) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.isFlying = state;
	}
	
	public static boolean isFlying() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.capabilities.isFlying;
	}
	
	public static void setFlySpeed(float speed) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.setFlySpeed(speed);
	}
	
	public static float getFlySpeed() {
		if (isNull()) {
			return 0F;
		}
		return Minecraft.getMinecraft().player.capabilities.getFlySpeed();
	}
	
	public static void setWalkSpeed(float speed) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.setPlayerWalkSpeed(speed);
	}
	
	public static float getWalkSpeed() {
		if (isNull()) {
			return 0F;
		}
		return Minecraft.getMinecraft().player.capabilities.getWalkSpeed();
	}
	
	/**
	 * Is the Minecraft game even loaded ?
	 * 
	 * @return
	 */
	public static boolean isNull() {
		if (Minecraft.getMinecraft().player == null) {
			return true;
		}
		return false;
	}

}

package me.deftware.client.framework.Wrappers.Entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;

/**
 * EntityPlayerSP wrapper
 * 
 * @author deftware
 *
 */
public class IEntityPlayer {
	
	private static int ping = 0;

	public static void attackEntity(IEntity entity) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().player, entity.getEntity());
		swingArmClientSide();
	}

	public static boolean isCreative() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isCreative();
	}

	public static void respawnPlayer() {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.respawnPlayer();
	}

	public static void swingArmClientSide() {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
	}

	public static float getSaturationLevel() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.getFoodStats().getSaturationLevel();
	}

	public static void swingArmPacket() {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
	}

	public static float getCooldown() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.getCooledAttackStrength(0);
	}

	public static void setRotationYaw(float yaw) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.rotationYaw = yaw;
	}

	public static void setRotationPitch(float pitch) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.rotationPitch = pitch;
	}

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

	public static double getEyeHeight() {
		return Minecraft.getMinecraft().player.getEyeHeight();
	}

	public static int getItemInUseMaxCount() {
		return Minecraft.getMinecraft().player.getItemInUseMaxCount();
	}

	public static double getPrevPosX() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.prevPosX;
	}

	public static double getPrevPosY() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.prevPosY;
	}

	public static double getPrevPosZ() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.prevPosZ;
	}

	public static float getHealth() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.getHealth();
	}

	public static float getFallDistance() {
		if (isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.fallDistance;
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

	public static boolean isRidingHorse() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isRidingHorse();
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
	
	public static String getName() {
		if (isNull()) {
			return "";
		}
		return Minecraft.getMinecraft().player.getName();
	}

	public static boolean isOnGround() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.onGround;
	}

	public static boolean isOnLadder() {
		if (isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isOnLadder();
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

	public static boolean isHoldingItem(HandItem item) {
		if (isNull()) {
			return false;
		}
		if (item.equals(HandItem.ItemBow)) {
			return Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemBow
					|| Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() instanceof ItemBow;
		}
		return false;
	}

	public static enum HandItem {
		ItemBow
	}


}

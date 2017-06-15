package me.deftware.client.framework.Wrappers.Entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * EntityPlayerSP wrapper
 * 
 * @author deftware
 *
 */
public class IEntityPlayer {
	
	private static int ping = 0;

	private static boolean fakeRotation;
	private static float serverYaw;
	private static float serverPitch;

	public static boolean faceEntityPacket(IEntity entity) {
		Vec3d eyesPos = getEyesPos();
		Vec3d lookVec = getServerLookVec();

		AxisAlignedBB bb = entity.getEntity().boundingBox;
		if (faceVectorPacket(bb.getCenter()))
			return true;

		return bb.calculateIntercept(eyesPos, eyesPos.add(lookVec.scale(6))) != null;
	}

	public static float getAngleToClientRotation(IEntity entity) {
		float[] needed = getRotations(entity.getEntity().boundingBox.getCenter());
		float diffYaw = MathHelper.wrapDegrees(Minecraft.getMinecraft().player.rotationYaw) - needed[0];
		float diffPitch = MathHelper.wrapDegrees(Minecraft.getMinecraft().player.rotationPitch) - needed[1];
		float angle = MathHelper.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
		return angle;
	}

	public static float getAngleToServerRotation(IEntity entity) {
		float[] needed = getRotations(entity.getEntity().boundingBox.getCenter());
		float diffYaw = serverYaw - needed[0];
		float diffPitch = serverPitch - needed[1];
		float angle = MathHelper.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
		return angle;
	}

	private static float limitAngleChange(float current, float intended, float maxChange) {
		float change = MathHelper.wrapDegrees(intended - current);
		change = MathHelper.clamp(change, -maxChange, maxChange);
		return MathHelper.wrapDegrees(current + change);
	}

	private static float[] getRotations(Vec3d vec) {
		Vec3d eyesPos = getEyesPos();
		double diffX = vec.xCoord - eyesPos.xCoord;
		double diffY = vec.yCoord - eyesPos.yCoord;
		double diffZ = vec.zCoord - eyesPos.zCoord;
		double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));
		return new float[] { MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch) };
	}

	private static boolean faceVectorPacket(Vec3d vec) {
		fakeRotation = true;
		float[] rotations = getRotations(vec);
		serverYaw = limitAngleChange(serverYaw, rotations[0], 30);
		serverPitch = rotations[1];
		return Math.abs(serverYaw - rotations[0]) < 1F;
	}

	private static Vec3d getServerLookVec() {
		float f = MathHelper.cos(-serverYaw * 0.017453292F - (float) Math.PI);
		float f1 = MathHelper.sin(-serverYaw * 0.017453292F - (float) Math.PI);
		float f2 = -MathHelper.cos(-serverPitch * 0.017453292F);
		float f3 = MathHelper.sin(-serverPitch * 0.017453292F);
		return new Vec3d(f1 * f2, f3, f * f2);
	}

	private static Vec3d getEyesPos() {
		return new Vec3d(Minecraft.getMinecraft().player.posX,
				Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(),
				Minecraft.getMinecraft().player.posZ);
	}

	public static void attackEntity(IEntity entity) {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().player, entity.getEntity());
		swingArmClientSide();
	}

	public static void swingArmClientSide() {
		if (isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
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
	
	public static String getName() {
		if (isNull()) {
			return "";
		}
		return Minecraft.getMinecraft().player.getName();
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

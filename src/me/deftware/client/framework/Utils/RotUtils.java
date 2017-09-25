package me.deftware.client.framework.Utils;

import me.deftware.client.framework.Wrappers.Entity.IEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotUtils {

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

	public static boolean faceEntityClient(IEntity entity) {
		Vec3d eyesPos = getEyesPos();
		Vec3d lookVec = getServerLookVec();

		AxisAlignedBB bb = entity.getEntity().boundingBox;
		if (faceVectorClient(bb.getCenter()))
			return true;

		return bb.calculateIntercept(eyesPos, eyesPos.add(lookVec.scale(6))) != null;
	}

	public static boolean faceVectorClient(Vec3d vec) {
		float[] rotations = getRotations(vec);
		float oldYaw = Minecraft.getMinecraft().player.prevRotationYaw;
		float oldPitch = Minecraft.getMinecraft().player.prevRotationPitch;
		Minecraft.getMinecraft().player.rotationYaw = limitAngleChange(oldYaw, rotations[0], 30);
		Minecraft.getMinecraft().player.rotationPitch = rotations[1];
		return Math.abs(oldYaw - rotations[0]) + Math.abs(oldPitch - rotations[1]) < 1F;
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

}

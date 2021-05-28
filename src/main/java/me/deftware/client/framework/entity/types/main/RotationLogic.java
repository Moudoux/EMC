package me.deftware.client.framework.entity.types.main;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.types.EntityPlayer;
import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.math.vector.Vector3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

/**
 * @author Deftware
 */
public class RotationLogic extends EntityPlayer {

	private static boolean fakeRotation;
	private float serverYaw, serverPitch;

	public RotationLogic(PlayerEntity entity) {
		super(entity);
	}

	public boolean faceEntityClient(Entity entity) {
		Vector3d eyesPos = getEyesPos(), lookVec = getServerLookVec();
		BoundingBox bb = entity.getBoundingBox();
		if (faceVectorClient(bb.getCenter())) {
			return true;
		}
		return bb.rayTrace(eyesPos, eyesPos.add(lookVec.multiply(6))) != null;
	}

	public boolean faceEntityPacket(Entity entity) {
		Vector3d eyesPos = getEyesPos(), lookVec = getServerLookVec();
		BoundingBox bb = entity.getBoundingBox();
		if (faceVectorPacket(bb.getCenter())) {
			return true;
		}
		return bb.rayTrace(eyesPos, eyesPos.add(lookVec.multiply(6))) != null;
	}

	public boolean faceVectorClient(Vector3d vec) {
		float[] rotations = getRotations(vec);
		float oldYaw = entity.prevYaw;
		float oldPitch = entity.prevPitch;
		entity.setYaw(limitAngleChange(oldYaw, rotations[0], 30));
		entity.setPitch(rotations[1]);
		return Math.abs(oldYaw - rotations[0]) + Math.abs(oldPitch - rotations[1]) < 1F;
	}

	private boolean faceVectorPacket(Vector3d vec) {
		fakeRotation = true;
		float[] rotations = getRotations(vec);
		serverYaw = limitAngleChange(serverYaw, rotations[0], 30);
		serverPitch = rotations[1];
		return Math.abs(serverYaw - rotations[0]) < 1F;
	}

	private float[] getRotations(Vector3d vec) {
		Vector3d eyesPos = getEyesPos();
		double diffX = vec.getX() - eyesPos.getX();
		double diffY = vec.getY() - eyesPos.getY();
		double diffZ = vec.getZ() - eyesPos.getZ();
		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));
		return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch)};
	}

	public float getAngleToClientRotation(Entity entity) {
		float[] needed = getRotations(entity.getBoundingBox().getCenter());
		float diffYaw = MathHelper.wrapDegrees(this.entity.getYaw()) - needed[0];
		float diffPitch = MathHelper.wrapDegrees(this.entity.getPitch()) - needed[1];
		return MathHelper.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
	}

	public float getAngleToServerRotation(Entity entity) {
		float[] needed = getRotations(entity.getBoundingBox().getCenter());
		float diffYaw = serverYaw - needed[0];
		float diffPitch = serverPitch - needed[1];
		return MathHelper.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
	}

	private float limitAngleChange(float current, float intended, float maxChange) {
		float change = MathHelper.wrapDegrees(intended - current);
		change = MathHelper.clamp(change, -maxChange, maxChange);
		return MathHelper.wrapDegrees(current + change);
	}

	private Vector3d getServerLookVec() {
		float f = MathHelper.cos(-serverYaw * 0.017453292F - (float) Math.PI);
		float f1 = MathHelper.sin(-serverYaw * 0.017453292F - (float) Math.PI);
		float f2 = -MathHelper.cos(-serverPitch * 0.017453292F);
		float f3 = MathHelper.sin(-serverPitch * 0.017453292F);
		return new Vector3d(f1 * f2, f3, f * f2);
	}

}

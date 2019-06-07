package me.deftware.client.framework.utils;

import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.math.IVec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotUtils {

    private static boolean fakeRotation;
    private static float serverYaw;
    private static float serverPitch;

    public static boolean faceEntityPacket(IEntity entity) {
        Vec3d eyesPos = RotUtils.getEyesPos();
        Vec3d lookVec = RotUtils.getServerLookVec();

        Box bb = entity.getEntity().getBoundingBox();
        if (RotUtils.faceVectorPacket(bb.getCenter())) {
            return true;
        }

        // TODO: Might have to reverse this?
        return bb.rayTrace(eyesPos, eyesPos.add(lookVec.multiply(6))).isPresent();
    }

    public static boolean faceEntityClient(IEntity entity) {
        Vec3d eyesPos = RotUtils.getEyesPos();
        Vec3d lookVec = RotUtils.getServerLookVec();

        Box bb = entity.getEntity().getBoundingBox();
        if (RotUtils.faceVectorClient(bb.getCenter())) {
            return true;
        }

        return bb.rayTrace(eyesPos, eyesPos.add(lookVec.multiply(6))).isPresent();
    }

    public static boolean faceVectorClient(Vec3d vec) {
        float[] rotations = RotUtils.getRotations(vec);
        float oldYaw = MinecraftClient.getInstance().player.prevYaw;
        float oldPitch = MinecraftClient.getInstance().player.prevPitch;
        MinecraftClient.getInstance().player.yaw = RotUtils.limitAngleChange(oldYaw, rotations[0], 30);
        MinecraftClient.getInstance().player.pitch = rotations[1];
        return Math.abs(oldYaw - rotations[0]) + Math.abs(oldPitch - rotations[1]) < 1F;
    }

    public static float getAngleToClientRotation(IEntity entity) {
        float[] needed = RotUtils.getRotations(entity.getEntity().getBoundingBox().getCenter());
        float diffYaw = MathHelper.wrapDegrees(MinecraftClient.getInstance().player.yaw) - needed[0];
        float diffPitch = MathHelper.wrapDegrees(MinecraftClient.getInstance().player.pitch) - needed[1];
        float angle = MathHelper.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        return angle;
    }

    public static float getAngleToServerRotation(IEntity entity) {
        float[] needed = RotUtils.getRotations(entity.getEntity().getBoundingBox().getCenter());
        float diffYaw = RotUtils.serverYaw - needed[0];
        float diffPitch = RotUtils.serverPitch - needed[1];
        float angle = MathHelper.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        return angle;
    }

    private static float limitAngleChange(float current, float intended, float maxChange) {
        float change = MathHelper.wrapDegrees(intended - current);
        change = MathHelper.clamp(change, -maxChange, maxChange);
        return MathHelper.wrapDegrees(current + change);
    }

    private static float[] getRotations(Vec3d vec) {
        Vec3d eyesPos = RotUtils.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));
        return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch)};
    }

    private static boolean faceVectorPacket(Vec3d vec) {
        RotUtils.fakeRotation = true;
        float[] rotations = RotUtils.getRotations(vec);
        RotUtils.serverYaw = RotUtils.limitAngleChange(RotUtils.serverYaw, rotations[0], 30);
        RotUtils.serverPitch = rotations[1];
        return Math.abs(RotUtils.serverYaw - rotations[0]) < 1F;
    }

    private static Vec3d getServerLookVec() {
        float f = MathHelper.cos(-RotUtils.serverYaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-RotUtils.serverYaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-RotUtils.serverPitch * 0.017453292F);
        float f3 = MathHelper.sin(-RotUtils.serverPitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }

    private static Vec3d getEyesPos() {
        return new Vec3d(MinecraftClient.getInstance().player.x,
                MinecraftClient.getInstance().player.y + MinecraftClient.getInstance().player.getEyeHeight(EntityPose.STANDING),
                MinecraftClient.getInstance().player.z);
    }

    public static IVec3d getEyesPosIVec() {
        return new IVec3d(MinecraftClient.getInstance().player.x,
                MinecraftClient.getInstance().player.y + MinecraftClient.getInstance().player.getEyeHeight(EntityPose.STANDING),
                MinecraftClient.getInstance().player.z);
    }

}

package me.deftware.client.framework.wrappers.world;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class ICamera {

    public static Entity getRenderViewEntity() {
        return MinecraftClient.getInstance().getCameraEntity();
    }

    public static double getPosX() {
        return ICamera.getRenderViewEntity().getX();
    }

    public static void setPosX(double pos) {
        ICamera.getRenderViewEntity().setPos(pos, ICamera.getRenderViewEntity().getY(), ICamera.getRenderViewEntity().getZ());
    }

    public static double getPosY() {
        return ICamera.getRenderViewEntity().getY();
    }

    public static void setPosY(double pos) {
        ICamera.getRenderViewEntity().setPos(ICamera.getRenderViewEntity().getX(), pos, ICamera.getRenderViewEntity().getZ());
    }

    public static double getPosZ() {
        return ICamera.getRenderViewEntity().getZ();
    }

    public static void setPosZ(double pos) {
        ICamera.getRenderViewEntity().setPos(ICamera.getRenderViewEntity().getX(), ICamera.getRenderViewEntity().getY(), pos);
    }

    public static double getPrevPosX() {
        return ICamera.getRenderViewEntity().prevX;
    }

    public static double getPrevPosY() {
        return ICamera.getRenderViewEntity().prevY;
    }

    public static double getPrevPosZ() {
        return ICamera.getRenderViewEntity().prevZ;
    }

    public static double getDistance(double x, double y, double z) {
        return ICamera.getRenderViewEntity().squaredDistanceTo(x, y, z);
    }

}

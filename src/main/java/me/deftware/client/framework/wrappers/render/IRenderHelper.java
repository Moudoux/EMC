package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.util.math.Matrix4f;

public class IRenderHelper {

    public static void disableStandardItemLighting() {
        DiffuseLighting.disable();
    }

    public static void enableStandardItemLighting() {
        DiffuseLighting.enable();
    }

    public static void enableGUIStandardItemLighting() {
        DiffuseLighting.enableForLevel(new Matrix4f());
    }

}

package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.util.math.Matrix4f;

public class IRenderHelper {

    public static void disableStandardItemLighting() {
        GuiLighting.disable();
    }

    public static void enableStandardItemLighting() {
        GuiLighting.enable();
    }

    public static void enableGUIStandardItemLighting() {
        GuiLighting.enableForItems(new Matrix4f());
    }

}

package me.deftware.client.framework.wrappers.render;

import net.minecraft.client.render.GuiLighting;

public class IRenderHelper {

    public static void disableStandardItemLighting() {
        GuiLighting.disable();
    }

    public static void enableStandardItemLighting() {
        GuiLighting.enable();
    }

    public static void enableGUIStandardItemLighting() {
        GuiLighting.enableForItems();
    }

}

package me.deftware.client.framework.wrappers.render;

import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;

public class IRenderItem {

    private static ItemRenderer getRenderItem() {
        return MinecraftClient.getInstance().getItemRenderer();
    }

    public static void renderItemIntoGUI(IItemStack stack, int x, int y) {
        IRenderItem.getRenderItem().renderGuiItemIcon(stack.getStack(), x, y);
    }

    public static void renderItemOverlays(IItemStack stack, int x, int y) {
        IRenderItem.getRenderItem().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, stack.getStack(), x, y);
    }

    public static void renderItemAndEffectIntoGUI(IItemStack stack, int x, int y) {
        IRenderItem.getRenderItem().renderGuiItem(stack.getStack(), x, y);
    }

    public static void renderItemOverlayIntoGUI(IItemStack stack, int x, int y, String text) {
        IRenderItem.getRenderItem().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, stack.getStack(), x, y, text);
    }

    public static void setZLevel(float z) {
        IRenderItem.getRenderItem().zOffset = z;
    }

}

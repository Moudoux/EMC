package me.deftware.client.framework.wrappers.render;

import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;

public class IRenderItem {

	private static RenderItem getRenderItem() {
		return Minecraft.getMinecraft().getRenderItem();
	}

	public static void renderItemIntoGUI(IItemStack stack, int x, int y) {
		IRenderItem.getRenderItem().renderItemIntoGUI(stack.getStack(), x, y);
	}

	public static void renderItemOverlays(IItemStack stack, int x, int y) {
		IRenderItem.getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRenderer, stack.getStack(), x, y);
	}

	public static void renderItemAndEffectIntoGUI(IItemStack stack, int x, int y) {
		IRenderItem.getRenderItem().renderItemAndEffectIntoGUI(stack.getStack(), x, y);
	}

	public static void setZLevel(float z) {
		IRenderItem.getRenderItem().zLevel = z;
	}

}

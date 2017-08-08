package me.deftware.client.framework.Wrappers.Render;

import me.deftware.client.framework.Wrappers.Item.IItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;

public class IRenderItem {

	private static RenderItem getRenderItem() {
		return Minecraft.getMinecraft().getRenderItem();
	}

	public static void renderItemIntoGUI(IItemStack stack, int x, int y) {
		getRenderItem().renderItemIntoGUI(stack.getStack(), x, y);
	}

	public static void renderItemOverlays(IItemStack stack, int x, int y) {
		getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, stack.getStack(), x, y);
	}

	public static void renderItemAndEffectIntoGUI(IItemStack stack, int x, int y) {
		getRenderItem().renderItemAndEffectIntoGUI(stack.getStack(), x, y);
	}

	public static void setZLevel(float z) {
		getRenderItem().zLevel = z;
	}

}

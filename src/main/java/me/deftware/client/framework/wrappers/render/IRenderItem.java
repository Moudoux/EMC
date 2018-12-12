package me.deftware.client.framework.wrappers.render;

import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;

public class IRenderItem {

	private static ItemRenderer getRenderItem() {
		return Minecraft.getInstance().getItemRenderer();
	}

	public static void renderItemIntoGUI(IItemStack stack, int x, int y) {
		IRenderItem.getRenderItem().renderItemIntoGUI(stack.getStack(), x, y);
	}

	public static void renderItemOverlays(IItemStack stack, int x, int y) {
		IRenderItem.getRenderItem().renderItemOverlays(Minecraft.getInstance().fontRenderer, stack.getStack(), x, y);
	}

	public static void renderItemAndEffectIntoGUI(IItemStack stack, int x, int y) {
		IRenderItem.getRenderItem().renderItemAndEffectIntoGUI(stack.getStack(), x, y);
	}

	public static void setZLevel(float z) {
		IRenderItem.getRenderItem().zLevel = z;
	}

}

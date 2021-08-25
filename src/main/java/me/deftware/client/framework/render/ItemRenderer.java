package me.deftware.client.framework.render;

import me.deftware.client.framework.item.Item;
import me.deftware.client.framework.item.ItemStack;
import me.deftware.client.framework.world.block.Block;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class ItemRenderer {

	private static final HashMap<Block, ItemStack> blockToStack = new HashMap<>();
	private static final HashMap<Item, ItemStack> itemToStack = new HashMap<>();

	public static void drawBlock(int x, int y, Block block) {
		drawItemStack(x, y, blockToStack.computeIfAbsent(
				block, k -> new ItemStack(k, 1)
		));
	}

	public static void drawItem(int x, int y, Item item) {
		drawItemStack(x, y, itemToStack.computeIfAbsent(
				item, k -> new ItemStack(k, 1)
		));
	}

	public static void drawItemStack(int x, int y, ItemStack stack) {
		try {
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			stack.renderItemAndEffectIntoGUI(x, y);
			stack.renderItemOverlays(x, y);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

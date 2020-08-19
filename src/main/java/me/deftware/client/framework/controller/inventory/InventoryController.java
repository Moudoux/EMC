package me.deftware.client.framework.controller.inventory;

import me.deftware.client.framework.conversion.ConvertedList;
import me.deftware.client.framework.inventory.Slot;
import me.deftware.client.framework.item.ItemStack;
import me.deftware.client.framework.minecraft.Minecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

import java.util.List;
import java.util.Objects;

/**
 * @author Deftware
 */
public class InventoryController {

	private static final ConvertedList<Slot, net.minecraft.screen.slot.Slot> inventorySlots =
			new ConvertedList<>(() -> Objects.requireNonNull(MinecraftClient.getInstance().player).currentScreenHandler.slots, pair ->
					pair.getLeft().getMinecraftSlot() == Objects.requireNonNull(MinecraftClient.getInstance().player).currentScreenHandler.slots.get(pair.getRight())
					, Slot::new);

	public static void windowClick(int id, int next, WindowClickAction type) {
		windowClick(0, id, next, type);
	}

	public static void windowClick(int windowID, int id, int next, WindowClickAction type) {
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).clickSlot(windowID, id, next,
				type.getMinecraftActionType(), MinecraftClient.getInstance().player);
	}

	public static void swapStack(int one, int two, int windowId) {
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).clickSlot(windowId, one, 0, SlotActionType.SWAP,
				MinecraftClient.getInstance().player);
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).clickSlot(windowId, two, 0, SlotActionType.SWAP,
				MinecraftClient.getInstance().player);
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).tick();
	}

	public static void swapStack(int srcInventoryId, int dstInventoryId, int srcSlot, int dstSlot) {
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).clickSlot(srcInventoryId, srcSlot, 0,
				SlotActionType.SWAP, MinecraftClient.getInstance().player);
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).clickSlot(dstInventoryId, dstSlot, 0,
				SlotActionType.SWAP, MinecraftClient.getInstance().player);
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).tick();
	}

	public static void moveStack(int srcInventoryId, int dstInventoryId, int srcSlot, int dstSlot) {
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).clickSlot(srcInventoryId, srcSlot, 0,
				SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).clickSlot(dstInventoryId, dstSlot, 0,
				SlotActionType.QUICK_MOVE, MinecraftClient.getInstance().player);
	}

	public static void moveItem(int slotId) {
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).clickSlot(0, slotId, 0, SlotActionType.QUICK_MOVE,
				MinecraftClient.getInstance().player);
	}

	public static boolean placeStackInHotbar(ItemStack stack) {
		for (int i = 0; i < 9; i++) {
			if (Objects.requireNonNull(Minecraft.getPlayer()).getInventory().getStackInSlot(i).isEmpty()) {
				Objects.requireNonNull(MinecraftClient.getInstance().player).networkHandler
						.sendPacket(new CreativeInventoryActionC2SPacket(36 + i, stack.getMinecraftItemStack()));
				return true;
			}
		}
		return false;
	}

	public static List<Slot> getInventorySlots() {
		return inventorySlots.poll();
	}

}

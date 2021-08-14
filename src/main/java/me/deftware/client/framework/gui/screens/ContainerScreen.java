package me.deftware.client.framework.gui.screens;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.inventory.Inventory;
import me.deftware.client.framework.item.ItemStack;
import me.deftware.mixin.imp.IMixinShulkerBoxScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.ApiStatus;

/**
 * @author Deftware
 */
public interface ContainerScreen extends MinecraftScreen {

	Slot getMinecraftSlot();

	ScreenHandler getScreenHandler();

	Inventory getContainerInventory();

	ChatMessage getInventoryName();

	/*
		Hovered
	 */

	default int getSlotId() {
		return getMinecraftSlot().id;
	}

	default boolean isHovered() {
		return getMinecraftSlot() != null;
	}

	default int getHoveredIndex() {
		return getMinecraftSlot().getIndex();
	}

	default ItemStack getHoveredItemStack() {
		return new ItemStack(getMinecraftSlot().getStack());
	}

	/*
		Container
	 */

	default boolean isPlayerInventory() {
		return this instanceof AbstractInventoryScreen;
	}

	default int getContainerID() {
		return getScreenHandler().syncId;
	}

	default int getMaxSlots() {
		return getScreenHandler().slots.size();
	}

	@ApiStatus.Internal
	default net.minecraft.inventory.Inventory getHandlerInventory() {
		if (getScreenHandler() instanceof IMixinShulkerBoxScreenHandler screenHandler) {
			return screenHandler.getInventory();
		} else if (getScreenHandler() instanceof GenericContainerScreenHandler screenHandler) {
			return screenHandler.getInventory();
		}
		return null;
	}

	@Override
	default void close() {
		if (getScreenHandler() != null) {
			MinecraftClient.getInstance().player.closeHandledScreen();
			return;
		}
		MinecraftScreen.super.close();
	}

}

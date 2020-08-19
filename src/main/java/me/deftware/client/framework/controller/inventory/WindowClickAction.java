package me.deftware.client.framework.controller.inventory;

import net.minecraft.screen.slot.SlotActionType;

/**
 * @author Deftware
 */
public enum WindowClickAction {

	THROW(SlotActionType.THROW), QUICK_MOVE(SlotActionType.QUICK_MOVE), PICKUP(SlotActionType.PICKUP);

	private final SlotActionType actionType;

	WindowClickAction(SlotActionType actionType) {
		this.actionType = actionType;
	}

	public SlotActionType getMinecraftActionType() {
		return actionType;
	}

}

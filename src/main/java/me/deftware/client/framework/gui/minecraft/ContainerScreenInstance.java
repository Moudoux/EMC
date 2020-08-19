package me.deftware.client.framework.gui.minecraft;

import me.deftware.client.framework.inventory.Slot;
import me.deftware.mixin.imp.IMixinGuiContainer;
import net.minecraft.client.gui.screen.Screen;

/**
 * @author Deftware
 */
public class ContainerScreenInstance extends ScreenInstance {

	private Slot slot;

	public ContainerScreenInstance(Screen screen) {
		super(screen);
	}

	public Slot getHoveredSlot() {
		if (((IMixinGuiContainer) getMinecraftScreen()).getHoveredSlot() == null) return null;
		if (slot != null) {
			if (slot.getMinecraftSlot() == ((IMixinGuiContainer) getMinecraftScreen()).getHoveredSlot()) {
				return slot;
			}
		}
		return slot = new Slot(((IMixinGuiContainer) getMinecraftScreen()).getHoveredSlot());
	}

}

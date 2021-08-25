package me.deftware.client.framework.gui.widgets.properties;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.screens.MinecraftScreen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * @since 17.0.0
 * @author Deftware
 */
@ApiStatus.Internal
public interface Tooltipable {

	/**
	 * Sets a tooltip
	 *
	 * @param tooltip List of lines
	 */
	default void _setTooltip(List<TooltipComponent> list, ChatMessage... tooltip) {
		list.clear();
		list.addAll(MinecraftScreen.getTooltipList(tooltip));
	}

	default void _setTooltip(ChatMessage... tooltip) {
		this._setTooltip(this.getTooltipComponents(0, 0), tooltip);
	}

	@ApiStatus.Internal
	List<TooltipComponent> getTooltipComponents(int mouseX, int mouseY);

	boolean isMouseOverComponent(int mouseX, int mouseY);

}

package me.deftware.client.framework.gui.widgets.properties;

import me.deftware.client.framework.chat.ChatMessage;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * @since 17.0.0
 * @author Deftware
 */
public interface Tooltipable<T> {

	/**
	 * Sets a tooltip
	 *
	 * @param tooltip List of lines
	 */
	T _setTooltip(ChatMessage... tooltip);

	@ApiStatus.Internal
	List<TooltipComponent> _getTooltip();

}

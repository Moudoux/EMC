package me.deftware.client.framework.gui.widgets;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.widgets.properties.Nameable;
import me.deftware.client.framework.gui.widgets.properties.Tooltipable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.function.Function;

/**
 * @author Deftware
 */
@SuppressWarnings("ConstantConditions")
public interface Button extends Component, Nameable<Button>, Tooltipable<Button> {

	/**
	 * Creates a new button instance
	 *
	 * @param id The button ID
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param widthIn The width of the button
	 * @param heightIn The height of the button
	 * @param buttonText The label of the button
	 * @param shouldPlaySound If the button should player a sound when pressed
	 * @param onClick Button click handler
	 * @return A button component instance
	 */
	static Button create(int id, int x, int y, int widthIn, int heightIn, ChatMessage buttonText, boolean shouldPlaySound, Function<Integer, Boolean> onClick) {
		ClickableWidget widget = new ClickableWidget(x, y, widthIn, heightIn, buttonText.build()) {

			@Override
			public void appendNarrations(NarrationMessageBuilder builder) { }

			@Override
			public boolean mouseClicked(double mouseX, double mouseY, int button) {
				if (this.clicked(mouseX, mouseY)) {
					if (shouldPlaySound)
						this.playDownSound(MinecraftClient.getInstance().getSoundManager());
					return onClick.apply(button);
				}
				return false;
			}

		};
		return (Button) widget;
	}

}

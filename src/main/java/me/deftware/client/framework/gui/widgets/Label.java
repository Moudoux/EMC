package me.deftware.client.framework.gui.widgets;

import me.deftware.client.framework.chat.ChatMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 17.0.0
 * @author Deftware
 */
public class Label implements Drawable, GenericComponent {

	private List<Text> text;
	private int width, height, x, y;
	private final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

	public Label(int x, int y, ChatMessage... text) {
		this.setText(text);
		this.x = x;
		this.y = y;
	}

	public void setText(ChatMessage... text) {
		this.text = Arrays.stream(text)
				.map(ChatMessage::build)
				.collect(Collectors.toList());
		this.width = this.text.stream()
				.map(textRenderer::getWidth)
				.max(Comparator.naturalOrder())
				.orElseThrow(() -> new RuntimeException("Found no text, this shouldn't happen!"));
		this.height = text.length * textRenderer.fontHeight;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		int x = this.x, y = this.y;
		for (Text line : this.text) {
			int center = x + this.width / 2 - textRenderer.getWidth(line) / 2;
			textRenderer.drawWithShadow(matrices, line, center, y, 0xFFFFFF);
			y += textRenderer.fontHeight;
		}
	}

}

package me.deftware.client.framework.helper;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.mixin.imp.IMixinGuiEditSign;
import me.deftware.mixin.imp.IMixinSignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.text.LiteralText;

import java.util.Objects;

/**
 * @author Deftware
 */
public class SignHelper {

	public static boolean isEditing() {
		return MinecraftClient.getInstance().currentScreen != null && MinecraftClient.getInstance().currentScreen.getClass() == SignEditScreen.class;
	}

	public static int getCurrentLine() {
		return Objects.requireNonNull(((IMixinGuiEditSign) MinecraftClient.getInstance().currentScreen)).getEditLine();
	}

	public static ChatMessage getText(int line) {
		return new ChatMessage().fromText(((IMixinSignBlockEntity) Objects.requireNonNull(((IMixinGuiEditSign) MinecraftClient.getInstance().currentScreen)).getTileSign()).getTextRows()[line]);
	}

	public static void setText(String text, int line) {
		Objects.requireNonNull((IMixinGuiEditSign) MinecraftClient.getInstance().currentScreen).setTextOnLine(text, line);
		Objects.requireNonNull((IMixinGuiEditSign) MinecraftClient.getInstance().currentScreen).getTileSign().setTextOnRow(line, new LiteralText(text));
	}

	public static void save() {
		Objects.requireNonNull((IMixinGuiEditSign) MinecraftClient.getInstance().currentScreen).getTileSign().markDirty();
	}

}

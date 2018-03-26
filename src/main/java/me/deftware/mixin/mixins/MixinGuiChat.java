package me.deftware.mixin.mixins;

import me.deftware.mixin.components.InternalGuiTextField;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.TabCompleter;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiChat.class)
public class MixinGuiChat {

	@Shadow
	protected GuiTextField inputField;

	@Shadow
	private String defaultInputFieldText;

	@Shadow
	private TabCompleter tabCompleter;

	@Shadow
	private int sentHistoryCursor = -1;

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		sentHistoryCursor = Minecraft.getMinecraft().ingameGUI.getChatGUI().getSentMessages().size();
		inputField = new InternalGuiTextField(0, ((IMixinGuiScreen) this).getFontRenderer(), 4, ((GuiScreen) (Object) this).height - 12, ((GuiScreen) (Object) this).width - 4, 12);
		inputField.setMaxStringLength(256);
		inputField.setEnableBackgroundDrawing(false);
		inputField.setFocused(true);
		inputField.setText(defaultInputFieldText);
		inputField.setCanLoseFocus(false);
		tabCompleter = new GuiChat.ChatTabCompleter(inputField);
	}

}

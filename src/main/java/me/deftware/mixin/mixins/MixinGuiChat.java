package me.deftware.mixin.mixins;

import me.deftware.mixin.components.InternalGuiTextField;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiChat.class)
public abstract class MixinGuiChat extends GuiScreen {

	@Shadow
	protected GuiTextField inputField;

	@Shadow
	private String defaultInputFieldText;

	@Shadow
	private int sentHistoryCursor;

	@Shadow
	public abstract String func_195130_a(String p_195130_1_, int p_195130_2_);

	@Shadow
	public abstract void func_195128_a(int p_195128_1_, String p_195128_2_);

	@Shadow
	public abstract void func_195129_h();

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public void initGui() {
		Minecraft.getMinecraft().keyboardListener.enableRepeatEvents(true);
		this.sentHistoryCursor = Minecraft.getMinecraft().ingameGUI.getChatGUI().getSentMessages().size();
		this.inputField = new InternalGuiTextField(0, ((IMixinGuiScreen) this).getFontRenderer(), 4, ((GuiScreen) (Object) this).height - 12, ((GuiScreen) (Object) this).width - 4, 12);
		this.inputField.setMaxStringLength(256);
		this.inputField.setEnableBackgroundDrawing(false);
		this.inputField.setFocused(true);
		this.inputField.setText(this.defaultInputFieldText);
		this.inputField.setCanLoseFocus(false);
		this.inputField.func_195607_a(this::func_195130_a);
		this.inputField.func_195609_a(this::func_195128_a);
		this.field_195124_j.add(this.inputField);
		this.func_195129_h();
	}

}
package me.deftware.mixin.mixins;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.components.InternalGuiTextField;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.command.ISuggestionProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("ALL")
@Mixin(GuiChat.class)
public abstract class MixinGuiChat extends GuiScreen {

	@Shadow
	protected GuiTextField inputField;

	@Shadow
	private String defaultInputFieldText;

	@Shadow
	private int sentHistoryCursor;

	@Shadow
	public abstract String formatMessage(String p_195130_1_, int p_195130_2_);

	@Shadow
	public abstract void acceptMessage(int p_195128_1_, String p_195128_2_);

	@Shadow
	public abstract void updateUsageInfo();

	@Shadow
	private ParseResults<ISuggestionProvider> currentParse;

	@Shadow
	private CompletableFuture<Suggestions> pendingSuggestions;

	@Shadow
	public abstract void updateSuggestion();

	@Shadow
	private boolean field_212338_z;

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public void initGui() {
		Minecraft.getInstance().keyboardListener.enableRepeatEvents(true);
		this.sentHistoryCursor = Minecraft.getInstance().ingameGUI.getChatGUI().getSentMessages().size();
		this.inputField = new InternalGuiTextField(0, ((IMixinGuiScreen) this).getFontRenderer(), 4, ((GuiScreen) (Object) this).height - 12, ((GuiScreen) (Object) this).width - 4, 12);
		this.inputField.setMaxStringLength(256);
		this.inputField.setEnableBackgroundDrawing(false);
		this.inputField.setFocused(true);
		this.inputField.setText(this.defaultInputFieldText);
		this.inputField.setCanLoseFocus(false);
		this.inputField.setTextFormatter(this::formatMessage);
		this.inputField.setTextAcceptHandler(this::acceptMessage);
		this.children.add(this.inputField);
		this.updateSuggestion();
	}


	@Inject(method = "updateSuggestion", at = @At("RETURN"), cancellable = true)
	private void updateSuggestionInject(CallbackInfo ci) {
		String lvt_1_1_ = this.inputField.getText();
		StringReader lvt_2_1_ = new StringReader(lvt_1_1_);
		if (lvt_2_1_.canRead() && this.inputField.getText().startsWith((String) SettingsMap.getValue(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", "."))) {
			lvt_2_1_.skip();
			CommandDispatcher<ISuggestionProvider> lvt_3_1_ = CommandRegister.getDispatcher();
			this.currentParse = lvt_3_1_.parse(lvt_2_1_, this.mc.player.connection.getSuggestionProvider());
			if (!field_212338_z) {
				StringReader lvt_4_1_ = new StringReader(lvt_1_1_.substring(0, Math.min(lvt_1_1_.length(), this.inputField.getCursorPosition())));
				if (lvt_4_1_.canRead()) {
					lvt_4_1_.skip();
					ParseResults<ISuggestionProvider> lvt_5_1_ = lvt_3_1_.parse(lvt_4_1_, this.mc.player.connection.getSuggestionProvider());
					this.pendingSuggestions = lvt_3_1_.getCompletionSuggestions(lvt_5_1_);
					this.pendingSuggestions.thenRun(() -> {
						if (this.pendingSuggestions.isDone()) {
							this.updateUsageInfo();
						}
					});
				}
			}
		}
	}

}
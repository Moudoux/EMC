package me.deftware.mixin.mixins;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestions;
import me.deftware.client.framework.command.CommandRegister;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.command.ISuggestionProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
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
	public abstract String func_195130_a(String p_195130_1_, int p_195130_2_);

	@Shadow
	public abstract void func_195128_a(int p_195128_1_, String p_195128_2_);

	@Shadow
	public abstract void func_195133_i();

	@Shadow
	private ParseResults<ISuggestionProvider> field_195135_u;

	@Shadow
	private CompletableFuture<Suggestions> field_195137_v;

	@Final
	@Shadow
	private final List<String> field_195136_f = Lists.newArrayList();

	@Shadow
	private String field_195141_x = "";

	@Shadow
	public abstract void func_195129_h();


	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	private boolean func_208601_i() {
		// TODO: Replace . with command trigger
		return this.inputField.getText().startsWith("/") || this.inputField.getText().startsWith(".");
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	private static String func_208602_b(String p_208602_0_, String p_208602_1_) {
		// TODO: Replace . with command trigger
		if (p_208602_0_.startsWith(".")) {
			p_208602_1_ = "." + p_208602_1_.substring(1);
		}
		String output = p_208602_1_.startsWith(p_208602_0_) ? p_208602_1_.substring(p_208602_0_.length()) : null;
		return output;
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	private void func_208604_b(String p_208604_1_) {
		if (inputField.getText().startsWith(".") && p_208604_1_.startsWith("/")) {
			p_208604_1_ = "." + p_208604_1_.substring(1);
		}
		this.field_195141_x = p_208604_1_;
		this.inputField.setText(p_208604_1_);
	}

	@Inject(method = "func_195129_h", at = @At("RETURN"), cancellable = true)
	private void chatFunction(CallbackInfo ci) {
		// TODO: Replace . with command trigger
		if (this.inputField.getText().startsWith(".")) {
			CommandDispatcher<ISuggestionProvider> lvt_1_1_ = CommandRegister.getDispatcher();
			String lvt_2_1_ = this.inputField.getText().substring(1);
			this.field_195135_u = lvt_1_1_.parse(lvt_2_1_, this.mc.player.connection.func_195513_b());
			this.field_195137_v = lvt_1_1_.getCompletionSuggestions(this.field_195135_u);
			this.field_195137_v.thenRun(() -> {
				if (this.field_195137_v.isDone()) {
					this.func_195133_i();
				}
			});
		}
	}

}
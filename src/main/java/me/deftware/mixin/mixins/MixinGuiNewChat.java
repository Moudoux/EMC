package me.deftware.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.deftware.client.framework.event.events.EventChatReceive;
import me.deftware.mixin.imp.IMixinGuiNewChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.ITextComponent;

@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat implements IMixinGuiNewChat {

	@Shadow
	protected abstract void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter,
			boolean displayOnly);

	@Override
	public void setTheChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
		setChatLine(chatComponent, chatLineId, updateCounter, displayOnly);
	}

	@ModifyVariable(method = "printChatMessageWithOptionalDeletion", at = @At("HEAD"))
	public ITextComponent printChatMessageWithOptionalDeletion_modify(ITextComponent chatComponent) {
		EventChatReceive event = new EventChatReceive(chatComponent).send();
		return event.getItc();
	}

	@Inject(method = "printChatMessageWithOptionalDeletion", at = @At("HEAD"), cancellable = true)
	public void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId, CallbackInfo ci) {
		EventChatReceive event = new EventChatReceive(chatComponent).send();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

}

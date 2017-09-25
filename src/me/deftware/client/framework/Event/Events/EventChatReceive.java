package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Utils.Chat.ChatProcessor;
import net.minecraft.util.text.ITextComponent;

public class EventChatReceive extends Event {

	private ITextComponent itc;

	public EventChatReceive(ITextComponent itc) {
		this.itc = itc;
	}

	public ITextComponent getItc() {
		return itc;
	}

	public void setItc(ITextComponent itc) {
		this.itc = itc;
	}

	
	public void setColorCodes() {
		this.itc = ChatProcessor.getITextComponent(
				ChatProcessor.convertColorCodes(itc.getFormattedText()));
	}

	public String getMessage() {
		return itc.getUnformattedText();
	}

	public void replace(String original, String _new) {
		String message = itc.getFormattedText();
		itc = ChatProcessor.getITextComponent(message.replace(original, _new));
	}

}

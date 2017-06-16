package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Utils.Chat.ChatProcessor;
import net.minecraft.util.text.ITextComponent;

public class EventChatRecive extends Event {

	private ITextComponent itc;

	public EventChatRecive(ITextComponent itc) {
		this.itc = itc;
	}

	public ITextComponent getItc() {
		return itc;
	}

	public void setItc(ITextComponent itc) {
		this.itc = itc;
	}

	/**
	 * Converts &c to §c, etc
	 */
	public void setColorCodes() {
		this.itc = ChatProcessor.getITextComponent(
				ChatProcessor.convertColorCodes(itc.getFormattedText()));
	}

	public String getMessage() {
		return itc.getUnformattedText();
	}

}

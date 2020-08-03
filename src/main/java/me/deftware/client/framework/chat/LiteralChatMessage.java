package me.deftware.client.framework.chat;

/**
 * @author Deftware
 */
public class LiteralChatMessage extends ChatMessage {

	public LiteralChatMessage(String text) {
		sectionList.add(new ChatSection(text));
	}

}

package me.deftware.client.framework.chat.hud;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.deftware.client.framework.chat.ChatMessage;

/**
 * @author Deftware
 */
public @AllArgsConstructor @Data class HudLine {
	
	private ChatMessage message;
	private int lineId;
	
}

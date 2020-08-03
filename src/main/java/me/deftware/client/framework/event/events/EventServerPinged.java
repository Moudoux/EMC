package me.deftware.client.framework.event.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.Event;

import java.util.List;

/**
 * Triggered by Minecraft server list gui when server is being pinged.
 * This event includes the info about server like: MOTD, IP address, Servers' game varsion, Servers' population info and ping delay
 */
@EqualsAndHashCode(callSuper = true)
public @AllArgsConstructor @Data class EventServerPinged extends Event {

	private ChatMessage serverMOTD, playerList, gameVersion;
	private List<ChatMessage> populationInfo;
	private int version;
	private long pingToServer;

}

package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.IMinecraft;

/**
 * Triggered when user disconnects from a server.
 * Can return IP and Port of that server
 */
public class EventDisconnected extends Event {

	public String getIP() {
		return IMinecraft.lastServer.getIIP().contains(":") ? IMinecraft.lastServer.getIIP().split(":")[0]
				: IMinecraft.lastServer.getIIP();
	}


	public int getPort() {
		return IMinecraft.lastServer.getIIP().contains(":")
				? Integer.valueOf(IMinecraft.lastServer.getIIP().split(":")[1])
				: 25565;
	}

}

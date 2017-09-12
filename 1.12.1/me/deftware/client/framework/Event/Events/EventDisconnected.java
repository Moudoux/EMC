package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IMinecraft;

/**
 * When the player is disconnected from a server
 * 
 * @author deftware
 *
 */
public class EventDisconnected extends Event {

	/**
	 * The server ip the player was disconnected from
	 * 
	 * @return
	 */
	public String getIP() {
		return IMinecraft.lastServer.getIIP().contains(":") ? IMinecraft.lastServer.getIIP().split(":")[0]
				: IMinecraft.lastServer.getIIP();
	}

	/**
	 * The server port the player was disconnected from
	 * 
	 * @return
	 */
	public int getPort() {
		return IMinecraft.lastServer.getIIP().contains(":")
				? Integer.valueOf(IMinecraft.lastServer.getIIP().split(":")[1])
				: 25565;
	}

}

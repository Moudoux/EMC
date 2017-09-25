package me.deftware.client.framework.Event.Events;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Wrappers.IMinecraft;


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

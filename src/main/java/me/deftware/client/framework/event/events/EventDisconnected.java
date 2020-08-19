package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.minecraft.Minecraft;

import java.util.Objects;

/**
 * Triggered when user disconnects from a server.
 * Can return IP and Port of that server
 */
public class EventDisconnected extends Event {

	private final String address = Objects.requireNonNull(Minecraft.getLastConnectedServer()).getIPAccessor();

	public String getIP() {
		return address.contains(":") ? address.split(":")[0] : address;
	}

	public int getPort() {
		return address.contains(":") ? Integer.parseInt(address.split(":")[1]) : 25565;
	}

}

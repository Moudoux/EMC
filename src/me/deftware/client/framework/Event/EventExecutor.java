package me.deftware.client.framework.Event;

import me.deftware.client.framework.Client.EMCClient;
import me.deftware.client.framework.Event.Events.EventRender2D;
import me.deftware.client.framework.Main.FrameworkLoader;
import me.deftware.client.framework.Wrappers.IInventory;


public class EventExecutor {

	protected static Event postEvent(Event event) {
		if (event instanceof EventRender2D) {
			IInventory.onRender();
		}
		sendEvent(event);
		return event;
	}

	private static void sendEvent(Event event) {
		if (event == null) {
			return;
		}
		for (EMCClient client : FrameworkLoader.getClients().values()) {
			client.onEvent(event);
		}
	}

}

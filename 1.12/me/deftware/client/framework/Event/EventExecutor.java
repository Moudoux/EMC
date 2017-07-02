package me.deftware.client.framework.Event;

import me.deftware.client.framework.Client.EMCClient;
import me.deftware.client.framework.Main.FrameworkLoader;

/**
 * 
 * @author deftware
 *
 */
public class EventExecutor {

	protected static Event postEvent(Event event) {
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

package me.deftware.client.framework.Event;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.deftware.client.framework.Client.EMCClient;
import me.deftware.client.framework.Main.FrameworkLoader;

/**
 * 
 * @author deftware
 *
 */
public class EventExecutor {

	private static final ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
	private static HashMap<String, EventPool> pool = new HashMap<String, EventPool>();

	protected static Event postEvent(Event event) {
		if (event instanceof AsyncEvent) {
			final Event clone = event;
			new Thread() {
				@Override
				public void run() {
					sendEvent(clone);
					if (!pool.containsKey(clone.getClass().getName())) {
						pool.put(clone.getClass().getName(), new EventPool());
					}
					pool.get(clone.getClass().getName()).setEvent(clone);
				}
			}.start();
			if (pool.containsKey(event.getClass().getName())) {
				event = pool.get(event.getClass().getName()).getEvent();
			}
			return event;
		} else {
			sendEvent(event);
			return event;
		}
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

package me.deftware.client.framework.Event;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.Event.Events.EventClientCommand;
import me.deftware.client.framework.Main.FrameworkLoader;
import me.deftware.client.framework.Utils.Chat.ChatProcessor;

public abstract class Event {

	private boolean canceled = false;
	
	public Event() {}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
	public static void sendEvent(Event event) {
		try {
			if (event instanceof EventClientCommand) {
				if (((EventClientCommand) event).getCommand().equals(".version")) {
					ChatProcessor.printClientMessage("§7You are running " + FrameworkConstants.FRAMEWORK_NAME + " version " + FrameworkConstants.VERSION
							+ " built by " + FrameworkConstants.AUTHOR, false);
					return;
				}
			}
			if (FrameworkLoader.getClient() != null) {
				FrameworkLoader.getClient().onEvent(event);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}

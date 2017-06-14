package me.deftware.client.framework.Event;

import org.lwjgl.opengl.Display;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.Event.Events.EventClientCommand;
import me.deftware.client.framework.Main.FrameworkLoader;
import me.deftware.client.framework.Utils.Chat.ChatProcessor;
import me.deftware.client.framework.Wrappers.IMinecraft;

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
				} else if (((EventClientCommand) event).getCommand().equals(".unload")) {
					FrameworkLoader.ejectClient();
					Display.setTitle("Minecraft " + IMinecraft.getMinecraftVersion());
					ChatProcessor.printClientMessage("Unloaded client jar, Minecraft is now running as vanilla");
					return;
				} else if (((EventClientCommand) event).getCommand().equals(".reload")) {
					ChatProcessor.printClientMessage("Reloading client jar");
					FrameworkLoader.reload();
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

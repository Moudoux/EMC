package me.deftware.client.framework.Event;

import org.lwjgl.opengl.Display;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.Client.EMCClient;
import me.deftware.client.framework.Event.Events.EventClientCommand;
import me.deftware.client.framework.Event.Events.EventRender2D;
import me.deftware.client.framework.Event.Events.EventRender3D;
import me.deftware.client.framework.Main.FrameworkLoader;
import me.deftware.client.framework.Utils.Chat.ChatProcessor;
import me.deftware.client.framework.Wrappers.IMinecraft;

public abstract class Event {

	private boolean canceled = false;

	public Event() {
	}

	public void send() {
		Event.sendEvent(this);
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public static Event sendEvent(Event event) {
		try {
			if (event instanceof EventClientCommand) {
				if (((EventClientCommand) event).getCommand().equals(".version")) {
					ChatProcessor.printFrameworkMessage("§7You are running " + FrameworkConstants.FRAMEWORK_NAME
							+ " version " + FrameworkConstants.VERSION + " built by " + FrameworkConstants.AUTHOR);
					return event;
				} else if (((EventClientCommand) event).getCommand().equals(".unload")) {
					FrameworkLoader.ejectClients();
					Display.setTitle("Minecraft " + IMinecraft.getMinecraftVersion());
					IMinecraft.setGamma(0.5F);
					ChatProcessor.printFrameworkMessage("Unloaded mods, Minecraft is now running as vanilla");
					return event;
				} else if (((EventClientCommand) event).getCommand().equals(".cinfo")) {
					if (FrameworkLoader.modsInfo == null || FrameworkLoader.getClients().isEmpty()) {
						ChatProcessor.printFrameworkMessage("You are running vanilla Minecraft, no mods are loaded");
						return event;
					}
					ChatProcessor.printFrameworkMessage("== Loaded mods ==");
					for (EMCClient client : FrameworkLoader.getClients().values()) {
						String name = client.clientInfo.get("name").getAsString();
						int version = client.clientInfo.get("version").getAsInt();
						String author = client.clientInfo.get("author").getAsString();
						ChatProcessor.printFrameworkMessage(
								name + " version " + version + " made by " + author);
					}
					return event;
				}
			}
			long start = System.currentTimeMillis();
			if (FrameworkLoader.getClients() != null) {
				EventExecutor.postEvent(event);
				long delay = System.currentTimeMillis() - start;
				if (delay > 1 && !(event instanceof EventRender2D) && !(event instanceof EventRender3D)) {
					// Debugging
					// System.out.println(">> Execution Time: " + delay + " >
					// Type: " + event.getClass().getSimpleName());
				}
				return event;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return event;
	}

}

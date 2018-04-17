package me.deftware.client.framework.event;

import org.lwjgl.opengl.Display;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.event.events.EventClientCommand;
import me.deftware.client.framework.event.events.EventRender2D;
import me.deftware.client.framework.event.events.EventRender3D;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsSharedConstants;

public class Event {

	private boolean canceled = false;

	public boolean isCanceled() {
		return canceled;
	}

	public <T extends Event> T setCanceled(boolean canceled) {
		this.canceled = canceled;
		return (T) this;
	}

	public <T extends Event> T send() {
		try {
			if (this instanceof EventClientCommand) {
				if (((EventClientCommand) this).getCommand().equals(".version")) {
					ChatProcessor.printFrameworkMessage("You are running " + FrameworkConstants.FRAMEWORK_NAME
							+ " version " + FrameworkConstants.VERSION + " built by " + FrameworkConstants.AUTHOR);
					return (T) this;
				} else if (((EventClientCommand) this).getCommand().equals(".unload")) {
					if (((EventClientCommand) this).getArgs().isEmpty()) {
						// Unload all
						Bootstrap.ejectClients();
						Display.setTitle("Minecraft " + RealmsSharedConstants.VERSION_STRING);
						Minecraft.getMinecraft().gameSettings.gammaSetting = 0.5F;
						ChatProcessor.printFrameworkMessage("Unloaded mods, Minecraft is now running as vanilla");
					} else {
						if (Bootstrap.getClients().containsKey(((EventClientCommand) this).getArgs())) {
							Bootstrap.getClients().get(((EventClientCommand) this).getArgs()).onUnload();
							Bootstrap.getClients().remove(((EventClientCommand) this).getArgs());
							ChatProcessor.printFrameworkMessage("Unloaded " + ((EventClientCommand) this).getArgs());
						}
					}
					return (T) this;
				} else if (((EventClientCommand) this).getCommand().equals(".cinfo")) {
					if (Bootstrap.modsInfo == null || Bootstrap.getClients().isEmpty()) {
						ChatProcessor.printFrameworkMessage("You are running vanilla Minecraft, no mods are loaded");
						return (T) this;
					}
					ChatProcessor.printFrameworkMessage("== Loaded mods ==");
					for (EMCMod client : Bootstrap.getClients().values()) {
						String name = client.clientInfo.get("name").getAsString();
						int version = client.clientInfo.get("version").getAsInt();
						String author = client.clientInfo.get("author").getAsString();
						ChatProcessor.printFrameworkMessage(name + " version " + version + " made by " + author);
					}
					return (T) this;
				}
			}
			long start = System.currentTimeMillis();
			if (Bootstrap.getClients() != null) {
				Bootstrap.getClients().forEach((key, mod) -> {
					mod.onEvent(this);
				});
				long delay = System.currentTimeMillis() - start;
				if (delay > 1 && !(this instanceof EventRender2D) && !(this instanceof EventRender3D)) {
					// Debugging
					// System.out.println(">> Execution Time: " + delay + " >
					// Type: " + event.getClass().getSimpleName());
				}
				return (T) this;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (T) this;
	}

}

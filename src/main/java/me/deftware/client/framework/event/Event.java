package me.deftware.client.framework.event;


import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.event.events.EventClientCommand;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.client.Minecraft;

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

			// Internal EMC events

			if (this instanceof EventClientCommand) {
				EventClientCommand event = (EventClientCommand) this;
				switch (event.getCommand()) {
					case ".version":
						ChatProcessor.printFrameworkMessage("You are running " + FrameworkConstants.FRAMEWORK_NAME
								+ " version " + FrameworkConstants.VERSION + " built by " + FrameworkConstants.AUTHOR);
						return (T) this;
					case ".unload":
						// Unload EMC mods
						if (event.getArgs().isEmpty()) {
							// Unload all mods
							Bootstrap.ejectMods();
							Minecraft.getMinecraft().gameSettings.gammaSetting = 0.5F;
							ChatProcessor.printFrameworkMessage("Unloaded all EMC mods, Minecraft is now running as vanilla");
						} else {
							// Unload specific EMC mod
							if (Bootstrap.getMods().containsKey(event.getArgs())) {
								Bootstrap.getMods().get(event.getArgs()).onUnload();
								Bootstrap.getMods().remove(event.getArgs());
								ChatProcessor.printFrameworkMessage("Unloaded " + event.getArgs());
							} else {
								ChatProcessor.printFrameworkMessage("Could not find mod " + event.getArgs());
							}
						}
						return (T) this;
					case ".mods":
						if (Bootstrap.modsInfo == null || Bootstrap.getMods().isEmpty()) {
							ChatProcessor.printFrameworkMessage("No EMC mods are loaded");
						} else {
							ChatProcessor.printFrameworkMessage("== Loaded EMC mods ==");
							Bootstrap.getMods().values().forEach((mod) -> {
								String name = mod.modInfo.get("name").getAsString();
								int version = mod.modInfo.get("version").getAsInt();
								String author = mod.modInfo.get("author").getAsString();
								ChatProcessor.printFrameworkMessage(name + " version " + version + " made by " + author);
							});
						}
						return (T) this;
				}
			}

			// Send event to all mods
			Bootstrap.getMods().forEach((key, mod) -> mod.onEvent(this));

		} catch (Exception ex) {
			Bootstrap.logger.warn("Failed to send event {}", this, ex);
		}

		return (T) this;
	}

}

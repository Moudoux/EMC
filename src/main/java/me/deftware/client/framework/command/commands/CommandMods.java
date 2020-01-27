package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.utils.ChatProcessor;

public class CommandMods extends EMCModCommand {

	@Override
	public CommandBuilder<?> getCommandBuilder() {
		return new CommandBuilder<>().addCommand("mods", result -> {
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
		});
	}

}

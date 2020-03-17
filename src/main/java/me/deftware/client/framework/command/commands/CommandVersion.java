package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.IMinecraft;

public class CommandVersion extends EMCModCommand {

	@Override
	public CommandBuilder<?> getCommandBuilder() {
		return new CommandBuilder<>().addCommand("version", result -> {
			ChatProcessor.printFrameworkMessage("== EMC info ==");
			ChatProcessor.printFrameworkMessage("You are running EMC version " + FrameworkConstants.VERSION + "." + FrameworkConstants.PATCH + " built by " + FrameworkConstants.AUTHOR);
			ChatProcessor.printFrameworkMessage("Minecraft version " + IMinecraft.getMinecraftVersion() + " protocol " + IMinecraft.getMinecraftProtocolVersion());
			ChatProcessor.printFrameworkMessage("EMC mappings is " + FrameworkConstants.MAPPING_LOADER.name());
			ChatProcessor.printFrameworkMessage("EMC mapper is " + FrameworkConstants.MAPPING_SYSTEM.name());
		});
	}

}
